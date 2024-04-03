package oogasalad;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.view.Screen.TitleScreen;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
  }

  /**
   * Start of the program.
   */
  public static void main(String[] args) {
    Main m = new Main();
    System.out.println(m.getVersion());
  }

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }
}
