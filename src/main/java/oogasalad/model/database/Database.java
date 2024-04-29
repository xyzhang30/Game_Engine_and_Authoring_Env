package oogasalad.model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
      setParameters(pstmt, gameName, playerName);
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

  @Override
  public Map<String, Boolean> getPlayerPermissionsForGames(String gameName) {
    Map<String, Boolean> scores = new TreeMap<>();
    String query = "SELECT username, permissions " +
        "FROM permissions WHERE gamename = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      setParameters(pstmt, gameName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        if (!rs.getString("permissions").equals("Owner")) {
          scores.put(rs.getString("username"), !rs.getString("permissions").equals("None"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return scores;
  }

  /**
   * Retrieves the general high scores for a specific game.
   *
   * @param gameName The name of the game.
   * @return A list of GameScore objects representing the general high scores of the game.
   */

  @Override
  public ObservableList<GameScore> getGeneralHighScoresForGame(String gameName, boolean desc) {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT gr.playerusername, gr.score, gr.gameresult " +
        "FROM gameresult gr " +
        "JOIN gameinstance gi ON gr.gameinstanceid = gi.gameinstanceid " +
        "WHERE gi.gamename = ? " +
        "ORDER BY gr.score ";
    query += desc ? "DESC" : "";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      setParameters(pstmt, gameName);
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
    return FXCollections.observableList(scores.subList(0,Math.min(10,scores.size() )));
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
      setParameters(pstmt, username);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String storedPassword = rs.getString("password");
          return BCrypt.checkpw(password, storedPassword);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
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
      String sql = "INSERT INTO Players (username, password, avatarurl) VALUES (?, ?, ?) ON "
          + "CONFLICT DO NOTHING";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        setParameters(stmt, username, hashedPassword, avatarUrl);
        stmt.executeUpdate();
        for (String gameName : getAllGames()) {
          grantPermissions(username, gameName, getGameAccessibility(gameName).equals("public") ?
                "Player" :
                "None");
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

  //checking whether user exists or not
  public boolean doesUserExist(String username) {
    String query = "SELECT 1 FROM Players WHERE username = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      setParameters(pstmt, username);
      ResultSet rs = pstmt.executeQuery();
      return rs.next();  //true if user exists (bc at least one row exists)
    } catch (SQLException e) {
      e.printStackTrace();
      return false;  //false if user does not exist
    }
  }


  //returns true if game is publicly available, otherwise false
  public String getGameAccessibility(String gameName) {
    String sql = "SELECT accessibility FROM Games WHERE gamename = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, gameName);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getString("accessibility");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "public";
  }

  @Override
  public void setGameAccessibility(String gameName, String accessibility) {
    String sql = "UPDATE Games SET accessibility = ? WHERE gamename = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, accessibility, gameName);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Registers a new game.
   *
   * @param gameName        The name of the game.
   * @param ownerName       The name of the owner of the game.
   * @param numPlayers      The number of players required for the game.
   * @return True if the game is successfully registered, false otherwise.
   */

  @Override
  public boolean registerGame(String gameName, String ownerName, int numPlayers,
      String accessibility) {
    String sql = "INSERT INTO Games (gamename, owner, numplayers, accessibility) VALUES (?, ?, ?, "
        + "?) ON "
        + "CONFLICT DO NOTHING";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, gameName, ownerName, numPlayers, accessibility);
      int affectedRows = pstmt.executeUpdate();
      for (String username : getAllPlayers()) {
        String permission = accessibility.equals("public") || (accessibility.equals("friends") && areFriends(ownerName, username, conn)) ? "Player" : "None";
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
    String sql = "INSERT INTO Permissions (username, gamename, permissions) VALUES (?, ?, ?) "
        + "ON CONFLICT (username, gamename) DO UPDATE SET permissions = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, username, gameName, permission, permission);
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
      setParameters(pstmt, game);
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
      setParameters(pstmt, gameInstanceId, user, score, result);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      return false;
    }
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


  @Override
  public void assignFriends(String player, List<String> friends, List<String> notFriends) {
    try (Connection conn = DatabaseConfig.getConnection()) {
      for (String friend : friends) {
        if (!areFriends(player, friend, conn)) {
          insertFriendship(player, friend, conn);
        }
      }

      // Remove not friendships from the database
      for (String notFriend : notFriends) {
        if (areFriends(player, notFriend, conn)) {
          removeFriendship(player, notFriend, conn);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ObservableList<String> getPlayableGameIds(String playerName, int numPlayers) {
    String sql = "SELECT p.gamename FROM permissions p " +
        "JOIN games g ON p.gamename = g.gamename " +
        "WHERE p.username = ? AND p.permissions != 'None' AND g.numplayers <= ?";
    return getGames(playerName, numPlayers, sql);
  }

  private static ObservableList<String> getGames(String playerName, int numPlayers, String sql) {
    List<String> gameNames = new ArrayList<>();
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, playerName);
      if(numPlayers!=-1) {
        pstmt.setInt(2, numPlayers);
      }
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          String gameName = rs.getString("gamename");
          gameNames.add(gameName);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return FXCollections.observableList(gameNames);
  }

  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @return A list of game IDs that are playable by the player.
   */
  @Override
  public ObservableList<String> getManageableGames(String playerName) {
    String sql = "SELECT p.gamename FROM permissions p " +
        "JOIN games g ON p.gamename = g.gamename " +
        "WHERE p.username = ? AND p.permissions = 'Owner'";
    return getGames(playerName, -1, sql);
  }


  private boolean areFriends(String player1, String player2, Connection conn) throws SQLException {
    String sql = "SELECT EXISTS (SELECT 1 FROM friendships WHERE (player_username = ? AND friend_username = ?) OR (player_username = ? AND friend_username = ?))";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, player1, player2, player2, player1);
      ResultSet rs = pstmt.executeQuery();
      return rs.next() && rs.getBoolean(1);
    }
  }

  private void insertFriendship(String player1, String player2, Connection conn) throws SQLException {
    String sql = "INSERT INTO friendships (player_username, friend_username) VALUES (?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, player1, player2);
      pstmt.executeUpdate();
    }
  }

  private void removeFriendship(String player1, String player2, Connection conn) throws SQLException {
    String sql = "DELETE FROM friendships WHERE (player_username = ? AND friend_username = ?) OR (player_username = ? AND friend_username = ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      setParameters(pstmt, player1, player2, player2, player1);
      pstmt.executeUpdate();
    }
  }

  @Override
  public Map<String, Boolean> getFriends(String player) {
    Map<String, Boolean> friendsMap = new HashMap<>();
    String query = "SELECT p.username, COALESCE(f.friendship_status, false) AS is_friend FROM players p LEFT JOIN ( SELECT CASE WHEN player_username = ? THEN friend_username ELSE player_username END AS friend, true AS friendship_status FROM friendships WHERE player_username = ?) f ON p.username = f.friend WHERE p.username != ?;";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      setParameters(pstmt, player, player, player);
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          String username = rs.getString("username");
          boolean isFriend = rs.getBoolean("is_friend");
          friendsMap.put(username, isFriend);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return friendsMap;
  }


  private void setParameters(PreparedStatement pstmt, Object... values) throws SQLException {
    for (int i = 0; i < values.length; i++) {
      pstmt.setObject(i + 1, values[i]);
    }
  }
}
