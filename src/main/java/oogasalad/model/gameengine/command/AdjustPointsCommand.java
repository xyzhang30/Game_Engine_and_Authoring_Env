package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.Player;

public class AdjustPointsCommand extends AdjustPointsGivenPlayerCommand {

  private final List<Double> arguments;

  public AdjustPointsCommand(List<Double> arguments) {
    super(arguments);
    this.arguments = arguments;
  }

  @Override
  public double execute(GameEngine engine) {
    return adjust(engine, (int) Math.round(arguments.get(1)));
  }
}

//Backlog
//Currently Parameters to Commands required to be defined at Authoring Time
//Runtime is ideal
/**
 * (FIX): Directional Collisions ==> But what if other things
 */