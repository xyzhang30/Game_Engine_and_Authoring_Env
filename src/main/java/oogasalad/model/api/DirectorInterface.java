package oogasalad.model.api;

import oogasalad.model.api.data.GameData;
import oogasalad.model.gamebuilder.GameBuilder;

public interface DirectorInterface {

  void constructCollidableObjects(GameData gameData);

  void constructPlayers(GameData gameData);
}