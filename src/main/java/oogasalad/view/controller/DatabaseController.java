package oogasalad.view.controller;

import static oogasalad.view.Warning.showAlert;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.database.Database;
import oogasalad.model.database.GameScore;
import oogasalad.view.Warning;
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
    try {
      databaseView.assignPermissionToPlayers(gameName, playersWithAccess, "Player");
      databaseView.assignPermissionToPlayers(gameName, playersWithoutAccess, "None");
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Error Assigning Permissions",
          e.getMessage());
    }
  }

  public Map<String, Boolean> getPlayerPermissions(String gameName) {
    try {
      return databaseView.getPlayerPermissionsForGames(gameName);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Error Getting Permissions",
          e.getMessage());
      return Map.of();
    }
  }

  public String getGameAccessibility(String gameName) {
    try {
      return databaseView.getGameAccessibility(gameName);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Error Retreiving Accessibility Details."
              + " Assumed to be private.",
          e.getMessage());
      return "private";
    }

  }

  public void setPublicPrivate(String gameName, String accessibility) {
    try {
      databaseView.setGameAccessibility(gameName, accessibility);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to Update Accessibility.",
          e.getMessage());
    }

  }

  public boolean canUserLogin(String username) {
    try {
      return databaseView.doesUserExist(username);  // user exists, can log in
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "User assumed to not exist",
          e.getMessage());
      return false;
    }
  }

  public boolean loginUser(String username, String password)
      throws UserNotFoundException, IncorrectPasswordException {
    if (!canUserLogin(username)) {
      LOGGER.error("login failed - username does not exist");
      throw new UserNotFoundException("username does not exist.");
    } else {
      try {
        return databaseView.loginUser(username, password);
      }
      catch (SQLException e) {
        LOGGER.error("login failed - incorrect password");
        throw new IncorrectPasswordException("Password is incorrect.");
      }
    }
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
    try {
      boolean doesExist = databaseView.doesUserExist(username);
    if (!doesExist) {
      databaseView.registerUser(username, password, avatarUrl);  // add to database
      return true;  // new user created
    }
    } catch (SQLException e) {
      throw new CreatingDuplicateUserException("User creation failed: User already exists.");
    }
    return false;
  }

  /**
   * Updates the UI component directly with the top five formatted high scores for a specified
   * game.
   *
   * @param gameName The name of the game for which to update the leaderboard scores.
   */
  public void getFormattedScoresForLeaderboard(String gameName, boolean descending) {
    try {
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
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to show Leaderboard", e.getMessage());
      return;
    }
  }

  public List<String> getPlayerNames() {
    return currentPlayersManager;
  }

  public void leaderboardSet(ListView<String> scoresListView) {
    leaderboard.setLeaderboard(scoresListView);
  }


  public ObservableList<String> getManageableGames() {
    String host = currentPlayersManager.get(0);
    try {
      return databaseView.getManageableGames(host);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to get friends. Assumed owner of"
              + " no games", e.getMessage());
      return FXCollections.observableList(List.of());
    }
  }

  public ObservableList<String> getNewGameTitles() {
    String host = currentPlayersManager.get(0);
    int size = currentPlayersManager.size();
    try { return databaseView.getPlayableGameIds(host, size); }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "No Games Available",
          e.getMessage());
      return FXCollections.observableList(List.of());
    }
  }

  public void addGameResult(Map<Integer, String> playerMap, List<PlayerRecord> players,
      String gameName) {
    try {
      int id = databaseView.addGameInstance(gameName);
      databaseView.addGameScore(id, playerMap.get(players.get(0).playerId()),
          getScoreFromId(players, players.get(0).playerId()), true);
      for (int i = 1; i < players.size(); i++) {
        databaseView.addGameScore(id, playerMap.get(players.get(i).playerId()),
            getScoreFromId(players, players.get(i).playerId()), false);
      }
    }
    catch(SQLException e){
      showAlert(Alert.AlertType.ERROR, "Database Error", "Game Result Cannot be added to database",
          e.getMessage());
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
    try {
      databaseView.assignFriends(player, friends, notFriends);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to assign friends",
          e.getMessage());
    }
  }

  public Map<String, Boolean> getFriends(String player) {
    try {
      return databaseView.getFriends(player);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to get friends. Assumed you have"
              + " no friends",
          e.getMessage());
      return Map.of();
    }
  }
}
