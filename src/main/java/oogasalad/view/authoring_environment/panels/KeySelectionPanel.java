package oogasalad.view.authoring_environment.panels;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.api.authoring.UIElementFactory;
import oogasalad.view.api.enums.KeyInputType;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;

public class KeySelectionPanel implements Panel {

  private final AuthoringProxy authoringProxy;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private final StackPane canvas;
  private final UIElementFactory uiElementFactory;

  public KeySelectionPanel(AuthoringProxy authoringProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas, UIElementFactory uiElementFactory) {
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    this.uiElementFactory = uiElementFactory;
    createElements();
    handleEvents();
  }


  @Override
  public void createElements() {
    createInputFields();
  }

  private void createInputFields() {
    int heightIdx = 1;
    for (KeyInputType keyInputType : KeyInputType.values()) {
      String keyTypeLabel = String.join(" ", keyInputType.toString().split("_"));
      createInput(keyTypeLabel, heightIdx);
      heightIdx ++;
    }
  }

  private void createInput(String label, int heightIdx) {
    Label keyInputLabel = new Label(label);
    AnchorPane.setTopAnchor(keyInputLabel, 50.0 * heightIdx);
    AnchorPane.setLeftAnchor(keyInputLabel, 300.0);

//    TextField inputField = new TextField();
//    inputField.setEditable(false);
//
//    inputField.setOnKeyPressed(event -> {
//      KeyCode keycode = event.getCode();
//      if (!keycodes.toString().contains(keycode.toString())) {
//        keycodes.append(keycode).append(", ");
//        updateInputField(inputField);
//      }
//    });

    containerPane.getChildren().add(keyInputLabel);
  }

  @Override
  public void handleEvents() {

  }
}
