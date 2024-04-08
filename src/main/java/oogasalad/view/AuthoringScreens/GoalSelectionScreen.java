package oogasalad.view.AuthoringScreens;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which user places and customizes goal objects in their unique
 * game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class GoalSelectionScreen extends AuthoringScreen {

  public GoalSelectionScreen(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    root.getChildren().add(authoringBox);
    createShapeDisplayOptionBox();
    createDraggableShapeTemplates();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * When the next button is clicked, controller is prompted to start the next selection process
   */
  void endSelection() {
    //TODO: add goal elements to authoringbox stackpane
    controller.startNextSelection(ImageType.GOAL, authoringBox);
  }

  /**
   * Returns goal image type indicating that user is placing goal objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.GOAL;
  }

}
