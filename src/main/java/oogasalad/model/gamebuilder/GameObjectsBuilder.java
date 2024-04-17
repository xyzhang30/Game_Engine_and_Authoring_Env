package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GameData;

public class GameObjectsBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    List<GameObjectProperties> cos = (List<GameObjectProperties>) gameField;
    gameData.setGameObject(cos);
  }
}
