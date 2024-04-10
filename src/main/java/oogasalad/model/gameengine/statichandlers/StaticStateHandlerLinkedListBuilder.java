package oogasalad.model.gameengine.statichandlers;

import java.util.List;

public class StaticStateHandlerLinkedListBuilder {

  public GenericStaticStateHandler buildLinkedList(List<String> classNames) throws Exception {
    StaticStateHandlerFactory factory = new StaticStateHandlerFactory();
    GenericStaticStateHandler firstHandler = null;
    GenericStaticStateHandler prevHandler = null;
    for (String className : classNames) {
      GenericStaticStateHandler handler = factory.createHandler(className);
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
