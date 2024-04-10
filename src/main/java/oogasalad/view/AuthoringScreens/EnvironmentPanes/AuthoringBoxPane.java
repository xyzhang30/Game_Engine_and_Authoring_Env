package oogasalad.view.AuthoringScreens.EnvironmentPanes;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.input.MouseEvent;

/**
 * A custom pane for the authoring environment that supports adding, selecting, and dragging shapes.
 *
 * @author Doga Ozmen
 */
public class AuthoringBoxPane extends StackPane {
  // Constructor
  public AuthoringBoxPane() {
    super();
    initializePane();
  }

  // Initialize the pane with default configurations
  private void initializePane() {
    // Set default styles or configurations if needed
    this.setStyle("-fx-background-color: #EEE;"); // Example style
    // Add any other initialization logic here
  }

  /**
   * Adds a shape to the authoring box and makes it interactive (selectable and draggable).
   * @param shape The shape to be added to the authoring box.
   */
  public void addShape(Shape shape) {
    makeDraggable(shape);
    makeSelectable(shape);
    this.getChildren().add(shape);
  }

  // Make a shape draggable within the authoring box
  private void makeDraggable(Shape shape) {
    final double[] initialPosition = new double[2];

    shape.setOnMousePressed(event -> {
      initialPosition[0] = event.getSceneX() - shape.getTranslateX();
      initialPosition[1] = event.getSceneY() - shape.getTranslateY();
    });

    shape.setOnMouseDragged(event -> {
      shape.setTranslateX(event.getSceneX() - initialPosition[0]);
      shape.setTranslateY(event.getSceneY() - initialPosition[1]);
    });
  }

  // Make a shape selectable within the authoring box
  private void makeSelectable(Shape shape) {
    shape.setOnMouseClicked(event -> {

      shape.setStroke(Color.BLUE); // Example: Change border to blue to indicate selection
      shape.setStrokeWidth(2); // Example: Set stroke width to indicate selection

      // Deselect other shapes if necessary or implement a mechanism to allow multiple selections
      deselectOtherShapes(shape);
    });
  }

  // Deselect all shapes except the passed shape
  private void deselectOtherShapes(Shape selectedShape) {
    this.getChildren().forEach(node -> {
      if (node instanceof Shape && node != selectedShape) {
        ((Shape) node).setStroke(null); // Remove the stroke to indicate deselection
        ((Shape) node).setStrokeWidth(0); // Reset stroke width
      }
    });
  }
}
