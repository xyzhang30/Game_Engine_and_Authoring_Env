package oogasalad.model.api;

import java.util.List;
import oogasalad.model.api.data.GameData;
import oogasalad.model.gamebuilder.GameBuilder;

public interface DirectorInterface {

  void constructCollidableObjects(GameData gameData, List<Record> gameField);

  void constructPlayers(GameData gameData, List<Record> gameField);
}