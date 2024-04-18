package oogasalad.view.authoring_environment.panels;

import java.awt.Choice;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import oogasalad.model.annotations.AvailableCommands;
import oogasalad.model.annotations.ChoiceType;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.authoring_screens.PolicyType;
import org.controlsfx.control.CheckComboBox;

public class PolicyPanel implements Panel{

  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private static final String GAME_ENGINE_PACKAGE_PATH = "src/main/java/oogasalad/model/gameengine/";
  private static final String REFLECTION_ENGINE_PACKAGE_PATH = "oogasalad.model.gameengine.";

  public PolicyPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    Field[] fields = PolicyType.class.getDeclaredFields();
    int heightidx = 1;
    for (Field policyType : fields) {
      String policyLabel = String.join(" ",policyType.getName().split("_")) + ": ";
      System.out.println(policyLabel);

      if (policyType.isAnnotationPresent(ChoiceType.class)) {
        ChoiceType choiceTypeAnnotation = policyType.getAnnotation(ChoiceType.class);
        AvailableCommands availableCommands = policyType.getAnnotation(AvailableCommands.class);
//        System.out.println("annotation:" + choiceTypeAnnotation);
        createPolicySelectionDropdown(policyLabel, choiceTypeAnnotation.singleChoice(), availableCommands.commandPackage(), heightidx);
      }
    heightidx ++;
    }
  }

  private void createPolicySelectionDropdown(String policyNameLabel, boolean singleChoice, String commandPackage, int heightIdx) {
    Label label = new Label(policyNameLabel);
    AnchorPane.setTopAnchor(label,50.0*heightIdx);
    List<String> availableCommands = getAvailableCommands(commandPackage);
    if (singleChoice) {
      ComboBox<String> comboBox = new ComboBox<>();
      AnchorPane.setLeftAnchor(comboBox, 500.0);
      AnchorPane.setTopAnchor(comboBox,100.0*heightIdx);
      // Add items to the ComboBox
      comboBox.getItems().addAll(availableCommands);
      // Set a default value
//      comboBox.setValue("");
      comboBox.setPromptText("Select command...");
      comboBox.setId(policyNameLabel);
      // Add the ComboBox to the containerPane
      containerPane.getChildren().addAll(label,comboBox);
    } else {
//      String[] options = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
      CheckComboBox<String> checkComboBox = new CheckComboBox<>(
          FXCollections.observableArrayList(availableCommands)
      );
      containerPane.getChildren().addAll(label,checkComboBox);
      AnchorPane.setTopAnchor(checkComboBox,100.0);
      checkComboBox.setId(policyNameLabel);
    }
  }

  private List<String> getAvailableCommands(String commandPackage) {
    Path path = Paths.get(GAME_ENGINE_PACKAGE_PATH + commandPackage);
    File packageDir = path.toFile();
    List<String> commands = new ArrayList<>();
    System.out.println();
    System.out.println("packageDir.isDirectory() "+ packageDir.isDirectory());
    if (packageDir.isDirectory()) {
      File[] files = packageDir.listFiles((dir, name) -> {
        if (name.endsWith(".java")) {
          try {
            String className = name.substring(0, name.length() - 5); // Remove ".java" extension
            Class<?> clazz = Class.forName(
                REFLECTION_ENGINE_PACKAGE_PATH + commandPackage + "." + className);
            boolean isCommand = clazz.getDeclaredAnnotation(IsCommand.class).isCommand();
            if (isCommand) {
              commands.add(className);
            }
            return isCommand;
          } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handle or log the exception
          }
        }
        return false; // Default return value if class is not found or is not annotated
      });
    }
    return commands;
  }

  @Override
  public void handleEvents() {

  }
}
