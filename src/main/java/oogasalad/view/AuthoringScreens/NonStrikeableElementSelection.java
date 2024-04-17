package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes obstacle objects in their
 * unique game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class NonStrikeableElementSelection extends AuthoringScreen {

  private ComboBox<NonStrikeableType> obstacleTypeComboBox;

  private Map<Shape, NonStrikeableType> obstacleTypeMap;


  public NonStrikeableElementSelection(AuthoringController controller, StackPane authoringBox,
      Map<Shape, List<Double>> posMap,
      Map<Shape, NonStrikeableType> nonStrikeableMap, List<Shape> strikeableList,
      Map<Shape, String> imageMap) {
    super(controller, authoringBox, posMap, nonStrikeableMap, strikeableList, imageMap);
    obstacleTypeMap = new HashMap<>();
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new AnchorPane();
    createTitle("NonStrikeable Selection");
    root.getChildren().add(authoringBox);
    addElements();
    createSizeAndAngleSliders();
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    createTypeDropDown();
    createTransitionButton("Next");
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * Updates object type to reflect type of currently selected object
   */
  void updateOptionSelections() {
    NonStrikeableType type = obstacleTypeMap.getOrDefault(selectedShape, null);
    obstacleTypeComboBox.setValue(type);
  }

  void createTypeDropDown() {
    obstacleTypeComboBox = new ComboBox<>();
    obstacleTypeComboBox.getItems()
        .addAll(NonStrikeableType.SURFACE, NonStrikeableType.COLLIDABLE);
    obstacleTypeComboBox.setPromptText("Select Obstacle Type");
    AnchorPane.setRightAnchor(obstacleTypeComboBox, 50.0);
    AnchorPane.setBottomAnchor(obstacleTypeComboBox, 300.0);
    obstacleTypeComboBox.setPrefSize(200, 100);
    root.getChildren().add(obstacleTypeComboBox);

    // Listen for changes and update the map
    obstacleTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
      if (selectedShape != null && newVal != null) {
        obstacleTypeMap.put(selectedShape, newVal);
      }
    });
  }

  private boolean allSelectionsMade() {
    for (Shape shape : selectableShapes) {
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if (authoringBoxBounds.contains(shapeBounds)) {
        if (!obstacleTypeMap.containsKey(shape)) {
          return false; // Found an obstacle without an assigned type
        }
      }
    }
    return true; // All obstacles have an assigned type
  }

  /**
   * When the next button is clicked, controller is prompted to start the next selection process
   */
  @Override
  void endSelection() {
    if (allSelectionsMade()) {
      addNewSelectionsToAuthoringBox();

      for (Shape shape : selectableShapes) {
        Bounds shapeBounds = shape.getBoundsInParent();
        Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

        if (authoringBoxBounds.contains(shapeBounds)) {
          NonStrikeableType type = obstacleTypeMap.get(shape);
          nonStrikeableMap.put(shape, type);
        }
      }

      controller.startNextSelection(ImageType.NONSTRIKEABLE_ELEMENT,
          authoringBox, posMap, nonStrikeableMap,
          strikeableList, imageMap); // Adjust NEXT_TYPE to whatever comes next
    } else {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText("Assign A Type To All Elements Before Continuing");
      alert.showAndWait();
    }
  }


  /**
   * Returns obstacle image type indicating that user is placing obstacle objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.NONSTRIKEABLE_ELEMENT;
  }
}
