package oogasalad.model.api;

import java.util.List;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.model.gamebuilder.GameBuilder;

public interface DirectorInterface {

  void constructCollidableObjects(GameData gameData, List<Record> fieldData);
  void constructPlayers(GameData gameData, List<Record> fieldData);
  void constructVaraibles(GameData gameData, List<Record> fieldData);
  void constructRules(GameData gameData, List<Record> fieldData);

}