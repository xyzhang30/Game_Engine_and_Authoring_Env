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
import oogasalad.model.gameparser.data.CollidableObject;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the Model.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderModel extends GameLoader {

  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;

  /**
   * Constructs a GameLoaderModel object with the specified ID.
   * @param id The ID of the game data to load.
   */
  public GameLoaderModel(int id) {
    super(id);
    this.createPlayerContainer();
    this.createCollidableContainer();
    this.createRulesRecord();
  }

  // alisha
  private void createPlayerContainer() {


  }

  /**
   * Retrieves the player container.
   * @return The player container.
   */
  public PlayerContainer getPlayerContainer(){
    return playerContainer;
  }

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

  /**
   * Retrieves the collidable container.
   * @return The collidable container.
   */
  public CollidableContainer getCollidableContainer(){
    return collidableContainer;
  }

  // alisha
  private void createRulesRecord(){

  }

  /**
   * Retrieves the rules record.
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

}
