package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import oogasalad.model.annotations.AvailableCommands;
import oogasalad.model.annotations.ChoiceType;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.view.authoring_environment.authoring_screens.PolicyType;
import org.controlsfx.control.CheckComboBox;
import java.lang.reflect.Constructor;

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
    AnchorPane.setLeftAnchor(label,350.0);
    List<String> availableCommands = getAvailableCommands(commandPackage);
    if (singleChoice) {
      ComboBox<String> comboBox = new ComboBox<>();
      AnchorPane.setLeftAnchor(comboBox, 500.0);
      AnchorPane.setTopAnchor(comboBox,50.0*heightIdx);
      // Add items to the ComboBox
      comboBox.getItems().addAll(availableCommands);
      // Set a default value
//      comboBox.setValue("");
      comboBox.setPromptText("Select command...");
      comboBox.setId(policyNameLabel);
      // Add the ComboBox to the containerPane
      containerPane.getChildren().addAll(label,comboBox);

      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          enterParam(commandPackage, newValue);
        }
      });
    } else {
//      String[] options = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
      CheckComboBox<String> checkComboBox = new CheckComboBox<>(
          FXCollections.observableArrayList(availableCommands)
      );
      checkComboBox.setMaxWidth(300);
      containerPane.getChildren().addAll(label,checkComboBox);
      AnchorPane.setLeftAnchor(checkComboBox, 500.0);
      AnchorPane.setTopAnchor(checkComboBox,50.0*heightIdx);
      checkComboBox.setId(policyNameLabel);

//      checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
//        while (c.next()) {
//          if (c.wasAdded()) {
//            for (String selectedCommand : c.getAddedSubList()) {
//              enterParam(commandPackage, selectedCommand);
//            }
//          }
//        }
//      });
    }
  }

  private void enterParam(String commandPackage, String newValue) {
    System.out.println("selected:" + REFLECTION_ENGINE_PACKAGE_PATH + commandPackage + "." + newValue);
    String classPath = REFLECTION_ENGINE_PACKAGE_PATH + commandPackage + "." + newValue;
    try {
      Class<?> clazz = Class.forName(
          classPath);

      Constructor<?> constructor;
      if (!commandPackage.equals("strike") && !commandPackage.equals("turn")){
        constructor = clazz.getConstructor(List.class);
        if (constructor.getAnnotation(ExpectedParamNumber.class) != null && clazz.getDeclaredConstructor(List.class).getAnnotation(ExpectedParamNumber.class).value() != 0){
          int numParam = constructor.getAnnotation(ExpectedParamNumber.class).value();
          enterParamsPopup(numParam, newValue);
        }
      }
    } catch (NoSuchMethodException | ClassNotFoundException e) {
//      throw new RuntimeException(e);
      e.printStackTrace();
    }
  }

  private void enterParamsPopup(int numParam, String item) {
    Stage popupStage = new Stage();
    popupStage.setTitle("Specify Command Parameters");

    Label label = new Label(item+": (expected " + numParam + ")");
    VBox vbox = new VBox(label);

    for (int i = 0; i < numParam; i ++){
      TextArea input = new TextArea();
      input.setId(String.valueOf(i));
      vbox.getChildren().add(input);
    }
    Scene scene = new Scene(vbox, 500, 300);
    popupStage.setScene(scene);

    popupStage.setResizable(false);
    popupStage.show();
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
