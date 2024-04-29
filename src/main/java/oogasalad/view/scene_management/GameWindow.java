package oogasalad.view.scene_management;

import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;

/**
 * The `GameWindow` class represents the stage being shown to the user It provides functionality to
 * initialize and display the game window with the appropriate scene based on the context in which
 * it is used.
 *
 * @author Jordan Haytaian
 */
public class GameWindow {

  public final static double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
  public final static double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

  /**
   * Constructs a `GameWindow` instance. A new stage is created for displaying the game window.
   * <p>
   * This constructor does not require any parameters and uses the primary screen dimensions for
   * initializing the game window.
   */
  public GameWindow() {
    this(new Stage());
  }

  /**
   * Constructs a `GameWindow` instance with the given stage.
   *
   * @param stage The stage to be used for displaying the game window.
   */
  public GameWindow(Stage stage) {
    GameController controller = new GameController(SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(controller.getScene());
    stage.setFullScreen(true);
    stage.show();
  }
}
