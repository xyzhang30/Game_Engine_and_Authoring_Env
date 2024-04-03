package oogasalad.model.gameparser;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.CollidableContainer;

public class GameLoaderModel extends GameLoader {

  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;

  public GameLoaderModel(int id) {

    createPlayerContainer();
    createCollidableContainer();
    createRulesRecord();
  }

  // alisha
  private void createPlayerContainer() {


  }

  public PlayerContainer getPlayerContainer(){
    return playerContainer;
  }

  // judy
  private void createCollidableContainer() {




  }


  public CollidableContainer getCollidableContainer(){
    return collidableContainer;
  }

  // alisha
  private void createRulesRecord(){

  }

  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

}
