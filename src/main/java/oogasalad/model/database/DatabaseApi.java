package oogasalad.model.Database;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseApi {

  List<GameScore> getPlayerHighScoresForGame(String gameName, String playerName, int n);

  List<GameScore> getGeneralHighScoresForGame(String gameName, int n);

  boolean loginUser(String username, String password);

  List<String> getPlayableGameIds(String playerName, int numPlayers);

  boolean registerUser(String username, String password, String avatarUrl);

  boolean registerGame(String gameName, String ownerName, int numPlayers, boolean publicOrPrivate);

  int addGameInstance(String game);

  boolean addGameScore(int gameInstanceId, String user, int score, boolean result);

  void assignPermissionToPlayers(String game, List<String> users, String permission);
}
