package oogasalad.model.gameengine.statichandlers;

import java.util.List;

/**
 * Factory class for building a linked list of static state handlers based on a list of class
 * names.
 *
 * @author Noah Loewy
 */

public class StaticStateHandlerLinkedListFactory {

  /**
   * Builds a linked list of static state handlers based on a list of class names.
   *
   * @param classNames The list of class names representing static state handler implementations.
   * @return The first static state handler in the linked list.
   */


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
