package oogasalad;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.view.Window;
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    new Window(primaryStage);
  }
}