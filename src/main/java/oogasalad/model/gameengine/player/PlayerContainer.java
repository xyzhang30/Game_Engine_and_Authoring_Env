package oogasalad.model.gameengine.player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.model.api.PlayerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The PlayerContainer class manages a collection of Players within the game environment, and
 * provides efficient access to Players to be accessed/manipulated via their unique IDs, while also
 * encapsulating the Collection of players
 *
 * @author Noah Loewy
 */

public class PlayerContainer {

  private final Collection <Player> myPlayers;
  private Player active;

  /**
   * Initializes player container object
   *
   * @param players, a map from the unique identifier of a player to the actual Player object
   */
  public PlayerContainer(Collection<Player> players) {
    myPlayers = players;
    myPlayers.forEach(Player::addPlayerHistory);
    active = players.iterator().next();
  }

  /**
   * Retrieves all the players in the game
   *
   * @return a list of all player objects
   */
  public Collection<Player> getPlayers() {
    return myPlayers;
  }

  /**
   * Retrieves the currently active player.
   *
   * @return The active player.
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

}
