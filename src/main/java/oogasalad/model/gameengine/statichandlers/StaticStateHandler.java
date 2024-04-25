package oogasalad.model.gameengine.statichandlers;

import java.util.Collection;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The StaticStateHandler abstract class defines a contract for classes that handle static game
 * states, such as game over conditions, round completion events, and turn completion events.
 *
 * <p>Concrete implementations of the static state interface must provide logic for handling a
 * staticState, evaluating whether they can handle the current game state and specify the actions to
 * be taken if they can handle it.
 *
 * @author Noah Loewy
 */
@IsCommand(isCommand = false)
public abstract class StaticStateHandler {

  protected static final Logger LOGGER = LogManager.getLogger(StaticStateHandler.class);

  private StaticStateHandler next;
  private StaticStateHandler prev;


  /**
   * Handles the specified game engine and rules record based on the current static state. If this
   * handler can't handle the state, it delegates the handling to the next handler in the chain.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   */

  public void handle(GameEngine engine, RulesRecord rules) {
    if (canHandle(engine, rules)) {
      handleIt(engine, rules);
    } else if (next != null) {
      next.handle(engine, rules);
    }
  }

  /**
   * Retrieves the next static state handler in the linked list chain.
   *
   * @return The next static state handler.
   */
  protected StaticStateHandler getNext() {
    return next;
  }

  /**
   * Sets the next static state handler in the linked list chain.
   *
   * @param h The next static state handler to set.
   */
  protected void setNext(StaticStateHandler h) {
    next = h;
  }

  /**
   * Retrieves the previous static state handler in the linked list chain.
   *
   * @return The previous static state handler.
   */
  protected StaticStateHandler getPrev() {
    return prev;
  }

  /**
   * Sets the previous static state handler in the linked list chain.
   *
   * @param h The previous static state handler to set.
   */
  protected void setPrev(StaticStateHandler h) {
    prev = h;
  }

  protected void executeCommands(Collection<Command> commands, GameEngine engine,
      RulesRecord rules) {
    commands.stream()
        .peek(cmd -> LOGGER.info(cmd.getClass().getSimpleName() + " (advance) "))
        .forEach(cmd -> cmd.execute(engine));
    if (getPrev().canHandle(engine, rules)) {
      getPrev().handleIt(engine, rules);
    }
  }

  /**
   * Determines whether this static state handler can handle the current game state, as specified by
   * the game rules.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   * @return True if this handler can handle the state, otherwise false.
   */
  protected abstract boolean canHandle(GameEngine engine, RulesRecord rules);

  /**
   * Performs the actions associated with handling the current game state, as specified by the game
   * rules.
   *
   * @param engine The game engine instance.
   * @param rules  The rules record containing game rules and conditions.
   */
  protected abstract void handleIt(GameEngine engine, RulesRecord rules);

}