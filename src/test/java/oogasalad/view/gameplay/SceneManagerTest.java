package oogasalad.view.gameplay;

import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import javafx.application.Platform;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


/**
 * Test class for SceneManager using DukeApplicationTest to simulate UI interactions.
 *
 * @author Doga Ozmen
 */

public class SceneManagerTest extends DukeApplicationTest {

  private SceneManager sceneManager;
  private Stage testStage;

  @BeforeEach
  public void setUp() {
    Platform.runLater(() -> {
      testStage = new Stage();
      sceneManager = new SceneManager(new GameController(800, 600), 800, 600);
      testStage.setScene(sceneManager.getScene());
      testStage.show();
    });

    waitForFxEvents();
  }

  @Test
  public void testCreateTitleScene() {
    Platform.runLater(() -> {
      sceneManager.createTitleScene();
    });
    waitForFxEvents();
  }

}
