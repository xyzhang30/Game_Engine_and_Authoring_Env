package oogasalad.view.AuthoringScreens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import oogasalad.view.Controlling.AuthoringController;

public class InteractionSelectionScreen extends AuthoringScreen {

  private Map<List<Shape>, Map<InteractionType, Integer>> interactionMap = new HashMap<>();
  private TextField pointPrompt;
  private CheckBox advanceTurnCheckBox;
  private CheckBox resetCheckBox;
  private CheckBox changeSpeedCheckBox;
  private Set<Shape> clickedShapes = new HashSet<>();

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
    setUpShapes();
    createTransitionButton("Submit");
    scene = new Scene(root, screenWidth, screenHeight);
  }

  void updateOptionSelections() {
    if (clickedShapes.size() != 2) {
      pointPrompt.setText("");
      pointPrompt.setEditable(false);
      advanceTurnCheckBox.setSelected(false);
      advanceTurnCheckBox.setDisable(true);
      resetCheckBox.setSelected(false);
      resetCheckBox.setDisable(true);
      changeSpeedCheckBox.setSelected(false);
      changeSpeedCheckBox.setDisable(true);
    } else {
      updatePointPrompt();
      updateCheckBoxes();
      if (canInteract()) {
        pointPrompt.setEditable(true);
        advanceTurnCheckBox.setDisable(false);
        resetCheckBox.setDisable(false);
        if (slowIsOption()) {
          changeSpeedCheckBox.setDisable(false);
        }
      }
    }
  }

  ImageType getImageType() {
    return null;
  }

  void endSelection() {
    controller.endAuthoring(interactionMap);
  }

  private void createInteractionOptions() {
    createCheckBoxes();
    createPointOptions();
  }

  private void createCheckBoxes() {
    advanceTurnCheckBox = new CheckBox("Advance");
    resetCheckBox = new CheckBox("Reset");
    changeSpeedCheckBox = new CheckBox("Change Speed");

    advanceTurnCheckBox.setDisable(true);
    resetCheckBox.setDisable(true);
    changeSpeedCheckBox.setDisable(true);

    advanceTurnCheckBox.setPrefSize(150, 150);
    StackPane.setAlignment(advanceTurnCheckBox, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(advanceTurnCheckBox, new Insets(0, 75, 300, 0));
    createAdvanceHandler();

    resetCheckBox.setPrefSize(150, 150);
    StackPane.setAlignment(resetCheckBox, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(resetCheckBox, new Insets(0, 75, 400, 0));
    createResetHandler();

    changeSpeedCheckBox.setPrefSize(150, 150);
    StackPane.setAlignment(changeSpeedCheckBox, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(changeSpeedCheckBox, new Insets(0, 75, 500, 0));
    createChangeHandler();

    root.getChildren().addAll(advanceTurnCheckBox, resetCheckBox, changeSpeedCheckBox);
  }

  private void createAdvanceHandler() {
    advanceTurnCheckBox.setOnMouseClicked(e -> {
      resetCheckBox.setSelected(false);
      changeSpeedCheckBox.setSelected(false);

      for (List<Shape> list : interactionMap.keySet()) {
        if (list.containsAll(clickedShapes)) {
          Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
          if (currentInteractions.containsKey(InteractionType.RESET)) {
            currentInteractions.remove(InteractionType.RESET);
          }
          if (currentInteractions.containsKey(InteractionType.CHANGE_SPEED)) {
            currentInteractions.remove(InteractionType.CHANGE_SPEED);
          }
          currentInteractions.put(InteractionType.ADVANCE, -1);
          return;
        }
      }
      List<Shape> shapeList = new ArrayList<>(clickedShapes);
      Map<InteractionType, Integer> currentInteractions = new HashMap<>();
      currentInteractions.put(InteractionType.ADVANCE, -1);
      interactionMap.put(shapeList, currentInteractions);
    });
  }

  private void createResetHandler() {
    resetCheckBox.setOnMouseClicked(e -> {
      advanceTurnCheckBox.setSelected(false);
      changeSpeedCheckBox.setSelected(false);

      for (List<Shape> list : interactionMap.keySet()) {
        if (list.containsAll(clickedShapes)) {
          Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
          if (currentInteractions.containsKey(InteractionType.ADVANCE)) {
            currentInteractions.remove(InteractionType.ADVANCE);
          }
          if (currentInteractions.containsKey(InteractionType.CHANGE_SPEED)) {
            currentInteractions.remove(InteractionType.CHANGE_SPEED);
          }
          currentInteractions.put(InteractionType.RESET, -1);
          return;
        }
      }
      List<Shape> shapeList = new ArrayList<>(clickedShapes);
      Map<InteractionType, Integer> currentInteractions = new HashMap<>();
      currentInteractions.put(InteractionType.RESET, -1);
      interactionMap.put(shapeList, currentInteractions);
    });
  }

  private void createChangeHandler() {
    changeSpeedCheckBox.setOnMouseClicked(e -> {
      advanceTurnCheckBox.setSelected(false);
      resetCheckBox.setSelected(false);

      for (List<Shape> list : interactionMap.keySet()) {
        if (list.containsAll(clickedShapes)) {
          Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
          if (currentInteractions.containsKey(InteractionType.RESET)) {
            currentInteractions.remove(InteractionType.RESET);
          }
          if (currentInteractions.containsKey(InteractionType.ADVANCE)) {
            currentInteractions.remove(InteractionType.ADVANCE);
          }
          currentInteractions.put(InteractionType.CHANGE_SPEED, -1);
          return;
        }
        List<Shape> shapeList = new ArrayList<>(clickedShapes);
        Map<InteractionType, Integer> currentInteractions = new HashMap<>();
        currentInteractions.put(InteractionType.CHANGE_SPEED, -1);
        interactionMap.put(shapeList, currentInteractions);
      }
    });
  }

  private void createPointOptions() {
    pointPrompt = new TextField();
    pointPrompt.setEditable(false);
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

  private void createPointsScoredHandler() {
    pointPrompt.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        String pointsText = pointPrompt.getText();
        try {
          Integer points = Integer.parseInt(pointsText);
          for (List<Shape> list : interactionMap.keySet()) {
            if (list.containsAll(clickedShapes)) {
              Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
              currentInteractions.put(InteractionType.SCORE, points);
              return;
            }
          }

          List<Shape> shapeList = new ArrayList<>(clickedShapes);
          Map<InteractionType, Integer> currentInteractions = new HashMap<>();
          currentInteractions.put(InteractionType.SCORE, points);
          interactionMap.put(shapeList, currentInteractions);

        } catch (NumberFormatException e) {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText(null);
          alert.setContentText("Please Enter an Integer");
          alert.showAndWait();
        }
      }
    });
  }

  private void updatePointPrompt() {
    pointPrompt.setText("");
    for (List<Shape> list : interactionMap.keySet()) {
      if (list.containsAll(clickedShapes)) {
        Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
        if (currentInteractions.containsKey(InteractionType.SCORE)) {
          Integer points = currentInteractions.get(InteractionType.SCORE);
          pointPrompt.setText(points.toString());
        }
      }
    }
  }

  private void updateCheckBoxes() {
    for (List<Shape> list : interactionMap.keySet()) {
      if (list.containsAll(clickedShapes)) {
        Map<InteractionType, Integer> currentInteractions = interactionMap.get(list);
        if (currentInteractions.containsKey(InteractionType.ADVANCE)) {
          advanceTurnCheckBox.setSelected(true);
        } else if (currentInteractions.containsKey(InteractionType.RESET)) {
          resetCheckBox.setSelected(true);
        } else if (currentInteractions.containsKey(InteractionType.CHANGE_SPEED)) {
          changeSpeedCheckBox.setSelected(true);
        }
      }
    }
  }

  private void makeMultiSelectable(Shape shape) {
    selectableShapes.add(shape);
    shape.setOnMouseClicked(event -> {
      if (clickedShapes.size() <= 2) {
        if (shape.getStrokeWidth() == 3) { //if already selected, unselected
          shape.setStrokeWidth(0);
          clickedShapes.remove(shape);
        } else if (clickedShapes.size() < 2) {
          clickedShapes.add(shape);
          shape.setStroke(Color.YELLOW);
          shape.setStrokeWidth(3);
        }
        updateOptionSelections();
      }
    });
  }


  private void setUpShapes() {
    for (Shape shape : controllableList) {
      makeMultiSelectable(shape);
    }
    for (Shape shape : nonControllableMap.keySet()) {
      makeMultiSelectable(shape);
    }
  }

  private boolean canInteract() {
    int numControllables = getNumClickedControllables();
    if (numControllables > 0) {
      if (numControllables == 2) {
        return true;
      }
      if (getNumClickedNonControllables() > 0) {
        return true;
      }
    }
    return false;
  }

  private int getNumClickedControllables() {
    int count = 0;
    for (Shape shape : clickedShapes) {
      if (controllableList.contains(shape)) {
        count++;
      }
    }
    return count;
  }

  private int getNumClickedNonControllables() {
    int count = 0;
    for (Shape shape : clickedShapes) {
      if (nonControllableMap.keySet().contains(shape)) {
        count++;
      }
    }
    return count;
  }

  private boolean slowIsOption() {
    for (Shape shape : clickedShapes) {
      if (nonControllableMap.getOrDefault(shape, NonControllableType.OBJECT)
          == NonControllableType.SURFACE) {
        return true;
      }
    }
    return false;
  }

}
