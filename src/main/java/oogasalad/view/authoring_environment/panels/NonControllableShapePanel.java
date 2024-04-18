package oogasalad.view.authoring_environment.panels;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;

public class NonControllableShapePanel extends ShapePanel {

  private ComboBox<GameObjectType> nonControllableTypeDropdown;
  private ComboBox<String> collidableTypeDropDown;
  private TextField kFrictionTextField;
  private TextField sFrictionTextField;
  private TextField massTextField;
  private TextField elasticityTextField;
  private CheckBox scoreableCheckBox;
  private Label kFriction;
  private Label sFriction;
  private Label mass;
  private Label elasticity;
  private Label scoreable;

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
    createCollidableOptions();
    createMakePlayers();
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
    kFrictionTextField = new TextField();
    kFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(kFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(kFrictionTextField, 120.0);

    kFriction = new Label("Kinetic Friction Coefficient");
    AnchorPane.setRightAnchor(kFriction, 300.0);
    AnchorPane.setTopAnchor(kFriction, 120.0);

    sFrictionTextField = new TextField();
    sFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(sFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(sFrictionTextField, 160.0);

    sFriction = new Label("Static Friction Coefficient");
    AnchorPane.setRightAnchor(sFriction, 300.0);
    AnchorPane.setTopAnchor(sFriction, 160.0);

    containerPane.getChildren()
        .addAll(kFrictionTextField, kFriction, sFrictionTextField, sFriction);
  }

  private void createCollidableOptions() {
    createCollidableTypeOptions();
    createCollidableParameterOptions();
    createScoreableOption();
  }

  private void createCollidableTypeOptions() {
    collidableTypeDropDown = new ComboBox<>();
    collidableTypeDropDown.getItems()
        .addAll("STRIKEABLE", "CONTROLLABLE", "NON-CONTROLLABLE");
    collidableTypeDropDown.setPromptText("Select Collidable Type");
    AnchorPane.setRightAnchor(collidableTypeDropDown, 300.0);
    AnchorPane.setTopAnchor(collidableTypeDropDown, 200.0);
    collidableTypeDropDown.setPrefSize(200, 50);
    containerPane.getChildren().add(collidableTypeDropDown);
  }

  private void createCollidableParameterOptions() {
    massTextField = new TextField();
    massTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(massTextField, 450.0);
    AnchorPane.setTopAnchor(massTextField, 270.0);

    mass = new Label("Mass");
    AnchorPane.setRightAnchor(mass, 410.0);
    AnchorPane.setTopAnchor(mass, 270.0);

    elasticityTextField = new TextField();
    elasticityTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(elasticityTextField, 450.0);
    AnchorPane.setTopAnchor(elasticityTextField, 310.0);

    elasticity = new Label("Elasticity");
    AnchorPane.setRightAnchor(elasticity, 390.0);
    AnchorPane.setTopAnchor(elasticity, 310.0);

    containerPane.getChildren()
        .addAll(massTextField, mass, elasticityTextField, elasticity);
  }

  private void createScoreableOption() {
    scoreableCheckBox = new CheckBox();
    scoreableCheckBox.setPrefSize(20, 20);
    AnchorPane.setRightAnchor(scoreableCheckBox, 470.0);
    AnchorPane.setTopAnchor(scoreableCheckBox, 350.0);

    scoreable = new Label("Scoreable");
    AnchorPane.setRightAnchor(scoreable, 400.0);
    AnchorPane.setTopAnchor(scoreable, 350.0);

    containerPane.getChildren()
        .addAll(scoreableCheckBox, scoreable);
  }

  private void createMakePlayers() {

  }

  private void handleNonControllableTypeSelection() {
    nonControllableTypeDropdown.valueProperty().addListener((obs, oldVal, gameObjectType) -> {
      if (shapeProxy.getShape() != null && gameObjectType != null) {
        authoringProxy.addNonControllableShape(shapeProxy.getShape(), gameObjectType);
        updateSelectionOptions(gameObjectType);
      }
    });
  }

  private void updateSelectionOptions(GameObjectType gameObjectType) {
    if (gameObjectType == GameObjectType.COLLIDABLE) {
      setSurfaceOptionVisibility(false);
      setCollidableOptionVisibility(true);
    } else if (gameObjectType == GameObjectType.SURFACE) {
      setSurfaceOptionVisibility(true);
      setCollidableOptionVisibility(false);
    }
  }

  private void setSurfaceOptionVisibility(boolean visibilty) {
    kFrictionTextField.setVisible(visibilty);
    sFrictionTextField.setVisible(visibilty);
    kFriction.setVisible(visibilty);
    sFriction.setVisible(visibilty);
  }

  private void setCollidableOptionVisibility(boolean visibility) {
    collidableTypeDropDown.setVisible(visibility);
    massTextField.setVisible(visibility);
    mass.setVisible(visibility);
    elasticityTextField.setVisible(visibility);
    elasticity.setVisible(visibility);
    scoreableCheckBox.setVisible(visibility);
    scoreable.setVisible(visibility);
  }
}
