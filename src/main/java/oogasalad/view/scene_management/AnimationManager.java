package oogasalad.view.scene_management;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import oogasalad.view.controller.GameController;

/**
 * Class to manage animations
 *
 * @author Doga Ozmen, Jordan Haytaian
 */
public class AnimationManager {

  private final int FRAMES_PER_SECOND = 120;
  private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private final Timeline animation;
  private boolean running;

  public AnimationManager() {
    animation = new Timeline();
  }

  /**
   * Starts the animation, runs until runGame returns false indicating that round is over
   *
   * @param controller used to request updates from model as animation runs
   */
  public void runAnimation(GameController controller) {
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
      if (controller.runGameAndCheckStatic(SECOND_DELAY)) {
        animation.stop();
        running = false;
      }
    }));
    animation.play();
    running = true;
  }

  /**
   * Pause the animation
   */
  public void pauseAnimation() {
    animation.pause();
    running = false;
  }

  /**
   * Resume the animation
   */
  public void resumeAnimation() {
    animation.play();
    running = true;
  }

  public boolean isRunning() {
    return running;
  }
}