package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;

public class DataCreateObject {

  // Register a new user
  public boolean registerUser(String username, String password, String avatarUrl)   {
    try (Connection conn = DatabaseConfig.getConnection()) {
      String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
      String sql = "INSERT INTO Players (username, password, avatarurl) VALUES (?, ?, ?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, avatarUrl);
        stmt.executeUpdate();

       for (String gameName : getAllGames()) {
          grantPermissions(username, gameName, isGamePublic(gameName) ? "Player":"None");
        }
        return true;
      }
    } catch (PSQLException uniqueViolation) {
      uniqueViolation.printStackTrace();
      return true;
    }
    catch (SQLException e) {
      return false;
    }
  }

  private boolean isGamePublic(String gameName) {
    String sql = "SELECT public FROM Games WHERE gamename = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, gameName);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean registerGame(String gameName, String ownerName, int numPlayers, boolean publicOrPrivate) {
    String sql = "INSERT INTO Games (gamename, owner, numplayers, public) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, gameName);
      pstmt.setString(2, ownerName);
      pstmt.setInt(3, numPlayers);
      pstmt.setBoolean(4, publicOrPrivate);
      int affectedRows = pstmt.executeUpdate();
      String permission = publicOrPrivate ? "Player" : "None";
      for (String username : getAllPlayers()) {
        grantPermissions(username, gameName, permission);
      }
      grantPermissions(ownerName, gameName, "Owner");
      return affectedRows > 0;
    } catch (PSQLException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }



  private List<String> getAllPlayers() {
    List<String> usernames = new ArrayList<>();
    String sql = "SELECT username FROM Players";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        String username = rs.getString("username");
        usernames.add(username);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return usernames;
  }

  private List<String> getAllGames() {
    List<String> gamenames = new ArrayList<>();
    String sql = "SELECT gamename FROM Games";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        String game = rs.getString("gamename");
        gamenames.add(game);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return gamenames;
  }


  private void grantPermissions( String username, String gameName,
      String permission) throws SQLException {
    String sql = "INSERT INTO Permissions (playerusername, gamename, permissions) VALUES (?, ?, ?) "
        + "ON CONFLICT (playerusername, gamename) DO UPDATE SET permissions = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, username);
      pstmt.setString(2, gameName);
      pstmt.setString(3, permission);
      pstmt.setString(4, permission);
      pstmt.executeUpdate();
    }
  }


  public int addGameInstance(String game) {
    String sql = "INSERT INTO gameinstance (gamename) VALUES (?)";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      pstmt.setString(1, game);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return -1;
  }

  public boolean addGameScore(int gameInstanceId, String user, int score, boolean result) {
    String sql = "INSERT INTO GameResult (gameinstanceid, playerusername, score, gameresult) "
        + "VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, gameInstanceId);
      pstmt.setString(2, user);
      pstmt.setInt(3, score);
      pstmt.setBoolean(4, result);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }


  public void assignPermissionToPlayers(String game, List<String> users, String permission)
      throws SQLException {
    for(String user : users) {
      grantPermissions(user, game, permission);
    }
  }






}
