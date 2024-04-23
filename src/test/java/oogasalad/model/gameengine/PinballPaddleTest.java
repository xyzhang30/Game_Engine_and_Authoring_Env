package oogasalad.model.gameengine;

import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.gameengine.gameobject.GameObjectContainer;
import org.junit.jupiter.api.BeforeEach;

public class PinballPaddleTest {
  private static final double DELTA = .0001;
  private GameEngine gameEngine;
  private GameObjectContainer container;

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine("testPhysics");
    container = gameEngine.getGameObjectContainer();
  }

  private boolean isStatic(GameRecord r) {
    for (GameObjectRecord cr : r.gameObjectRecords()) {
      if (cr.velocityY() != 0 || cr.velocityX() != 0) {
        return false;
      }
    }
    return true;
  }



}
