package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameengine.player.Player;

public class DataAccessObject {

  // Method to retrieve a Player object by its ID
  public Player getPlayer(int id) {
    // SQL query to select player details
    String query = "SELECT player_id, username, avatar_url, language, age FROM Players WHERE player_id = ?";

    // Try-with-resources statement to ensure the closing of resources
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

      // Set the parameter for the prepared statement
      pstmt.setInt(1, id);

      // Execute the query and get the result set
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          // Extract data from the result set
          int playerId = rs.getInt("player_id");
          String username = rs.getString("username");
          String avatarUrl = rs.getString("avatar_url");
          String language = rs.getString("language");
          int age = rs.getInt("age");

          // Create and return a Player object with retrieved data
          Player player = new Player(playerId);
          //player.setUsername(username);  //TODO - create method in Player
          //player.setAvatarUrl(avatarUrl); //TODO - create method in Player
          //player.setLanguage(language); //TODO - create method in Player
         // player.setAge(age); //TODO - create method in Player

          // Retrieve and set other properties as needed
          // For example, retrieving associated scoreables, strikeables, etc.

          return player;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null; // Return null if player not found or in case of an error
  }

  public List<GameScore> getPlayerHighScores(int playerId) {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT game_id, score, game_result FROM GameScores WHERE player_id = ? ORDER BY score DESC";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setInt(1, playerId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int gameId = rs.getInt("game_id");
        int score = rs.getInt("score");
        String gameResult = rs.getString("game_result");

        // Create a new GameScore object and add it to the list
        scores.add(new GameScore(playerId, gameId, score, gameResult));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return scores;
  }




}
