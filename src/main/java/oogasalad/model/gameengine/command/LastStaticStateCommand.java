package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

/**
 * The PlayerCompletedTurnCommand class represents a command to reset the properties of the game
 * to its properties from the last static state.
 *
 * @author Noah Loewy
 */

public class LastStaticStateCommand implements Command {

  /**
   * Constructs an instance of the LastStaticStateCommand. This constructor does not
   * actually do anything, and exists for the sake of consistency across commands.
   *
   * @param arguments An empty list.
   */

  @ExpectedParamNumber(0)
  public LastStaticStateCommand(List<Double> arguments) {

  }

  /**
   * Delegates the work to the engine lastStaticState function, which handles the logic for
   * actually updating the game state entities (players, gameObjects, etc) to their values from
   * the previous static state.
   *
   * @param engine the GameEngine instance
   */

  @Override
  public void execute(GameEngine engine) {
    engine.toLastStaticState();
  }


}
