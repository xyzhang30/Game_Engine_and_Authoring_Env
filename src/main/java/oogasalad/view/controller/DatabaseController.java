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
  public DatabaseController(){
    this.leaderboard = new Leaderboard(this);
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
    ObservableList<String> formattedScores = scores.stream()
        .sorted((s1, s2) -> Integer.compare(s2.score(), s1.score()))  // Sort from high to low
        .map(score -> formatScoreForDisplay(score))  // Format each score for display
        .limit(5)  // Limit to only top 5 scores
        .collect(Collectors.toCollection(FXCollections::observableArrayList));  // Collect into an ObservableList

    // Assuming you have a ListView<String> member variable named `leaderboardListView`
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
