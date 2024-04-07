package oogasalad.model.gamebuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import oogasalad.model.api.data.GameData;

/**
 * @author Alisha Zhang
 */

public class GameBuilder {

  private GameData gameData;

  public GameBuilder(String gameName, List<Record> gameDataRecords) throws IOException {
    buildGameData(gameDataRecords);
    writeGame(gameName);
  }

  private void buildGameData(List<Record> gameDataRecords) {
    //make the gameData
  }

  private void writeGame(String gameName) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File("game_data.json"), gameData);
  }
}
