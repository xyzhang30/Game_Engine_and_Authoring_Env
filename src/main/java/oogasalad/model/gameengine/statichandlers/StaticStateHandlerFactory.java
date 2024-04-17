package oogasalad.model.gameengine.statichandlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.model.api.exception.InvalidCommandException;

public class StaticStateHandlerFactory {

  private static final String HANDLER_PATH = "oogasalad.model.gameengine.statichandlers.";

  public static StaticStateHandler createHandler(String className) {
    try {
      Class<?> clazz = Class.forName(HANDLER_PATH + className);
      Constructor<?> constructor = clazz.getConstructor();
      return (StaticStateHandler) constructor.newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException e) {
      throw new InvalidCommandException("");
    }
  }
}

