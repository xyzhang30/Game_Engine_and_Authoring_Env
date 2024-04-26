package oogasalad.view;

import javafx.stage.Screen;
import javafx.stage.Stage;
import oogasalad.view.controller.GameController;
import oogasalad.view.scene_management.scene_managers.GameSceneManager;
import oogasalad.view.scene_management.scene_managers.NonGameSceneManager;

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
  private final NonGameSceneManager nonGameSceneManager;

  /**
   * Constructs a `GameWindow` instance and sets the scene to the main menu. A new stage is created
   * for displaying the game window.
   * <p>
   * This constructor does not require any parameters and uses the primary screen dimensions for
   * initializing the game window.
   */
  public GameWindow() {
    GameController controller = new GameController(SCREEN_WIDTH, SCREEN_HEIGHT);
    nonGameSceneManager = new NonGameSceneManager(controller, SCREEN_WIDTH, SCREEN_HEIGHT);
    Stage newStage = new Stage();
    newStage.setScene(nonGameSceneManager.getScene());
    newStage.setFullScreen(true);
    newStage.show();
  }

  /**
   * Constructs a `GameWindow` instance with the given stage and sets the scene to the title
   * screen.
   * <p>
   * This constructor initializes a `GameController` with the primary screen dimensions and sets the
   * scene to the title screen of the game.
   *
   * @param stage The stage to be used for displaying the game window.
   */
  public GameWindow(Stage stage) {
    GameController controller = new GameController(SCREEN_WIDTH, SCREEN_HEIGHT);
    nonGameSceneManager = new NonGameSceneManager(controller, SCREEN_WIDTH, SCREEN_HEIGHT);
    stage.setScene(nonGameSceneManager.getScene());
    stage.setFullScreen(true);
    stage.show();
  }
}
