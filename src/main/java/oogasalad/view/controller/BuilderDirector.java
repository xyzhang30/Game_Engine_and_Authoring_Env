package oogasalad.view.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.exception.InvalidJSONDataException;
import oogasalad.model.gamebuilder.GameObjectsBuilder;
import oogasalad.model.gamebuilder.KeysBuilder;
import oogasalad.model.gamebuilder.PlayersBuilder;
import oogasalad.model.gamebuilder.RulesBuilder;
import oogasalad.model.gamebuilder.VariablesBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BuilderDirector manages the construction of game elements and data.
 * It handles the creation of game objects, players, variables, and rules from provided data.
 * The class also writes game data to JSON files.
 *
 * @author Judy He, Alisha Zhang
 */
public class BuilderDirector {

  private static final Logger LOGGER = LogManager.getLogger(GameBuilder.class);
  private static final String RESOURCE_FOLDER_PATH = "model.";
  private static final String ERROR_RESOURCE_FOLDER = "error.";
  private static final String ERROR_FILE_PREFIX = "Error";
  private static final String NEW_GAME_DATA_FOLDER_PATH = "data/playable_games/";
  private static final String JSON_EXTENSION = ".json";
  private final String language = "English";
  private final ResourceBundle resourceBundle = ResourceBundle.getBundle(
      RESOURCE_FOLDER_PATH + ERROR_RESOURCE_FOLDER + ERROR_FILE_PREFIX + language);
  private final GameData gameData;

  public BuilderDirector() {
    this.gameData = new GameData();
  }

  public <T> void constructCollidableObjects(List<T> fieldData) {
    GameObjectsBuilder gameObjectsBuilder = new GameObjectsBuilder();
    gameObjectsBuilder.buildGameField(gameData, fieldData);
  }

  public <T> void constructPlayers(List<T> fieldData) {
    PlayersBuilder playersBuilder = new PlayersBuilder();
    playersBuilder.buildGameField(gameData, fieldData);
  }

  public <T> void constructVaraibles(List<T> fieldData) {
    VariablesBuilder variablesBuilder = new VariablesBuilder();
    variablesBuilder.buildGameField(gameData, fieldData);
  }

  public <T> void constructRules(List<T> fieldData) {
    RulesBuilder rulesBuilder = new RulesBuilder();
    rulesBuilder.buildGameField(gameData, fieldData);
  }

  public <T> void constructKeys(List<T> fieldData) {
    KeysBuilder keysBuilder = new KeysBuilder();
    keysBuilder.buildGameField(gameData, fieldData);
  }

  public void writeGame(String fileName) throws InvalidJSONDataException {
    writeGame(fileName, this.gameData, NEW_GAME_DATA_FOLDER_PATH);
//    this.gameData.setGameName(fileName);
//    ObjectMapper mapper = new ObjectMapper();
//    if (gameData.getGameObjects() == null || gameData.getPlayers() == null
//        || gameData.getVariables() == null || gameData.getRules() == null) {
//      LOGGER.error(resourceBundle.getString("NullJSONFieldError"));
//      throw new InvalidJSONDataException(String.format(
//          String.format(resourceBundle.getString("NullJSONFieldError"))));
//    }
//    try {
//      mapper.writerWithDefaultPrettyPrinter()
//          .writeValue(new File(DATA_FOLDER_PATH + fileName + JSON_EXTENSION), gameData);
//    } catch (IOException e) {
//      LOGGER.error(resourceBundle.getString("JSONWritingError"), e.getMessage());
//      throw new InvalidJSONDataException(String.format(
//          String.format(resourceBundle.getString("JSONWritingError"), e.getMessage())), e);
//    }
  }

  public void writeGame(String fileName, GameData gameData, String folderPath) throws InvalidJSONDataException {
    gameData.setGameName(fileName);
    ObjectMapper mapper = new ObjectMapper();
    if (gameData.getGameObjectProperties() == null || gameData.getPlayers() == null
        || gameData.getVariables() == null || gameData.getRules() == null) {
      LOGGER.error(resourceBundle.getString("NullJSONFieldError"));
      throw new InvalidJSONDataException(String.format(
          String.format(resourceBundle.getString("NullJSONFieldError"))));
    }
    try {
      mapper.writerWithDefaultPrettyPrinter()
          .writeValue(new File(folderPath + fileName + JSON_EXTENSION), gameData);
    } catch (IOException e) {
      LOGGER.error(resourceBundle.getString("JSONWritingError"), e.getMessage());
      throw new InvalidJSONDataException(String.format(
          String.format(resourceBundle.getString("JSONWritingError"), e.getMessage())), e);
    }
  }
}
