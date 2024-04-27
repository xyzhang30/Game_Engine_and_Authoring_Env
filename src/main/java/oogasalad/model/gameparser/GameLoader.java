package oogasalad.model.gameparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.exception.InvalidFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Abstract class for loading game data from JSON files.
 *
 * @author Judy He, Alisha Zhang
 */
public abstract class GameLoader {

  private static final Logger LOGGER = LogManager.getLogger(GameLoader.class);

  private static final String DATA_FOLDER_PATH = "data/playable_games/";
  private static final String JSON_EXTENSION = ".json";
  private static final String RESOURCE_FOLDER_PATH = "model.";
  private static final String ERROR_RESOURCE_FOLDER = "error.";
  private static final String ERROR_FILE_PREFIX = "Error";
  private final String language = "English";
  private final ResourceBundle resourceBundle;
  protected GameData gameData;
  private Map<Integer, GameObjectProperties> idToObjMap = new HashMap<>();

  /**
   * Constructs a GameLoader object with the specified file path.
   *
   * @param gameName The name of the game file to parse.
   */
  public GameLoader(String gameName) throws InvalidFileException {
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + ERROR_RESOURCE_FOLDER + ERROR_FILE_PREFIX + language);
    try {
      parseJSON(DATA_FOLDER_PATH + gameName + JSON_EXTENSION);

    } catch (IOException e) {
      LOGGER.error(resourceBundle.getString("JSONParsingError"), e.getMessage());
      throw new InvalidFileException(String.format(
          String.format(resourceBundle.getString("JSONParsingError"), e.getMessage())), e);
    }
  }

  private void parseJSON(String filePath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File f = new File(filePath);
    this.gameData = objectMapper.readValue(f, GameData.class);
    createIdToObjectMap();
  }

  private void createIdToObjectMap() {
    gameData.getGameObjects().forEach((gameObj) -> {
      idToObjMap.put(gameObj.collidableId(), gameObj);
    });
  }

  public GameData getGameData(){
    return gameData;
  }

  public GameObjectProperties getGameObjRecordById(int id){
    return idToObjMap.get(id);
  }

}
