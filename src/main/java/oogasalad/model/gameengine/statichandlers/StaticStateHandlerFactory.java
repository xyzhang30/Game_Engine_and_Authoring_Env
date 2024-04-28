package oogasalad.model.gameengine.statichandlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.api.exception.InvalidCommandException;

/**
 * A factory class for creating instances of StaticStateHandler subclasses. This factory uses
 * reflection to dynamically instantiate handler objects based on class names.
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = false)
public class StaticStateHandlerFactory {

  private static final String HANDLER_PATH = "oogasalad.model.gameengine.statichandlers.";

  /**
   * Creates a new instance of a StaticStateHandler subclass based on its class name.
   *
   * @param className The name of the handler class to instantiate.
   * @return A new instance of the specified handler class.
   * @throws InvalidCommandException If there is an error during the instantiation process.
   */

  public static StaticStateHandler createHandler(String className) {
    try {
      Class<?> clazz = Class.forName(HANDLER_PATH + className);
      Constructor<?> constructor = clazz.getConstructor();
      return (StaticStateHandler) constructor.newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException e) {
      throw new InvalidCommandException("Error creating handler: " + e.getMessage());
    }
  }
}
