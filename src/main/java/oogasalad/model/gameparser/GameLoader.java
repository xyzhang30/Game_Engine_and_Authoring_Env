package oogasalad.model.gameparser;

import java.util.List;
import java.util.Map;
import oogasalad.model.gameengine.Collidable;
import oogasalad.model.gameengine.LogicManager;

public abstract class GameLoader {

  public GameLoader(int id){
    //choose the file based on the game id/name/filepath ???
  }

  public parseJSON(String filePath) {

  }

  public PlayerManager getPlayerManager(){

  }
  public LogicManager getLogicManager(){

  }
  public List<Collidable> getCollidables(){
  }




  // should there be a separate parser then for the view stuff since model is calling this one and if
  // model is holding an instance of it then it probably shouldn't be parsing view stuff (?)


}
