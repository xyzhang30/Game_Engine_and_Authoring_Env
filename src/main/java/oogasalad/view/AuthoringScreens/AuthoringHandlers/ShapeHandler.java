package oogasalad.view.AuthoringScreens.AuthoringHandlers;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShapeHandler {
  private List<Shape> selectableShapes = new ArrayList<>();
  private Shape selectedShape = null;
  private final StackPane root;
  private Consumer<Shape> onShapeSelected;

  public ShapeHandler(StackPane root) {
    this.root = root;
  }

  public void setOnShapeSelected(Consumer<Shape> onShapeSelected) {
    this.onShapeSelected = onShapeSelected;
  }

  public void addShape(Shape shape) {
    makeDraggable(shape);
    makeSelectable(shape);
    root.getChildren().add(shape);
  }

  private void makeDraggable(Shape shape) {
    shape.setOnMousePressed(event -> {
      double offsetX = event.getSceneX() - shape.getLayoutX();
      double offsetY = event.getSceneY() - shape.getLayoutY();
      shape.setUserData(new double[]{offsetX, offsetY});
    });

    shape.setOnMouseDragged(event -> {
      double[] offset = (double[]) shape.getUserData();
      shape.setLayoutX(event.getSceneX() - offset[0]);
      shape.setLayoutY(event.getSceneY() - offset[1]);
    });
  }

  private void makeSelectable(Shape shape) {
    shape.setOnMouseClicked(event -> {
      if (selectedShape != null) {
        selectedShape.setStrokeWidth(0); // Deselect previous shape
      }
      selectedShape = shape;
      shape.setStroke(Color.YELLOW);
      shape.setStrokeWidth(3);
      if (onShapeSelected != null) {
        onShapeSelected.accept(shape);
      }
    });
  }

  public Shape getSelectedShape() {
    return selectedShape;
  }
}
