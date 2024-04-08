package oogasalad.view.AuthoringScreens;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Controlling.AuthoringController;


public class GoalSelectionScreen extends AuthoringScreen{

  public GoalSelectionScreen(AuthoringController controller, Rectangle backgroundBox){
    this.controller = controller;
    createScene(backgroundBox);
  }

  private void createScene(Rectangle backgroundBox){
    root = new StackPane();
    root.getChildren().add(backgroundBox);
    createOptionBox();
    scene = new Scene(root, screenWidth, screenHeight);
  }
  void endSelection(){}

  void changeColor(){}

  void changeImage(){}

}
