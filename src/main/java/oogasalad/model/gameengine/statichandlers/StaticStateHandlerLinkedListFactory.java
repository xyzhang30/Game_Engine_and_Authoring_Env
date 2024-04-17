package oogasalad.model.gameengine.statichandlers;

import java.util.List;

public class StaticStateHandlerLinkedListFactory {

  public static GenericStaticStateHandler buildLinkedList(List<String> classNames) {
    GenericStaticStateHandler firstHandler = null;
    GenericStaticStateHandler prevHandler = null;
    for (String className : classNames) {
      GenericStaticStateHandler handler = StaticStateHandlerFactory.createHandler(className);
      handler.setPrev(prevHandler);
      if (prevHandler != null) {
        prevHandler.setNext(handler);
      }
      if (prevHandler == null) {
        firstHandler = handler;
      }
      prevHandler = handler;
    }
    return firstHandler;
  }
}
