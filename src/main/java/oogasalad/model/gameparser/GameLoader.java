package oogasalad.model.gameparser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import java.io.IOException;

import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameparser.data.GameData;


/**
 * Abstract class for loading game data from JSON files.
 *
 * @author Judy He, Alisha Zhang
 */
public abstract class GameLoader {

  private static final String DATA_FOLDER_PATH = "data/";
  private static final String JSON_EXTENSION = ".json";

  public GameData gameData;


  /**
   * Constructs a GameLoader object with the specified ID.
   *
   * @param id The ID of the game data to load.
   */
  public GameLoader(int id){
    parseJSON("/data/singlePlayerMiniGolf.json");
  }

  /**
   * Constructs a GameLoader object with the specified file path.
   * @param gameName The name of the game file to parse.
   */
  public GameLoader(String gameName){
    parseJSON(DATA_FOLDER_PATH + gameName + JSON_EXTENSION);
  }

  private void parseJSON(String filePath) throws InvalidFileException {
    //find the file according to id (for database)
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      File f = new File(filePath);
      this.gameData = objectMapper.readValue(f, GameData.class);
    } catch (IOException e) {
      throw new InvalidFileException("Error: Invalid File", e);
    }
  }

  // testing
//  public static void main(String[] args) {
//    GameLoader gameLoader = new GameLoaderModel(0);
//    System.out.println("Game Name: " + gameLoader.gameData.gameName());
//    System.out.println("Number of Collidable Objects: " + gameLoader.gameData.collidableObjects().size());
//    for (Player p: gameLoader.gameData.players()) {
//      System.out.println(p.playerId());
//      System.out.println(p.myCollidable());
//    }
//  }

}
