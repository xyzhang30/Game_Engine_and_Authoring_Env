package oogasalad.view.AuthoringScreens;


import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Shape;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Controlling.AuthoringController;


public class GoalSelectionScreen extends AuthoringScreen{

  double orgSceneX, orgSceneY;
  double orgTranslateX, orgTranslateY;
  private List<Shape> goals;

  public GoalSelectionScreen(AuthoringController controller, Rectangle backgroundBox){
    goals = new ArrayList<>();
    this.controller = controller;
    createScene(backgroundBox);
  }

  private void createScene(Rectangle backgroundBox){
    root = new StackPane();
    root.getChildren().add(backgroundBox);
    createOptionBox();
    createDraggableShapes();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  private void createDraggableShapes(){
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    StackPane.setAlignment(rectangle, Pos.TOP_RIGHT);
    StackPane.setMargin(rectangle, new Insets(300, 150, 0, 0));

    Circle circle = new Circle(30, Color.BLACK);
    StackPane.setAlignment(circle, Pos.TOP_RIGHT);
    StackPane.setMargin(circle, new Insets(300, 50, 0, 0));

    goals.add(rectangle);
    goals.add(circle);

    makeSelectable(rectangle, goals);
    makeSelectable(circle, goals);

    root.getChildren().addAll(rectangle, circle);

    rectangle.setOnMousePressed((MouseEvent event) -> {
      orgSceneX = event.getSceneX();
      orgSceneY = event.getSceneY();
      orgTranslateX = rectangle.getTranslateX();
      orgTranslateY = rectangle.getTranslateY();
    });

    rectangle.setOnMouseDragged((MouseEvent event) -> {
      double offsetX = event.getSceneX() - orgSceneX;
      double offsetY = event.getSceneY() - orgSceneY;
      double newTranslateX = orgTranslateX + offsetX;
      double newTranslateY = orgTranslateY + offsetY;
      rectangle.setTranslateX(newTranslateX);
      rectangle.setTranslateY(newTranslateY);
    });
  }
  void endSelection(){}

  void changeColor(){}

  void changeImage(){}

}
