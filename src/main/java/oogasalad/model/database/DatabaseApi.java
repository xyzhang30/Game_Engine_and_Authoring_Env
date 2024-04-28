package oogasalad.model.database;

/**
 * The DatabaseApi interface defines methods for interacting with the database to manage user
 * accounts, game data, and permissions.
 */

import java.util.List;

public interface DatabaseApi {

  /**
   * Retrieves the high scores of a player for a specific game.
   *
   * @param gameName   The name of the game.
   * @param playerName The name of the player.
   * @param n          The number of high scores to retrieve.
   * @return A list of GameScore objects representing the high scores of the player.
   */
  List<GameScore> getPlayerHighScoresForGame(String gameName, String playerName, int n);

  /**
   * Retrieves the general high scores for a specific game.
   *
   * @param gameName The name of the game.
   * @param n        The number of high scores to retrieve.
   * @return A list of GameScore objects representing the general high scores of the game.
   */
  List<GameScore> getGeneralHighScoresForGame(String gameName, int n);

  /**
   * Verifies the login credentials of a user.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   * @return True if the login credentials are valid, false otherwise.
   */
  boolean loginUser(String username, String password);

  /**
   * Retrieves the IDs of playable games for a player with a specified number of players.
   *
   * @param playerName The name of the player.
   * @param numPlayers The number of players required for the game.
   * @return A list of game IDs that are playable by the player.
   */
  List<String> getPlayableGameIds(String playerName, int numPlayers);

  /**
   * Registers a new user.
   *
   * @param username  The username of the user.
   * @param password  The password of the user.
   * @param avatarUrl The URL of the user's avatar.
   * @return True if the user is successfully registered, false otherwise.
   */
  boolean registerUser(String username, String password, String avatarUrl);

  /**
   * Registers a new game.
   *
   * @param gameName        The name of the game.
   * @param ownerName       The name of the owner of the game.
   * @param numPlayers      The number of players required for the game.
   * @param publicOrPrivate True if the game is public, false if private.
   * @return True if the game is successfully registered, false otherwise.
   */
  boolean registerGame(String gameName, String ownerName, int numPlayers, boolean publicOrPrivate);

  /**
   * Adds a new game instance.
   *
   * @param game The name of the game.
   * @return The ID of the newly added game instance.
   */
  int addGameInstance(String game);

  /**
   * Adds a game score for a specific game instance and player.
   *
   * @param gameInstanceId The ID of the game instance.
   * @param user           The username of the player.
   * @param score          The score achieved by the player.
   * @param result         The result of the game (true for win, false for loss).
   * @return True if the game score is successfully added, false otherwise.
   */
  boolean addGameScore(int gameInstanceId, String user, int score, boolean result);

  /**
   * Assigns a permission to a list of players for a specific game.
   *
   * @param game       The name of the game.
   * @param users      The list of usernames of the players.
   * @param permission The permission to assign (Owner, Player, None).
   */
  void assignPermissionToPlayers(String game, List<String> users, String permission);

  List<String> getManageableGames(String playerName);
}
