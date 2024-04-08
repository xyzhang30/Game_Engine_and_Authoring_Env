package oogasalad.model.api;

import oogasalad.model.gamebuilder.GameBuilder;

public interface DirectorInterface {

  void constructCollidableObjects(GameBuilder gameBuilder);

  void constructPlayers(GameBuilder gameBuilder);
}