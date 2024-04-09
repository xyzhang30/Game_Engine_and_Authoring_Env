package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
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

  private Map<Shape, Boolean> advanceTurnMap = new HashMap<>();
  private Map<Shape, Integer> pointsScoredMap = new HashMap<>();

  public GoalSelectionScreen(AuthoringController controller, StackPane authoringBox) {
    super(controller, authoringBox);
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
    createGoalOptions();
    scene = new Scene(root, screenWidth, screenHeight);
  }

  /**
   * When the next button is clicked, controller is prompted to start the next selection process
   */
  void endSelection() {
    if (allSelectionsMade()) {
      addNewSelectionsToAuthoringBox();
      controller.startNextSelection(ImageType.GOAL, authoringBox);
    }
    //TODO: Show message explaining that not all selections have been made
  }

  /**
   * Returns goal image type indicating that user is placing goal objects
   *
   * @return enum to represent goal image type
   */
  ImageType getImageType() {
    return ImageType.GOAL;
  }

  private void createGoalOptions() {
    createAdvanceTurnOptions();
    createPointOptions();
  }

  private void createAdvanceTurnOptions() {
    Button trueButton = new Button("True");
    trueButton.setPrefSize(75, 75);
    StackPane.setAlignment(trueButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(trueButton, new Insets(0, 150, 300, 0));
    createTrueAdvanceTurnHandler(trueButton);

    Button falseButton = new Button("False");
    falseButton.setPrefSize(75, 75);
    StackPane.setAlignment(falseButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(falseButton, new Insets(0, 50, 300, 0));
    createFalseAdvanceTurnHandler(falseButton);

    Label label = new Label("Advance Turn on Goal");
    StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(label, new Insets(0, 75, 400, 0));

    root.getChildren().addAll(label, trueButton, falseButton);
  }

  private void createPointOptions() {
    TextField pointPrompt = new TextField();
    pointPrompt.setPrefSize(75, 75);
    createPointsScoredHandler(pointPrompt);

    Label label = new Label("Points Scored on Goal");
    StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(label, new Insets(0, 75, 250, 0));

    HBox pointPromptContainer = new HBox(pointPrompt);
    pointPromptContainer.setMaxSize(75, 75);
    StackPane.setAlignment(pointPromptContainer, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(pointPromptContainer, new Insets(0, 100, 150, 0));

    root.getChildren().addAll(label, pointPromptContainer);
  }

  private void createTrueAdvanceTurnHandler(Button button) {
    button.setOnMouseClicked(event -> {
      if (selectedShape != null) {
        advanceTurnMap.put(selectedShape, true);
      }
    });
  }

  private void createFalseAdvanceTurnHandler(Button button) {
    button.setOnMouseClicked(event -> {
      if (selectedShape != null) {
        advanceTurnMap.put(selectedShape, false);
      }
    });
  }

  private void createPointsScoredHandler(TextField pointPrompt) {
    pointPrompt.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER && selectedShape != null) {
        //TODO: Exception handling if a non integer is entered
        String points = pointPrompt.getText();
        pointsScoredMap.put(selectedShape, Integer.parseInt(points));
      }
    });
  }

  private boolean allSelectionsMade() {
    for (Shape shape : selectableShapes) {
      Bounds shapeBounds = shape.getBoundsInParent();
      Bounds authoringBoxBounds = authoringBox.getBoundsInParent();

      if (authoringBoxBounds.contains(shapeBounds)) {
        if (!advanceTurnMap.containsKey(shape) || !pointsScoredMap.containsKey(shape)) {
          return false;
        }
      }
    }
    return true;
  }
}
