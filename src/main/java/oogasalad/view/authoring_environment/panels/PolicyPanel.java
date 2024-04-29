package oogasalad.view.authoring_environment.panels;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.annotations.AvailableCommands;
import oogasalad.model.annotations.ChoiceType;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.annotations.VariableParamNumber;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.api.authoring.UIElementFactory;
import oogasalad.view.api.enums.PolicyType;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

/**
 * Panel for creating and managing policies in the authoring environment.
 *
 * @author Judy He, Alisha Zhang
 */
public class PolicyPanel implements Panel {

  private static final Logger LOGGER = LogManager.getLogger(PolicyPanel.class);
  private static final String GAME_ENGINE_PACKAGE_PATH = "src/main/java/oogasalad/model"
      + "/gameengine/";
  private static final String REFLECTION_ENGINE_PACKAGE_PATH = "oogasalad.model.gameengine.";
  private final AuthoringProxy authoringProxy;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private final String language = "English"; // PASS IN LANGUAGE
  private final ResourceBundle resourceBundle;
  private final UIElementFactory uiElementFactory;
  private final Map<String, String> commandPackageMap = new HashMap<>();
  private final Map<ComboBox<String>, String> singleChoiceComboxBoxes = new HashMap<>();
  private final Map<CheckComboBox<String>, String> multiChoiceCheckBoxes = new HashMap<>();

  /**
   * Constructor for PolicyPanel.
   *
   * @param authoringProxy   the authoring proxy instance
   * @param rootPane         the root anchor pane
   * @param containerPane    the container anchor pane
   * @param canvas           the canvas stack pane
   * @param uiElementFactory the UI element factory
   */
  public PolicyPanel(AuthoringProxy authoringProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas, UIElementFactory uiElementFactory) {
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    this.uiElementFactory = uiElementFactory;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + VIEW_PROPERTIES_FOLDER + UI_FILE_PREFIX + language);
    createElements();
    handleEvents();
  }

  /**
   * Creates UI elements for the policy panel.
   */
  @Override
  public void createElements() {
    createPolicySelection();
  }

  private void createPolicySelection() {
    Field[] fields = PolicyType.class.getDeclaredFields();
    int heightidx = 1;
    for (Field policyType : fields) {
      String policyLabel = String.join(" ", policyType.getName().split("_")) + ": ";
      String policyNameConcat = String.join("", policyType.getName().toLowerCase().split("_"));

      if (policyType.isAnnotationPresent(ChoiceType.class)) {
        ChoiceType choiceTypeAnnotation = policyType.getAnnotation(ChoiceType.class);
        AvailableCommands availableCommands = policyType.getAnnotation(AvailableCommands.class);
        commandPackageMap.put(policyNameConcat, availableCommands.commandPackage());
        createPolicySelectionDropdown(policyLabel, choiceTypeAnnotation.singleChoice(),
            availableCommands.commandPackage(), heightidx);
      }
      heightidx++;
    }
  }

  private void createPolicySelectionDropdown(String policyNameLabel, boolean singleChoice,
      String commandPackage, int heightIdx) {
    Label label = new Label(policyNameLabel);
    AnchorPane.setTopAnchor(label, 50.0 * heightIdx);
    AnchorPane.setLeftAnchor(label, 300.0);
    List<String> availableCommands = getAvailableCommands(commandPackage);
    String ruleTypeName = String.join("", policyNameLabel.toLowerCase().split(" "));
    ruleTypeName = ruleTypeName.substring(0, ruleTypeName.length() - 1);
    if (singleChoice) {
      ComboBox<String> comboBox = new ComboBox<>();
      AnchorPane.setLeftAnchor(comboBox, 500.0);
      AnchorPane.setTopAnchor(comboBox, 50.0 * heightIdx);

      comboBox.getItems().addAll(availableCommands);
      comboBox.setId(ruleTypeName);

      if (authoringProxy.ruleAlreadySelected(ruleTypeName)) {
        comboBox.setValue(authoringProxy.getSelectedSingleChoiceCommands(ruleTypeName));
      } else {
        comboBox.setPromptText(resourceBundle.getString("policyPromptText"));
      }

      containerPane.getChildren().addAll(label, comboBox);
      comboBox.setMinWidth(300);
      comboBox.setMaxWidth(300);

      singleChoiceComboxBoxes.put(comboBox, comboBox.getId());

    } else {
      CheckComboBox<String> checkComboBox = new CheckComboBox<>(
          FXCollections.observableArrayList(availableCommands)
      );
      checkComboBox.setMinWidth(300);
      checkComboBox.setMaxWidth(300);
      containerPane.getChildren().addAll(label, checkComboBox);
      AnchorPane.setLeftAnchor(checkComboBox, 500.0);
      AnchorPane.setTopAnchor(checkComboBox, 50.0 * heightIdx);
      checkComboBox.setId(ruleTypeName);

      if (authoringProxy.getMultiCommandCheckedIdx().containsKey(checkComboBox.getId())) {
        authoringProxy.getMultiCommandCheckedIdx().get(checkComboBox.getId()).forEach(idx -> {
          checkComboBox.getCheckModel().checkIndices(idx);
        });
      }

      multiChoiceCheckBoxes.put(checkComboBox, checkComboBox.getId());
    }
  }

  private List<Integer> enterParam(String commandType, String commandPackage, String newValue) {
    String classPath = REFLECTION_ENGINE_PACKAGE_PATH + commandPackage + "." + newValue;
    try {
      Class<?> clazz = Class.forName(classPath);
      if (!commandPackage.equals("strike") && !commandPackage.equals("turn")
          && !commandPackage.equals("rank")) {
        //commands that takes in arguments (or empty param list)
        if (clazz.getAnnotation(ExpectedParamNumber.class) != null
            && clazz.getDeclaredAnnotation(ExpectedParamNumber.class).value() != 0) {
          //prompt user to enter param
          int numParam = clazz.getDeclaredAnnotation(ExpectedParamNumber.class).value();
          //get and return the params from popup
          return uiElementFactory.createConstantParamsPopup(numParam, newValue);
        } else if (clazz.getDeclaredAnnotation(VariableParamNumber.class) != null
            && clazz.getDeclaredAnnotation(
            VariableParamNumber.class).isVariable()) {
          //for commands without a constant param number
          return enterCustomNumParamsPopup(newValue);
        } else {
          //command does not take in params -- return empty param list
          return new ArrayList<>();
        }
      } else {
        //commands that don't take in arguments (turn policy and strike policy) -- call save
        // directly (because no need to distinguish between adding a command and replacing a
        // command based on whether it's a combobox or a checkComboBox)
        saveSelectionNoParam(commandType, newValue);
        return null;
      }
    } catch (ClassNotFoundException e) {
      LOGGER.error(e.getMessage());
      return null;
    }
  }

  private void saveSelectionNoParam(String commandType, String commandName) {
    authoringProxy.addNoParamPolicies(commandType, commandName);
  }


  private List<Integer> enterCustomNumParamsPopup(String newValue) {
    Stage popupStage = new Stage();
    popupStage.setTitle(resourceBundle.getString("policyParamPopupPrompt"));

    List<Integer> params = new ArrayList<>();

    Label label = new Label(
        String.format(resourceBundle.getString("policyParamPopupLabel"), newValue));
    VBox vbox = new VBox(label);

    List<TextArea> textAreas = new ArrayList<>();

    Button addTextAreaButton = new Button(resourceBundle.getString("addButton"));

    addTextAreaButton.setOnAction(event -> {
      TextArea newTextArea = new TextArea();
      textAreas.add(newTextArea);
      vbox.getChildren().add(newTextArea);
    });

    HBox buttonBox = new HBox(addTextAreaButton);
    vbox.getChildren().add(buttonBox);

    Button confirmSaveParam = new Button(resourceBundle.getString("saveButton"));
    confirmSaveParam.setDisable(false);
    //allowed to save at any time because no restriction for param numbers

    textAreas.forEach(area -> {
      area.addEventFilter(KeyEvent.KEY_TYPED, event -> {
        String character = event.getCharacter();
        if (!character.matches("[0-9]")) {
          event.consume();
        }
      });
    });

    return uiElementFactory.getParams(confirmSaveParam, textAreas, params, popupStage, vbox);

  }

  private List<String> getAvailableCommands(String commandPackage) {
    Path path = Paths.get(GAME_ENGINE_PACKAGE_PATH + commandPackage);
    File packageDir = path.toFile();
    List<String> commands = new ArrayList<>();
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
            LOGGER.warn("command not found");
            return false;
          }
        }
        return false;
      });
    }
    return commands;
  }

  /**
   * Handles command selection for policy panel
   */
  @Override
  public void handleEvents() {
    // loop through each comboBox + add listeners
    for (ComboBox<String> comboBox : singleChoiceComboxBoxes.keySet()) {
      comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          List<Integer> params = enterParam(comboBox.getId(),
              commandPackageMap.get(singleChoiceComboxBoxes.get(comboBox)),
              newValue); //commandPackage, newValue
          if (params != null) {
            authoringProxy.replaceConditionsCommandsWithParam(comboBox.getId(), newValue, params);
          }
        }
      });
    }

    //add listeners for the multi-choice checkComboBoxes
    for (CheckComboBox<String> checkComboBox : multiChoiceCheckBoxes.keySet()) {
      checkComboBox.getCheckModel().getCheckedItems()
          .addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
              if (c.wasAdded()) {
                for (String selectedCommand : c.getAddedSubList()) {
                  List<Integer> params = enterParam(checkComboBox.getId(),
                      commandPackageMap.get(multiChoiceCheckBoxes.get(checkComboBox)),
                      selectedCommand);
                  if (params != null) {
                    authoringProxy.addConditionsCommandsWithParam(checkComboBox.getId(),
                        selectedCommand, params);
                  }
                  authoringProxy.updateMultiCommandCheckedIdx(checkComboBox.getId(),
                      checkComboBox.getCheckModel().getCheckedIndices());
                }
              }
              if (c.wasRemoved()) {
                for (String removedCommand : c.getRemoved()) {
                  authoringProxy.removeConditionsCommandsWithParam(checkComboBox.getId(),
                      removedCommand);
                  authoringProxy.updateMultiCommandCheckedIdx(checkComboBox.getId(),
                      checkComboBox.getCheckModel().getCheckedIndices());
                }
              }
            }
          });
    }
  }
}
