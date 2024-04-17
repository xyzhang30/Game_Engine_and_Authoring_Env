package oogasalad.model.gameengine.statichandlers;

import java.util.List;

public class StaticStateHandlerLinkedListFactory {

  public static StaticStateHandler buildLinkedList(List<String> classNames) {
    StaticStateHandler firstHandler = null;
    StaticStateHandler prevHandler = null;
    for (String className : classNames) {
      StaticStateHandler handler = StaticStateHandlerFactory.createHandler(className);
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
