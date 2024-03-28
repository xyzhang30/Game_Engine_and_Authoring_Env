/**
 * @author Konur Nordberg
 */

package com.gameengine.factories;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Generic Factory interface for creating instances.
 *
 * @param <T> the type of object created by the factory
 */
public interface Factory<T> {

  T create(Object... args);
}

/**
 * Functional interface for handling collisions between two collidable objects.
 */
@FunctionalInterface
public interface CollisionHandler<T extends Collidable> extends BiConsumer<T, T> {
  // Inherits BiConsumer to accept two collidable objects and perform the collision logic
}

/**
 * Functional interface for modifying player state.
 */
@FunctionalInterface
public interface PlayerModifier<T extends Player> extends Consumer<T> {
  // Defines a single abstract method to accept a player and modify its state
}

package com.gameengine.factories;

import java.lang.reflect.Constructor;

/**
 * ReflectiveFactory uses reflection to instantiate objects dynamically.
 */
public class ReflectiveFactory<T> implements Factory<T> {

  private final Class<T> typeToken;

  public ReflectiveFactory(Class<T> typeToken) {
    this.typeToken = typeToken;
  }

  @Override
  public T create(Object... args) {
    try {
      Class<?>[] argClasses = new Class<?>[args.length];
      for (int i = 0; i < args.length; i++) {
        argClasses[i] = args[i].getClass();
      }
      Constructor<T> constructor = typeToken.getConstructor(argClasses);
      return constructor.newInstance(args);
    } catch (Exception e) {
      throw new RuntimeException("Could not instantiate object of type " + typeToken.getName(), e);
    }
  }
}

//LAMBDA FACTORY
package com.gameengine.factories;

/**
 * Provides static methods to create lambdas for game behaviors such as collision handling.
 */
public class LambdaFactory {

  /**
   * Creates a lambda for handling collisions between collidable objects.
   *
   * @param <T> the type of collidable objects
   * @return a CollisionHandler lambda
   */
  public static <T extends Collidable> CollisionHandler<T> createCollisionHandler() {
    return (collidable1, collidable2) -> {
      // Define collision behavior here
    };
  }

  /**
   * Creates a lambda for modifying the state of a player.
   *
   * @param <T> the type of player
   * @return a PlayerModifier lambda
   */
  public static <T extends Player> PlayerModifier<T> createPlayerModifier() {
    return (player) -> {
      // Define player modification behavior here
    };
  }
}
