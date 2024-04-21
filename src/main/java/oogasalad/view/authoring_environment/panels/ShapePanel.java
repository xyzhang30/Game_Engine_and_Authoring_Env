package oogasalad.view.authoring_environment.panels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import oogasalad.view.authoring_environment.Coordinate;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;

public class ShapePanel implements Panel {

  protected final ShapeProxy shapeProxy;
  protected final AuthoringProxy authoringProxy;
  protected final StackPane canvas;
  protected final AnchorPane rootPane;
  protected final AnchorPane containerPane;
  private final List<Shape> templateShapes = new ArrayList<>();
  private Coordinate startPos;
  private Coordinate translatePos;
  private Slider xSlider;
  private Slider ySlider;
  private Slider angleSlider;
  private ComboBox<GameObjectType> gameObjectTypeDropdown;
  private ComboBox<String> collidableTypeDropDown;
  private TextField kFrictionTextField;
  private TextField sFrictionTextField;
  private TextField massTextField;
  private TextField elasticityTextField;
  private ListView<String> playerAssignmentListView;
  private CheckBox scoreableCheckBox;
  private Button addPlayerButton;
  private Button removePlayerButton;
  private Text numPlayers;
  private Label kFriction;
  private Label sFriction;
  private Label mass;
  private Label elasticity;
  private Label scoreable;

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
    templateShapes.addAll(shapeProxy.createTemplateShapes()); // strategy
    containerPane.getChildren().addAll(templateShapes);
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
    for (Shape shape : templateShapes) {
      handleShapeEvents(shape);
    }
    handleGameObjectTypeSelection();
    handlePlayerAssignment();
    handleAddAndRemovePlayers();
  }

  private void handleShapeEvents(Shape shape) {
    shape.setOnMouseClicked(event -> setShapeOnClick((Shape) event.getSource()));
    shape.setOnMousePressed(event -> handleMousePressed(event));
    shape.setOnMouseDragged(event -> setShapeOnCompleteDrag((Shape) event.getSource(), event));
    shape.setOnMouseReleased(event -> setShapeOnRelease((Shape) event.getSource()));
  }

  private void handleMousePressed(MouseEvent event) {
    Shape shape = (Shape) event.getSource();
    try {
      duplicateAndDragShape(shape, event);
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }
  }
  private void duplicateAndDragShape(Shape originalShape, MouseEvent event) throws ReflectiveOperationException {
    System.out.println("Initiating Drag: " + originalShape);

    // Duplicate shape with properties
    Shape duplicateShape = originalShape.getClass().getDeclaredConstructor().newInstance();
    duplicateShape.setFill(originalShape.getFill());
    duplicateShape.setStroke(originalShape.getStroke());
    duplicateShape.setStrokeWidth(originalShape.getStrokeWidth());
    duplicateShape.setId(String.valueOf(shapeProxy.getShapeCount())); // Update ID to next available
    shapeProxy.setShapeCount(shapeProxy.getShapeCount()+1);  // Increment shape count

    // Add event handlers if not already handled
    handleShapeEvents(duplicateShape);

    // Add duplicate to the pane
    containerPane.getChildren().add(duplicateShape);

    // Optionally, manage the original shape's location or properties
    moveShapeToCanvas(originalShape, event); // Ensure this is intended for the original
  }


  private void moveShapeToCanvas(Shape shape, MouseEvent event) {
    if (shape.getParent() != null) {
      ((Pane) shape.getParent()).getChildren().remove(shape);
    }
    rootPane.getChildren().add(shape);
    templateShapes.remove(shape);
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
    }
  }

  private void setShapeOnClick(Shape shape) {
    if (shapeProxy.getShape() != null) {
      shapeProxy.setFinalShapeDisplay();
      authoringProxy.setGameObject(shapeProxy.getShape(), shapeProxy.getGameObjectAttributesContainer());
    }

    shapeProxy.setShape(shape);
    gameObjectTypeDropdown.valueProperty().setValue(null);
    clearFields();
    shape.setStroke(Color.YELLOW);
    shapeProxy.updateShapeSelectionDisplay();

    updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
  }

  private void clearFields() {
    collidableTypeDropDown.valueProperty().setValue(null);
    playerAssignmentListView.getSelectionModel().clearSelection();
    kFrictionTextField.clear();
    sFrictionTextField.clear();
    massTextField.clear();
    elasticityTextField.clear();
    scoreableCheckBox.setSelected(false);
    setCollidableOptionVisibility(false);
    setSurfaceOptionVisibility(false);
    setPlayerAssignmentVisibility(false);
  }

  // Refactor to the ShapeProxy -> separate into perform different handle events for shape in container (templates) vs shape in canvas
//  private void handleShapeEvents(Shape shape) {
//    shape.setOnMouseClicked(event -> setShapeOnClick(shape));
//    shape.setOnMousePressed(event -> {
//      try {
//        setShapeOnDrag(shape, event);
//      } catch (NoSuchMethodException e) {
//        throw new RuntimeException(e);
//      } catch (InvocationTargetException e) {
//        throw new RuntimeException(e);
//      } catch (InstantiationException e) {
//        throw new RuntimeException(e);
//      } catch (IllegalAccessException e) {
//        throw new RuntimeException(e);
//      }
//    });
//    shape.setOnMouseDragged(event -> setShapeOnCompleteDrag(shape, event));
//    shape.setOnMouseReleased(event -> setShapeOnRelease(shape));
//    // JavaFX drag and drop -> drop target
//  }

  //  private void addElements() {
//    for (Shape shape : shapePositionMap.keySet()) {
//      shape.setOnMousePressed(null);
//      shape.setOnMouseClicked(null);
//      shape.setOnMouseDragged(null);
//      AnchorPane.setTopAnchor(shape, shapePositionMap.get(shape).get(0));
//      AnchorPane.setLeftAnchor(shape, shapePositionMap.get(shape).get(1));
//      rootPane.getChildren().add(shape);
//    }
//  }
//  private void setShapeOnClick(Shape shape) {
//    shapeProxy.setShape(shape);
//    shape.setStroke(Color.YELLOW);
//    if (shape.getStrokeWidth() != 0) {
//      shape.setStrokeWidth(5);
//    } else {
//      shape.setStrokeWidth(0);
//    }
//    updateSlider(shape.getScaleX(), shape.getScaleY(), shape.getRotate());
////    for (Shape currShape : authoringProxy.getControllables()) {
////      if (!currShape.equals(shape)) {
////        currShape.setStrokeWidth(0);
////      }
////    }
//  }

  private void setShapeOnDrag(Shape shape, MouseEvent event)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    // TODO: make a copy for keeping a template -> BETTER DESGIN?
    System.out.println("DRAGGING");
    // shape.getClass(

    System.out.println(shape);
    rootPane.getChildren().add(shape);
    templateShapes.remove(shape);
    shapeProxy.setShape(shape);
    shape.setStroke(Color.GREEN);
    //shape.setId(String.valueOf(authoringProxy.getControllables().size() + 1));

    // JavaFX drag and drop -> drop target - example, labe Reflection
    Shape duplicateShape = shape.getClass().getDeclaredConstructor()
        .newInstance(); // TODO: Handle exception

    templateShapes.add(duplicateShape);
    handleShapeEvents(duplicateShape);
    containerPane.getChildren().add(duplicateShape);

    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
  }

//  private void setShapeOnCompleteDrag(Shape shape, MouseEvent event) {
//    System.out.println("DRAGGED");
//    System.out.println(shape);
//    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(),
//        event.getSceneY() - startPos.y());
//    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(),
//        translatePos.y() + offset.y());
//    shape.setTranslateX(newTranslatePos.x());
//    shape.setTranslateY(newTranslatePos.y());
//  }

  private boolean isInAuthoringBox(Shape shape) {
    Bounds shapeBounds = shape.getBoundsInParent();
    Bounds authoringBoxBounds = canvas.getBoundsInParent();
    System.out.println(shapeBounds);
    System.out.println(authoringBoxBounds);
    return authoringBoxBounds.contains(shapeBounds);
  }

//  private void setShapeOnRelease(Shape shape) {
//    System.out.println("RELEASED");
//    System.out.println(shape);
//    System.out.println(shape.getTranslateX());
//    System.out.println(shape.getTranslateY());
//    if (isInAuthoringBox(shape)) {
////      shape.setStrokeWidth(0);
//      //authoringProxy.addControllableShape(shape);
//      Coordinate coordinate = new Coordinate(AnchorPane.getLeftAnchor(shape),
//          AnchorPane.getTopAnchor(shape));
//      authoringProxy.addShapePosition(shape, coordinate);
//    } else {
//      shape.setVisible(false);
//    }
//  }

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
    slider.setMin(0.2);
    slider.setMax(2);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.1);
    slider.setOrientation(Orientation.HORIZONTAL);

    Label label = new Label(labelText);
    HBox sliderContainer = new HBox(label, slider);
    sliderContainer.setSpacing(10);

    sliderContainerBox.getChildren().add(sliderContainer);
    return slider;
  }

  private void updateSlider(double xScale, double yScale, double angle) {
    xSlider.setValue(xScale);
    ySlider.setValue(yScale);
    angleSlider.setValue(angle);
  }

  private void changeAngle(double angle) {
    shapeProxy.getShape().setRotate(angle);
  }

  private void changeXSize(double xScale) {
    shapeProxy.getShape().setScaleX(xScale);
  }

  private void changeYSize(double yScale) {
    shapeProxy.getShape().setScaleY(yScale);
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
    createScoreableOption();
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
    massTextField.setId("mass");
    massTextField.textProperty().addListener(new TextFieldListener(massTextField.getId(), shapeProxy));
    massTextField.setPrefSize(40, 20);
    AnchorPane.setRightAnchor(massTextField, 450.0);
    AnchorPane.setTopAnchor(massTextField, 270.0);

    mass = new Label("Mass");
    AnchorPane.setRightAnchor(mass, 410.0);
    AnchorPane.setTopAnchor(mass, 270.0);

    elasticityTextField = new TextField();
    elasticityTextField.setId("elasticity");
    elasticityTextField.textProperty().addListener(new TextFieldListener(elasticityTextField.getId(), shapeProxy));
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
    // default 1 player
    authoringProxy.getPlayers().putIfAbsent(authoringProxy.getNumPlayers(), new ArrayList<>());

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
        shapeProxy.getGameObjectAttributesContainer().getProperties().remove(oldVal.toString().toLowerCase());
      }
      shapeProxy.getGameObjectAttributesContainer().getProperties().add(gameObjectType.toString().toLowerCase());
      updateSelectionOptions(gameObjectType);

      if (gameObjectType.equals(GameObjectType.SURFACE)) {
        removeFromAuthoringPlayers();
        setPlayerAssignmentVisibility(false);
      }

    });
  }

  private void handleAddAndRemovePlayers() {
    addPlayerButton.setOnMouseClicked(e -> {
      authoringProxy.increaseNumPlayers();
      authoringProxy.getPlayers().putIfAbsent(authoringProxy.getNumPlayers(), new ArrayList<>());
      playerAssignmentListView.getItems().add("Player " + authoringProxy.getNumPlayers());
      numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
    });

    removePlayerButton.setOnMouseClicked(e -> {
      if (authoringProxy.getNumPlayers() > 1) {
        authoringProxy.getPlayers().remove(authoringProxy.getNumPlayers());
        playerAssignmentListView.getItems().remove("Player " + authoringProxy.getNumPlayers());
        authoringProxy.decreaseNumPlayers();
        numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
      }
    });
  }

  private void updateProxyMapWithTextFieldInput(TextField textField,
      Consumer<String> inputConsumer) {
    if (textField.isVisible()) {
      String inputText = textField.getText();
      textField.clear();
      inputConsumer.accept(inputText);
    }
  }

  private void handlePlayerAssignment() {
    handlePlayerListViewOnChange();
    handleScorableCheckBoxOnChange();
    handleCollidableTypeDropdownOnChange();
  }
  private void handlePlayerListViewOnChange() {
    playerAssignmentListView.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newPlayerId) -> {
      if (scoreableCheckBox.isSelected()) addToAuthoringPlayers((Integer) newPlayerId);
    }));
  }
  private void handleCollidableTypeDropdownOnChange() {
    collidableTypeDropDown.valueProperty().addListener((obs, oldVal, collidableType) -> {
      if (collidableType == null) return;

      shapeProxy.getGameObjectAttributesContainer().getProperties().remove(oldVal);
      shapeProxy.getGameObjectAttributesContainer().getProperties().add(collidableType.toLowerCase());

      if (collidableType.equals("NON-CONTROLLABLE")) {
        removeFromAuthoringPlayers();
        setPlayerAssignmentVisibility(false);
      } else {
        setPlayerAssignmentVisibility(true);
        addToAuthoringPlayers(playerAssignmentListView.getSelectionModel().getSelectedIndex());
      }
    });
  }
  private void handleScorableCheckBoxOnChange() {
    scoreableCheckBox.selectedProperty().addListener((observable, oldValue, newState) -> {
      if (newState) {
        setPlayerAssignmentVisibility(true);
        addToAuthoringPlayers(playerAssignmentListView.getSelectionModel().getSelectedIndex());
      } else {
        removeFromAuthoringPlayers();
      }
    });
  }
  private void addToAuthoringPlayers(int selectedPlayerId) {
    Map<Integer, List<Integer>> playersMap = authoringProxy.getPlayers();
    if (selectedPlayerId >= 0) {
      if (!playersMap.get(selectedPlayerId+1).contains(Integer.parseInt(shapeProxy.getShape().getId()))) {
        playersMap.get(selectedPlayerId+1).add(Integer.parseInt(shapeProxy.getShape().getId()));
      }
    }
  }

  // remove selected shape from the player holding it
  private void removeFromAuthoringPlayers() {
    Map<Integer, List<Integer>> playersMap = authoringProxy.getPlayers();
    for (Integer player: playersMap.keySet()) {
      if (playersMap.get(player).contains(Integer.parseInt(shapeProxy.getShape().getId()))) {
        playersMap.get(player).remove((Integer) Integer.parseInt(shapeProxy.getShape().getId()));
      }
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
    elasticityTextField.setVisible(visibility);
    elasticity.setVisible(visibility);
    scoreableCheckBox.setVisible(visibility);
    scoreable.setVisible(visibility);
  }

  private void setPlayerAssignmentVisibility(boolean visibility) {
    playerAssignmentListView.setVisible(visibility);
  }

}
