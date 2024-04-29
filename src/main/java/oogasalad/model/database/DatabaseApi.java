package oogasalad.model.database;

/**
 * The DatabaseApi interface defines methods for interacting with the database to manage user
 * accounts, game data, and permissions.
 */

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;

public interface DatabaseApi {

  /**
   * Retrieves the high scores of a specific game.
   *
   * @param gameName The name of the game.
   * @return A list of GameScore objects representing the high scores of the player.
   */

  Map<String, Boolean> getPlayerPermissionsForGames(String gameName) throws SQLException;


  /**
   * Gets the accessibility level of a game.
   *
   * @param gameName the game being queried
   * @return If the game is public to all, private, or open to friends of the creator
   */

  String getGameAccessibility(String gameName) throws SQLException;

  /**
   * Sets accessibility of a game
   *
   * @param gameName      the game being updated
   * @param accessibility If the game is public to all, private, or open to friends of creator.
   */

  void setGameAccessibility(String gameName, String accessibility) throws SQLException;


  /**
   * Retrieves the general high scores for a specific game.
   *
   * @param gameName The name of the game.
   * @return A list of GameScore objects representing the general high scores of the game.
   */


  ObservableList<GameScore> getGeneralHighScoresForGame(String gameName, boolean desc)
      throws SQLException;

  /**
   * Verifies the login credentials of a user.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return True if the login credentials are valid, false otherwise.
   */
  boolean loginUser(String username, String password) throws SQLException;

  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @param numPlayers The number of players required for the game.
   * @return A list of game IDs that are playable by the player.
   */
  List<String> getPlayableGameIds(String playerName, int numPlayers) throws SQLException;

  /**
   * Registers a new user.
   *
   * @param username  The username of the user.
   * @param password  The password of the user.
   * @param avatarUrl The URL of the user's avatar.
   * @return True if the user is successfully registered, false otherwise.
   */
  boolean registerUser(String username, String password, String avatarUrl) throws SQLException;


  /**
   * Registers a new game.
   *
   * @param gameName   The name of the game.
   * @param ownerName  The name of the owner of the game.
   * @param numPlayers The number of players required for the game.
   * @return True if the game is successfully registered, false otherwise.
   */
  boolean registerGame(String gameName, String ownerName, int numPlayers,
      String accessibility) throws SQLException;

  /**
   * Adds a new game instance.
   *
   * @param game The name of the game.
   * @return The ID of the newly added game instance.
   */
  int addGameInstance(String game) throws SQLException;

  /**
   * Adds a game score for a specific game instance and player.
   *
   * @param gameInstanceId The ID of the game instance.
   * @param user           The username of the player.
   * @param score          The score achieved by the player.
   * @param result         The result of the game (true for win, false for loss).
   * @return True if the game score is successfully added, false otherwise.
   */
  boolean addGameScore(int gameInstanceId, String user, int score, boolean result)
      throws SQLException;

  /**
   * Assigns a permission to a list of players for a specific game.
   *
   * @param game       The name of the game.
   * @param users      The list of usernames of the players.
   * @param permission The permission to assign (Owner, Player, None).
   */
  void assignPermissionToPlayers(String game, List<String> users, String permission)
      throws SQLException;


  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @return A list of game IDs that are playable by the player.
   */

  List<String> getManageableGames(String playerName) throws SQLException;

  /**
   * Assigns a friend list for a specific player.
   *
   * @param player     The player whose friends are being updated.
   * @param friends    All users that are friends of player.
   * @param notFriends All users that are not friends of player
   */

  void assignFriends(String player, List<String> friends, List<String> notFriends)
      throws SQLException;


  /**
   * Returns whether user with given username is in the database
   *
   * @param username of a user
   * @return if the given user is in the database
   */

  boolean doesUserExist(String username) throws SQLException;


  /**
   * Gets a list of all players that are friends with a given player
   *
   * @param player who user wants to see their friends
   * @return map from player username to whether or not "player" is friends with them
   */

  Map<String, Boolean> getFriends(String player) throws SQLException;
}
