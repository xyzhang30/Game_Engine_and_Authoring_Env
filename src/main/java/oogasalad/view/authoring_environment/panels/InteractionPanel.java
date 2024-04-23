package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import org.controlsfx.control.CheckComboBox;

public class InteractionPanel implements Panel {

  private static final String COMMAND_PACKAGE_PATH = "src/main/java/oogasalad/model/gameengine/command";
  private static final String REFLECTION_COMMAND_PACKAGE_PATH = "oogasalad.model.gameengine.command";
  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;

  private final Set<Shape> clickedShapes = new HashSet<>();
  private TextField gameNameTextField;
  private TextField infoTextField;
  private CheckComboBox<String> checkComboBox;
  private Map<String, List<Integer>> tempSavedCommands = new HashMap<>();
  private Button saveSelectionButton;
  private int numMultiSelect = 2;


  public InteractionPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    // TODO: REMOVE HARD CODING
    shapeProxy.setNumberOfMultiSelectAllowed(numMultiSelect);
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    makeObjectIdTextField();

    Label label = new Label("ON COLLISION: ");
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

//    checkComboBox.disableProperty().bind(Bindings.size(shapeProxy.getShapeStackProperty()).lessThan(2));

    saveSelectionButton = new Button("Save");
    AnchorPane.setRightAnchor(saveSelectionButton, 0.0);
    AnchorPane.setTopAnchor(saveSelectionButton, 150.0);
    containerPane.getChildren().add(saveSelectionButton);
  }

  private void makeObjectIdTextField() {
    Label idsLabel = new Label("GAME OBJECTS: ");
    AnchorPane.setLeftAnchor(idsLabel, 350.0);
    AnchorPane.setTopAnchor(idsLabel, 50.0);

    infoTextField = new TextField();
    infoTextField.setEditable(false); //ids are populated automatically after user clicks on object, user can't edit it
    infoTextField.setFocusTraversable(false);
    AnchorPane.setLeftAnchor(infoTextField, 500.0);
    AnchorPane.setTopAnchor(infoTextField, 50.0);

//    infoTextField.textProperty().bind(Bindings.createStringBinding(() -> {
//      StringBuilder sb = new StringBuilder();
//      for (int shapeId : shapeProxy.getSelectedShapeIds()) {
//        sb.append(shapeId).append(" ");
//      }
//      if (sb.length() > 2) {
//        sb.setLength(sb.length() - 2);
//      }
//      return sb.toString();
//    }, shapeProxy.getShapeStackProperty()));

    containerPane.getChildren().addAll(idsLabel, infoTextField);
  }

  private List<Integer> enterParam(String newValue) {
    System.out.println("selected:" +REFLECTION_COMMAND_PACKAGE_PATH + "." + newValue);
    String classPath = REFLECTION_COMMAND_PACKAGE_PATH + "." + newValue;
    try {
      System.out.println("path: "+classPath);
      Class<?> clazz = Class.forName(classPath);
      Constructor<?> constructor = clazz.getConstructor(List.class, Map.class);
      if (constructor.getAnnotation(ExpectedParamNumber.class) != null && clazz.getDeclaredConstructor(List.class, Map.class).getAnnotation(ExpectedParamNumber.class).value() != 0){
        int numParam = constructor.getAnnotation(ExpectedParamNumber.class).value();
        return PolicyPanel.enterConstantParamsPopup(numParam, newValue);
      } else {
        return new ArrayList<>();
      }
    } catch (NoSuchMethodException | ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
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
          System.out.println("classname:"+name);
          try {
            String className = name.substring(0, name.length() - 5); // Remove ".java" extension
            Class<?> clazz = Class.forName(REFLECTION_COMMAND_PACKAGE_PATH + "." + className);
            boolean isCommand = clazz.getDeclaredAnnotation(IsCommand.class).isCommand();
            if (isCommand) {
              commands.add(className);
            }
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return commands;
  }


  @Override
  public void handleEvents() {
    //set listener for the command dropdown
    checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
      infoTextField.textProperty().setValue(shapeProxy.getSelectedShapeIds().toString());
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
        System.out.println("CURRENT SELECTION: "+tempSavedCommands);
      }
    });

    //handle the save button for each collision pair
    saveSelectionButton.setOnAction(e -> {
      authoringProxy.addShapeInteraction(shapeProxy.getSelectedShapeIds(), tempSavedCommands);
      tempSavedCommands = new HashMap<>();
      checkComboBox.getCheckModel().clearChecks();
      infoTextField.clear();
    });

  }
}
