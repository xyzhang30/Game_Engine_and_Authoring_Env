package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
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
public class NonControllableElementSelection extends AuthoringScreen {

  private ComboBox<NonControllableType> obstacleTypeComboBox;

  private Map<Shape, NonControllableType> obstacleTypeMap;


  public NonControllableElementSelection(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
    obstacleTypeMap = new HashMap<>();
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    createTitle("NonControllable Selection");
    root.getChildren().add(authoringBox);
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
    NonControllableType type = obstacleTypeMap.getOrDefault(selectedShape, null);
    obstacleTypeComboBox.setValue(type);
  }

  void createTypeDropDown() {
    obstacleTypeComboBox = new ComboBox<>();
    obstacleTypeComboBox.getItems()
        .addAll(NonControllableType.SURFACE, NonControllableType.OBJECT);
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
      controller.startNextSelection(ImageType.NONCONTROLLABLE_ELEMENT, authoringBox); // Adjust NEXT_TYPE to whatever comes next
    } else {
      // TODO: Show a message to the user explaining that not all obstacles have types assigned
      System.out.println("Please assign types to all obstacles.");
    }
  }


  /**
   * Returns obstacle image type indicating that user is placing obstacle objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.NONCONTROLLABLE_ELEMENT;
  }
}
