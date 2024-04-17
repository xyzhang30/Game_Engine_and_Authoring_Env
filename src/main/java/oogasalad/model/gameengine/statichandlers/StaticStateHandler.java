package oogasalad.model.gameengine.statichandlers;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The StaticStateHandler abstract class defines a contract for classes that handle static
 * game states, such as game over conditions or round completion events.
 *
 * <p>Concrete implementations of the static state interface must provide logic for handling a
 * staticState,
 * evaluating whether they can handle the current
 * game state and specify the actions to be taken if they can handle it.
 *
 * <p>
 */
public abstract class StaticStateHandler {

  protected static final Logger LOGGER = LogManager.getLogger(StaticStateHandler.class);

  private StaticStateHandler next;
  private StaticStateHandler prev;

  public void handle(GameEngine engine, RulesRecord rules) {
    if (canHandle(engine, rules)) {
      handleIt(engine, rules);
    } else if (next != null) {
      next.handle(engine, rules);
    }
  }

  protected StaticStateHandler getNext() {
    return next;
  }

  protected void setNext(StaticStateHandler h) {
    next = h;
  }

  protected StaticStateHandler getPrev() {
    return prev;
  }

  protected void setPrev(StaticStateHandler h) {
    prev = h;
  }

  protected abstract boolean canHandle(GameEngine engine, RulesRecord rules);

  protected abstract void handleIt(GameEngine engine, RulesRecord rules);




}
