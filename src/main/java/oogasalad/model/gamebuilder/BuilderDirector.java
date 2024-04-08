package oogasalad.model.gamebuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import oogasalad.model.api.DirectorInterface;
import oogasalad.model.api.data.CollidableObject;
import oogasalad.model.api.data.GameData;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;

public class BuilderDirector implements DirectorInterface {

  private GameData gameData;

  public BuilderDirector(){

  }
  //change game data record into an actual class
//
//  @JsonProperty("gameName") String gameName;
//  @JsonProperty("collidable_objects") List<CollidableObject> collidableObjects;
//  List<ParserPlayer> players;
//  List<Variables> variables;
//  Rules rules;

//  JSONObject jsonData = new JSONObject();

  @Override
  public void constructCollidableObjects(GameData gameData) {
    //call buildGameField in collidablesbuilder ??
  }

  @Override
  public void constructPlayers(GameData gameData) {

  }

  private void writeGame() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File("game_data.json"), gameData);
  }

}
