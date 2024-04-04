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
  public Window(int id) {
    this(new Stage(), id);
  }
  public Window(Stage stage, int id) {
    this.stage = stage;
    Controller controller = new Controller(stage); // Dirty fix. Change once scene swapped by root.
    stage.setTitle("Fysics Fun"); // Port this hard coding into data.
    stage.show();
  }
}
