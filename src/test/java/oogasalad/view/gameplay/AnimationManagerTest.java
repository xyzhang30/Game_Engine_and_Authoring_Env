package oogasalad.view.gameplay;

import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.AnimationManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class AnimationManagerTest extends DukeApplicationTest {
  private AnimationManager animationManager;
  @BeforeEach
  public void setup(){
    animationManager = new AnimationManager();
  }
  @Test
  void testStartAnimation(){
    animationManager.runAnimation(new GameController(10,10));
  }
  @Test
  public void testPausePlay(){
    animationManager.pauseAnimation();
    Assertions.assertFalse(animationManager.isRunning());
    animationManager.resumeAnimation();
    Assertions.assertTrue(animationManager.isRunning());
  }
}
