package oogasalad.view.gameplay;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class SceneManagerTest extends DukeApplicationTest {

  private SceneManager sceneManager;
  private Stage testStage;

  @BeforeEach
  public void setUp() throws InterruptedException {
    Platform.runLater(() -> {
      testStage = new Stage();
      sceneManager = new SceneManager(new GameController(800, 600), 800, 600);
      testStage.setScene(sceneManager.getScene());
      testStage.show();
    });
    waitForFxEvents(); // Ensure the stage is shown and scene is fully loaded
  }

  @Test
  public void testCreateTitleScene() {
    Platform.runLater(() -> sceneManager.createTitleScene());
    waitForFxEvents(); // Wait for the scene to update
    Platform.runLater(() -> {
      Node titleNode = lookup("#titleElement").query();
      assertNotNull(titleNode, "Title scene should contain title element with ID 'titleElement'");
    });
  }

  @Test
  public void testLanguageSelectionScene() {
    Platform.runLater(() -> sceneManager.createLanguageSelectionScene());
    waitForFxEvents(); // Ensure the scene changes are reflected
    Platform.runLater(() -> {
      Node languageSelector = lookup("#languageElement").query();
      assertNotNull(languageSelector, "Language selection scene should contain a language selector");
    });
  }

  @Test
  public void testCreateHelpInstructions() {
    Platform.runLater(() -> sceneManager.createHelpInstructions());
    waitForFxEvents();
    Platform.runLater(() -> {
      Node helpNode = lookup("#helpElement").query();
      assertNotNull(helpNode, "Help scene should contain help element with ID 'helpElement'");
    });
  }

  @Test
  public void testGameOverScene() {
    Platform.runLater(() -> sceneManager.createGameOverScene());
    waitForFxEvents(); // Ensure game over scene is loaded
    Platform.runLater(() -> {
      Node gameOverText = lookup("#gameOverText").query();
      assertNotNull(gameOverText, "Game over scene should show game over text");
    });
  }
}
