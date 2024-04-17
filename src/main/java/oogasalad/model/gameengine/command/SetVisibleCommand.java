package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;

/**
 * The SetVisibleCommand class represents a command to set the visibility of a GameObject.
 *
 * @author Noah Loewy
 */

public class SetVisibleCommand implements Command {

  private final List<Double> arguments;

  /**
   * Constructs an instance of the MultiplySpeedCommand with the provided arguments.
   *
   * @param arguments Consists of two arguments: the ID of the GameObject whose visibility is to be
   *                  updated, and a boolean flag representing whether visibility should be set to
   *                  true or false.
   */

  @ExpectedParamNumber(2)
  public SetVisibleCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * Executes the command to set the visibility of a GameObject. It retrieves the GameObject
   * corresponding to the provided ID (argument 1) from the game engine and sets its visibility
   * based on the second argument.
   *
   * @param engine The game engine instance.
   */

  @Override
  public void execute(GameEngine engine) {
    int obj = (int) Math.round(arguments.get(0));
    int isVisible = (int) Math.round(arguments.get(1));
    engine.getGameObjectContainer().getGameObject(obj).setVisible(isVisible == 1.0);
  }
}

