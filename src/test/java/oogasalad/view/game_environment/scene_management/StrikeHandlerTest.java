package oogasalad.view.game_environment.scene_management;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
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
import util.DukeApplicationTest;

// Assuming we are testing methods in StrikeHandler that modify the power meter and angle arrow
public class StrikeHandlerTest extends ApplicationTest {

  private StrikeHandler strikeHandler;
  private Rectangle powerMeter;
  private Arrow angleArrow;
  private GameController gameController;

  @BeforeEach
  public void setup() {
    // Initialize the real objects
    powerMeter = new Rectangle(0, 0, 100, 10);  // Example dimensions
    angleArrow = new Arrow();  // Assuming Arrow has a no-arg constructor

    gameController = new GameController(800, 600);
    gameController.startGamePlay("data/playable_games/billiards");
    List<String> buffer = new ArrayList<>();
    SceneManager sceneManager = new SceneManager(gameController, new DatabaseController(new Leaderboard(), buffer), 500, 400, buffer);
    strikeHandler = new StrikeHandler(gameController, sceneManager);

    // Inject the real instances into the handler
    strikeHandler.createPowerHandler(powerMeter);
    strikeHandler.setAngleArrow(angleArrow);
  }

  @Test
  public void testIncreasePower() {
    double initialHeight = powerMeter.getHeight();
    strikeHandler.increasePower();  // Assuming this method exists and modifies height
    assertTrue(powerMeter.getHeight() > initialHeight);
  }

  @Test
  public void testIncreaseAngle() {
    double initialRotation = angleArrow.getRotate();
    strikeHandler.increaseAngle();  // Assuming this method exists and modifies rotation
    assertTrue(angleArrow.getRotate() > initialRotation);
  }
}
