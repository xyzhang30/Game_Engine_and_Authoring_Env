package oogasalad.model.database;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseApi {

  List<GameScore> getPlayerHighScoresForGame(String gameName, String playerName, int n);

  // Method to retrieve general high scores for a specific game
  List<GameScore> getGeneralHighScoresForGame(String gameName, int n);

  // Method to verify user login credentials
  boolean loginUser(String username, String password);

  List<String> getPlayableGameIds(String playerName, int numPlayers);

  boolean registerUser(String username, String password, String avatarUrl);

  boolean registerGame(String gameName, String ownerName, int numPlayers, boolean publicOrPrivate);

  int addGameInstance(String game);

  boolean addGameScore(int gameInstanceId, String user, int score, boolean result);

  void assignPermissionToPlayers(String game, List<String> users, String permission)
      throws SQLException;
}
