package oogasalad.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to represent the stage and scene being shown to the user
 *
 * @author Jordan Haytaian
 */
public class Window {

  private final Stage stage;

  public Window(Stage stage, Scene scene, int id) {
    this.stage = stage;
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Method for changing scene shown on stage
   *
   * @param scene new scene to be shown
   */
  public void changeScene(Scene scene) {
    stage.setScene(scene);
    stage.show();
  }

}
