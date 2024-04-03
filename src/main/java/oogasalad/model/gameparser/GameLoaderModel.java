package oogasalad.model.gameparser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.invalidParameterNumberException;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.collidable.Moveable;
import oogasalad.model.gameengine.collidable.Surface;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameparser.data.CollidableObject;
import oogasalad.model.gameparser.data.CollisionRule;
import oogasalad.model.gameparser.data.ParserPlayer;

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
    this.createCollidableContainer();
    this.createPlayerContainer();
    this.createRulesRecord();
  }

  // alisha
  private void createPlayerContainer() {
    Map<Integer, Player> playerMap = new HashMap<>();
    for (ParserPlayer p : gameData.players()){
      int id = gameData.players().get(0).playerId();
      int myCollidableId = gameData.players().get(0).myCollidable();
      Player player = new Player(id, getCollidableContainer().getCollidable(myCollidableId));
      playerMap.put(0, player);
    }
    this.playerContainer = new PlayerContainer(playerMap);
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
  private void createRulesRecord() {
    Map<Pair, Command> commandMap = new HashMap<>();
    int maxRounds = gameData.variables().get(0).global().maxRounds();
    int maxTurns = gameData.variables().get(0).global().maxTurns();
    for (CollisionRule rule : gameData.rules().collisions()){
      Pair pair = new Pair(rule.firstId(), rule.secondId());
      for (Map<String, List<Integer>> command : rule.command()){
        for(String s : command.keySet()){
          commandMap.put(pair,)
        }
      }
    }
  }


  /**
   * Retrieves the rules record.
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

}
