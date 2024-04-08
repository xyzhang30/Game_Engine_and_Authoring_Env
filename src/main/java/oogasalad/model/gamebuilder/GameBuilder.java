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

  void buildGameField(GameData gameData, List<Record> gameField);

}
