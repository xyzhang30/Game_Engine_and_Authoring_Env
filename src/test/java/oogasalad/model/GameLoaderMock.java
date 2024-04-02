package oogasalad.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.Player;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.Moveable;
import oogasalad.model.gameengine.collidable.Surface;
import oogasalad.model.gameengine.command.AdvanceTurnCommand;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.command.SingleCommand;
import oogasalad.model.gameparser.GameLoaderModel;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.CollidableContainer;

public class GameLoaderMock extends GameLoaderModel {

  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rules;
  private GameEngine engine;

  public GameLoaderMock(int id, GameEngine engine) {
    super(id, engine);
    this.engine = engine;
    createPlayerContainer();
    createCollidableContainer();
    createRulesRecord();
  }

  protected void createPlayerContainer() {
    Map<Integer, Player> mockPlayers = new HashMap<>();
    mockPlayers.put(1, new Player(1, collidableContainer.getCollidable(1)));

    PlayerContainer mockPlayerContainer = new PlayerContainer(mockPlayers);

    this.playerContainer = mockPlayerContainer;
  }



  protected void createCollidableContainer() {
    Map<Integer, Collidable> mockCollidables = new HashMap<>();
    mockCollidables.put(1, new Moveable(1, 50, 0, 0, true));
    mockCollidables.put(2, new Surface(2, 50, 0, 0, true, .5));
    mockCollidables.put(3, new Moveable(3, Double.MAX_VALUE, 100, 100, true));
    mockCollidables.put(4, new Surface(4, Double.MAX_VALUE, 70, 70, true, 1));

    this.collidableContainer = new CollidableContainer(mockCollidables);
  }


  protected void createRulesRecord() {
    Map <Pair, Command> myMap = new HashMap<>();
    myMap.put(new Pair(1,4), new SingleCommand(new AdvanceTurnCommand(engine, new ArrayList<>())));
    this.rules = new RulesRecord(1, Integer.MAX_VALUE, new HashMap<>());
  }

  @Override
  public PlayerContainer getPlayerContainer() {
    return playerContainer;
  }

  @Override
  public CollidableContainer getCollidableContainer() {
    return collidableContainer;
  }

  @Override
  public RulesRecord getRulesRecord() {
    return rules;
  }
}
