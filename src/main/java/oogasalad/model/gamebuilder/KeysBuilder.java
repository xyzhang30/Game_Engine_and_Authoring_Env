package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.KeyPreferences;

public class KeysBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    KeyPreferences keyPreferences = (KeyPreferences) gameField.get(0);
    gameData.setKeyPreferences(keyPreferences);
  }
}
