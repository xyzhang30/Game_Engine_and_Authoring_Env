package oogasalad.view.AuthoringScreens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes ball objects in their unique
 * game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class ControllableElementSelectionScreen extends AuthoringScreen {

  private List<Shape> controllableList;

  public ControllableElementSelectionScreen(AuthoringController controller,
      StackPane authoringBox, Map<Shape, List<Double>> posMap,
      Map<Shape, NonControllableType> nonControllableMap,
      List<Shape> controllableList, Map<Shape, String> imageMap) {
    super(controller, authoringBox, posMap, nonControllableMap, controllableList, imageMap);
    this.controllableList = controllableList;
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new AnchorPane();
    createTitle("Controllable Selection");
    root.getChildren().add(authoringBox);
    addElements();
    createSizeAndAngleSliders();
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    createTransitionButton("Next");
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * Called when user presses submit, triggers passing of info to back end
   */
  void endSelection() {
    addNewSelectionsToAuthoringBox();
    for (Shape shape : selectableShapes) {
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if (authoringBoxBounds.contains(shapeBounds)) {
        controllableList.add(shape);
      }
    }
    controller.startNextSelection(ImageType.CONTROLLABLE_ELEMENT, authoringBox, posMap,
        nonControllableMap,
        controllableList, imageMap);
  }

  /**
   * Returns ball image type indicating that user is placing ball objects
   *
   * @return enum to represent ball image type
   */
  ImageType getImageType() {
    return ImageType.CONTROLLABLE_ELEMENT;
  }

  /**
   * Updates ball options for currently selected ball
   */
  void updateOptionSelections() {//TODO: Can this be empty/ are there ball options?
  }
}
