package oogasalad.model.gameengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.gameobject.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestVisibleBalls {



  private static final String TITLE = "data/playable_games/threePlayerMiniGolfHoleTwo";


  private GameEngine gameEngine;
  private boolean isStatic(GameRecord r) {
    for (GameObjectRecord cr : r.gameObjectRecords()) {
      if (cr.visible() && (cr.velocityY() != 0 || cr.velocityX() != 0)) {
        return false;
      }
    }
    return true;
  }

  @BeforeEach
  public void setUp() {
    gameEngine = new GameEngine(TITLE);
  }

  @Test
  public void testMiniGolf() {

for (GameObject g : gameEngine.getGameObjects())
  {
    System.out.println(g.getId() + " " + g.getVisible());
      assertTrue((g.getId()!=19 && g.getId()!=20) == g.getVisible());
    }
  }

}

