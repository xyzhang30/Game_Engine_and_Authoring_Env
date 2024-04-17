package oogasalad.model.gameengine.gameobject;


import java.util.List;
import java.util.function.Supplier;

/**
 * The DefaultStrikeable class represents a default implementation of the DefaultStrikeable
 * interface.
 *
 * <p>This implementation allows for initial velocity to be applied to a GameObject through means
 * other than a collision. They also have the capability to be converted into a GameObject.
 *
 * <p>This class acts as an adapter between general GameObjects and GameObjects that are Strikeable.
 * By holding a reference to the Strikeable interface, GameObjects gain the ability to have an
 * initial velocity applied to them and be treated as Strikeable objects.
 *
 * @author Noah Loewy
 */

public class DefaultStrikeable implements Strikeable {

  private final GameObject gameObject;

  /**
   * Constructs a DefaultStrikeable object with the specified GameObject.
   *
   * @param go The GameObject to associate with this DefaultStrikeable object.
   */

  public DefaultStrikeable(GameObject go) {
    gameObject = go;
  }

  /**
   * Applies an initial velocity to the GameObject based on the specified magnitude and direction
   * The function calculates the velocity vector and then utilizes a Supplier functional interface
   * to encapsulate the calculation logic and provide the speed components to the GameObject's
   * calculateSpeeds method.
   *
   * @param magnitude The magnitude of the new velocity.
   * @param direction The direction of the new velocity with respect to the positive x-axis
   *                  (in radians).
   */

  @Override
  public void applyInitialVelocity(double magnitude, double direction) {
    Supplier<List<Double>> supplier = () -> {
      double speedX = magnitude * Math.cos(direction);
      double speedY = magnitude * Math.sin(direction);
      return List.of(speedX, speedY);
    };
    gameObject.calculateSpeeds(supplier);
  }

  /**
   * Converts the DefaultScoreable object into a GameObject.
   *
   * @return The GameObject representation of the DefaultScoreable object.
   */

  @Override
  public GameObject asGameObject() {
    return gameObject;
  }

}