package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes obstacle objects in their
 * unique game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class ObstacleSelectionScreen extends AuthoringScreen{
  private ComboBox<ObstacleType> obstacleTypeComboBox;

  private Map<Shape, ObstacleType> obstacleTypeMap;


  public ObstacleSelectionScreen(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
    obstacleTypeMap = new HashMap<>();
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    root.getChildren().add(authoringBox);
    createSizeAndAngleSliders();
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    createNextButton();
    createObstacleTypeDropdown();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  void createObstacleTypeDropdown() {
    obstacleTypeComboBox = new ComboBox<>();
    obstacleTypeComboBox.getItems().addAll(ObstacleType.BOUNCE, ObstacleType.RESET, ObstacleType.SLOW);
    obstacleTypeComboBox.setPromptText("Select Obstacle Type");
    StackPane.setAlignment(obstacleTypeComboBox, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(obstacleTypeComboBox, new Insets(0, 50, 350, 0));
    obstacleTypeComboBox.setPrefSize(200, 100);
    root.getChildren().add(obstacleTypeComboBox);

    // Listen for changes and update the map
    obstacleTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
      if (selectedShape != null && newVal != null) {
        obstacleTypeMap.put(selectedShape, newVal);
      }
    });
  }


  private void makeSelectable(Shape shape) {
    shape.setOnMouseClicked(event -> {
      selectedShape = shape;
      ObstacleType type = obstacleTypeMap.getOrDefault(shape, null);
      obstacleTypeComboBox.setValue(type); // sets the ComboBox to the shape's type or null if not present
    });
  }

  /**
   * When the next button is clicked, controller is prompted to start the next selection process
   */
  void endSelection() {
    printShapesAndTheirTypes();
    addNewSelectionsToAuthoringBox();
    controller.startNextSelection(ImageType.GOAL, authoringBox);
  }

  private void printShapesAndTheirTypes() {
    obstacleTypeMap.forEach((shape, type) -> {
      // Assuming shapes don't have a custom ID, we'll use the class name and hash code for identification
      String identifier = shape.getClass().getSimpleName() + "@" + Integer.toHexString(shape.hashCode());
      System.out.println("Shape: " + identifier + ", Type: " + type);
    });
  }

  @Override
  void createDraggableShapeTemplates() {
    super.createDraggableShapeTemplates();
    selectableShapes.forEach(this::makeSelectable);
  }



  /**
   * Returns obstacle image type indicating that user is placing obstacle objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.OBSTACLE;
  }


}
