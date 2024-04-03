package oogasalad.model.gameparser;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.api.exception.InvalidCommandException;
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
    try{
      Map<Pair, List<Command>> commandMap = new HashMap<>();
      int maxRounds = gameData.variables().get(0).global().maxRounds();
      int maxTurns = gameData.variables().get(0).global().maxTurns();
      for (CollisionRule rule : gameData.rules().collisions()){
        Pair pair = new Pair(rule.firstId(), rule.secondId()); //collision rule is the one with ids and command map
        List<Command> commands = new ArrayList<>();
        for (Map<String, List<Integer>> command : rule.command()){ //looping through the list of command maps
          for(String s : command.keySet()){ //this is a for loop but there's always only going to be 1 command in the map (probably should change the structure of the json afterwards)
            Class<?> cc = Class.forName(s);
            commands.add((Command) cc.getDeclaredConstructor(List.class).newInstance(command.get(s)));
            commandMap.put(pair,commands);
          }
        }
      }
      rulesRecord = new RulesRecord(maxRounds,maxTurns,commandMap);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
             ClassNotFoundException | InvocationTargetException e) {
      throw new InvalidCommandException(e.getMessage());
    }
  }

//  public TurnPolicy getTurnPolicy(){
//
//  }


  /**
   * Retrieves the rules record.
   * @return The rules record.
   */
  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

}
