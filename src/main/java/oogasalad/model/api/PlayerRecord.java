package oogasalad.model.api;

/**
 * An immutable object representing the current state of a player
 *
 * @param playerId         An integer representing the player's id
 * @param score            A double representing the player's current score
 * @param activeStrikeable An integer representing the gameObjectId of the player's active
 *                         strikeable
 * @author Noah Loewy
 */
public record PlayerRecord(int playerId, int score, int activeStrikeable) {
}

