package oogasalad.view.AuthoringScreens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

public class InteractionSelectionScreen extends AuthoringScreen {

  private Map<Shape, Boolean> advanceTurnMap = new HashMap<>();
  private Map<Shape, Integer> pointsScoredMap = new HashMap<>();
  private TextField pointPrompt;
  private ToggleButton falseButton;
  private ToggleButton trueButton;

  public InteractionSelectionScreen(AuthoringController controller, StackPane authoringBox,
      Map<Shape, NonControllableType> nonControllableTypeMap, List<Shape> controllableList) {
    super(controller, authoringBox, nonControllableTypeMap, controllableList);
  }

  /**
   * Creates the scene including the previously selected background
   */
  void createScene() {
    root = new StackPane();
    createTitle("Interaction Selection");
    root.getChildren().add(authoringBox);
    createInteractionOptions();
    createTransitionButton("Next");
    scene = new Scene(root, screenWidth, screenHeight);
  }

  void updateOptionSelections() {
    updatePointPrompt();
    updateAdvanceRoundButtons();
  }

  ImageType getImageType() {
    return null;
  }

  void endSelection() {
  }

  private void createInteractionOptions() {
    createAdvanceTurnOptions();
    createPointOptions();
  }

  private void createAdvanceTurnOptions() {
    trueButton = new ToggleButton("True");
    falseButton = new ToggleButton("False");

    trueButton.setPrefSize(75, 75);
    StackPane.setAlignment(trueButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(trueButton, new Insets(0, 150, 300, 0));
    createTrueAdvanceTurnHandler();

    falseButton.setPrefSize(75, 75);
    StackPane.setAlignment(falseButton, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(falseButton, new Insets(0, 50, 300, 0));
    createFalseAdvanceTurnHandler();

    Label label = new Label("Advance Turn on Collision");
    StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(label, new Insets(0, 75, 400, 0));

    root.getChildren().addAll(label, trueButton, falseButton);
  }

  private void createPointOptions() {
    pointPrompt = new TextField();
    pointPrompt.setPrefSize(75, 75);
    createPointsScoredHandler();

    Label label = new Label("Points Scored on Collision");
    StackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(label, new Insets(0, 75, 250, 0));

    HBox pointPromptContainer = new HBox(pointPrompt);
    pointPromptContainer.setMaxSize(75, 75);
    StackPane.setAlignment(pointPromptContainer, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(pointPromptContainer, new Insets(0, 100, 150, 0));

    root.getChildren().addAll(label, pointPromptContainer);
  }

  private void createTrueAdvanceTurnHandler() {
    trueButton.setOnMouseClicked(event -> {
      if (selectedShape != null) {
        advanceTurnMap.put(selectedShape, true);
        falseButton.setSelected(false);
      }
    });
  }

  private void createFalseAdvanceTurnHandler() {
    falseButton.setOnMouseClicked(event -> {
      if (selectedShape != null) {
        advanceTurnMap.put(selectedShape, false);
        trueButton.setSelected(false);
      }
    });
  }

  private void createPointsScoredHandler() {
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

  private void updatePointPrompt() {
    Integer points = pointsScoredMap.get(selectedShape);
    if (points != null) {
      pointPrompt.setText(points.toString());
    } else {
      pointPrompt.setText("");
    }
  }

  private void updateAdvanceRoundButtons() {
    Boolean advanceRound = advanceTurnMap.get(selectedShape);
    if (advanceRound != null) {
      if (advanceRound) {
        trueButton.setSelected(true);
        falseButton.setSelected(false);
      } else {
        trueButton.setSelected(false);
        falseButton.setSelected(true);
      }
    } else {
      trueButton.setSelected(false);
      falseButton.setSelected(false);
    }
  }


}
