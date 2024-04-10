package oogasalad.model.gamebuilder;

import java.util.List;
import oogasalad.model.api.GameBuilder;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.GameData;

public class CollidablesBuilder implements GameBuilder {

  @Override
  public <T> void buildGameField(GameData gameData, List<T> gameField) {
    List<CollidableObject> cos = (List<CollidableObject>) gameField;
    gameData.setCollidableObjects(cos);
  }
}
