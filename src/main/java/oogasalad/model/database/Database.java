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

  private static final Connection conn;

  static {
    try {
      conn = DatabaseConfig.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public Map<String, Boolean> getPlayerPermissionsForGames(String gameName) throws SQLException {
    Map<String, Boolean> scores = new TreeMap<>();
    String query = "SELECT username, permissions FROM permissions WHERE gamename = ?";
    ResultSet rs = executeQuery(query, gameName);
    while (rs.next()) {
      if (!rs.getString("permissions").equals("Owner")) {
        scores.put(rs.getString("username"), !rs.getString("permissions").equals("None"));
      }
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
  public ObservableList<GameScore> getGeneralHighScoresForGame(String gameName, boolean desc)
      throws SQLException {
    List<GameScore> scores = new ArrayList<>();
    String query = "SELECT gr.playerusername, gr.score, gr.gameresult " +
        "FROM gameresult gr " +
        "JOIN gameinstance gi ON gr.gameinstanceid = gi.gameinstanceid " +
        "WHERE gi.gamename = ? " +
        "ORDER BY gr.score ";
    query += desc ? "DESC" : "";
    ResultSet rs = executeQuery(query, gameName);
    while (rs.next()) {
      scores.add(new GameScore(rs.getString("playerusername"), gameName,
          rs.getInt("score"), rs.getBoolean("gameresult")));
    }
    return FXCollections.observableList(scores.subList(0, Math.min(10, scores.size())));
  }

  /**
   * Verifies the login credentials of a user.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return True if the login credentials are valid, false otherwise.
   */
  @Override
  public boolean loginUser(String username, String password) throws SQLException {
    String query = "SELECT password FROM Players WHERE username = ?";
    ResultSet rs = executeQuery(query, username);
    return  (rs.next() && BCrypt.checkpw(password, rs.getString("password")));
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
  public boolean registerUser(String username, String password, String avatarUrl)
      throws SQLException {
    try {
      String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
      String sql = "INSERT INTO Players (username, password, avatarurl) VALUES (?, ?, ?) ON "
          + "CONFLICT DO NOTHING";
      executeUpdate(sql, username, hashedPassword, avatarUrl);
      for (String gameName : getAllGames()) {
        grantPermissions(username, gameName, getGameAccessibility(gameName).equals("public") ?
            "Player" : "None");
      }
    } catch (PSQLException ignored) {}
    return true;
  }

  /**
   * Gets the accessibility level of a game.
   * @param gameName the game being queried
   * @return If the game is public to all, private, or open to friends of the creator
   */
  @Override
  public String getGameAccessibility(String gameName) throws SQLException {
    String sql = "SELECT accessibility FROM Games WHERE gamename = ?";
    ResultSet rs = executeQuery(sql, gameName);
    return rs.next() ? rs.getString("accessibility") : "public";
  }

  @Override
  public void setGameAccessibility(String gameName, String accessibility) throws SQLException {
    String sql = "UPDATE Games SET accessibility = ? WHERE gamename = ?";
    executeUpdate(sql, accessibility, gameName);
  }


  /**
   * Registers a new game.
   *
   * @param gameName   The name of the game.
   * @param ownerName  The name of the owner of the game.
   * @param numPlayers The number of players required for the game.
   * @return True if the game is successfully registered, false otherwise.
   */

  @Override
  public boolean registerGame(String gameName, String ownerName, int numPlayers,
      String accessibility) throws SQLException {
    String sql = "INSERT INTO Games (gamename, owner, numplayers, accessibility) VALUES (?, ?, ?, "
        + "?) ON CONFLICT DO NOTHING";
    int affectedRows = executeUpdate(sql, gameName, ownerName, numPlayers, accessibility);
    for (String username : getAllPlayers()) {
      String permission =
          accessibility.equals("public") || (accessibility.equals("friends") && areFriends(
              ownerName, username)) ? "Player" : "None";
      grantPermissions(username, gameName, permission);
    }
    grantPermissions(ownerName, gameName, "Owner");
    return affectedRows > 0;
  }


  /**
   * Adds a new game instance.
   *
   * @param game The name of the game.
   * @return The ID of the newly added game instance.
   */

  @Override
  public int addGameInstance(String game) throws SQLException {
    String sql = "INSERT INTO gameinstance (gamename) VALUES (?)";
    PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    pstmt.setString(1, game);
    int affectedRows = pstmt.executeUpdate();
    return (affectedRows > 0 && pstmt.getGeneratedKeys().next()) ?
        pstmt.getGeneratedKeys().getInt(1) : -1;
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
  public boolean addGameScore(int gameInstanceId, String user, int score, boolean result)
      throws SQLException {
    String sql = "INSERT INTO GameResult (gameinstanceid, playerusername, score, gameresult) "
        + "VALUES (?, ?, ?, ?)";
    return executeUpdate(sql, gameInstanceId, user, score, result) > 0;}


  /**
   * Assigns a permission to a list of players for a specific game.
   *
   * @param game       The name of the game.
   * @param users      The list of usernames of the players.
   * @param permission The permission to assign (Owner, Player, None).
   */


  @Override
  public void assignPermissionToPlayers(String game, List<String> users, String permission) throws SQLException
  {
    for (String user : users) {
      grantPermissions(user, game, permission);
    }
  }


  /**
   * Assigns a friend list for a specific player.
   *
   * @param player     The player whose friends are being updated.
   * @param friends    All users that are friends of player.
   * @param notFriends All users that are not friends of player
   */

  @Override
  public void assignFriends(String player, List<String> friends, List<String> notFriends) throws SQLException{
    for (String friend : friends) {
      if (!areFriends(player, friend)) {
        insertFriendship(player, friend);
      }
    }
    for (String notFriend : notFriends) {
      if (areFriends(player, notFriend)) {
        removeFriendship(player, notFriend);
      }
    }

  }

  /**
   * Returns a list of the games a given group of players can play
   * @param playerName The name of the player.
   * @param numPlayers The number of players available to play.
   * @return all games that the given player can play with the specified number of players
   */
  @Override
  public ObservableList<String> getPlayableGameIds(String playerName, int numPlayers) throws SQLException{
    String sql = "SELECT p.gamename FROM permissions p " +
        "JOIN games g ON p.gamename = g.gamename " +
        "WHERE p.username = ? AND p.permissions != 'None' AND g.numplayers <= ?";
    return getGames(playerName, numPlayers, sql);
  }

  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @return A list of game IDs that are playable by the player.
   */
  @Override
  public ObservableList<String> getManageableGames(String playerName) throws SQLException {
    String sql = "SELECT p.gamename FROM permissions p " +
        "JOIN games g ON p.gamename = g.gamename " +
        "WHERE p.username = ? AND p.permissions = 'Owner'";
    return getGames(playerName, -1, sql);
  }

  /**
   * Returns whether user with given username is in the database
   * @param username of a user
   * @return if the given user is in the database
   */
  @Override
  public boolean doesUserExist(String username) throws SQLException {
    String query = "SELECT 1 FROM Players WHERE username = ?";
    return executeQuery(query, username).next();
  }


  /**
   * Gets a list of all players that are friends with a given player
   * @param player who user wants to see their friends
   * @return map from player username to whether or not "player" is friends with them
   */
  @Override
  public Map<String, Boolean> getFriends(String player) throws SQLException {
    Map<String, Boolean> friendsMap = new HashMap<>();
    String query = "SELECT p.username, COALESCE(f.friendship_status, false) AS is_friend FROM players p LEFT JOIN ( SELECT CASE WHEN player_username = ? THEN friend_username ELSE player_username END AS friend, true AS friendship_status FROM friendships WHERE player_username = ?) f ON p.username = f.friend WHERE p.username != ?;";
    ResultSet rs = executeQuery(query, player, player, player);
    while (rs.next()) {
      friendsMap.put(rs.getString("username"), rs.getBoolean("is_friend"));
    }
    return friendsMap;
  }

  //querys the database given a query and params
  private ResultSet executeQuery(String query, Object... params) throws SQLException {
    PreparedStatement pstmt = conn.prepareStatement(query);
    for (int i = 0; i < params.length; i++) {
      pstmt.setObject(i + 1, params[i]);
    }
    return pstmt.executeQuery();
  }

  // updates the database given a query and params
  private int executeUpdate(String query, Object... params) throws SQLException {
    PreparedStatement pstmt = conn.prepareStatement(query);
    for (int i = 0; i < params.length; i++) {
      pstmt.setObject(i + 1, params[i]);
    }
    return pstmt.executeUpdate();
  }

  //returns all games that playerName has access to, which requires no more than numPlayers
  private ObservableList<String> getGames(String playerName, int numPlayers, String sql)
      throws SQLException {
    List<String> gameNames = new ArrayList<>();
    PreparedStatement pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, playerName);
    if (numPlayers != -1) {
      pstmt.setInt(2, numPlayers);
    }
    try (ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        String gameName = rs.getString("gamename");
        gameNames.add(gameName);
      }
    }
    return FXCollections.observableList(gameNames);
  }


  //returns true iff player 1 is friends with player 2 in database
  private boolean areFriends(String player1, String player2) throws SQLException {
    String sql = "SELECT EXISTS (SELECT 1 FROM friendships WHERE (player_username = ? AND friend_username = ?) OR (player_username = ? AND friend_username = ?))";
    ResultSet rs = executeQuery(sql, player1, player2, player2, player1);
    return rs.next() && rs.getBoolean(1);
  }

  //player1 friends player 2 in database

  private void insertFriendship(String player1, String player2)
      throws SQLException {
    String sql = "INSERT INTO friendships (player_username, friend_username) VALUES (?, ?)";
    executeUpdate(sql, player1, player2);
  }

  //player1 defriends player 2 in database
  private void removeFriendship(String player1, String player2)
      throws SQLException {
    String sql = "DELETE FROM friendships WHERE (player_username = ? AND friend_username = ?) OR (player_username = ? AND friend_username = ?)";
    executeUpdate(sql, player1, player2, player2, player1);
  }


  //retrieves list of all usernames
  private List<String> getAllPlayers() throws SQLException {
    List<String> usernames = new ArrayList<>();
    String sql = "SELECT username FROM Players";
    ResultSet rs = executeQuery(sql);
    while (rs.next()) {
      String username = rs.getString("username");
      usernames.add(username);
    }
    return usernames;
  }

  //retireves list of all game names
  private List<String> getAllGames() throws SQLException{
    List<String> gamenames = new ArrayList<>();
    String sql = "SELECT gamename FROM Games";
    Connection conn = DatabaseConfig.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
      String game = rs.getString("gamename");
      gamenames.add(game);
    }
    return gamenames;
  }

  //grants permissiosn to player for game in permissions db
  private void grantPermissions(String username, String gameName,
      String permission) throws SQLException {
    String sql = "INSERT INTO Permissions (username, gamename, permissions) VALUES (?, ?, ?) "
        + "ON CONFLICT (username, gamename) DO UPDATE SET permissions = ?";
    executeUpdate(sql, username, gameName, permission, permission);
  }
}





