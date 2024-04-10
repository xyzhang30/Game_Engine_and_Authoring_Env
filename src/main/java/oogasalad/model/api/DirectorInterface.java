package oogasalad.model.api;

import java.util.List;
import oogasalad.model.api.data.GameData;
/**
 * This interface defines the methods required for constructing and writing game data.
 *
 * @author Judy He, Alisha Zhang
 */
public interface DirectorInterface {

  /**
   * Constructs collidable objects based on the provided game data and field data.
   *
   * @param gameData the game data to be updated with collidable objects
   * @param fieldData the list of records containing field data for constructing collidable objects
   */
  void constructCollidableObjects(GameData gameData, List<Record> fieldData);

  /**
   * Constructs players based on the provided game data and field data.
   *
   * @param gameData the game data to be updated with player information
   * @param fieldData the list of records containing field data for constructing players
   */
  void constructPlayers(GameData gameData, List<Record> fieldData);

  /**
   * Constructs variables needed for the game based on the provided game data and field data.
   *
   * @param gameData the game data to be updated with variables
   * @param fieldData the list of records containing field data for constructing variables
   */
  void constructVaraibles(GameData gameData, List<Record> fieldData);

  /**
   * Constructs rules for the game based on the provided game data and field data.
   *
   * @param gameData the game data to be updated with rules
   * @param fieldData the list of records containing field data for constructing rules
   */
  void constructRules(GameData gameData, List<Record> fieldData);

  /**
   * Writes the game data to a JSON file with the specified name and path.
   *
   * @param gameData the game data to be written
   * @param fileName the name of the file
   */
  void writeGame(GameData gameData, String fileName);

}