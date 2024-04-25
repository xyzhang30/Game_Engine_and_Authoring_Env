package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class DataCreateObject {

  // Register a new user
  public boolean registerUser(String username, String password, String avatarUrl, String language, int age, String favoriteVariantsJson, int tokens, String colorsJson) {
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

  public boolean updatePermission(int permissionId, String role) {
    String sql = "UPDATE Permissions SET role = ? WHERE permission_id = ?;";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, role);
      pstmt.setInt(2, permissionId);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


}
