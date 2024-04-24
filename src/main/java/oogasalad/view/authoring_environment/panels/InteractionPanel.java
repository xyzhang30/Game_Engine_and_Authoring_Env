package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import org.controlsfx.control.CheckComboBox;

public class InteractionPanel implements Panel {

  private static final String COMMAND_PACKAGE_PATH = "src/main/java/oogasalad/model/gameengine/command";
  private static final String REFLECTION_COMMAND_PACKAGE_PATH = "oogasalad.model.gameengine.command";
  private final String language = "English"; // TODO: PASS IN LANGUAGE
  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private TextField infoTextField;
  private CheckComboBox<String> checkComboBox;
  private Map<String, List<Integer>> tempSavedCommands = new HashMap<>();
  private Button saveSelectionButton;
  private int numMultiSelect = 2; // TODO: REMOVE HARDCODING
  private final ResourceBundle resourceBundle;

  public InteractionPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + UI_FILE_PREFIX + language);
    shapeProxy.setNumberOfMultiSelectAllowed(numMultiSelect);
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    createObjectIdTextField();
    createCommandsDropdown();
  }

  private void createCommandsDropdown() {
    Label label = new Label(resourceBundle.getString("commandDropdownLabel"));
    AnchorPane.setTopAnchor(label,100.0);
    AnchorPane.setLeftAnchor(label,350.0);
    List<String> availableCommands = getAvailableCommands();

    checkComboBox = new CheckComboBox<>(
        FXCollections.observableArrayList(availableCommands)
    );
    checkComboBox.setMaxWidth(300);
    containerPane.getChildren().addAll(label,checkComboBox);
    AnchorPane.setLeftAnchor(checkComboBox, 500.0);
    AnchorPane.setTopAnchor(checkComboBox,100.0);
    checkComboBox.setId("collision");

    checkComboBox.disableProperty().bind(Bindings.size(shapeProxy.getShapeStackProperty()).lessThan(2));

    saveSelectionButton = new Button(resourceBundle.getString("saveButton"));
    AnchorPane.setRightAnchor(saveSelectionButton, 0.0);
    AnchorPane.setTopAnchor(saveSelectionButton, 150.0);
    containerPane.getChildren().add(saveSelectionButton);
  }

  private void createObjectIdTextField() {
    Label idsLabel = new Label(resourceBundle.getString("selectedGameObjectsLabel"));
    AnchorPane.setLeftAnchor(idsLabel, 350.0);
    AnchorPane.setTopAnchor(idsLabel, 50.0);

    infoTextField = new TextField();
    infoTextField.setEditable(false); //ids are populated automatically after user clicks on object, user can't edit it
    infoTextField.setFocusTraversable(false);
    AnchorPane.setLeftAnchor(infoTextField, 500.0);
    AnchorPane.setTopAnchor(infoTextField, 50.0);

    infoTextField.textProperty().bind(Bindings.createStringBinding(() -> {
      StringBuilder sb = new StringBuilder();
      for (int shapeId : shapeProxy.getShapeStackProperty()) {
        sb.append(shapeId).append("  ");
      }
      return sb.toString();
    }, shapeProxy.getShapeStackProperty()));

    containerPane.getChildren().addAll(idsLabel, infoTextField);
  }

  private List<Integer> enterParam(String newValue) {
    String classPath = REFLECTION_COMMAND_PACKAGE_PATH + "." + newValue;
    try {
      Class<?> clazz = Class.forName(classPath);
      if (clazz.getDeclaredAnnotation(ExpectedParamNumber.class) != null && clazz.getAnnotation(ExpectedParamNumber.class).value() != 0){
        int numParam = clazz.getDeclaredAnnotation(ExpectedParamNumber.class).value();
        return Panel.enterConstantParamsPopup(numParam, newValue);
      } else {
        return new ArrayList<>();
      }
    } catch (ClassNotFoundException e) {
      LOGGER.error(e);
      return null;
    }
  }

  private List<String> getAvailableCommands() {
    Path path = Paths.get(COMMAND_PACKAGE_PATH);
    File packageDir = path.toFile();
    List<String> commands = new ArrayList<>();
    if (packageDir.isDirectory()) {
      for (File file : Objects.requireNonNull(packageDir.listFiles())){
        String name = file.getName();
        if (name.endsWith(".java")) {
          try {
            String className = name.substring(0, name.length() - 5); // Remove ".java" extension
            Class<?> clazz = Class.forName(REFLECTION_COMMAND_PACKAGE_PATH + "." + className);
            boolean isCommand = clazz.getDeclaredAnnotation(IsCommand.class).isCommand();
            if (isCommand) {
              commands.add(className);
            }
          } catch (ClassNotFoundException e) {
            LOGGER.error(e);
          }
        }
      }
    }
    return commands;
  }

  @Override
  public void handleEvents() {
    handleCommandDropdown();
    handleSaveButton();
  }

  private void handleCommandDropdown() {
    checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          for (String selectedCommand : c.getAddedSubList()) {
            List<Integer> params = enterParam(selectedCommand);
            if (params != null){
              tempSavedCommands.put(selectedCommand,params);
            }
          }
        } if (c.wasRemoved()) {
          for (String removedCommand : c.getRemoved()) {
            tempSavedCommands.remove(removedCommand);
          }
        }
      }
    });
  }
  private void handleSaveButton() {
    saveSelectionButton.setOnAction(e -> {
      authoringProxy.addShapeInteraction(shapeProxy.getSelectedShapeIds(), tempSavedCommands);
      tempSavedCommands = new HashMap<>();
      checkComboBox.getCheckModel().clearChecks();
      infoTextField.clear();
    });
  }

}
