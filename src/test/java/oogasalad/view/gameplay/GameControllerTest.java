package oogasalad.view.gameplay;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import util.DukeApplicationTest;

/**
 * Test class for GameController using DukeApplicationTest to simulate UI interactions.
 *
 * @author Doga Ozmen
 */
public class GameControllerTest extends DukeApplicationTest {

  private GameController gameController;
  private Scene scene;

  /**
   * Setup method to initialize the environment for each test.
   * @param stage The primary stage for this test.
   */
  @Start
  public void start(Stage stage) {
    gameController = new GameController(800, 600);
    scene = gameController.getScene();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Test pausing the game.
   */
  @Test
  public void testPauseGame() {
    runAsJFXAction(() -> gameController.pauseGame());
  }

  /**
   * Test resuming the game.
   */
  @Test
  public void testResumeGame() {
    runAsJFXAction(() -> gameController.resumeGame());

  }

  @Test
  public void testGameInitialization() {
    assertNotNull(gameController, "GameController should not be null after initialization.");
    assertNotNull(scene, "Scene should not be null after initialization.");
  }




  /**
   * Test starting a game play.
   */
//  @Test
//  public void testStartGamePlay() {
//   // runAsJFXAction(() -> gameController.startGamePlay("data/playable_games/shuffleboard.json"));
//
//  }
}
