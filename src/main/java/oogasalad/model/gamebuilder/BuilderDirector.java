package oogasalad.model.gamebuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.api.DirectorInterface;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.model.api.exception.InvalidFileException;
import oogasalad.model.api.exception.InvalidJSONDataException;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameparser.GameLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuilderDirector implements DirectorInterface {
  private static final Logger LOGGER = LogManager.getLogger(GameBuilder.class);
  private static final String RESOURCE_FOLDER_PATH = "model.";
  private static final String ERROR_RESOURCE_FOLDER = "error.";
  private static final String ERROR_FILE_PREFIX = "Error";
  private final String language = "English";
  private final ResourceBundle resourceBundle = ResourceBundle.getBundle(
  RESOURCE_FOLDER_PATH + ERROR_RESOURCE_FOLDER + ERROR_FILE_PREFIX + language);

  @Override
  public void constructCollidableObjects(GameData gameData, List<Record> fieldData) {
    CollidablesBuilder collidablesBuilder = new CollidablesBuilder();
    collidablesBuilder.buildGameField(gameData, fieldData);
  }

  @Override
  public void constructPlayers(GameData gameData, List<Record> fieldData) {
    PlayersBuilder playersBuilder = new PlayersBuilder();
    playersBuilder.buildGameField(gameData, fieldData);
  }

  @Override
  public void constructVaraibles(GameData gameData, List<Record> fieldData) {
    VariablesBuilder variablesBuilder = new VariablesBuilder();
    variablesBuilder.buildGameField(gameData, fieldData);
  }

  @Override
  public void constructRules(GameData gameData, List<Record> fieldData) {
    RulesBuilder rulesBuilder = new RulesBuilder();
    rulesBuilder.buildGameField(gameData, fieldData);
  }

  public void writeGame(GameData gameData, String gameName, String filePath, String fileName) throws InvalidJSONDataException {
    gameData.setGameName(gameName);
    ObjectMapper mapper = new ObjectMapper();
    if (gameData.getCollidableObjects() == null || gameData.getPlayers() == null || gameData.getVariables() == null || gameData.getRules() == null) {
      LOGGER.error(resourceBundle.getString("NullJSONFieldError"));
      throw new InvalidJSONDataException(String.format(
          String.format(resourceBundle.getString("NullJSONFieldError"))));
    }
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath+fileName), gameData);
    } catch (IOException e) {
      LOGGER.error(resourceBundle.getString("JSONWritingError"), e.getMessage());
      throw new InvalidJSONDataException(String.format(
          String.format(resourceBundle.getString("JSONWritingError"), e.getMessage())), e);
    }
  }

}
