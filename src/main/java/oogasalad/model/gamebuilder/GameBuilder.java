package oogasalad.model.gamebuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import oogasalad.model.api.data.GameData;

/**
 * @author Alisha Zhang
 */

public interface GameBuilder {

//  public GameBuilder(String gameName, Record gameField) throws IOException {
//    buildGameData(gameDataRecords);
//    writeGame(gameName);
//  }


  void buildGameField(String gameName, Record gameField);

}
