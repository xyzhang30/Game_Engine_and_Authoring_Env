package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.player.Player;

public abstract class AdjustPointsGivenPlayerCommand implements Command {

  private static final int SCORE_INCREASE = 0;
  private static final String SCORE_VARIABLE = "score";

  private final List<Double> arguments;

  @ExpectedParamNumber(1)
  public AdjustPointsGivenPlayerCommand(List<Double> arguments) {
    this.arguments = arguments;
  }

  public double adjust(GameEngine engine, int id) {
    Player currentPlayer = engine.getPlayerContainer().getPlayer(id);
    double currentScore = currentPlayer.getVariable(SCORE_VARIABLE);
    currentPlayer.setVariable(SCORE_VARIABLE, currentScore + arguments.get(SCORE_INCREASE));
    return currentPlayer.getVariable(SCORE_VARIABLE);
  }

  public abstract void execute(GameEngine engine);
}

//Backlog
//Currently Parameters to Commands required to be defined at Authoring Time
//Runtime is ideal
/**
 * (FIX): Directional Collisions ==> But what if other things
 */