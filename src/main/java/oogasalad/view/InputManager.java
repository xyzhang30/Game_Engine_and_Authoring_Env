package oogasalad.view;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import oogasalad.view.VisualElements.InputIndicators.AngleIndicator;
import oogasalad.view.VisualElements.InputIndicators.PowerIndicator;

/**
 * Manager for user inputs.
 */
public class InputManager {
  private AngleIndicator angleDevice;
  private PowerIndicator powerDevice;
  public InputManager(AngleIndicator ang, PowerIndicator pow){
    // Receive devices from indicator factory
    angleDevice = ang;
    powerDevice = pow;
  }
  // Some form of getter for nodes, or roll into composite elements.
  public void animate(Timeline animation){
    // animates devices.
  }
  public void attachListeners(Scene scene){
    // binds trigger events.
  }
}
