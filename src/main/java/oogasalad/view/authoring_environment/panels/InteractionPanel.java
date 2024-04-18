package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.authoring_screens.InteractionType;

public class InteractionPanel implements Panel {

  private static final String COMMAND_PACKAGE_PATH = "src/main/java/oogasalad/model/gameengine/command/";
  private static final String REFLECTION_COMMAND_PACKAGE_PATH = "src.main.java.oogasalad.model.gameengine.command.";

  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private TextField pointPrompt;
  private CheckBox advanceTurnCheckBox;
  private CheckBox resetCheckBox;
  private CheckBox changeSpeedCheckBox;
  private final Set<Shape> clickedShapes = new HashSet<>();
  private TextField gameNameTextField;


  public InteractionPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
//    createCheckBoxes();
//    createPointOptions();
    Label label = new Label("ON COLLISION: ");
    AnchorPane.setTopAnchor(label,50.0);
    AnchorPane.setLeftAnchor(label,350.0);

  }


  private List<String> getAvailableCommands() {
    Path path = Paths.get(COMMAND_PACKAGE_PATH);
    File packageDir = path.toFile();
    List<String> commands = new ArrayList<>();
    System.out.println("packageDir.isDirectory() "+ packageDir.isDirectory());
    if (packageDir.isDirectory()) {
      for (File file : Objects.requireNonNull(packageDir.listFiles())){
        String name = file.getName();
        if (name.endsWith(".java")) {
          try {
            String className = name.substring(0, name.length() - 5); // Remove ".java" extension
            Class<?> clazz = Class.forName(
                REFLECTION_COMMAND_PACKAGE_PATH + className);
            boolean isCommand = clazz.getDeclaredAnnotation(IsCommand.class).isCommand();
            if (isCommand) {
              commands.add(className);
            }
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
      }
//      File[] files = packageDir.listFiles((dir, name) -> {
//        if (name.endsWith(".java")) {
//          try {
//            String className = name.substring(0, name.length() - 5); // Remove ".java" extension
//            Class<?> clazz = Class.forName(
//                REFLECTION_ENGINE_PACKAGE_PATH + commandPackage + "." + className);
//            boolean isCommand = clazz.getDeclaredAnnotation(IsCommand.class).isCommand();
//            if (isCommand) {
//              commands.add(className);
//            }
//            return isCommand;
//          } catch (ClassNotFoundException e) {
//            e.printStackTrace(); // Handle or log the exception
//          }
//        }
//        return false; // Default return value if class is not found or is not annotated
//      });
    }
    return commands;
  }


  @Override
  public void handleEvents() {
    advanceTurnCheckBox.setOnMouseClicked(e -> handleAdvance());
    resetCheckBox.setOnMouseClicked(e -> handleReset());
    changeSpeedCheckBox.setOnMouseClicked(e -> handleChangeSpeed());
    pointPrompt.setOnKeyPressed(e -> handlePointPrompt(e.getCode()));
  }

  private void createCheckBoxes() {
    advanceTurnCheckBox = new CheckBox("Advance");
    advanceTurnCheckBox.setId("advanceTurnCheckBox");
    resetCheckBox = new CheckBox("Reset");
    resetCheckBox.setId("resetCheckBox");
    changeSpeedCheckBox = new CheckBox("Change Speed");
    changeSpeedCheckBox.setId("changeSpeedCheckBox");

    AnchorPane.setTopAnchor(advanceTurnCheckBox, 50.0);
    AnchorPane.setLeftAnchor(advanceTurnCheckBox, 100.0);
    AnchorPane.setTopAnchor(resetCheckBox, 100.0);
    AnchorPane.setLeftAnchor(resetCheckBox, 100.0);
    AnchorPane.setTopAnchor(changeSpeedCheckBox, 150.0);
    AnchorPane.setLeftAnchor(changeSpeedCheckBox, 100.0);


    advanceTurnCheckBox.setDisable(true);
    resetCheckBox.setDisable(true);
    changeSpeedCheckBox.setDisable(true);

    advanceTurnCheckBox.setPrefSize(150, 150);

    resetCheckBox.setPrefSize(150, 150);

    changeSpeedCheckBox.setPrefSize(150, 150);

    containerPane.getChildren().addAll(advanceTurnCheckBox, resetCheckBox, changeSpeedCheckBox);
  }

  private void handleAdvance() {
    resetCheckBox.setSelected(false);
    changeSpeedCheckBox.setSelected(false);

    for (List<Shape> list : authoringProxy.getInteractionMap().keySet()) {
      if (list.containsAll(clickedShapes)) {
        Map<InteractionType, List<Double>> currentInteractions = authoringProxy.getInteractionMap()
            .get(list);
        currentInteractions.remove(InteractionType.RESET);
        currentInteractions.remove(InteractionType.CHANGE_SPEED);
        currentInteractions.put(InteractionType.ADVANCE, List.of((double) -1));
        return;
      }
    }
    List<Shape> shapeList = new ArrayList<>(clickedShapes);
    Map<InteractionType, List<Double>> currentInteractions = new HashMap<>();
    currentInteractions.put(InteractionType.ADVANCE, List.of((double) -1));
    authoringProxy.addShapeInteraction(shapeList, currentInteractions);

  }

  private void handleReset() {
    advanceTurnCheckBox.setSelected(false);
    changeSpeedCheckBox.setSelected(false);

    for (List<Shape> list : authoringProxy.getInteractionMap().keySet()) {
      if (list.containsAll(clickedShapes)) {
        Map<InteractionType, List<Double>> currentInteractions = authoringProxy.getInteractionMap()
            .get(list);
        currentInteractions.remove(InteractionType.ADVANCE);
        currentInteractions.remove(InteractionType.CHANGE_SPEED);
        currentInteractions.put(InteractionType.RESET, List.of((double) -1));
        return;
      }
    }
    List<Shape> shapeList = new ArrayList<>(clickedShapes);
    Map<InteractionType, List<Double>> currentInteractions = new HashMap<>();
    currentInteractions.put(InteractionType.RESET, List.of((double) -1));
    authoringProxy.addShapeInteraction(shapeList, currentInteractions);
  }

  private void handleChangeSpeed() {
    advanceTurnCheckBox.setSelected(false);
    resetCheckBox.setSelected(false);

    for (List<Shape> list : authoringProxy.getInteractionMap().keySet()) {
      if (list.containsAll(clickedShapes)) {
        Map<InteractionType, List<Double>> currentInteractions = authoringProxy.getInteractionMap()
            .get(list);
        currentInteractions.remove(InteractionType.RESET);
        currentInteractions.remove(InteractionType.ADVANCE);
        currentInteractions.put(InteractionType.CHANGE_SPEED, List.of((double) -1));
        return;
      }
      List<Shape> shapeList = new ArrayList<>(clickedShapes);
      Map<InteractionType, List<Double>> currentInteractions = new HashMap<>();
      currentInteractions.put(InteractionType.CHANGE_SPEED, List.of((double) -1));
      authoringProxy.addShapeInteraction(shapeList, currentInteractions);
    }

  }

  private void createPointOptions() {
    pointPrompt = new TextField();
    pointPrompt.setEditable(false);
    pointPrompt.setPrefSize(75, 75);

    Label label = new Label("Points Scored on Collision");
    AnchorPane.setLeftAnchor(label, 100.0);
    AnchorPane.setTopAnchor(label, 50.0);

    HBox pointPromptContainer = new HBox(pointPrompt);
    pointPromptContainer.setMaxSize(75, 75);
    AnchorPane.setRightAnchor(pointPromptContainer, 100.0);
    AnchorPane.setBottomAnchor(pointPromptContainer, 150.0);

    containerPane.getChildren().addAll(label, pointPromptContainer);
  }

  private void handlePointPrompt(KeyCode event) {
    if (event == KeyCode.ENTER) {
      String pointsText = pointPrompt.getText();
      try {
        Integer points = Integer.parseInt(pointsText);
        for (List<Shape> list : authoringProxy.getInteractionMap().keySet()) {
          if (list.containsAll(clickedShapes)) {
            Map<InteractionType, List<Double>> currentInteractions = authoringProxy.getInteractionMap()
                .get(list);
            currentInteractions.put(InteractionType.SCORE, List.of((double) 1, (double) points));
            return;
          }
        }

        List<Shape> shapeList = new ArrayList<>(clickedShapes);
        Map<InteractionType, List<Double>> currentInteractions = new HashMap<>();
        currentInteractions.put(InteractionType.SCORE, List.of((double) 1, (double) points));
        authoringProxy.addShapeInteraction(shapeList, currentInteractions);

      } catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please Enter an Integer");
        alert.showAndWait();
      }
    }
  }


}
