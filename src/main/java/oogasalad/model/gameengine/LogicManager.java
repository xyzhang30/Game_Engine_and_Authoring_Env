package oogasalad.model.gameengine;

/**
 * Class for managing game logic.
 */
public class LogicManager {

  private final int stage;
  private final int turn;
  private final int subturn;

  public LogicManager() {
    stage = 0;
    turn = 0;
    subturn = 0;
  }
  //QUESTION:
  //does this also need to handle the resetting of everything?
  //some sort of Turn Policy / user-defined lambdas created

  /**
   * Updates the status of the game based on handlers defined by users.
   */
  public void advance() {

  }

  /**
   * Retrieves the current turn number.
   *
   * @return The current turn number.
   */
  public int getTurn() {
    return 0;
  }

  /**
   * Retrieves the current sub-turn number.
   *
   * @return The current sub-turn number.
   */
  public int getSubTurn() {
    return 0;
  }

  /**
   * Retrieves the current stage of the game.
   *
   * @return The current stage of the game.
   */
  public int getStage() {
    return 0;
  }

}