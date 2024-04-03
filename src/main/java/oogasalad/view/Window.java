package oogasalad.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Window {
  private final Scene scene;
  private final Controller controller;
  private final Stage stage;
  public Window(Stage stage, int id){
    this.stage = stage;
    StackPane root = new StackPane(); //
    scene = new Scene(root);
    stage.setScene(scene);
    controller = new Controller(id, scene);
    stage.show();
    controller.startTitleListening();
  }


}
