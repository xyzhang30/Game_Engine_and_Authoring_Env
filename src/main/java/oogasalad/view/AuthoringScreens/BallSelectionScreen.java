package oogasalad.view.AuthoringScreens;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes ball objects in their unique
 * game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class BallSelectionScreen extends AuthoringScreen {

  public BallSelectionScreen(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    createTitle("Ball Selection");
    root.getChildren().add(authoringBox);
    createSizeAndAngleSliders();
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * Called when user presses submit, triggers passing of info to back end
   */
  void endSelection() {
    //TODO: implement
  }

  /**
   * Returns ball image type indicating that user is placing ball objects
   *
   * @return enum to represent ball image type
   */
  ImageType getImageType() {
    return ImageType.BALL;
  }

  /**
   * Updates ball options for currently selected ball
   */
  void updateOptionSelections() {

  }
}
