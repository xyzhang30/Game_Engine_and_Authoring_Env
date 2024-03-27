/**
 * @author Noah Loewy
 */
interface Database {

  /**
   * Inserts a game result into the database.
   * @param gameType The type of the game.
   * @param winnerId The ID of the winner.
   * @param loserId The ID of the loser.
   * @param points The points scored by the winner.
   * @param opponentPoints The points scored by the loser.
   * @param timeOfMatch The time of the match.
   */
  void insertGameResult(String gameType, int winnerId, int loserId, int points, int opponentPoints, Date timeOfMatch);

  /**
   * Retrieves standings from the database for the specified league.
   * @param leagueName The name of the league.
   * @return A list of standings.
   */
  List<Integer> getStandings(String leagueName);

  /**
   * Inserts player information into the database.
   * @param name The name of the player.
   * @param username The username of the player.
   * @param password The password of the player.
   * @param email The email address of the player.
   */
  void insertPlayer(String name, String username, String password, String email);
}

/**
 * Checks if a player exists in the database.
 * @param username The username of the player to check.
 * @return True if the player exists, false otherwise.
 */
boolean playerExists(String username, String password);
