package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DataCreateObject {

  // Register a new user
  public static boolean registerUser(String username, String password, String avatarUrl, String language, int age, String favoriteVariantsJson, int tokens, String colorsJson) {
    try (Connection conn = DatabaseConfig.getConnection()) {
      String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
      String sql = "INSERT INTO Players (username, password, avatar_url, language, age, favorite_variants, tokens, colors) VALUES (?, ?, ?, ?, ?, ?::jsonb, ?, ?::jsonb)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, avatarUrl);
        stmt.setString(4, language);
        stmt.setInt(5, age);
        stmt.setString(6, favoriteVariantsJson);
        stmt.setInt(7, tokens);
        stmt.setString(8, colorsJson);
        stmt.executeUpdate();
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Update user information
  public boolean updateUser(int playerId, String newAvatarUrl, String newLanguage) {
    try (Connection conn = DatabaseConfig.getConnection()) {
      String sql = "UPDATE Players SET avatar_url = ?, language = ? WHERE player_id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, newAvatarUrl);
        stmt.setString(2, newLanguage);
        stmt.setInt(3, playerId);
        stmt.executeUpdate();
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean updateGame(int gameId, int ownerId, String gameName) {
    String sql = "UPDATE Games SET owner_id = ?, name = ? WHERE game_id = ?;";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, ownerId);
      pstmt.setString(2, gameName);
      pstmt.setInt(3, gameId);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


  public boolean updateGameScore(int scoreId, int score, String gameResult) {
    String sql = "UPDATE GameScores SET score = ?, game_result = ? WHERE score_id = ?;";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, score);
      pstmt.setString(2, gameResult);
      pstmt.setInt(3, scoreId);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Grants a permission to multiple players for a specific game.
   * @param gameId The game ID to which the permission relates.
   * @param playerIds Array of player IDs to grant the permission.
   * @param role The role to be granted.
   * @return true if the permissions are successfully granted, false otherwise.
   */
  public boolean grantPermissions(int gameId, int[] playerIds, String role) {
    String sql = "INSERT INTO Permissions (player_id, game_id, role) VALUES (?, ?, ?);";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      int count = 0;
      for (int playerId : playerIds) {
        pstmt.setInt(1, playerId);
        pstmt.setInt(2, gameId);
        pstmt.setString(3, role);
        pstmt.addBatch();  // Add each set of parameters to the batch
        count++;
      }
      int[] results = pstmt.executeBatch();  // Execute all the batched commands
      return results.length == count;  // Check if all commands were successful
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean createGame(int ownerId, String gameName) {
    String sql = "INSERT INTO Games (owner_id, name) VALUES (?, ?)";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, ownerId);
      pstmt.setString(2, gameName);

      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }


  public boolean assignPermissionToPlayers(int gameId, List<Integer> playerIds, String permission) {
    String sql = "INSERT INTO Permissions (player_id, game_id, role) VALUES (?, ?, ?)";
    Connection conn = null;

    try {
      conn = DatabaseConfig.getConnection();
      // Set auto-commit to false to manage the transaction manually
      conn.setAutoCommit(false);

      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // For each player ID, add a batch insert operation
        for (Integer playerId : playerIds) {
          pstmt.setInt(1, playerId);
          pstmt.setInt(2, gameId);
          pstmt.setString(3, permission);
          pstmt.addBatch();
        }
        // Execute the batch insert
        int[] affectedRows = pstmt.executeBatch();

        // Verify if all insertions were successful
        for (int rows : affectedRows) {
          if (rows == 0) {
            // If any insertion failed, roll back the transaction and return false
            conn.rollback();
            return false;
          }
        }
        // Commit the transaction
        conn.commit();
      }

      // If execution reaches here, all insertions were successful
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      if (conn != null) {
        try {
          // Attempt to roll back the transaction if anything goes wrong
          conn.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
      return false;
    } finally {
      try {
        if (conn != null && !conn.getAutoCommit()) {
          conn.setAutoCommit(true);
        }
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }




  public static void main(String[] args) {
    // Sample user data for testing
    String username = "testUser";
    String password = "testPassword123!";
    String avatarUrl = "http://example.com/avatar.jpg";
    String language = "English";
    int age = 30;
    String favoriteVariantsJson = "{\"game\":\"chess\",\"variant\":\"speed\"}";
    int tokens = 100;
    String colorsJson = "{\"primary\":\"blue\",\"secondary\":\"green\"}";

    // Attempt to register the user
    boolean result = registerUser(username, password, avatarUrl, language, age, favoriteVariantsJson, tokens, colorsJson);

    if (result) {
      System.out.println("User registration successful.");
    } else {
      System.out.println("User registration failed.");
    }
  }



}
