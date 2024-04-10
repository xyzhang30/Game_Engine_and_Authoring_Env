package oogasalad.model.gameengine.statichandlers;

import java.lang.reflect.Constructor;
import java.util.List;

public class StaticStateHandlerFactory {
  public GenericStaticStateHandler createHandler(String className) throws Exception {
    Class<?> clazz = Class.forName(className);
    Constructor<?> constructor = clazz.getConstructor();
    return (GenericStaticStateHandler) constructor.newInstance();
  }
}

