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
}
