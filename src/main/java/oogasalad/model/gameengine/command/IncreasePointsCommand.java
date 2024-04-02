package oogasalad.model.gameengine.command;

import java.util.List;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.Player;

public class IncreasePointsCommand implements Command {

  private static final int PLAYER_ID = 0;
  private static final int SCORE_INCREASE = 1;
  private static final String SCORE_VARIABLE = "score";
  @Override
  public double execute(GameEngine engine, List<Double> arguments) {
    Player currentPlayer =
        engine.getPlayerContainer().getPlayer((int) Math.round(arguments.get(PLAYER_ID)));
    double currentScore = currentPlayer.getVariable(SCORE_VARIABLE);
    currentPlayer.setVariable(SCORE_VARIABLE, currentScore + arguments.get(SCORE_INCREASE));
    return currentPlayer.getVariable(SCORE_VARIABLE);
  }
}
