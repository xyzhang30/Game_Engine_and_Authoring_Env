package oogasalad.view.game_environment.scene_management;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.database.Leaderboard;
import oogasalad.view.scene_management.scene_element.scene_element_handler.StrikeHandler;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import oogasalad.view.visual_elements.Arrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import util.DukeApplicationTest;

/**
 * Unit tests for the StrikeHandler class.
 * @author Doga Ozmen
 */
public class StrikeHandlerTest extends ApplicationTest {

  private StrikeHandler strikeHandler;
  private Rectangle powerMeter;
  private Arrow angleArrow;
  private GameController gameController;

  @BeforeEach
  public void setup() {
    powerMeter = new Rectangle(0, 0, 100, 1000);
    angleArrow = new Arrow();

    gameController = new GameController(800, 600);
    gameController.startGamePlay("data/playable_games/billiards");
    gameController.runGameAndCheckStatic((double) 1 /120);
    List<String> buffer = new ArrayList<>();
    SceneManager sceneManager = new SceneManager(gameController, new DatabaseController(new Leaderboard(), buffer), 500, 400, buffer);
    strikeHandler = new StrikeHandler(gameController, sceneManager);

    strikeHandler.createPowerHandler(powerMeter);
    strikeHandler.setAngleArrow(angleArrow);
  }

  @Test
  public void testIncreasePower() {
    Platform.runLater(() -> {
      double initialHeight = powerMeter.getHeight();
      strikeHandler.getMaxPower(powerMeter);
      strikeHandler.increasePower();
      double newHeight = powerMeter.getHeight();
      assertFalse(newHeight > initialHeight, "Power should not increase, because already at max power");
    });
    WaitForAsyncUtils.waitForFxEvents();
  }


  @Test
  public void testIncreaseAngle() {
    double initialRotation = angleArrow.getRotate();
    strikeHandler.increaseAngle();
    assertTrue(angleArrow.getRotate() > initialRotation);
  }
}
