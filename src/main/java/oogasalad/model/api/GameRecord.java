package oogasalad.model.api;

import java.util.List;

/**
 * An immutable object representing the current state of the game
 *
 * @param gameObjectRecords A list of immutable GameObjects, representing the state of each
 *                          GameObject, including its position, velocity, visibility, and size
 * @param players           A list of immutable players, representing each player's score, and id of
 *                          the player's active strikeable
 * @param round             Integer current round number the game is on
 * @param turn              Integer playerid of the player whose turn it is, if it is a turn based
 *                          game
 * @param gameOver          Boolean representation of whether the game is over
 * @param staticState       Boolean representation of if the game is in a static state (no
 *                          gameObject is moving, and if hitting should therefore be enabled)
 * @author Noah Loewy
 */
public record GameRecord(List<GameObjectRecord> gameObjectRecords, List<PlayerRecord> players,
                         int round,
                         int turn, boolean gameOver, boolean staticState) {

}




