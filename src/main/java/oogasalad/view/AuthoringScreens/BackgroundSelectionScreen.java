package oogasalad.view.AuthoringScreens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

/**
 * Class to represent the screen in which the user customizes the background of their unique game
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class BackgroundSelectionScreen extends AuthoringScreen {

  private Rectangle background;

  public BackgroundSelectionScreen(AuthoringController controller, StackPane authoringBox,
      Map<Shape, NonControllableType> nonControllableMap, List<Shape> controllableList) {
    super(controller, authoringBox, nonControllableMap, controllableList);
  }

  /**
   * Creates the scene for configuring the background
   */
  void createScene() {
    root = new StackPane();
    createTitle("Background Selection");
    createAuthoringBox();
    createShapeDisplayOptionBox();
    createTransitionButton("Next");
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * When next button is clicked, controller is prompted to start the next selection process
   */
  void endSelection() {
    controller.startNextSelection(ImageType.BACKGROUND, authoringBox, nonControllableMap,
        new ArrayList<>());
  }

  /**
   * Returns background image type indicating that user is configuring the background
   *
   * @return enum to represent background image type
   */
  ImageType getImageType() {
    return ImageType.BACKGROUND;
  }

  /**
   * No objects on this screen are selectable, so update never occurs
   */
  //TODO: Is it ok to have empty method here
  void updateOptionSelections() {
  }

  private void createAuthoringBox() {
    authoringBox.setMaxSize(authoringBoxWidth, authoringBoxHeight);
    StackPane.setAlignment(authoringBox, Pos.TOP_LEFT);
    StackPane.setMargin(authoringBox, new Insets(50, 0, 0, 50));

    background = new Rectangle(authoringBoxWidth, authoringBoxHeight);
    background.setStroke(Color.BLACK);
    background.setFill(Color.WHITE);
    background.setStrokeWidth(10);
    StackPane.setAlignment(background, Pos.TOP_LEFT);
    selectedShape = background;

    nonControllableMap.put(background, NonControllableType.SURFACE);

    authoringBox.getChildren().add(background);
    root.getChildren().add(authoringBox);
  }
}
