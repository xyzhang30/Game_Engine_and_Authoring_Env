package oogasalad.model.gamebuilder;

import java.security.Key;
import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.KeyPreferences;
import oogasalad.model.api.data.Rules;

public class KeysBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    KeyPreferences keyPreferences = (KeyPreferences) gameField.get(0);
    gameData.setKeyPreferences(keyPreferences);
  }
}
