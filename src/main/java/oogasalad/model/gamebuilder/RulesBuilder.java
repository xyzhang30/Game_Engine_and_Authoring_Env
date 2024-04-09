package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.Rules;

public class RulesBuilder implements GameBuilder{

  @Override
  public void buildGameField(GameData gameData, List<Record> gameField) {
    gameData.setRules((Rules)gameField.get(0));
  }
}
