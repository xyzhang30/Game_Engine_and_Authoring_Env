package oogasalad.view.authoring_environment.panels;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;

public class NonControllableShapePanel extends ShapePanel {

  private ComboBox<GameObjectType> nonControllableTypeDropdown;

  public NonControllableShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy,
      AnchorPane rootPane, AnchorPane containerPane, StackPane canvas) {
    super(authoringProxy, shapeProxy, rootPane, containerPane, canvas);
  }

  @Override
  public void handleEvents() {
    super.handleEvents();
    handleNonControllableTypeSelection();
  }

  @Override
  public void createElements() {
    super.createElements();
    createGameObjectTypeSelection();
    createSurfaceOptions();
  }

  private void createGameObjectTypeSelection() {
    nonControllableTypeDropdown = new ComboBox<>();
    nonControllableTypeDropdown.getItems()
        .addAll(GameObjectType.SURFACE, GameObjectType.COLLIDABLE);
    nonControllableTypeDropdown.setPromptText("Select Obstacle Type");
    AnchorPane.setRightAnchor(nonControllableTypeDropdown, 300.0);
    AnchorPane.setTopAnchor(nonControllableTypeDropdown, 50.0);
    nonControllableTypeDropdown.setPrefSize(200, 50);
    containerPane.getChildren().add(nonControllableTypeDropdown);
  }

  private void createSurfaceOptions() {
    TextField kFrictionTextField = new TextField();
    kFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(kFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(kFrictionTextField, 120.0);

    Label kFriction = new Label("Kinetic Friction Coefficient");
    AnchorPane.setRightAnchor(kFriction, 300.0);
    AnchorPane.setTopAnchor(kFriction, 120.0);

    TextField sFrictionTextField = new TextField();
    sFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(sFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(sFrictionTextField, 160.0);

    Label sFriction = new Label("Static Friction Coefficient");
    AnchorPane.setRightAnchor(sFriction, 300.0);
    AnchorPane.setTopAnchor(sFriction, 160.0);

    containerPane.getChildren()
        .addAll(kFrictionTextField, kFriction, sFrictionTextField, sFriction);
  }

  private void handleNonControllableTypeSelection() {
    nonControllableTypeDropdown.valueProperty().addListener((obs, oldVal, nonControllableType) -> {
      if (shapeProxy.getShape() != null && nonControllableType != null) {
        authoringProxy.addNonControllableShape(shapeProxy.getShape(), nonControllableType);
      }
    });
  }
}
