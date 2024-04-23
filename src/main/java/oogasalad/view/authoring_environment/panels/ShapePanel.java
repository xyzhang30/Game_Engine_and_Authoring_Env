package oogasalad.view.authoring_environment.panels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import oogasalad.view.authoring_environment.data.Coordinate;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;
import oogasalad.view.authoring_environment.data.GameObjectAttributesContainer;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.enums.CollidableType;
import org.controlsfx.control.CheckComboBox;

public class ShapePanel implements Panel {

  protected final ShapeProxy shapeProxy;
  protected final AuthoringProxy authoringProxy;
  protected final StackPane canvas;
  protected final AnchorPane rootPane;
  protected final AnchorPane containerPane;
  private Coordinate startPos;
  private Coordinate translatePos;
  private Slider xSlider;
  private Slider ySlider;
  private Slider angleSlider;
  private ComboBox<GameObjectType> gameObjectTypeDropdown;
  private CheckComboBox<CollidableType> collidableTypeDropDown;
  private TextField kFrictionTextField;
  private TextField sFrictionTextField;
  private TextField massTextField;
  private CheckBox elasticityCheckBox;
  private ListView<String> playerAssignmentListView;
  private CheckBox scoreableCheckBox;
  private Button addPlayerButton;
  private Button removePlayerButton;
  private Text numPlayers;
  private Label kFriction;
  private Label sFriction;
  private Label mass;
  private Label elasticity;
  private CheckBox xSpeedCheckBox, ySpeedCheckBox;
  private Label xSpeedCheckBoxLabel, ySpeedCheckBoxLabel;

  public ShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    // TODO: REMOVE HARD CODING
    shapeProxy.setNumberOfMultiSelectAllowed(1);
    createElements();
    handleEvents();
  }

  @Override
  public void createElements() {
    createSizeAndAngleSliders(); // strategy
    shapeProxy.createGameObjectTemplates(); // strategy
    containerPane.getChildren().addAll(shapeProxy.getTemplates());
    createGameObjectTypeSelection();
    createSurfaceOptions();
    createCollidableOptions();
    createMakePlayers();
    createPlayerAssignment();
    setCollidableOptionVisibility(false);
    setSurfaceOptionVisibility(false);
    setPlayerAssignmentVisibility(false);
  }

  @Override
  public void handleEvents() {
    for (Shape shape : shapeProxy.getTemplates()) {
      handleGameObjectTemplateEvents(shape);
    }
    handleGameObjectTypeSelection();
    handlePlayerAssignment();
    handleAddAndRemovePlayers();
  }

  private void handleGameObjectTemplateEvents(Shape shape) {
    shape.setOnMouseClicked(event -> {
      try {
        Shape clonedShape = shapeProxy.setTemplateOnClick((Shape) event.getSource());
        handleGameObjectEvents(clonedShape);
        rootPane.getChildren().add(clonedShape);
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void handleGameObjectEvents(Shape shape) {
    shape.setOnMouseClicked(event -> setShapeOnClick((Shape) event.getSource()));
    shape.setOnMousePressed(this::handleMousePressed);
    shape.setOnMouseDragged(event -> setShapeOnCompleteDrag((Shape) event.getSource(), event));
    shape.setOnMouseReleased(event -> setShapeOnRelease((Shape) event.getSource()));
  }

  private void handleMousePressed(MouseEvent event) {
    Shape shape = (Shape) event.getSource();
    try {
      setShapeBeginDrag(shape, event);
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }
  }

  private void setShapeBeginDrag(Shape shape, MouseEvent event) throws ReflectiveOperationException {
    if (shape.getParent() != null) {
      ((Pane) shape.getParent()).getChildren().remove(shape);
    }
    rootPane.getChildren().add(shape);
    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
  }

  private void setShapeOnCompleteDrag(Shape shape, MouseEvent event) {
    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(), event.getSceneY() - startPos.y());
    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(), translatePos.y() + offset.y());
    shape.setTranslateX(newTranslatePos.x());
    shape.setTranslateY(newTranslatePos.y());
  }
  private void setShapeOnRelease(Shape shape) {
    if (isInAuthoringBox(shape)) {
      Double leftAnchor = AnchorPane.getLeftAnchor(shape);
      Double topAnchor = AnchorPane.getTopAnchor(shape);
      if (leftAnchor == null) leftAnchor = 0.0;
      if (topAnchor == null) topAnchor = 0.0;
      shapeProxy.getGameObjectAttributesContainer().setPosition(new Coordinate(leftAnchor, topAnchor));
    } else {
      shape.setVisible(false);
      rootPane.getChildren().remove(shape);
      shapeProxy.removeFromShapeStack(shape);
    }
  }
  private void setShapeOnClick(Shape shape) {
    if (shapeProxy.getShape() != null) {
      shapeProxy.setFinalShapeDisplay();
      try {
        GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer().clone();
        authoringProxy.setGameObject(shapeProxy.getShape(), copy);
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
      }
//      authoringProxy.setGameObject(shapeProxy.getShape(), shapeProxy.getGameObjectAttributesContainer());
    }

    shapeProxy.selectShape(shape);
    gameObjectTypeDropdown.valueProperty().setValue(null);
    clearFields();
    shape.setStroke(Color.YELLOW);
    shapeProxy.updateShapeSelectionDisplay();

    updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
  }
  private void clearFields() {
    collidableTypeDropDown.getCheckModel().clearChecks();
    playerAssignmentListView.getSelectionModel().clearSelection();
    kFrictionTextField.clear();
    sFrictionTextField.clear();
    massTextField.clear();
    elasticityCheckBox.setSelected(false);
//    scoreableCheckBox.setSelected(false);
    setCollidableOptionVisibility(false);
    setSurfaceOptionVisibility(false);
    setPlayerAssignmentVisibility(false);
  }

  private boolean isInAuthoringBox(Shape shape) {
    Bounds shapeBounds = shape.getBoundsInParent();
    Bounds authoringBoxBounds = canvas.getBoundsInParent();
    return authoringBoxBounds.contains(shapeBounds);
  }

  private void createSizeAndAngleSliders() {
    VBox sliderContainerBox = new VBox();
    sliderContainerBox.setPrefSize(200, 10);
    AnchorPane.setTopAnchor(sliderContainerBox, 400.0);
    AnchorPane.setRightAnchor(sliderContainerBox, 50.0);
    sliderContainerBox.setAlignment(Pos.CENTER_RIGHT);

    xSlider = createSizeSlider("X Scale", sliderContainerBox);
    xSlider.setId("XSizeSlider");
    ySlider = createSizeSlider("Y Scale", sliderContainerBox);
    ySlider.setId("YSizeSlider");
    angleSlider = createAngleSlider(sliderContainerBox);
    angleSlider.setId("angleSlider");

    xSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeXSize(newValue.doubleValue()));
    ySlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeYSize(newValue.doubleValue()));
    angleSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeAngle(newValue.doubleValue()));

    containerPane.getChildren().add(sliderContainerBox);
  }

  private Slider createAngleSlider(VBox sliderContainerBox) {
    Slider slider = new Slider();
    slider.setPrefWidth(200);
    slider.setMin(0);
    slider.setMax(360);
    slider.setValue(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(20);
    slider.setOrientation(Orientation.HORIZONTAL);

    Label label = new Label("Angle");

    HBox sliderContainer = new HBox(label, slider);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }

  private Slider createSizeSlider(String labelText, VBox sliderContainerBox) {
    Slider slider = new Slider();
    slider.setPrefWidth(200);
    slider.setMin(0);
    slider.setMax(20);
    slider.setValue(0);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(1);
    slider.setOrientation(Orientation.HORIZONTAL);

    Label label = new Label(labelText);
    HBox sliderContainer = new HBox(label, slider);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }

  private void updateSlider(double xScale, double yScale, double angle) {
    xSlider.setValue(xScale);
    xSlider.setMax(10);
    ySlider.setValue(yScale);
    ySlider.setMax(10);
    angleSlider.setValue(angle);
  }

  private void changeAngle(double angle) {
    shapeProxy.getShape().setRotate(angle);
    // shapeProxy.getGameObjectAttributesContainer().setAngle(angle);
  }

  private void changeXSize(double xScale) {
    shapeProxy.getShape().setScaleX(xScale);
    shapeProxy.getGameObjectAttributesContainer().setWidth(shapeProxy.getShape().getLayoutBounds().getWidth()*xScale);
  }

  private void changeYSize(double yScale) {
    shapeProxy.getShape().setScaleY(yScale);
    shapeProxy.getGameObjectAttributesContainer().setHeight(shapeProxy.getShape().getLayoutBounds().getHeight()*yScale);
  }
  private void createGameObjectTypeSelection() {
    gameObjectTypeDropdown = new ComboBox<>();
    gameObjectTypeDropdown.getItems()
        .addAll(GameObjectType.SURFACE, GameObjectType.COLLIDABLE);
    gameObjectTypeDropdown.setPromptText("Select Obstacle Type");
    AnchorPane.setRightAnchor(gameObjectTypeDropdown, 300.0);
    AnchorPane.setTopAnchor(gameObjectTypeDropdown, 50.0);
    gameObjectTypeDropdown.setPrefSize(200, 50);
    containerPane.getChildren().add(gameObjectTypeDropdown);
  }

  private void createSurfaceOptions() {
    kFrictionTextField = new TextField();
    kFrictionTextField.setId("kFriction");
    kFrictionTextField.textProperty().addListener(new TextFieldListener(kFrictionTextField.getId(), shapeProxy));
    kFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(kFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(kFrictionTextField, 120.0);

    kFriction = new Label("Kinetic Friction Coefficient");
    AnchorPane.setRightAnchor(kFriction, 300.0);
    AnchorPane.setTopAnchor(kFriction, 120.0);

    sFrictionTextField = new TextField();
    sFrictionTextField.setId("sFriction");
    sFrictionTextField.textProperty().addListener(new TextFieldListener(sFrictionTextField.getId(), shapeProxy));
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
//    createScoreableOption();
  }

  private void createPlayerAssignment() {
    playerAssignmentListView = new ListView<String>();

    for (int currPlayerNum = 1; currPlayerNum <= authoringProxy.getNumPlayers(); currPlayerNum++) {
      playerAssignmentListView.getItems().add("Player " + currPlayerNum);
    }

    playerAssignmentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    AnchorPane.setRightAnchor(playerAssignmentListView, 300.0);
    AnchorPane.setTopAnchor(playerAssignmentListView, 400.0);
    playerAssignmentListView.setPrefSize(200, 150);

    containerPane.getChildren().add(playerAssignmentListView);
    setPlayerAssignmentVisibility(true);
  }

  private void createCollidableTypeOptions() {
    collidableTypeDropDown = new CheckComboBox<>();
    collidableTypeDropDown.getItems()
        .addAll(CollidableType.STRIKABLE, CollidableType.SCOREABLE, CollidableType.CONTROLLABLE, CollidableType.NONCONTROLLABLE);
//    collidableTypeDropDown.setPromptText("Select Collidable Type");
    AnchorPane.setRightAnchor(collidableTypeDropDown, 300.0);
    AnchorPane.setTopAnchor(collidableTypeDropDown, 200.0);
    collidableTypeDropDown.setPrefSize(200, 50);
    containerPane.getChildren().add(collidableTypeDropDown);
  }

  private void createCollidableParameterOptions() {
    massTextField = new TextField();
    massTextField.setId("mass");
    massTextField.textProperty().addListener(new TextFieldListener(massTextField.getId(), shapeProxy));
    massTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(massTextField, 450.0);
    AnchorPane.setTopAnchor(massTextField, 270.0);

    mass = new Label("Mass");
    AnchorPane.setRightAnchor(mass, 410.0);
    AnchorPane.setTopAnchor(mass, 270.0);

    elasticityCheckBox = new CheckBox();
    elasticityCheckBox.setId("elasticity");
    //THIS LISTENER DOESN'T DO ANYTHING NOW -- WHAT DOES IT NEED TO DO TO PASS THE VALUE BACK TO AUTHROING PROXY???
    elasticityCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
      shapeProxy.getGameObjectAttributesContainer().setElasticity(newValue);
    });
    elasticityCheckBox.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(elasticityCheckBox, 450.0);
    AnchorPane.setTopAnchor(elasticityCheckBox, 310.0);

    elasticity = new Label("Elasticity");
    AnchorPane.setRightAnchor(elasticity, 390.0);
    AnchorPane.setTopAnchor(elasticity, 310.0);

    containerPane.getChildren()
        .addAll(massTextField, mass, elasticityCheckBox, elasticity);
  }

//  private void createScoreableOption() {
//    scoreableCheckBox = new CheckBox();
//    scoreableCheckBox.setPrefSize(20, 20);
//    AnchorPane.setRightAnchor(scoreableCheckBox, 470.0);
//    AnchorPane.setTopAnchor(scoreableCheckBox, 350.0);
//
//    scoreable = new Label("Scoreable");
//    AnchorPane.setRightAnchor(scoreable, 400.0);
//    AnchorPane.setTopAnchor(scoreable, 350.0);
//
//    containerPane.getChildren()
//        .addAll(scoreableCheckBox, scoreable);
//  }

  private void addNewPlayerToProxy() {
    authoringProxy.getPlayers().putIfAbsent(authoringProxy.getCurrentPlayerId(), new HashMap<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.STRIKABLE, new ArrayList<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.CONTROLLABLE, new ArrayList<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.SCOREABLE, new ArrayList<>());
  }
  private void createMakePlayers() {
    // default 1 player
    addNewPlayerToProxy();

    Label numPlayersLabel = new Label("Number of Players");
    AnchorPane.setTopAnchor(numPlayersLabel, 525.0);
    AnchorPane.setRightAnchor(numPlayersLabel, 90.0);

    removePlayerButton = new Button("-");
    removePlayerButton.setPrefSize(50, 50);
    AnchorPane.setRightAnchor(removePlayerButton, 175.0);
    AnchorPane.setTopAnchor(removePlayerButton, 550.0);

    addPlayerButton = new Button("+");
    addPlayerButton.setPrefSize(50, 50);
    AnchorPane.setRightAnchor(addPlayerButton, 50.0);
    AnchorPane.setTopAnchor(addPlayerButton, 550.0);

    numPlayers = new Text(String.valueOf(authoringProxy.getNumPlayers()));
    AnchorPane.setRightAnchor(numPlayers, 130.0);
    AnchorPane.setTopAnchor(numPlayers, 565.0);

    containerPane.getChildren()
        .addAll(removePlayerButton, addPlayerButton, numPlayersLabel, numPlayers);
  }

  // properties
  private void handleGameObjectTypeSelection() {
    gameObjectTypeDropdown.valueProperty().addListener((obs, oldVal, gameObjectType) -> {
      if (gameObjectType == null || shapeProxy.getShape() == null) return;

      clearFields();
      if (oldVal != null) {
        shapeProxy.getGameObjectAttributesContainer().getProperties().remove(String.valueOf(oldVal));
      }
      shapeProxy.getGameObjectAttributesContainer().getProperties().add(String.valueOf(gameObjectType));
      updateSelectionOptions(gameObjectType);

      if (gameObjectType.equals(GameObjectType.SURFACE)) {
        removeObjectFromAuthoringPlayersAnyList();
        setPlayerAssignmentVisibility(false);
      }

    });
  }

  private void handleAddAndRemovePlayers() {
    addPlayerButton.setOnMouseClicked(e -> {
      authoringProxy.increaseNumPlayers();
      addNewPlayerToProxy();

      playerAssignmentListView.getItems().add("Player " + authoringProxy.getNumPlayers());
      numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
    });

    removePlayerButton.setOnMouseClicked(e -> {
      if (authoringProxy.getNumPlayers() > 1) {
        authoringProxy.getPlayers().remove(authoringProxy.getCurrentPlayerId());
        playerAssignmentListView.getItems().remove("Player " + authoringProxy.getNumPlayers());
        authoringProxy.decreaseNumPlayers();
        numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
      }
    });
  }

//  private void updateProxyMapWithTextFieldInput(TextField textField,
//      Consumer<String> inputConsumer) {
//    if (textField.isVisible()) {
//      String inputText = textField.getText();
//      textField.clear();
//      inputConsumer.accept(inputText);
//    }
//  }

  private void handlePlayerAssignment() {
    handlePlayerListViewOnChange();
//    handleScorableCheckBoxOnChange();
    handleCollidableTypeDropdownOnChange();
  }
  private void handlePlayerListViewOnChange() {
    playerAssignmentListView.getSelectionModel().selectedIndexProperty().addListener(((observable, oldPlayerId, newPlayerId) -> {
      List<CollidableType> collidableTypes = collidableTypeDropDown.getCheckModel().getCheckedItems();
      for (CollidableType type: collidableTypes) {
        if ((Integer) oldPlayerId >= 0) removeFromAuthoringPlayer((Integer) oldPlayerId, type);
        addToAuthoringPlayers((Integer) newPlayerId, type);
      }
    }));
  }
  private void handleCollidableTypeDropdownOnChange() {
//    collidableTypeDropDown.valueProperty().addListener((obs, oldVal, collidableType) -> {
    collidableTypeDropDown.getCheckModel().getCheckedItems().addListener((ListChangeListener<CollidableType>) collidableType -> {
      while (collidableType.next()) {
        if (collidableType.wasAdded()) {
          for (CollidableType selected : collidableType.getAddedSubList()) {
            if (!shapeProxy.getGameObjectAttributesContainer().getProperties().contains(selected.toString())) {
              shapeProxy.getGameObjectAttributesContainer().getProperties().add(selected.toString());
            }
            if (selected.equals(CollidableType.NONCONTROLLABLE)) {
              removeObjectFromAuthoringPlayersAnyList();
              setPlayerAssignmentVisibility(false);
            } else {
              setPlayerAssignmentVisibility(true);
              addToAuthoringPlayers(playerAssignmentListView.getSelectionModel().getSelectedIndex(),
                  selected);
            }
          }
        }
        else {
          for (CollidableType removed : collidableType.getRemoved()) {
            if (shapeProxy.getGameObjectAttributesContainer().getProperties().contains(removed.toString())) {
              shapeProxy.getGameObjectAttributesContainer().getProperties().remove(removed.toString());
            }
          }
        }
      }

//
//      if (collidableType == null) return;
//
//      shapeProxy.getGameObjectAttributesContainer().getProperties().remove(oldVal.toString());
//      shapeProxy.getGameObjectAttributesContainer().getProperties().add(collidableType.toString());
//
//      if (collidableType.equals(CollidableType.NONCONTROLLABLE)) {
//        removeObjectFromAuthoringPlayersAnyList();
//        setPlayerAssignmentVisibility(false);
//      } else {
//        setPlayerAssignmentVisibility(true);
//        addToAuthoringPlayers(playerAssignmentListView.getSelectionModel().getSelectedIndex(),
//           collidableTypeDropDown.getValue());
//      }
    });
  }
//  private void handleScorableCheckBoxOnChange() {
//    scoreableCheckBox.selectedProperty().addListener((observable, oldValue, newState) -> {
//      if (newState) {
//        setPlayerAssignmentVisibility(true);
//        addToAuthoringPlayers(playerAssignmentListView.getSelectionModel().getSelectedIndex(),
//            collidableTypeDropDown.getValue());
//      } else {
//        removeFromAuthoringPlayers(collidableTypeDropDown.getValue());
//      }
//    });
//  }
  private void addToAuthoringPlayers(int selectedPlayerId, CollidableType collidableType) {
    Map<Integer, Map<CollidableType, List<Integer>>> playersMap = authoringProxy.getPlayers();
    if (selectedPlayerId >= 0) {
      if (!playersMap.get(selectedPlayerId).get(collidableType).contains(Integer.parseInt(shapeProxy.getShape().getId()))) {
        playersMap.get(selectedPlayerId).get(collidableType).add(Integer.parseInt(shapeProxy.getShape().getId()));
      }
    }
  }

  private void removeObjectFromAuthoringPlayersAnyList() {
    removeFromAuthoringPlayers(CollidableType.STRIKABLE);
    removeFromAuthoringPlayers(CollidableType.CONTROLLABLE);
    removeFromAuthoringPlayers(CollidableType.SCOREABLE);
  }

  // remove selected shape from the player holding it
  private void removeFromAuthoringPlayers(CollidableType collidableType) {
    Map<Integer, Map<CollidableType, List<Integer>>> playersMap = authoringProxy.getPlayers();
    for (Integer player: playersMap.keySet()) {
      removeFromAuthoringPlayer(player, collidableType);
    }
  }

  private void removeFromAuthoringPlayer(int playerId, CollidableType collidableType) {
    Map<Integer, Map<CollidableType, List<Integer>>> playersMap = authoringProxy.getPlayers();
    if (playersMap.get(playerId).get(collidableType).contains(Integer.parseInt(shapeProxy.getShape().getId()))) {
      playersMap.get(playerId).get(collidableType).remove((Integer) Integer.parseInt(shapeProxy.getShape().getId()));
    }

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

  private void setSurfaceOptionVisibility(boolean visibility) {
    kFrictionTextField.setVisible(visibility);
    sFrictionTextField.setVisible(visibility);
    kFriction.setVisible(visibility);
    sFriction.setVisible(visibility);
  }

  private void setCollidableOptionVisibility(boolean visibility) {
    collidableTypeDropDown.setVisible(visibility);
    massTextField.setVisible(visibility);
    mass.setVisible(visibility);
    elasticityCheckBox.setVisible(visibility);
    elasticity.setVisible(visibility);
//    scoreableCheckBox.setVisible(visibility);
//    scoreable.setVisible(visibility);
  }

  private void setPlayerAssignmentVisibility(boolean visibility) {
    playerAssignmentListView.setVisible(visibility);
  }

  private void createXYSpeedControlCheckBoxes() {
    xSpeedCheckBox = new CheckBox();
    ySpeedCheckBox = new CheckBox();
    xSpeedCheckBoxLabel = new Label("X Speed");
    ySpeedCheckBoxLabel = new Label("Y Speed");

    AnchorPane.setRightAnchor(xSpeedCheckBox, 300.0);
    AnchorPane.setTopAnchor(xSpeedCheckBox, 570.0);
    AnchorPane.setRightAnchor(ySpeedCheckBox, 300.0);
    AnchorPane.setTopAnchor(ySpeedCheckBox, 600.0);

    AnchorPane.setRightAnchor(xSpeedCheckBoxLabel, 200.0);
    AnchorPane.setTopAnchor(xSpeedCheckBoxLabel, 570.0);
    AnchorPane.setRightAnchor(ySpeedCheckBoxLabel, 200.0);
    AnchorPane.setTopAnchor(ySpeedCheckBoxLabel, 600.0);

    containerPane.getChildren().addAll(xSpeedCheckBox, xSpeedCheckBoxLabel, ySpeedCheckBox, ySpeedCheckBoxLabel);

    setSpeedOptionVisibility(false);

  }

  private void setSpeedOptionVisibility(boolean visibility) {
    xSpeedCheckBox.setVisible(visibility);
    ySpeedCheckBox.setVisible(visibility);
    xSpeedCheckBoxLabel.setVisible(visibility);
    ySpeedCheckBoxLabel.setVisible(visibility);
  }

}
