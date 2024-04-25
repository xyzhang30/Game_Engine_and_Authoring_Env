package oogasalad.view;

import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;

/**
 * Class to represent the stage and scene being shown to the user
 *
 * @author Jordan Haytaian
 */
public class Window {

  public final static double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
  public final static double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();

  public Window(Stage stage) {
    GameController controller = new GameController(SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(controller.getScene());
    stage.setFullScreen(true);
    stage.show();
  }
}
