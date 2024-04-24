package oogasalad.view.authoring_environment.panels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import oogasalad.view.enums.GameObjectType;
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
  private Button addPlayerButton;
  private Button removePlayerButton;
  private Text numPlayers;
  private Label kFriction;
  private Label sFriction;
  private Label mass;
  private Label elasticity;

  private static final String RESOURCE_FOLDER_PATH = "view.";
  private static final String UI_FILE_PREFIX = "UIElements";
  private final String language = "English"; // PASS IN LANGUAGE
  private final ResourceBundle resourceBundle;

  public ShapePanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy, AnchorPane rootPane,
      AnchorPane containerPane, StackPane canvas) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    this.canvas = canvas;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + UI_FILE_PREFIX + language);
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
    if (shapeProxy.getShape() == null) return;

    if (authoringProxy.getCurrentScreenTitle().equals("Game Objects")) {
      shapeProxy.setFinalShapeDisplay();
      try {
        GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer().clone();
        authoringProxy.setGameObject(shapeProxy.getShape(), copy);
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
      }
    }
    shapeProxy.selectShape(shape);
    shape.setStroke(Color.YELLOW);
    shapeProxy.updateShapeSelectionDisplay();
    gameObjectTypeDropdown.valueProperty().setValue(null);
    clearFields();
    updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());

  }
  private void clearFields() {
    collidableTypeDropDown.getCheckModel().clearChecks();
    playerAssignmentListView.getSelectionModel().clearSelection();
    kFrictionTextField.clear();
    sFrictionTextField.clear();
    massTextField.clear();
    elasticityCheckBox.setSelected(false);
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

    // removing hardcoding - 2 or more times, appears in UI, involved in logic

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

    Label label = new Label(resourceBundle.getString("Angle"));

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

    kFriction = new Label(resourceBundle.getString("kFriction"));
    AnchorPane.setRightAnchor(kFriction, 300.0);
    AnchorPane.setTopAnchor(kFriction, 120.0);

    sFrictionTextField = new TextField();
    sFrictionTextField.setId("sFriction");
    sFrictionTextField.textProperty().addListener(new TextFieldListener(sFrictionTextField.getId(), shapeProxy));
    sFrictionTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(sFrictionTextField, 450.0);
    AnchorPane.setTopAnchor(sFrictionTextField, 160.0);

    sFriction = new Label(resourceBundle.getString("sFriction"));
    AnchorPane.setRightAnchor(sFriction, 300.0);
    AnchorPane.setTopAnchor(sFriction, 160.0);

    containerPane.getChildren()
        .addAll(kFrictionTextField, kFriction, sFrictionTextField, sFriction);
  }

  private void createCollidableOptions() {
    createCollidableTypeOptions();
    createCollidableParameterOptions();
  }

  private void createPlayerAssignment() {
    playerAssignmentListView = new ListView<>();

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
    AnchorPane.setRightAnchor(collidableTypeDropDown, 300.0);
    AnchorPane.setTopAnchor(collidableTypeDropDown, 200.0);
    collidableTypeDropDown.setPrefSize(200, 50);
    if (!containerPane.getChildren().contains(collidableTypeDropDown)) {
      containerPane.getChildren().add(collidableTypeDropDown);
    }
  }

  private void createCollidableParameterOptions() {
    massTextField = new TextField();
    massTextField.setId("mass");
    massTextField.textProperty().addListener(new TextFieldListener(massTextField.getId(), shapeProxy));
    massTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(massTextField, 450.0);
    AnchorPane.setTopAnchor(massTextField, 270.0);

    mass = new Label(resourceBundle.getString("Mass"));
    AnchorPane.setRightAnchor(mass, 410.0);
    AnchorPane.setTopAnchor(mass, 270.0);

    elasticityCheckBox = new CheckBox();
    elasticityCheckBox.setId("elasticity");
    elasticityCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
      shapeProxy.getGameObjectAttributesContainer().setElasticity(newValue);
    });
    elasticityCheckBox.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(elasticityCheckBox, 450.0);
    AnchorPane.setTopAnchor(elasticityCheckBox, 310.0);

    elasticity = new Label(resourceBundle.getString("Elasticity"));
    AnchorPane.setRightAnchor(elasticity, 390.0);
    AnchorPane.setTopAnchor(elasticity, 310.0);

    containerPane.getChildren()
        .addAll(massTextField, mass, elasticityCheckBox, elasticity);
  }

  private void addNewPlayerToProxy() {
    authoringProxy.getPlayers().putIfAbsent(authoringProxy.getCurrentPlayerId(), new HashMap<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.STRIKABLE, new ArrayList<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.CONTROLLABLE, new ArrayList<>());
    authoringProxy.getPlayers().get(authoringProxy.getCurrentPlayerId()).putIfAbsent(CollidableType.SCOREABLE, new ArrayList<>());
  }
  private void createMakePlayers() {
    // default 1 player
    addNewPlayerToProxy();

    Label numPlayersLabel = new Label(resourceBundle.getString("Players"));
    AnchorPane.setTopAnchor(numPlayersLabel, 525.0);
    AnchorPane.setRightAnchor(numPlayersLabel, 90.0);

    removePlayerButton = new Button(resourceBundle.getString("removeButton"));
    removePlayerButton.setPrefSize(50, 50);
    AnchorPane.setRightAnchor(removePlayerButton, 175.0);
    AnchorPane.setTopAnchor(removePlayerButton, 550.0);

    addPlayerButton = new Button(resourceBundle.getString("addButton"));
    addPlayerButton.setPrefSize(50, 50);
    AnchorPane.setRightAnchor(addPlayerButton, 50.0);
    AnchorPane.setTopAnchor(addPlayerButton, 550.0);

    numPlayers = new Text(String.valueOf(authoringProxy.getNumPlayers()));
    AnchorPane.setRightAnchor(numPlayers, 130.0);
    AnchorPane.setTopAnchor(numPlayers, 565.0);

    containerPane.getChildren()
        .addAll(removePlayerButton, addPlayerButton, numPlayersLabel, numPlayers);
  }

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
  private void handlePlayerAssignment() {
    handlePlayerListViewOnChange();
//    handleScorableCheckBoxOnChange();
    handleCollidableTypeDropdownOnChange();
  }
  private void handlePlayerListViewOnChange() {
    playerAssignmentListView.getSelectionModel().selectedIndexProperty().addListener(((observable, oldPlayerId, newPlayerId) -> {
      List<CollidableType> collidableTypes = collidableTypeDropDown.getCheckModel().getCheckedItems();
      for (CollidableType type: collidableTypes) {
        if ((Integer) oldPlayerId >= 0) removeCollidableTypeFromAuthoringPlayer((Integer) oldPlayerId, type);
        addCollidableToAuthoringPlayer((Integer) newPlayerId, type, type.equals(CollidableType.CONTROLLABLE));
      }
    }));
  }
  private void handleCollidableTypeDropdownOnChange() {
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
            }
            else {
              setPlayerAssignmentVisibility(true);
              if (selected.equals(CollidableType.CONTROLLABLE)) {
                List<Integer> xySpeeds = Panel.enterConstantParamsPopup(2, resourceBundle.getString("controllableXYSpeeds"));
                shapeProxy.getGameObjectAttributesContainer().setControllableXSpeed(xySpeeds.get(0));
                shapeProxy.getGameObjectAttributesContainer().setControllableYSpeed(xySpeeds.get(1));
              }
              addCollidableToAuthoringPlayer(playerAssignmentListView.getSelectionModel().getSelectedIndex(),
                  selected, selected.equals(CollidableType.CONTROLLABLE));
            }
          }
        }
        else {
          for (CollidableType removed : collidableType.getRemoved()) {
            if (shapeProxy.getGameObjectAttributesContainer().getProperties().contains(removed.toString())) {
              shapeProxy.getGameObjectAttributesContainer().getProperties().remove(removed.toString());
            }
            if (!removed.equals(CollidableType.NONCONTROLLABLE)) {
              removeCollidableTypeFromAuthoringPlayer(playerAssignmentListView.getSelectionModel().getSelectedIndex(),
                  removed);
            }
          }
        }
      }
    });
  }

  private void addCollidableToAuthoringPlayer(int selectedPlayerId, CollidableType collidableType, boolean isControllable) {
    Map<Integer, Map<CollidableType, List<Integer>>> playersMap = authoringProxy.getPlayers();
    if (selectedPlayerId >= 0) {
      if (isControllable) {
        int xSpeed = shapeProxy.getGameObjectAttributesContainer().getControllableXSpeed();
        int ySpeed = shapeProxy.getGameObjectAttributesContainer().getControllableYSpeed();
        playersMap.get(selectedPlayerId).put(collidableType, List.of(Integer.parseInt(shapeProxy.getShape().getId()), xSpeed, ySpeed));
      }
      else if (!playersMap.get(selectedPlayerId).get(collidableType).contains(Integer.parseInt(shapeProxy.getShape().getId()))) {
        playersMap.get(selectedPlayerId).get(collidableType).add(Integer.parseInt(shapeProxy.getShape().getId()));
      }
    }
  }

  private void removeObjectFromAuthoringPlayersAnyList() {
    removeFromAllAuthoringPlayers(CollidableType.STRIKABLE);
    removeFromAllAuthoringPlayers(CollidableType.CONTROLLABLE);
    removeFromAllAuthoringPlayers(CollidableType.SCOREABLE);
  }

  // remove selected shape from the player holding it
  private void removeFromAllAuthoringPlayers(CollidableType collidableType) {
    Map<Integer, Map<CollidableType, List<Integer>>> playersMap = authoringProxy.getPlayers();
    for (Integer player: playersMap.keySet()) {
      removeCollidableTypeFromAuthoringPlayer(player, collidableType);
    }
  }

  private void removeCollidableTypeFromAuthoringPlayer(int playerId, CollidableType collidableType) {
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
  }

  private void setPlayerAssignmentVisibility(boolean visibility) {
    playerAssignmentListView.setVisible(visibility);
  }

}
