package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.authoring_screens.InteractionType;
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
    Label label = new Label("ON COLLISION: ");
    AnchorPane.setTopAnchor(label,50.0);
    AnchorPane.setLeftAnchor(label,350.0);
    List<String> availableCommands = getAvailableCommands();

    CheckComboBox<String> checkComboBox = new CheckComboBox<>(
        FXCollections.observableArrayList(availableCommands)
    );
    checkComboBox.setMaxWidth(300);
    containerPane.getChildren().addAll(label,checkComboBox);
    AnchorPane.setLeftAnchor(checkComboBox, 500.0);
    AnchorPane.setTopAnchor(checkComboBox,50.0);
    checkComboBox.setId("collision");

    checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          for (String selectedCommand : c.getAddedSubList()) {
            enterParam(selectedCommand);
          }
        }
      }
    });
  }

  private void enterParam(String newValue) {
    System.out.println("selected:" +REFLECTION_COMMAND_PACKAGE_PATH + "." + newValue);
    String classPath = REFLECTION_COMMAND_PACKAGE_PATH + "." + newValue;
    try {
      System.out.println("path: "+classPath);
      Class<?> clazz = Class.forName(classPath);
      Constructor<?> constructor = clazz.getConstructor(List.class);
      if (constructor.getAnnotation(ExpectedParamNumber.class) != null && clazz.getDeclaredConstructor(List.class).getAnnotation(ExpectedParamNumber.class).value() != 0){
        int numParam = constructor.getAnnotation(ExpectedParamNumber.class).value();
        PolicyPanel.enterConstantParamsPopup(numParam, newValue);
      }
    } catch (NoSuchMethodException | ClassNotFoundException e) {
      e.printStackTrace();
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

  }


}
