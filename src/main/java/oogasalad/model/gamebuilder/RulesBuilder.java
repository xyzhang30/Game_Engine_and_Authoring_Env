package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.Rules;

public class RulesBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    Rules rules = (Rules) gameField.get(0);
    gameData.setRules(rules);
  }
}
