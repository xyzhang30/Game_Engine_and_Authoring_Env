package oogasalad.view.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.database.Database;
import oogasalad.model.database.GameScore;
import oogasalad.view.api.exception.CreatingDuplicateUserException;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.database.Leaderboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DatabaseController {

  private static final Logger LOGGER = LogManager.getLogger(DatabaseController.class);
  public Database databaseView;
  private final List<String> currentPlayersManager;
  private final Leaderboard leaderboard;

  public DatabaseController(Leaderboard leaderboard, List<String> currentPlayersManager) {
    this.databaseView = new Database();
    this.leaderboard = leaderboard;
    this.currentPlayersManager = currentPlayersManager;
  }

  public void writePlayerPermissions(String gameName, List<String> playersWithAccess,
      List<String> playersWithoutAccess) {
    databaseView.assignPermissionToPlayers(gameName, playersWithAccess, "Player");
    databaseView.assignPermissionToPlayers(gameName, playersWithoutAccess, "None");
  }

  public Map<String, Boolean> getPlayerPermissions(String gameName) {
    return databaseView.getPlayerPermissionsForGames(gameName);
  }

  public String getGameAccessibility(String gameName) {
    return databaseView.getGameAccessibility(gameName);
  }

  public void setPublicPrivate(String gameName, String accessibility) {
    databaseView.setGameAccessibility(gameName, accessibility);
  }


  public boolean canUserLogin(String username) {
    // if false then throw this exception throw new Exception("Login failed: User does not exist.");
    return databaseView.doesUserExist(username);  // user exists, can log in
  }

  public boolean loginUser(String username, String password)
      throws UserNotFoundException, IncorrectPasswordException {
    if (!canUserLogin(username)) {
      LOGGER.error("login failed - username does not exist");
      throw new UserNotFoundException("username does not exist.");
    } else if (!databaseView.loginUser(username, password)) {
      LOGGER.error("login failed - incorrect password");
      throw new IncorrectPasswordException("Password is incorrect.");
    }
    return true;
  }

  /**
   * Formats a single GameScore into a string representation.
   *
   * @param score The GameScore to format.
   * @return Formatted string representing the score.
   * @author Doga
   */
  private String formatScoreForDisplay(GameScore score) {
    return score.playerName() + ": " + score.score();
  }


  public boolean canCreateUser(String username, String password, String avatarUrl)
      throws CreatingDuplicateUserException {
    if (!databaseView.doesUserExist(username)) {
      databaseView.registerUser(username, password, avatarUrl);  // add to database
      return true;  // new user created
    } else {
      LOGGER.error("User creation failed: User already exists");
      throw new CreatingDuplicateUserException("User creation failed: User already exists.");
    }
  }

  /**
   * Updates the UI component directly with the top five formatted high scores for a specified
   * game.
   *
   * @param gameName The name of the game for which to update the leaderboard scores.
   */
  public void getFormattedScoresForLeaderboard(String gameName, boolean descending) {
    List<GameScore> scores = databaseView.getGeneralHighScoresForGame(gameName, descending);
    ObservableList<String> formattedScores = scores.stream()
        .sorted(Comparator.comparing(GameScore::score))
        .map(this::formatScoreForDisplay)
        .collect(Collectors.toCollection(FXCollections::observableArrayList));
    if (descending) {
      formattedScores.sort(Comparator.reverseOrder());
    }
    leaderboard.saveGameScores(formattedScores);
  }

  public List<String> getPlayerNames() {
    return currentPlayersManager;
  }

  public void leaderboardSet(ListView<String> scoresListView) {
    leaderboard.setLeaderboard(scoresListView);
  }


  public ObservableList<String> getManageableGames() {
    String host = currentPlayersManager.get(0);
    return databaseView.getManageableGames(host);
  }

  public ObservableList<String> getNewGameTitles() {
    String host = currentPlayersManager.get(0);
    int size = currentPlayersManager.size();
    return databaseView.getPlayableGameIds(host, size);
  }

  public void addGameResult(Map<Integer, String> playerMap, List<PlayerRecord> players,
      String gameName) {
    int id = databaseView.addGameInstance(gameName);
    databaseView.addGameScore(id, playerMap.get(players.get(0).playerId()),
        getScoreFromId(players, players.get(0).playerId()), true);
    for (int i = 1; i < players.size(); i++) {
      databaseView.addGameScore(id, playerMap.get(players.get(i).playerId()),
          getScoreFromId(players, players.get(i).playerId()), false);
    }
  }

  public int getScoreFromId(List<PlayerRecord> players, int id) {
    for (PlayerRecord p : players) {
      if (id == p.playerId()) {
        return (int) p.score();
      }
    }
    return 0;
  }

  public void writeFriends(String player, List<String> friends, List<String> notFriends) {
    databaseView.assignFriends(player, friends, notFriends);
  }

  public Map<String, Boolean> getFriends(String player) {
    return databaseView.getFriends(player);
  }
}
