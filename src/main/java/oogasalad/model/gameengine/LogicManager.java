package oogasalad.model.gameengine;

/**
 * Class for managing game logic.
 */
public class LogicManager {

  private int stage;
  private int turn;
  private int subturn;
  private int round;
  private final RulesRecord rules;

  public LogicManager(RulesRecord rules) {
    turn = 0;
    subturn = 0;
    round = 0;
    this.rules = rules;
  }
  //QUESTION:
  //does this also need to handle the resetting of everything?
  //some sort of Turn Policy / user-defined lambdas created
 // some sort of map from conditions to commands
  /**
   * Updates the status of the game based on handlers defined by users.
   */
  public void advance() {
    if(rules.endGameCondition()) {
      endGame;
    }
    if(rules.endRoundCondition()) {
      endRound;
    }
    else if(rules.endTurnCondition()) {
      endTurn;
    }
    else if(rules.endSubTurnCondition()) {
      endSubturn;
    }
  }

  private void advanceStage() {

  }

  /**
   * Retrieves t

   * he current turn number.
   *
   * @return The current turn number.
   */
  public int getTurn() {
    return turn;
  }

  /**
   * Retrieves the current sub-turn number.
   *
   * @return The current sub-turn number.
   */
  public int getSubTurn() {
    return subturn;
  }

  /**
   * Retrieves the current stage of the game.
   *
   * @return The current stage of the game.
   */

  public int getRound() {
    return round;
  }

}