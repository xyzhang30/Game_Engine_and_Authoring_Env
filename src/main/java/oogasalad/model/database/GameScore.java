package oogasalad.model.database;

public class GameScore {
  private int playerId;
  private int gameId;
  private int score;
  private String gameResult;

  public GameScore(int playerId, int gameId, int score, String gameResult) {
    this.playerId = playerId;
    this.gameId = gameId;
    this.score = score;
    this.gameResult = gameResult;
  }

  // Getters for accessing the properties
  public int getPlayerId() {
    return playerId;
  }

  public int getGameId() {
    return gameId;
  }

  public int getScore() {
    return score;
  }

  public String getGameResult() {
    return gameResult;
  }
}
