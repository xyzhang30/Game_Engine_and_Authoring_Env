package oogasalad.view.authoring_environment.authoring_screens.AuthoringHandlers;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DraggableShapeTemplates {
  private Pane container;

  public DraggableShapeTemplates(Pane container) {
    this.container = container;
  }

  public void initialize() {
    createDraggableRectangle();
    createDraggableEllipse();
  }

  private void createDraggableRectangle() {
    Rectangle rectangle = new Rectangle(100, 50);
    rectangle.setCursor(Cursor.MOVE);
    makeDraggable(rectangle);
    container.getChildren().add(rectangle);
  }

  private void createDraggableEllipse() {
    Ellipse ellipse = new Ellipse(50, 25);
    ellipse.setCursor(Cursor.MOVE);
    makeDraggable(ellipse);
    container.getChildren().add(ellipse);
  }

  private void makeDraggable(Shape shape) {
    Delta dragDelta = new Delta();

    shape.setOnMousePressed(mouseEvent -> {
      // Record the current mouse X and Y position on Node
      dragDelta.x = shape.getLayoutX() - mouseEvent.getSceneX();
      dragDelta.y = shape.getLayoutY() - mouseEvent.getSceneY();
      shape.setCursor(Cursor.MOVE);
    });

    shape.setOnMouseDragged(mouseEvent -> {
      // Set the position of the node after calculation
      shape.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
      shape.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
    });

    shape.setOnMouseReleased(mouseEvent -> {
      shape.setCursor(Cursor.HAND);
    });
  }

  // Records relative x and y co-ordinates.
  private static class Delta {
    double x, y;
  }
}
