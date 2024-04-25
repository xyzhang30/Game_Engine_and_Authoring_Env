package oogasalad.view.authoring_environment.panels;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.gameengine.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Panel class represents a panel in the authoring environment, providing methods for creating elements and handling events.
 * Implementing classes should provide concrete behavior for these methods.
 *
 * @author Judy He
 */
public interface Panel {
   Logger LOGGER = LogManager.getLogger(GameEngine.class);
   String RESOURCE_FOLDER_PATH = "view.";
   String UI_FILE_PREFIX = "UIElements";

  /**
   * Creates the UI elements in the panel necessary for the panel's functionality.
   *
   * @throws NoSuchFieldException If there is an error while creating elements due to missing fields.
   */
  void createElements() throws NoSuchFieldException;

  /**
   * Handles events evoked on UI components in the panel, such as user input or system events, to provide interactivity and functionality.
   */
  void handleEvents();

  static List<Integer> enterConstantParamsPopup(int numParam, String item) {
    Stage popupStage = new Stage();
    popupStage.setTitle("Specify Command Parameters");

    List<Integer> params = new ArrayList<>();

    Label label = new Label(item+": (expected " + numParam + ")");
    VBox vbox = new VBox(label);

    List<TextArea> textAreas = new ArrayList<>();

    for (int i = 0; i < numParam; i ++){
      TextArea input = new TextArea();
      input.setId(String.valueOf(i));
      textAreas.add(input);
      vbox.getChildren().add(input);
    }

    Button confirmSaveParam = new Button("save");
    confirmSaveParam.setDisable(true); //confirm button shouldn't do anything before user enters all params

    for (TextArea area : textAreas) {
      //only allow users to enter digits and the decimal point
      area.addEventFilter(KeyEvent.KEY_TYPED, event -> {
        String character = event.getCharacter();
        if (!character.matches("[0-9.]")) {
          event.consume();
        }
      });
      //only enable the confirm save button when user has entered all required params
      area.textProperty().addListener((observable, oldValue, newValue) -> {
        boolean allFilled = textAreas.stream().noneMatch(textArea -> textArea.getText().trim().isEmpty());
        confirmSaveParam.setDisable(!allFilled);
      });
    }

    confirmSaveParam.setOnAction(e -> {
      for (TextArea area : textAreas) {
        String text = area.getText();
        if (!text.isEmpty()) {
          try {
            Integer value = Integer.parseInt(text);
            params.add(value);
          } catch (NumberFormatException ex) {
            // Handle invalid input
            System.out.println("Invalid input: " + text);
          }
        }
      }
      popupStage.close();
    });

    vbox.getChildren().add(confirmSaveParam);

    Scene scene = new Scene(vbox, 500, 300);
    popupStage.setScene(scene);

    popupStage.setResizable(false);
    popupStage.showAndWait();

    return params;
  }

}
