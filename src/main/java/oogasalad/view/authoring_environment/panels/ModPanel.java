package oogasalad.view.authoring_environment.panels;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;

public class ModPanel implements Panel {

  private final AnchorPane containerPane;
  private final String language = "English"; // PASS IN LANGUAGE
  private final AuthoringProxy authoringProxy;
  private final ShapeProxy shapeProxy;
  private final ResourceBundle resourceBundle;
  private Button save;
  private Button newMod;
  private ComboBox<String> allMods; //later

  public ModPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy,
      AnchorPane containerPane) {
    this.containerPane = containerPane;
    this.authoringProxy = authoringProxy;
    this.shapeProxy = shapeProxy;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + VIEW_PROPERTIES_FOLDER + UI_FILE_PREFIX + language);
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    save = new Button("Save Mod");
    newMod = new Button("New Mod");
    newMod.setDisable(true); //only enabled after you've saved a mod
    allMods = new ComboBox<>();
    allMods.getItems().addAll(shapeProxy.getAllMods());
    allMods.setValue(shapeProxy.getCurrentMod());
    containerPane.getChildren().addAll(allMods, save, newMod);
    AnchorPane.setTopAnchor(save, 750.0);
    AnchorPane.setRightAnchor(save, 400.0);
    AnchorPane.setTopAnchor(newMod, 750.0);
    AnchorPane.setRightAnchor(newMod, 300.0);
    AnchorPane.setTopAnchor(allMods, 700.0);
    AnchorPane.setRightAnchor(allMods, 300.0);
  }

  @Override
  public void handleEvents() {
    save.setOnAction(event -> {
      newMod.setDisable(false);
    });

    newMod.setOnAction(event -> {
      enterNewModName();
    });
  }

  private void enterNewModName() {
    Stage newModPopup = new Stage();
    newModPopup.initModality(Modality.APPLICATION_MODAL);
    newModPopup.setTitle("Enter New Mod Name");

    VBox vbox = new VBox();
    Label enterName = new Label("Mod Name: ");
    TextArea gameNameTextField = new TextArea();
    gameNameTextField.setPromptText("Enter mod name...");

    Button submit = new Button("Save");
    submit.setOnAction(event -> {
      shapeProxy.setCurrentMod(gameNameTextField.getText());
      allMods.getItems().setAll(shapeProxy.getAllMods());
      allMods.setValue(shapeProxy.getCurrentMod());
      newMod.setDisable(true);
      shapeProxy.disableShapeSelection();
      newModPopup.close();
    });

    vbox.getChildren().addAll(enterName, gameNameTextField, submit);

    Scene scene = new Scene(vbox, 500, 200);
    newModPopup.setScene(scene);
    newModPopup.showAndWait();
  }
}
