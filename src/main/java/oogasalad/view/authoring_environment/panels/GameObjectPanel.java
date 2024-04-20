//package oogasalad.view.authoring_environment.panels;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Consumer;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.SelectionMode;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.text.Text;
//import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;
//
//// TODO: FIX DESIGN OF RECEIVE INPUT FROM TEXT FIELDS
//public class GameObjectPanel extends ShapePanel {
//
//  private ComboBox<GameObjectType> gameObjectTypeDropdown;
//  private ComboBox<String> collidableTypeDropDown;
//  private TextField kFrictionTextField;
//  private TextField sFrictionTextField;
//  private TextField massTextField;
//  private TextField elasticityTextField;
//  private ListView<String> playerAssignmentListView;
//  private CheckBox scoreableCheckBox;
//  private Button addPlayerButton;
//  private Button removePlayerButton;
//  private Text numPlayers;
//  private Label kFriction;
//  private Label sFriction;
//  private Label mass;
//  private Label elasticity;
//  private Label scoreable;
//
//  public GameObjectPanel(AuthoringProxy authoringProxy, ShapeProxy shapeProxy,
//      AnchorPane rootPane, AnchorPane containerPane, StackPane canvas) {
//    super(authoringProxy, shapeProxy, rootPane, containerPane, canvas);
//  }
//
//  @Override
//  public void handleEvents() {
//    super.handleEvents();
//    handleGameObjectTypeSelection();
//    handlePlayerAssignment();
//    handleAddAndRemovePlayers();
//  }
//
//  @Override
//  public void createElements() {
//    super.createElements();
//    createGameObjectTypeSelection();
//    createSurfaceOptions();
//    createCollidableOptions();
//    createMakePlayers();
//    createPlayerAssignment();
//    setCollidableOptionVisibility(false);
//    setSurfaceOptionVisibility(false);
//    setPlayerAssignmentVisibility(false);
//  }
//
//  private void createGameObjectTypeSelection() {
//    gameObjectTypeDropdown = new ComboBox<>();
//    gameObjectTypeDropdown.getItems()
//        .addAll(GameObjectType.SURFACE, GameObjectType.COLLIDABLE);
//    gameObjectTypeDropdown.setPromptText("Select Obstacle Type");
//    AnchorPane.setRightAnchor(gameObjectTypeDropdown, 300.0);
//    AnchorPane.setTopAnchor(gameObjectTypeDropdown, 50.0);
//    gameObjectTypeDropdown.setPrefSize(200, 50);
//    containerPane.getChildren().add(gameObjectTypeDropdown);
//  }
//
//  private void createSurfaceOptions() {
//    kFrictionTextField = new TextField();
//    kFrictionTextField.setId("kFriction");
//    kFrictionTextField.textProperty().addListener(new TextFieldListener(kFrictionTextField.getId(), shapeProxy));
//    kFrictionTextField.setPrefSize(40, 20);
//    AnchorPane.setRightAnchor(kFrictionTextField, 450.0);
//    AnchorPane.setTopAnchor(kFrictionTextField, 120.0);
//
//    kFriction = new Label("Kinetic Friction Coefficient");
//    AnchorPane.setRightAnchor(kFriction, 300.0);
//    AnchorPane.setTopAnchor(kFriction, 120.0);
//
//    sFrictionTextField = new TextField();
//    sFrictionTextField.setId("sFriction");
//    sFrictionTextField.textProperty().addListener(new TextFieldListener(sFrictionTextField.getId(), shapeProxy));
//    sFrictionTextField.setPrefSize(40, 20);
//    AnchorPane.setRightAnchor(sFrictionTextField, 450.0);
//    AnchorPane.setTopAnchor(sFrictionTextField, 160.0);
//
//    sFriction = new Label("Static Friction Coefficient");
//    AnchorPane.setRightAnchor(sFriction, 300.0);
//    AnchorPane.setTopAnchor(sFriction, 160.0);
//
//    containerPane.getChildren()
//        .addAll(kFrictionTextField, kFriction, sFrictionTextField, sFriction);
//  }
//
//  private void createCollidableOptions() {
//    createCollidableTypeOptions();
//    createCollidableParameterOptions();
//    createScoreableOption();
//  }
//
//  private void createPlayerAssignment() {
//    playerAssignmentListView = new ListView<String>();
//
//    for (int currPlayerNum = 1; currPlayerNum <= authoringProxy.getNumPlayers(); currPlayerNum++) {
//      playerAssignmentListView.getItems().add("Player " + currPlayerNum);
//    }
//
//    playerAssignmentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//    AnchorPane.setRightAnchor(playerAssignmentListView, 300.0);
//    AnchorPane.setTopAnchor(playerAssignmentListView, 400.0);
//    playerAssignmentListView.setPrefSize(200, 150);
//
//    containerPane.getChildren().add(playerAssignmentListView);
//  }
//
//  private void createCollidableTypeOptions() {
//    collidableTypeDropDown = new ComboBox<>();
//    collidableTypeDropDown.getItems()
//        .addAll("STRIKEABLE", "CONTROLLABLE", "NON-CONTROLLABLE");
//    collidableTypeDropDown.setPromptText("Select Collidable Type");
//    AnchorPane.setRightAnchor(collidableTypeDropDown, 300.0);
//    AnchorPane.setTopAnchor(collidableTypeDropDown, 200.0);
//    collidableTypeDropDown.setPrefSize(200, 50);
//    containerPane.getChildren().add(collidableTypeDropDown);
//  }
//
//  private void createCollidableParameterOptions() {
//    massTextField = new TextField();
//    massTextField.setId("mass");
//    massTextField.textProperty().addListener(new TextFieldListener(massTextField.getId(), shapeProxy));
//    massTextField.setPrefSize(40, 20);
//    AnchorPane.setRightAnchor(massTextField, 450.0);
//    AnchorPane.setTopAnchor(massTextField, 270.0);
//
//    mass = new Label("Mass");
//    AnchorPane.setRightAnchor(mass, 410.0);
//    AnchorPane.setTopAnchor(mass, 270.0);
//
//    elasticityTextField = new TextField();
//    elasticityTextField.setId("elasticity");
//    elasticityTextField.textProperty().addListener(new TextFieldListener(elasticityTextField.getId(), shapeProxy));
//    elasticityTextField.setPrefSize(40, 20);
//    AnchorPane.setRightAnchor(elasticityTextField, 450.0);
//    AnchorPane.setTopAnchor(elasticityTextField, 310.0);
//
//    elasticity = new Label("Elasticity");
//    AnchorPane.setRightAnchor(elasticity, 390.0);
//    AnchorPane.setTopAnchor(elasticity, 310.0);
//
//    containerPane.getChildren()
//        .addAll(massTextField, mass, elasticityTextField, elasticity);
//  }
//
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
//
//  private void createMakePlayers() {
//    Label numPlayersLabel = new Label("Number of Players");
//    AnchorPane.setTopAnchor(numPlayersLabel, 525.0);
//    AnchorPane.setRightAnchor(numPlayersLabel, 90.0);
//
//    removePlayerButton = new Button("-");
//    removePlayerButton.setPrefSize(50, 50);
//    AnchorPane.setRightAnchor(removePlayerButton, 175.0);
//    AnchorPane.setTopAnchor(removePlayerButton, 550.0);
//
//    addPlayerButton = new Button("+");
//    addPlayerButton.setPrefSize(50, 50);
//    AnchorPane.setRightAnchor(addPlayerButton, 50.0);
//    AnchorPane.setTopAnchor(addPlayerButton, 550.0);
//
//    numPlayers = new Text(String.valueOf(authoringProxy.getNumPlayers()));
//    AnchorPane.setRightAnchor(numPlayers, 130.0);
//    AnchorPane.setTopAnchor(numPlayers, 565.0);
//
//    containerPane.getChildren()
//        .addAll(removePlayerButton, addPlayerButton, numPlayersLabel, numPlayers);
//  }
//
//  // properties
//  private void handleGameObjectTypeSelection() {
//    gameObjectTypeDropdown.valueProperty().addListener((obs, oldVal, gameObjectType) -> {
//      if (shapeProxy.getShape() != null && gameObjectType != null) {
//        shapeProxy.getGameObjectAttributesContainer().getProperties().add(gameObjectType.toString().toLowerCase());
//        updateSelectionOptions(gameObjectType);
//      }
//      if (gameObjectType == GameObjectType.SURFACE) {
//        removeSelectedShapeFromAllPlayers();
//        setPlayerAssignmentVisibility(false);
//      }
//    });
//  }
//
//  private void handleAddAndRemovePlayers() {
//    addPlayerButton.setOnMouseClicked(e -> {
//      authoringProxy.increaseNumPlayers();
//      playerAssignmentListView.getItems().add("Player " + authoringProxy.getNumPlayers());
//      numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
//    });
//
//    removePlayerButton.setOnMouseClicked(e -> {
//      if (authoringProxy.getNumPlayers() > 1) {
//        playerAssignmentListView.getItems().remove("Player " + authoringProxy.getNumPlayers());
//        authoringProxy.decreaseNumPlayers();
//        numPlayers.setText(String.valueOf(authoringProxy.getNumPlayers()));
//      }
//    });
//  }
//
//  private void updateProxyMapWithTextFieldInput(TextField textField,
//      Consumer<String> inputConsumer) {
//    if (textField.isVisible()) {
//      String inputText = textField.getText();
//      textField.clear();
//      inputConsumer.accept(inputText);
//    }
//  }
//
//  private void handlePlayerAssignment() {
//    handlePlayerListViewOnChange();
//    handleScorableCheckBoxOnChange();
//    handleCollidableTypeDropdownOnChange();
//  }
//  private void handlePlayerListViewOnChange() {
//    playerAssignmentListView.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newPlayerId) -> {
//      authoringProxy.getPlayers().getOrDefault((Integer) newPlayerId, new ArrayList<>()).add(Integer.parseInt(shapeProxy.getShape().getId()));
//    }));
//  }
//  private void handleCollidableTypeDropdownOnChange() {
//    collidableTypeDropDown.valueProperty().addListener((obs, oldVal, collidableType) -> {
//      shapeProxy.getGameObjectAttributesContainer().getProperties().add(collidableType);
//
//      if (collidableType.equals("STRIKEABLE") || collidableType.equals("CONTROLLABLE")) {
//        addToAuthoringPlayers();
//      } else {
//        if (!scoreableCheckBox.isSelected()) {
//          removeFromAuthoringPlayers();
//        }
//      }
//    });
//  }
//  private void handleScorableCheckBoxOnChange() {
//    scoreableCheckBox.selectedProperty().addListener((observable, oldValue, newState) -> {
//      if (newState) {
//        addToAuthoringPlayers();
//      } else {
//        if (collidableTypeDropDown.getValue().equals("NON-CONTROLLABLE")) {
//          removeFromAuthoringPlayers();
//        }
//      }
//    });
//  }
//  private void removeSelectedShapeFromAllPlayers() {
//    Map<Integer, List<Integer>> playersMap = authoringProxy.getPlayers();
//    for (Integer player: playersMap.keySet()) {
//      playersMap.get(player).remove(Integer.parseInt(shapeProxy.getShape().getId()));
//    }
//  }
//  private void addToAuthoringPlayers() {
//    setPlayerAssignmentVisibility(true);
//    int selectedPlayerId = playerAssignmentListView.getSelectionModel().getSelectedIndex();
//    authoringProxy.getPlayers().getOrDefault(selectedPlayerId, new ArrayList<>()).add(Integer.parseInt(shapeProxy.getShape().getId()));
//  }
//  private void removeFromAuthoringPlayers() {
//    int selectedPlayerId = playerAssignmentListView.getSelectionModel().getSelectedIndex();
//    authoringProxy.getPlayers().getOrDefault(selectedPlayerId, new ArrayList<>()).remove(Integer.parseInt(shapeProxy.getShape().getId()));
//    setPlayerAssignmentVisibility(false);
//  }
//
//  private void updateSelectionOptions(GameObjectType gameObjectType) {
//    if (gameObjectType == GameObjectType.COLLIDABLE) {
//      setSurfaceOptionVisibility(false);
//      setCollidableOptionVisibility(true);
//    } else if (gameObjectType == GameObjectType.SURFACE) {
//      setSurfaceOptionVisibility(true);
//      setCollidableOptionVisibility(false);
//    }
//  }
//
//  private void setSurfaceOptionVisibility(boolean visibility) {
//    kFrictionTextField.setVisible(visibility);
//    sFrictionTextField.setVisible(visibility);
//    kFriction.setVisible(visibility);
//    sFriction.setVisible(visibility);
//  }
//
//  private void setCollidableOptionVisibility(boolean visibility) {
//    collidableTypeDropDown.setVisible(visibility);
//    massTextField.setVisible(visibility);
//    mass.setVisible(visibility);
//    elasticityTextField.setVisible(visibility);
//    elasticity.setVisible(visibility);
//    scoreableCheckBox.setVisible(visibility);
//    scoreable.setVisible(visibility);
//  }
//
//  private void setPlayerAssignmentVisibility(boolean visibility) {
//    playerAssignmentListView.setVisible(visibility);
//  }
//}
