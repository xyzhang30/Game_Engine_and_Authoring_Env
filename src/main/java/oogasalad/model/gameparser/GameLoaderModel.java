package oogasalad.model.gameparser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.Moveable;
import oogasalad.model.gameengine.collidable.Surface;

public class GameLoaderModel extends GameLoader {

  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;

  public GameLoaderModel(int id) {
    super(id);
    this.createPlayerContainer();
    this.createCollidableContainer();
    this.createRulesRecord();
  }

  // alisha
  private void createPlayerContainer() {


  }

  public PlayerContainer getPlayerContainer(){
    return playerContainer;
  }

  // judy
  private void createCollidableContainer() {
    List<CollidableObject> collidableObjects = gameData.collidableObjects();
    Map<Integer, Collidable> collidables = new HashMap<>();

    for (CollidableObject co: collidableObjects) {
      Collidable collidable = null;
      if (co.properties().contains("movable")) {
        collidable = createMovableCollidable(co);
      }
      else if (co.properties().contains("surface")) {
        collidable = createSurfaceCollidable(co);
      }
      collidables.put(co.collidableId(), collidable);
    }
    this.collidableContainer = new CollidableContainer(collidables);


  }

  private Collidable createMovableCollidable(CollidableObject co) {
    return new Moveable(
        co.collidableId(),
        co.mass(),
        co.position().xPosition(),
        co.position().yPosition(),
        co.properties().contains("visible")
    );
  }

  private Collidable createSurfaceCollidable(CollidableObject co) {
    return new Surface(
        co.collidableId(),
        co.mass(),
        co.position().xPosition(),
        co.position().yPosition(),
        co.properties().contains("visible"),
        co.friction()
    );
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
