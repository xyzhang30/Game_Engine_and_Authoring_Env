package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oogasalad.model.gameengine.player.Player;
import org.mindrot.jbcrypt.BCrypt;

public class DataAccessObject {

  // Method to retrieve a Player object by its ID
  //TODO-combine password checker with this
  public Player getPlayer(int id) {
    // SQL query to select player details
    String query = "SELECT * FROM Players WHERE player_id = ?";

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

  // Method to retrieve general high scores for a specific game
  public List<GameScore> getGeneralHighScoresForGame(int gameId) {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT player_id, score, game_result FROM GameScores WHERE game_id = ? ORDER BY score DESC";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setInt(1, gameId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int playerId = rs.getInt("player_id");
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

  // Method to check if a player has the permission to play a game
  public boolean hasPlayPermission(int playerId, int gameId) {
    String query = "SELECT role FROM Permissions WHERE player_id = ? AND game_id = ? AND role = 'PLAY'";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setInt(1, playerId);
      pstmt.setInt(2, gameId);
      ResultSet rs = pstmt.executeQuery();

      // If we find at least one row, it means the player has 'PLAY' permission
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false; // Assume no permission if there's an exception
    }
  }

  // Method to verify user login credentials
  public boolean loginUser(String username, String password) {
    String query = "SELECT password FROM Players WHERE username = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, username);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String storedPassword = rs.getString("password");
          // Use BCrypt to check if the entered password matches the stored hashed password
          return BCrypt.checkpw(password, storedPassword);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false; // Return false if user not found or password does not match
  }

  public List<Integer> getPlayableGameIds(int playerId) {
    List<Integer> gameIds = new ArrayList<>();
    String sql = "SELECT game_id FROM Permissions WHERE player_id = ? AND role = 'PLAY'";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, playerId);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          int gameId = rs.getInt("game_id");
          gameIds.add(gameId);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return gameIds;
  }



}
