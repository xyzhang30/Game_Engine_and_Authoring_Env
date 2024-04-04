package oogasalad.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Class to manage animations
 *
 * @author Doga Ozmen, Jordan Haytaian
 */
public class AnimationManager {

  private final int FRAMES_PER_SECOND = 60;
  private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private final Timeline animation;

  public AnimationManager() {
    animation = new Timeline();
  }

  /**
   * Getter for animation time step
   *
   * @return animation time step
   */
  public double getTimeStep() {
    return SECOND_DELAY;
  }

  /**
   * Starts the animation, runs until runGame returns false indicating that round is over
   */
  public void runAnimation(Controller controller) {
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
      if (controller.runGame(SECOND_DELAY)) {
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY)));
      } else {
        animation.stop();
      }
    }));
    animation.play();
  }

  public void pauseAnimation() {
    animation.pause();
  }
}