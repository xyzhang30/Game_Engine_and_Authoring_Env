package oogasalad.view.controller;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oogasalad.model.database.Database;
import oogasalad.model.database.GameScore;
import oogasalad.view.database.CurrentPlayersManager;


public class DatabaseController {
  private Database databaseView;
  private CurrentPlayersManager currentPlayersManager;
  public DatabaseController(CurrentPlayersManager currentPlayersManager){
    this.databaseView = new Database();
    this.currentPlayersManager = currentPlayersManager;
  }

  public boolean canUserLogin(String username) {
    // if false then throw this exception throw new Exception("Login failed: User does not exist.");
    System.out.println(databaseView.doesUserExist(username));
    return databaseView.doesUserExist(username);  // user exists, can log in
  }

  public boolean loginUser(String username, String password){
    System.out.println(databaseView.loginUser(username, password));
    return databaseView.loginUser(username, password);
  }

  public boolean canCreateUser(String username, String password, String avatarUrl) throws Exception {
    if (!databaseView.doesUserExist(username)) {
      databaseView.registerUser(username, password, avatarUrl);  // add to database
      return true;  // new user created
    } else {
      throw new Exception("User creation failed: User already exists.");
    }
  }

  /**
   * Retrieves formatted score strings for a specified game, suitable for leaderboard display.
   * @param gameName The game name for which formatted scores are needed.
   * @return ObservableList of formatted score strings.
   */
  public ObservableList<String> getFormattedScoresForLeaderboard(String gameName) {
    List<GameScore> scores = databaseView.getGeneralHighScoresForGame(gameName, Integer.MAX_VALUE);
    return scores.stream()
        .sorted((s1, s2) -> Integer.compare(s2.score(), s1.score()))  //from high to low
        .map(score -> formatScoreForDisplay(score))
        .limit(5)  // only top 5 scores
        .collect(Collectors.toCollection(FXCollections::observableArrayList));
  }

  /**
   * Formats a single GameScore into a string representation.
   * @param score The GameScore to format.
   * @return Formatted string representing the score.
   */
  private String formatScoreForDisplay(GameScore score) {
    return score.playerName() + ": " + score.score();
  }


  public ObservableList<String> getManageableGames() {
    String host = currentPlayersManager.getHost();
    return databaseView.getManageableGames(host);
  }

  public ObservableList<String> getNewGameTitles() {
    String host = currentPlayersManager.getHost();
    int size = currentPlayersManager.getPartySize();
    return databaseView.getPlayableGameIds(host, size);
  }

}
