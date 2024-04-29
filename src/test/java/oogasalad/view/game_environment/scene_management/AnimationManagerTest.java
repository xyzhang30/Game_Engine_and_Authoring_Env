package oogasalad.view.game_environment.scene_management;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.application.Platform;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.AnimationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * Unit tests for the AnimationManager class.
 * @author Doga Ozmen
 */
public class AnimationManagerTest extends DukeApplicationTest {

  private AnimationManager animationManager;
  private GameController gameController;

  @BeforeEach
  public void setUp() {
    gameController = new GameController(800, 600);
    gameController.startGamePlay("data/playable_games/billiards.json");
    animationManager = new AnimationManager();
  }

  @Test
  public void testRunAnimation() {
    Platform.runLater(() -> {
      animationManager.runAnimation(gameController);
      assertTrue(animationManager.isRunning());
      gameController.runGameAndCheckStatic(1.0 / 120);  //time step
      assertTrue(animationManager.isRunning());
    });

    //stop
    Platform.runLater(() -> animationManager.pauseAnimation());
  }

  @Test
  public void testPauseAnimation() {
    Platform.runLater(() -> {
      animationManager.runAnimation(gameController);
      animationManager.pauseAnimation();
      assertFalse(animationManager.isRunning());
    });
  }

  @Test
  public void testResumeAnimation() {
    Platform.runLater(() -> {
      animationManager.runAnimation(gameController);
      animationManager.pauseAnimation();
      animationManager.resumeAnimation();
      assertTrue(animationManager.isRunning());
    });
  }
}
