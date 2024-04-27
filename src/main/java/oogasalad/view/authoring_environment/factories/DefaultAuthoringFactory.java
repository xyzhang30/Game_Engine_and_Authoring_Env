package oogasalad.view.authoring_environment.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.view.api.authoring.AuthoringFactory;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.authoring_environment.util.TextFieldListener;
import oogasalad.view.api.authoring.UIElementFactory;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.api.enums.CollidableType;
import oogasalad.view.api.enums.GameObjectType;
import oogasalad.view.api.enums.SupportedLanguage;
import org.controlsfx.control.CheckComboBox;

/**
 * The `DefaultAuthoringFactory` class is an implementation of the `AuthoringFactory` interface. It
 * provides the default implementations for configuring game objects, surfaces, collidables, and
 * players within the authoring environment. It also handles the interaction and customization of
 * different parameters such as size, angle, elasticity, and player assignments for the created game
 * objects. This class further utilizes `UIElementFactory` to create various UI elements such as
 * combo boxes, sliders, text fields, and buttons. The class maintains references to a `ShapeProxy`
 * and an `AuthoringProxy` for handling shape and authoring related data and actions.
 *
 * @author Judy He
 */
public class DefaultAuthoringFactory implements AuthoringFactory {

  private final UIElementFactory uiElementFactory;
  ResourceBundle resourceBundle;
  protected final ShapeProxy shapeProxy;
  protected final AuthoringProxy authoringProxy;
  private ComboBox<GameObjectType> gameObjectTypeDropdown = new ComboBox<>();
  private CheckComboBox<CollidableType> collidableTypeDropDown = new CheckComboBox<>();
  private final List<TextField> textFields = new ArrayList<>();
  private VBox surfaceParameters = new VBox();
  private VBox collidableParameters = new VBox();
  private Slider xSlider, ySlider, angleSlider;
  private CheckBox elasticityCheckBox;
  private ListView<String> playerAssignmentListView = new ListView<>();
  private Button addPlayerButton, removePlayerButton;
  private Text numPlayers;
  private final String VIEW_PROPERTIES_FOLDER = "properties.";

  /**
   * Constructor for default AuthoringFactory
   *
   * @param uiElementFactory the factory used for creating UI elements
   * @param language         the supported language
   * @param shapeProxy       the proxy used for handling shape-related data and actions
   * @param authoringProxy   the proxy used for handling authoring-related data and actions
   */
  public DefaultAuthoringFactory(UIElementFactory uiElementFactory, SupportedLanguage language,
      ShapeProxy shapeProxy, AuthoringProxy authoringProxy) {
    this.uiElementFactory = uiElementFactory;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + VIEW_PROPERTIES_FOLDER + UI_FILE_PREFIX + language);
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
  }

  /**
   * Creates the game objects configuration by providing a list of nodes containing the game object
   * type selection and game object display customization options.
   *
   * @return a list of nodes representing the game objects configuration
   */
  @Override
  public List<Node> createGameObjectsConfiguration() {
    return List.of(createGameObjectTypeSelection(), createGameObjectDisplayCostumization());
  }

  /**
   * Creates the surfaces configuration by providing a list of nodes containing the surface
   * parameters options. It also sets the visibility of the surface parameters options to false
   * initially.
   *
   * @return a list of nodes representing the surfaces configuration
   */
  @Override
  public List<Node> createSurfacesConfiguration() {
    List<Node> nodes = List.of(createSurfaceParameters());
    setSurfaceParametersOptionVisibility(false);
    return nodes;
  }

  /**
   * Creates the collidables configuration by providing a list of nodes containing the collidable
   * type selection and collidable parameters options. It also sets the visibility of the collidable
   * parameters options to false initially.
   *
   * @return a list of nodes representing the collidables configuration
   */
  @Override
  public List<Node> createCollidablesConfiguration() {
    List<Node> nodes = List.of(createCollidableTypeSelection(), createCollidableParameters());
    setCollidableParametersOptionVisibility(false);
    return nodes;
  }

  /**
   * Creates the players configuration by providing a list of nodes containing the player selection
   * and player addition and removal options. It also sets the visibility of the player assignment
   * list view to false initially.
   *
   * @return a list of nodes representing the players configuration
   */
  @Override
  public List<Node> createPlayersConfiguration() {
    List<Node> nodes = List.of(createPlayerSelection(), createPlayerAdditionAndRemoval());
    setPlayerAssignmentVisibility(false);
    return nodes;
  }

  /**
   * Resets the authoring elements by clearing the game object type dropdown and text fields, and
   * updating the sliders based on the shape proxy's shape properties.
   */
  @Override
  public void resetAuthoringElements() {
    gameObjectTypeDropdown.valueProperty().setValue(null);
    clearFields();
    updateSlider(shapeProxy.getShape().getScaleX(), shapeProxy.getShape().getScaleY(),
        shapeProxy.getShape().getRotate());
  }

//  @Override
//  public List<Node> createGameConfiguration() {
//    List<Node> nodes = List.of(//createGameNameLabel(),
//         createGameNameText(),
//       // createGameDescriptionLabel(),
//        createGameDescriptionText());//,
//      //  createGameImage());
//    return nodes;
//  }

  private Node createGameDescriptionText() {
    TextField gameDescriptionTextField = uiElementFactory.createTextField(
        "Provide Game Description", 200,
        50);
    gameDescriptionTextField.setEditable(
        true); //ids are populated automatically after user clicks on object, user can't edit it
    gameDescriptionTextField.setFocusTraversable(false);
    AnchorPane.setLeftAnchor(gameDescriptionTextField, 450.0);
    AnchorPane.setTopAnchor(gameDescriptionTextField, 700.0);
    return gameDescriptionTextField;
  }

//  private Node createGameDescriptionLabel() {
//return null;
//  }

//  private Node createGameNameText() {
//    Label gameNameLabel = new Label("Select Game Name");
//
//
//    TextField gameDescriptionTextField = uiElementFactory.createTextField(
//        "Provide Game Name", 200,
//        50);
//    gameDescriptionTextField.setEditable(
//        true); //ids are populated automatically after user clicks on object, user can't edit it
//    gameDescriptionTextField.setFocusTraversable(false);
//    AnchorPane.setLeftAnchor(gameDescriptionTextField, 450.0);
//    AnchorPane.setTopAnchor(gameDescriptionTextField, 800.0);
//    return gameDescriptionTextField;
//  }

  private Node createGameObjectTypeSelection() {
    this.gameObjectTypeDropdown = uiElementFactory.createComboBox("gameObjectTypeDropdown",
        List.of(GameObjectType.SURFACE, GameObjectType.COLLIDABLE),
        resourceBundle.getString("gameObjectSelectionPrompt"), 200, 50);
    AnchorPane.setRightAnchor(gameObjectTypeDropdown, 300.0);
    AnchorPane.setTopAnchor(gameObjectTypeDropdown, 50.0);
    handleGameObjectTypeSelection();
    return gameObjectTypeDropdown;
  }

  private Node createGameObjectDisplayCostumization() {
    List<HBox> sliders = createSliders();
    VBox sliderContainerBox = uiElementFactory.createVContainer(5, 200, 10, sliders.toArray(
        new Node[0]));
    AnchorPane.setTopAnchor(sliderContainerBox, 400.0);
    AnchorPane.setRightAnchor(sliderContainerBox, 50.0);
    sliderContainerBox.setAlignment(Pos.CENTER_RIGHT);

    xSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeXSize(newValue.doubleValue()));
    ySlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeYSize(newValue.doubleValue()));
    angleSlider.valueProperty().addListener((observable, oldValue, newValue) ->
        changeAngle(newValue.doubleValue()));

    return sliderContainerBox;
  }

  private Node createSurfaceParameters() {
    List<HBox> textFields = createSurfaceTextFields();
    VBox paramContainerBox = uiElementFactory.createVContainer(5, 40, 40, textFields.toArray(
        new Node[0]));
    AnchorPane.setRightAnchor(paramContainerBox, 300.0);
    AnchorPane.setTopAnchor(paramContainerBox, 160.0);
    paramContainerBox.setAlignment(Pos.CENTER);
    this.surfaceParameters = paramContainerBox;
    return paramContainerBox;
  }

  private Node createCollidableTypeSelection() {
    this.collidableTypeDropDown = uiElementFactory.createCheckComboBox("collidableTypeDropDown",
        List.of(CollidableType.STRIKABLE, CollidableType.SCOREABLE, CollidableType.CONTROLLABLE,
            CollidableType.NONCONTROLLABLE), 200, 50);
    AnchorPane.setRightAnchor(collidableTypeDropDown, 300.0);
    AnchorPane.setTopAnchor(collidableTypeDropDown, 200.0);
    handleCollidableTypeSelection();
    return collidableTypeDropDown;
  }

  private Node createCollidableParameters() {
    HBox massTextFieldContainer = createTextFieldContainer("massTextField",
        resourceBundle.getString("mass"));
    this.elasticityCheckBox = uiElementFactory.createCheckBox("elasticity", 40, 20);
    // handling
    elasticityCheckBox.selectedProperty().addListener(
        (observable, oldValue, newValue) -> shapeProxy.getGameObjectAttributesContainer()
            .setElasticity(newValue));

    Label elasticityLabel = new Label(resourceBundle.getString("elasticity"));
    HBox elasticityCheckBoxContainer = uiElementFactory.createHContainer(10, 40, 20,
        elasticityLabel, elasticityCheckBox);

    VBox paramContainerBox = uiElementFactory.createVContainer(5, 40, 40, massTextFieldContainer,
        elasticityCheckBoxContainer);
    AnchorPane.setRightAnchor(paramContainerBox, 390.0);
    AnchorPane.setTopAnchor(paramContainerBox, 310.0);
    paramContainerBox.setAlignment(Pos.CENTER_RIGHT);
    this.collidableParameters = paramContainerBox;

    return paramContainerBox;
  }

  private Node createPlayerAdditionAndRemoval() {
    authoringProxy.addNewPlayer();

    Label numPlayersLabel = new Label(resourceBundle.getString("players"));
    removePlayerButton = uiElementFactory.createButton("removeBTN",
        resourceBundle.getString("removeButton"), 50, 50);
    addPlayerButton = uiElementFactory.createButton("addBTN", resourceBundle.getString("addButton"),
        50, 50);
    numPlayers = new Text(String.valueOf(authoringProxy.getNumPlayers()));
    HBox hBox = uiElementFactory.createHContainer(10, 150, 50, removePlayerButton, numPlayers,
        addPlayerButton);

    VBox elementsVBox = uiElementFactory.createVContainer(5, 150, 60, numPlayersLabel, hBox);
    AnchorPane.setRightAnchor(elementsVBox, 50.0);
    AnchorPane.setTopAnchor(elementsVBox, 565.0);
    elementsVBox.setAlignment(Pos.CENTER);

    handleAddAndRemovePlayers();

    return elementsVBox;
  }

  private Node createPlayerSelection() {
    playerAssignmentListView = uiElementFactory.createListView("playerListView", 200, 150);
    for (int currPlayerNum = 1; currPlayerNum <= authoringProxy.getNumPlayers(); currPlayerNum++) {
      playerAssignmentListView.getItems().add("Player " + currPlayerNum);
    }
    AnchorPane.setRightAnchor(playerAssignmentListView, 300.0);
    AnchorPane.setTopAnchor(playerAssignmentListView, 400.0);
    setPlayerAssignmentVisibility(true);
    handlePlayerListViewOnChange();
    return playerAssignmentListView;
  }

  private List<HBox> createSliders() {
    return List.of(createSliderContainer("XSizeSlider", resourceBundle.getString("XScaleLabel")),
        createSliderContainer("YSizeSlider", resourceBundle.getString("YScaleLabel")),
        createSliderContainer("AngleSlider", resourceBundle.getString("AngleLabel"))
    );
  }

  private HBox createSliderContainer(String id, String labelText) {
    Slider slider = uiElementFactory.createSlider(id, 200, 0, 20, 1);
    if (labelText.equals(resourceBundle.getString("XScaleLabel"))) {
      this.xSlider = slider;
    } else if (labelText.equals(resourceBundle.getString("YScaleLabel"))) {
      this.ySlider = slider;
    } else {
      slider = uiElementFactory.createSlider(id, 200, 0, 360, 1);
      this.angleSlider = slider;
//      this.angleSlider = uiElementFactory.createSlider(id, 200, 0, 360, 1);
    }
    Label label = new Label(labelText);
    label.setMinWidth(80);
    return uiElementFactory.createHContainer(10, 1500, 10, label, slider);
  }

  private void updateSlider(double xScale, double yScale, double angle) {
    xSlider.setValue(xScale);
    ySlider.setValue(yScale);
    angleSlider.setValue(angle);
  }

  private List<HBox> createSurfaceTextFields() {
    return List.of(createTextFieldContainer("kFriction", resourceBundle.getString("kFriction")),
        createTextFieldContainer("sFriction", resourceBundle.getString("sFriction"))
    );
  }

  private HBox createTextFieldContainer(String id, String labelText) {
    TextField textField = uiElementFactory.createTextField(id, 100, 20);
    textField.textProperty().addListener(new TextFieldListener(textField.getId(), shapeProxy));
    Label label = new Label(labelText);
    label.setMinWidth(100);
    textFields.add(textField);
    return uiElementFactory.createHContainer(10, 100, 20, label, textField);
  }

  // functionalities

  // update players
  private void handleAddAndRemovePlayers() {
    addPlayerButton.setOnMouseClicked(e -> handleAddPlayer());
    removePlayerButton.setOnMouseClicked(e -> handleRemovePlayer());
  }

  private void handleAddPlayer() {
    authoringProxy.increaseNumPlayers();
    authoringProxy.addNewPlayer();
    playerAssignmentListView.getItems().add("Player " + authoringProxy.getNumPlayers());
    updateNumPlayers();
  }

  private void handleRemovePlayer() {
    if (authoringProxy.getNumPlayers() <= 1) {
      return;
    }
    authoringProxy.removeMostRecentAddedPlayer();
    playerAssignmentListView.getItems().remove("Player " + authoringProxy.getCurrentPlayerId());
    authoringProxy.decreaseNumPlayers();
    updateNumPlayers();
  }

  private void updateNumPlayers() {
    numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
  }

  private void handlePlayerListViewOnChange() {
    playerAssignmentListView.getSelectionModel().selectedIndexProperty()
        .addListener(((observable, oldPlayerId, newPlayerId) -> {
          List<CollidableType> collidableTypes = collidableTypeDropDown.getCheckModel()
              .getCheckedItems();
          for (CollidableType type : collidableTypes) {
            if ((Integer) oldPlayerId >= 0) {
              authoringProxy.removeCollidableFromPlayer((Integer) oldPlayerId, type,
                  Integer.parseInt(shapeProxy.getShape().getId()));
            }
            int xSpeed = shapeProxy.getGameObjectAttributesContainer().getControllableXSpeed();
            int ySpeed = shapeProxy.getGameObjectAttributesContainer().getControllableYSpeed();
            authoringProxy.addCollidableToPlayer((Integer) newPlayerId, type,
                Integer.parseInt(shapeProxy.getShape().getId()),
                type.equals(CollidableType.CONTROLLABLE), xSpeed, ySpeed);
          }
        }));
  }

  // object display
  private void changeAngle(double angle) {
    shapeProxy.getShape().setRotate(angle);
    // shapeProxy.getGameObjectAttributesContainer().setAngle(angle);
  }

  private void changeXSize(double xScale) {
    shapeProxy.getShape().setScaleX(xScale);
    shapeProxy.getGameObjectAttributesContainer()
        .setWidth(shapeProxy.getShape().getLayoutBounds().getWidth() * xScale);
  }

  private void changeYSize(double yScale) {
    shapeProxy.getShape().setScaleY(yScale);
    shapeProxy.getGameObjectAttributesContainer()
        .setHeight(shapeProxy.getShape().getLayoutBounds().getHeight() * yScale);
  }

  // game object type selection
  private void handleGameObjectTypeSelection() {
    gameObjectTypeDropdown.valueProperty().addListener((obs, oldVal, gameObjectType) -> {
      if (gameObjectType == null || shapeProxy.getShape() == null) {
        return;
      }
      clearFieldsAndRemoveOldProperty(oldVal);
      addNewPropertyAndUpdateParameterOptions(gameObjectType);
      updatePlayersIfChangeToSurface(gameObjectType);
    });
  }

  private void clearFieldsAndRemoveOldProperty(GameObjectType oldVal) {
    clearFields();
    if (oldVal != null) {
      shapeProxy.getGameObjectAttributesContainer().getProperties().remove(String.valueOf(oldVal));
    }
  }

  private void addNewPropertyAndUpdateParameterOptions(GameObjectType gameObjectType) {
    shapeProxy.getGameObjectAttributesContainer().getProperties()
        .add(String.valueOf(gameObjectType));
    updateGameObjectParameterSelectionOptions(gameObjectType);
  }

  private void updatePlayersIfChangeToSurface(GameObjectType gameObjectType) {
    if (gameObjectType.equals(GameObjectType.SURFACE)) {
      authoringProxy.removeObjectFromPlayersAllLists(
          Integer.parseInt(shapeProxy.getShape().getId()));
      setPlayerAssignmentVisibility(false);
    }
  }

  private void updateGameObjectParameterSelectionOptions(GameObjectType gameObjectType) {
    if (gameObjectType.equals(GameObjectType.COLLIDABLE)) {
      setSurfaceParametersOptionVisibility(false);
      setCollidableParametersOptionVisibility(true);
    } else if (gameObjectType.equals(GameObjectType.SURFACE)) {
      setSurfaceParametersOptionVisibility(true);
      setCollidableParametersOptionVisibility(false);
    }
  }

  // collidable type selection
  private void handleCollidableTypeSelection() {
    collidableTypeDropDown.getCheckModel().getCheckedItems()
        .addListener((ListChangeListener<CollidableType>) change -> {
          while (change.next()) {
            handleAddedCollidableTypes(change);
            handleRemovedCollidableTypes(change);
          }
        });
  }

  private void handleAddedCollidableTypes(
      ListChangeListener.Change<? extends CollidableType> change) {
    if (change.wasAdded()) {
      for (CollidableType addedType : change.getAddedSubList()) {
        addCollidableType(addedType);
      }
    }
  }

  private void handleRemovedCollidableTypes(
      ListChangeListener.Change<? extends CollidableType> change) {
    if (change.wasRemoved()) {
      for (CollidableType removedType : change.getRemoved()) {
        removeCollidableType(removedType);
      }
    }
  }

  private void addCollidableType(CollidableType addedType) {
    if (!shapeProxy.getGameObjectAttributesContainer().getProperties()
        .contains(addedType.toString())) {
      shapeProxy.getGameObjectAttributesContainer().getProperties().add(addedType.toString());
    }

    if (addedType.equals(CollidableType.NONCONTROLLABLE)) {
      authoringProxy.removeObjectFromPlayersAllLists(
          Integer.parseInt(shapeProxy.getShape().getId()));
      setPlayerAssignmentVisibility(false);
    } else {
      setPlayerAssignmentVisibility(true);
      handleControllableType(addedType);
      int xSpeed = shapeProxy.getGameObjectAttributesContainer().getControllableXSpeed();
      int ySpeed = shapeProxy.getGameObjectAttributesContainer().getControllableYSpeed();
      authoringProxy.addCollidableToPlayer(
          playerAssignmentListView.getSelectionModel().getSelectedIndex(),
          addedType, Integer.parseInt(shapeProxy.getShape().getId()),
          addedType.equals(CollidableType.CONTROLLABLE), xSpeed, ySpeed);
    }
  }

  private void handleControllableType(CollidableType addedType) {
    if (addedType.equals(CollidableType.CONTROLLABLE)) {
      List<Integer> xySpeeds = uiElementFactory.createConstantParamsPopup(
          Integer.parseInt(defaultValuesResourceBundle.getString("numParamControllableSpeed")),
          resourceBundle.getString("controllableXYSpeeds"));
      shapeProxy.getGameObjectAttributesContainer().setControllableXSpeed(xySpeeds.get(0));
      shapeProxy.getGameObjectAttributesContainer().setControllableYSpeed(xySpeeds.get(1));
    }
  }

  private void removeCollidableType(CollidableType removedType) {
    if (shapeProxy.getGameObjectAttributesContainer().getProperties()
        .contains(removedType.toString())) {
      shapeProxy.getGameObjectAttributesContainer().getProperties().remove(removedType.toString());
    }

    if (!removedType.equals(CollidableType.NONCONTROLLABLE)) {
      authoringProxy.removeCollidableFromPlayer(
          playerAssignmentListView.getSelectionModel().getSelectedIndex(),
          removedType, Integer.parseInt(shapeProxy.getShape().getId()));
    }
  }

  // update view of fields
  private void clearFields() {
    collidableTypeDropDown.getCheckModel().clearChecks();
    playerAssignmentListView.getSelectionModel().clearSelection();
    for (TextField textField : textFields) {
      textField.clear();
    }
    elasticityCheckBox.setSelected(false);
    setCollidableParametersOptionVisibility(false);
    setSurfaceParametersOptionVisibility(false);
    setPlayerAssignmentVisibility(false);
  }

  private void setSurfaceParametersOptionVisibility(boolean visibility) {
    surfaceParameters.setVisible(visibility);
  }

  private void setCollidableParametersOptionVisibility(boolean visibility) {
    collidableTypeDropDown.setVisible(visibility);
    collidableParameters.setVisible(visibility);
  }

  private void setPlayerAssignmentVisibility(boolean visibility) {
    playerAssignmentListView.setVisible(visibility);
  }

}
