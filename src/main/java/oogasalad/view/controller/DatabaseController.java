package oogasalad.view.controller;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oogasalad.model.database.Database;
import oogasalad.model.database.GameScore;
import oogasalad.view.database.CurrentPlayersManager;
import oogasalad.view.database.Leaderboard;


public class DatabaseController {
  private CurrentPlayersManager currentPlayersManager;
  private Leaderboard leaderboard;
  private Database databaseView;
  public DatabaseController(Leaderboard leaderboard){
    this.leaderboard = leaderboard;
    this.databaseView = new Database();
    currentPlayersManager = new CurrentPlayersManager();
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
   * Updates the UI component directly with the top five formatted high scores for a specified game.
   *
   * @param gameName The name of the game for which to update the leaderboard scores.
   */
  public void getFormattedScoresForLeaderboard(String gameName) {
    List<GameScore> scores = databaseView.getGeneralHighScoresForGame(gameName, Integer.MAX_VALUE);
//    ObservableList<String> formattedScores = scores.stream()
//        .map(score -> formatScoreForDisplay(score))
//        .limit(5)
//        .collect(Collectors.toCollection(FXCollections::observableArrayList));
    ObservableList<String> formattedScores = FXCollections.observableArrayList(
        "Alpha Centauri",
        "Beta Carotene",
        "Gamma Rays",
        "Delta Force",
        "Epsilon Minus"
    );
    System.out.println("scores: " + formattedScores);
    leaderboard.saveGameScores(formattedScores);
  }

  /**
   * Formats a single GameScore into a string representation.
   * @param score The GameScore to format.
   * @return Formatted string representing the score.
   */
  private String formatScoreForDisplay(GameScore score) {
    return score.playerName() + ": " + score.score();
  }


}
