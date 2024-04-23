package oogasalad.model.gameengine.player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.api.PlayerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The PlayerContainer class manages a collection of Players within the game environment, and
 * provides efficient access to Players to be accessed/manipulated via their unique IDs, while also
 * encapsulating the map from player IDs to Players instances, encapsulating the implementation of
 * the Players.
 *
 * @author Noah Loewy
 */

public class PlayerContainer {

  private static final Logger LOGGER = LogManager.getLogger(PlayerContainer.class);
  private final Map<Integer, Player> myPlayers;
  private Player active;

  /**
   * Initializes player container object
   *
   * @param players, a map from the unique identifier of a player to the actual Player object
   */
  public PlayerContainer(Map<Integer, Player> players) {
    myPlayers = players;
    getPlayers().forEach(Player::addPlayerHistory);
    active = players.get(1);
  }

  /**
   * Retrieves the Player object associated with the specified player ID. This encapsulates the use
   * of the map from ids to actual Player objects
   *
   * @param playerId The unique identifier of the player.
   * @return The Player object corresponding to the player ID.
   */

  protected Player getPlayer(int playerId) {
    if (!myPlayers.containsKey(playerId)) {
      LOGGER.warn("Player " + playerId + " Not Found ");
    }
    return myPlayers.get(playerId);
  }

  public List<Player> getPlayers() {
    return myPlayers.values().stream().toList();
  }

  /**
   * Retrieves the ID of the currently active player.
   *
   * @return The ID of the active player.
   */
  public Player getActive() {
    return active;
  }

  /**
   * Sets the ID of the active player.
   *
   * @param newActive The ID of the player to set as active.
   */

  public void setActive(Player newActive) {
    active = newActive;
  }


  /**
   * Retrieves a list of PlayerRecord objects representing the current state of each player.
   *
   * @return A list of PlayerRecord objects.
   */
  public List<PlayerRecord> getPlayerRecords() {
    return myPlayers.values().stream()
        .map(Player::getPlayerRecord)
        .collect(Collectors.toList());
  }
}
