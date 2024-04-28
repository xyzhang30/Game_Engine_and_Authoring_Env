package oogasalad.view.gameplay;

import java.util.List;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Stage;
import oogasalad.view.controller.DatabaseController;
import oogasalad.view.controller.GameController;
import oogasalad.view.database.Leaderboard;
import oogasalad.view.scene_management.scene_managers.SceneManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import java.util.ArrayList;

public class SceneManagerTest extends DukeApplicationTest {

  private SceneManager sceneManager;
  private Stage testStage;

  @BeforeEach
  public void setUp() throws InterruptedException {
    Platform.runLater(() -> {
      testStage = new Stage();

      GameController gameController = new GameController(800, 600);
      List<String> currentPlayersManager = new ArrayList<>();
      DatabaseController databaseController = new DatabaseController(new Leaderboard(), currentPlayersManager);

      sceneManager = new SceneManager(gameController, databaseController, 800, 600, currentPlayersManager);
      testStage.setScene(sceneManager.getScene());
      testStage.show();
    });
    waitForFxEvents(); // Ensure the stage is shown and scene is fully loaded
  }

  @Test
  public void testCreateTitleScene() {
    Platform.runLater(() -> sceneManager.createTitleScene());
    waitForFxEvents();
    Platform.runLater(() -> {
      Node titleNode = lookup("#titleElement").query();
      assertNotNull(titleNode, "Title scene should contain title element with ID 'titleElement'");
    });
  }

  @Test
  public void testLanguageSelectionScene() {
    Platform.runLater(() -> sceneManager.createLanguageSelectionScene());
    waitForFxEvents();
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

}
