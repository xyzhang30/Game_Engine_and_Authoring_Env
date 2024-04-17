package oogasalad.model.gameengine.condition;

import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.GameEngine;

/**
 * The AllPlayersCompletedRoundCondition evaluates if any player's score has exceeded a certain
 * number
 *
 * @author Noah Loewy
 */

public class ScoreThresholdCondition implements Condition {

  private final List<Double> arguments;

  /**
   * Constructs an instance of AllPlayersCompletedRoundCondition with a list of arguments.
   *
   * @param arguments, a list of one element, representing the score threshold required for the
   *                   condition to evaluate to true
   */

  @ExpectedParamNumber(1)
  public ScoreThresholdCondition(List<Double> arguments) {
    this.arguments = arguments;
  }

  /**
   * This method retrieves the threshold value from the provided arguments and checks each player's
   * score against this threshold. If any player's score exceeds the threshold, the method returns
   * true; otherwise, it returns false.
   *
   * @param engine The game engine instance.
   * @return true if any player's score exceeds the specified threshold, false otherwise.
   */

  @Override
  public boolean evaluate(GameEngine engine) {
    double scoreThresh = arguments.get(0);
    List<PlayerRecord> lst = engine.getPlayerContainer().getPlayerRecords();
    for (PlayerRecord player : lst) {
      if (player.score() > scoreThresh) {
        return true;
      }
    }
    return false;
  }

}
