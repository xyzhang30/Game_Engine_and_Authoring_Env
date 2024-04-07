package oogasalad.model.gameparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.gameparser.data.GameData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Abstract class for loading game data from JSON files.
 *
 * @author Judy He, Alisha Zhang
 */
public abstract class GameLoader {

  private static final Logger LOGGER = LogManager.getLogger(GameLoader.class);

  private static final String DATA_FOLDER_PATH = "data/";
  private static final String JSON_EXTENSION = ".json";
  private static final String RESOURCE_FOLDER_PATH = "model.";
  private static final String ERROR_RESOURCE_FOLDER = "error.";
  private static final String ERROR_FILE_PREFIX = "Error";
  public GameData gameData;
  private final String language = "English";
  private final ResourceBundle resourceBundle;

  /**
   * Constructs a GameLoader object with the specified ID.
   *
   * @param id The ID of the game data to load.
   */
  public GameLoader(int id) throws InvalidFileException {
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + ERROR_RESOURCE_FOLDER + ERROR_FILE_PREFIX + language);
    try {
      parseJSON("/data/singlePlayerMiniGolf.json");
    } catch (IOException | InvalidFileException e) {
      LOGGER.error(resourceBundle.getString(e.getMessage()));
      throw new InvalidFileException(String.format(
          String.format(resourceBundle.getString("JSONParsingError"), e.getMessage())), e);
    }
  }

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
//      throw new InvalidFileException(String.format(
//          String.format(resourceBundle.getString("JSONParsingError"), e.getMessage())), e);
      LOGGER.error(resourceBundle.getString("JSONParsingError"), e.getMessage());
    }
  }

  private void parseJSON(String filePath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File f = new File(filePath);
    this.gameData = objectMapper.readValue(f, GameData.class);

  }

}
