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

public class Database implements DatabaseApi {

  /**
   * Retrieves the high scores of a player for a specific game.
   *
   * @param gameName   The name of the game.
   * @param playerName The name of the player.
   * @param n          The number of high scores to retrieve.
   * @return A list of GameScore objects representing the high scores of the player.
   */

  @Override
  public List<GameScore> getPlayerHighScoresForGame(String gameName, String playerName, int n) {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT gr.playerusername, gr.score, gr.gameresult " +
        "FROM gameresult gr " +
        "JOIN gameinstance gi ON gr.gameinstanceid = gi.gameinstanceid " +
        "WHERE gi.gamename = ? AND gr.playerusername = ?" +
        "ORDER BY gr.score DESC";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, gameName);
      pstmt.setString(2, playerName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {

        int score = rs.getInt("score");
        boolean gameResult = rs.getBoolean("gameresult");
        scores.add(new GameScore(playerName, gameName, score, gameResult));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return scores.subList(0, Math.min(scores.size(), n));
  }

  /**
   * Retrieves the general high scores for a specific game.
   *
   * @param gameName The name of the game.
   * @param n        The number of high scores to retrieve.
   * @return A list of GameScore objects representing the general high scores of the game.
   */

  @Override
  public List<GameScore> getGeneralHighScoresForGame(String gameName, int n) {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT gr.playerusername, gr.score, gr.gameresult " +
        "FROM gameresult gr " +
        "JOIN gameinstance gi ON gr.gameinstanceid = gi.gameinstanceid " +
        "WHERE gi.gamename = ? " +
        "ORDER BY gr.score DESC";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, gameName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String playerusername = rs.getString("playerusername");
        int score = rs.getInt("score");
        boolean gameResult = rs.getBoolean("gameresult");
        scores.add(new GameScore(playerusername, gameName, score, gameResult));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return scores.subList(0, Math.min(scores.size(), n));
  }

  /**
   * Verifies the login credentials of a user.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return True if the login credentials are valid, false otherwise.
   */
  @Override
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
    return false;
  }

  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @param numPlayers The number of players required for the game.
   * @return A list of game IDs that are playable by the player.
   */
  @Override
  public List<String> getPlayableGameIds(String playerName, int numPlayers) {
    List<String> gameNames = new ArrayList<>();
    String sql = "SELECT p.gamename FROM permissions p " +
        "JOIN games g ON p.gamename = g.gamename " +
        "WHERE p.playerusername = ? AND p.permissions != 'None' AND g.numplayers = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, playerName);
      pstmt.setInt(2, numPlayers);
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          String gameName = rs.getString("gamename");
          gameNames.add(gameName);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return gameNames;
  }

  /**
   * Registers a new user.
   *
   * @param username  The username of the user.
   * @param password  The password of the user.
   * @param avatarUrl The URL of the user's avatar.
   * @return True if the user is successfully registered, false otherwise.
   */

  @Override
  public boolean registerUser(String username, String password, String avatarUrl) {
    try (Connection conn = DatabaseConfig.getConnection()) {
      String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
      String sql = "INSERT INTO Players (username, password, avatarurl) VALUES (?, ?, ?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);
        stmt.setString(3, avatarUrl);
        stmt.executeUpdate();

        for (String gameName : getAllGames()) {
          try {
            grantPermissions(username, gameName, isGamePublic(gameName) ? "Player" : "None");
          } catch (SQLException e) {

          }
        }
        return true;
      }
    } catch (PSQLException uniqueViolation) {
      uniqueViolation.printStackTrace();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

  //returns true if game is publicly available, otherwise false
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

  /**
   * Registers a new game.
   *
   * @param gameName        The name of the game.
   * @param ownerName       The name of the owner of the game.
   * @param numPlayers      The number of players required for the game.
   * @param publicOrPrivate True if the game is public, false if private.
   * @return True if the game is successfully registered, false otherwise.
   */

  @Override
  public boolean registerGame(String gameName, String ownerName, int numPlayers,
      boolean publicOrPrivate) {
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

  //retrieves list of all usernames
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

  //retireves list of all game names
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


  //grants permissiosn to player for game in permissions db
  private void grantPermissions(String username, String gameName,
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
    } catch (SQLException e) {
    }
  }

  /**
   * Adds a new game instance.
   *
   * @param game The name of the game.
   * @return The ID of the newly added game instance.
   */

  @Override
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
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return -1;
  }


  /**
   * Adds a game score for a specific game instance and player.
   *
   * @param gameInstanceId The ID of the game instance.
   * @param user           The username of the player.
   * @param score          The score achieved by the player.
   * @param result         The result of the game (true for win, false for loss).
   * @return True if the game score is successfully added, false otherwise.
   */

  @Override
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

  /**
   * Assigns a permission to a list of players for a specific game.
   *
   * @param game       The name of the game.
   * @param users      The list of usernames of the players.
   * @param permission The permission to assign (Owner, Player, None).
   */


  @Override
  public void assignPermissionToPlayers(String game, List<String> users, String permission) {
    for (String user : users) {
      try {
        grantPermissions(user, game, permission);
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
