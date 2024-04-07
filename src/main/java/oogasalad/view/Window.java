package oogasalad.view;

import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.view.Controlling.GameController;

/**
 * Class to represent the stage and scene being shown to the user
 *
 * @author Jordan Haytaian
 */
public class Window {

  public final static double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
  public final static double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
  public final static String TITLE = "FYSICS FUN";

  public Window(int id) {
    this(new Stage(), id);
  }

  public Window(Stage stage, int id) {
    GameController controller = new GameController(stage); // Dirty fix. Change once scene swapped by root.
    stage.setTitle(TITLE); // Port this hard coding into data.
//    stage.setWidth(SCREEN_WIDTH * 0.8);
//    stage.setHeight(SCREEN_HEIGHT * 0.8);
    stage.setFullScreen(true);
    stage.show();
  }
}
