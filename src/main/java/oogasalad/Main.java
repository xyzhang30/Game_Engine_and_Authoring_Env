package oogasalad;


import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.view.AuthoringScreens.AuthoringScreen;
import oogasalad.view.AuthoringScreens.SurfaceSelectionScreen;
import oogasalad.view.Controlling.AuthoringController;
import oogasalad.view.Window;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    SurfaceSelectionScreen scene = new SurfaceSelectionScreen(new AuthoringController(primaryStage),
        new StackPane());
    primaryStage.setScene(scene.getScene());
    primaryStage.show();
    //Window mainWindow = new Window(primaryStage, 0);
  }
//
//  /**
//   * Start of the program.
//   */
//  public static void main(String[] args) {
//    Main m = new Main();
//    System.out.println(m.getVersion());
//  }
//
//  /**
//   * A method to test (and a joke :).
//   */
//  public double getVersion() {
//    return 0.001;
//  }
}
