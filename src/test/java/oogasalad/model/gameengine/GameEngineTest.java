package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameparser.GameLoaderModel;

public class GameEngineTest {

  private GameEngine gameEngine;
  private GameLoaderModel loader;

  private Collection<GameObject> gameObjects;
  @BeforeEach
  public void setUp() {
    loader = mock(GameLoaderModel.class);
    gameEngine = new GameEngine("threePlayerMiniGolf");
    gameObjects = gameEngine.getGameObjects();
  }

  private GameObjectRecord getRecord(int id) {
    for(GameObject o : gameObjects) {
      if(o.getId()==id) {
        return o.toGameObjectRecord();
      }
    }
    return null;
  }
  @Test
  public void testAdvanceRound() {
    int initialRound = gameEngine.restoreLastStaticGameRecord().round();
    gameEngine.advanceRound();
    assertEquals(initialRound + 1, gameEngine.restoreLastStaticGameRecord().round());
  }

  @Test
  public void testApplyInitialVelocityX() {
    gameEngine.applyInitialVelocity(10, 0);
    assertEquals(10,
        getRecord(8).velocityX());
  }

  @Test
  public void testApplyInitialVelocityY() {
    gameEngine.applyInitialVelocity(10, Math.PI/2);
    assertEquals(10,
        getRecord(8).velocityY(),
        .0001);
  }

  @Test
  public void testApplyInitialVelocityXY() {
    gameEngine.applyInitialVelocity(10*Math.sqrt(2), Math.PI/4);
    assertEquals(10,
        getRecord(8).velocityY());
    assertEquals(10,
        getRecord(8).velocityX(),
        .0001);
  }

  @Test
  public void testAdvanceTurn() {
    gameEngine.update(0);
    assertEquals( 2, gameEngine.restoreLastStaticGameRecord().turn());
    gameEngine.update(0);
    assertEquals( 3, gameEngine.restoreLastStaticGameRecord().turn());
    gameEngine.update(0);
    assertEquals( 1, gameEngine.restoreLastStaticGameRecord().turn());
    gameEngine.update(0);
    assertEquals( 2, gameEngine.restoreLastStaticGameRecord().turn());
    gameEngine.update(0);
    assertEquals( 3, gameEngine.restoreLastStaticGameRecord().turn());
    gameEngine.update(0);
    assertEquals( 1, gameEngine.restoreLastStaticGameRecord().turn());
  }



}
