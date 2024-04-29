package oogasalad.view.authoring_environment;

import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oogasalad.model.api.exception.AuthoringException;
import oogasalad.view.Warning;
import oogasalad.view.api.authoring.AuthoringFactory;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.api.enums.AuthoringScreenType;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.authoring_environment.factories.DefaultUIElementFactory;
import oogasalad.view.authoring_environment.panels.ColorPanel;
import oogasalad.view.authoring_environment.panels.ImagePanel;
import oogasalad.view.authoring_environment.panels.InteractionPanel;
import oogasalad.view.authoring_environment.panels.KeySelectionPanel;
import oogasalad.view.authoring_environment.panels.ModPanel;
import oogasalad.view.authoring_environment.panels.PolicyPanel;
import oogasalad.view.authoring_environment.panels.ShapePanel;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.authoring_environment.util.Container;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;
import oogasalad.view.scene_management.GameWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the authoring screen for the authoring environment in the application, providing the
 * user interface for creating and managing various game elements.
 *
 * @author Judy He
 */
public class AuthoringScreen {

  private static final String RESOURCE_FOLDER_PATH = "view.";
  private static final String VIEW_PROPERTIES_FOLDER = "properties.";
  private static final String UI_FILE_PREFIX = "UIElements";
  private static final Warning WARNING = new Warning();
  private static final Logger LOGGER = LogManager.getLogger(AuthoringScreen.class);
  private final AnchorPane rootPane = new AnchorPane();
  private final AnchorPane containerPane = new AnchorPane(); // PASSED TO PANELS
  private final StackPane canvasPane = new StackPane();
  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final Scene scene;
  private final Container container = new Container();
  private final ResourceBundle resourceBundle;
  private final AuthoringFactory authoringFactory;
  ComboBox<AuthoringScreenType> screensDropDown = new ComboBox<>();
  Text titleText = new Text();
  private SupportedLanguage language; // PASS IN LANGUAGE
  private TextField gameNameTextField;
  private TextArea gameDescriptionTextField;
  private Stage gameNameStage;
  private Button submitGameNameButton;
  private ComboBox<String> permissionSelection;

  /**
   * Constructs an AuthoringScreen instance.
   *
   * @param language         The language to use for resource bundle localization.
   * @param authoringFactory The factory used to create authoring components.
   * @param shapeProxy       The ShapeProxy instance associated with the game objects.
   * @param authoringProxy   The AuthoringProxy instance for managing authoring operations.
   */
  public AuthoringScreen(SupportedLanguage language, AuthoringFactory authoringFactory,
      ShapeProxy shapeProxy, AuthoringProxy authoringProxy) {
    this.language = language;
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + VIEW_PROPERTIES_FOLDER + UI_FILE_PREFIX + language);
    this.authoringFactory = authoringFactory;
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    createCanvas();
    createContainerPane();
    createScreenSelectionDropDown(
        List.of(AuthoringScreenType.GAMEOBJECTS, AuthoringScreenType.INTERACTIONS,
            AuthoringScreenType.POLICIES, AuthoringScreenType.KEY_PREFERENCES));
    handleScreenSelectionDropDown();
    createFinishButton();
    containerPane.getChildren().add(titleText);
    scene = new Scene(rootPane, GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
    setScene(AuthoringScreenType.GAMEOBJECTS);
    authoringProxy.setCurrentScreenTitle(AuthoringScreenType.GAMEOBJECTS.toString());
  }

  private void resetScene() {
    containerPane.getChildren().clear();
    containerPane.getChildren().add(titleText);
  }

  /**
   * Returns the scene associated with the authoring screen.
   *
   * @return The scene associated with the authoring screen.
   */
  public Scene getScene() {
    return this.scene;
  }

  /**
   * Sets the scene according to the specified authoring screen type.
   *
   * @param authoringScreenType The type of authoring screen to set.
   */
  private void setScene(AuthoringScreenType authoringScreenType) {
    setTitle(authoringScreenType.toString());
    switch (authoringScreenType) {
      case GAMEOBJECTS:
        container.setPanels(createGameObjectsPanel());
        break;
      case INTERACTIONS:
        container.setPanels(createInteractionsPanel());
        break;
      case POLICIES:
        container.setPanels(createPoliciesPanel());
        break;
      case KEY_PREFERENCES:
        container.setPanels(createKeySelectionPanel());
        break;
    }
  }

  private List<Panel> createGameObjectsPanel() {
    return List.of(
        new ColorPanel(shapeProxy, containerPane),
        new ImagePanel(authoringProxy, shapeProxy, containerPane),
        new ShapePanel(authoringFactory, shapeProxy, authoringProxy, canvasPane, rootPane,
            containerPane, new DefaultUIElementFactory()),
        new ModPanel(authoringProxy, shapeProxy, containerPane)
    );
  }

  private List<Panel> createInteractionsPanel() {
    return List.of(new InteractionPanel(authoringProxy, shapeProxy, containerPane,
        new DefaultUIElementFactory()));
  }

  private List<Panel> createPoliciesPanel() {
    return List.of(new PolicyPanel(authoringProxy, rootPane, containerPane, canvasPane,
        new DefaultUIElementFactory()));
  }

  private List<Panel> createKeySelectionPanel() {
    return List.of(new KeySelectionPanel(authoringProxy, rootPane, containerPane, canvasPane,
        new DefaultUIElementFactory()));
  }

  private void setTitle(String title) {
    titleText.setText(title);
    titleText.setId("titleText");
    titleText.setFont(Font.font("Arial", 30));
    AnchorPane.setTopAnchor(titleText, 5.0);
    AnchorPane.setLeftAnchor(titleText, 400.0);
  }

  /**
   * Sets the language for the resource bundle.
   *
   * @param language The language to set.
   */
  public void setLanguage(SupportedLanguage language) {
    this.language = language;
  }

  private void createContainerPane() {
    int width = 500;
    int height = 980;
    containerPane.setMaxSize(width, height);
    containerPane.setId("rootPane");
    AnchorPane.setTopAnchor(containerPane, 30.0);
    AnchorPane.setRightAnchor(containerPane, 30.0);
    rootPane.getChildren().add(containerPane);
  }

  private void createCanvas() {
    double canvasWidth = 0.5 * GameWindow.SCREEN_WIDTH;
    double canvasHeight = 0.85 * GameWindow.SCREEN_HEIGHT;
    canvasPane.setMaxSize(canvasWidth, canvasHeight);
    canvasPane.setId("canvasPane");

    AnchorPane.setTopAnchor(canvasPane, 50.0);
    AnchorPane.setLeftAnchor(canvasPane, 70.0);

    Rectangle background = new Rectangle(canvasWidth, canvasHeight);

    background.setStroke(Color.BLACK);
    background.setFill(Color.WHITE);
    background.setStrokeWidth(10);
    StackPane.setAlignment(background, Pos.TOP_LEFT);

    rootPane.getChildren().add(canvasPane);

    canvasPane.getChildren().add(background);
  }

  private void createFinishButton() {
    Button finishButton = new Button(resourceBundle.getString("finishButton"));
    finishButton.setId("finishButton");
    finishButton.setPrefSize(100, 50);
    finishButton.setOnMouseClicked(event -> {
      if (screensDropDown.getSelectionModel().getSelectedItem()
          .equals(AuthoringScreenType.GAMEOBJECTS)) {
        shapeProxy.setFinalShapeDisplay();
        try {
          GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer()
              .clone();
          authoringProxy.setGameObject(shapeProxy.getShape(), copy);
        } catch (CloneNotSupportedException e) {
          throw new RuntimeException(e);
        }
      }
      endAuthoring();
//      authoringProxy.completeAuthoring();
    });
    AnchorPane.setBottomAnchor(finishButton, 50.0);
    AnchorPane.setRightAnchor(finishButton, 50.0);
    rootPane.getChildren().add(finishButton);
  }

  private void endAuthoring() {
    showEnterGameNamePopup();
  }

  private void showEnterGameNamePopup() {
    if (gameNameStage == null) {
      gameNameStage = new Stage();
      gameNameStage.initModality(Modality.APPLICATION_MODAL);
      gameNameStage.setTitle("Enter Game Name and Description");

      VBox vbox = new VBox();
      Label enterName = new Label("Game Name: ");
      gameNameTextField = new TextField();
      gameNameTextField.setPromptText("Enter game name...");
      Label enterDescription = new Label("Game Description: ");
      gameDescriptionTextField = new TextArea();
      gameDescriptionTextField.setPromptText("Enter game description");
      gameDescriptionTextField.setPrefHeight(200);
      gameDescriptionTextField.setWrapText(true);
      Label permission = new Label("Game Permission: ");
      permissionSelection = new ComboBox<>();
      permissionSelection.getItems().addAll("public", "private", "friends");
      permissionSelection.setPromptText("Specify game permission");

      vbox.getChildren()
          .addAll(enterName, gameNameTextField, enterDescription, gameDescriptionTextField,
              permission, permissionSelection, makeSubmitGameNameButton());

      Scene scene = new Scene(vbox, 500, 500);
      gameNameStage.setScene(scene);
    }
    gameNameStage.showAndWait();
  }

  private Button makeSubmitGameNameButton() {
    if (submitGameNameButton == null) {
      submitGameNameButton = new Button("Submit");
      submitGameNameButton.setDisable(true);

      gameNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
        updateSubmitButtonState(newValue.trim());
      });

      permissionSelection.valueProperty().addListener((observable, oldValue, newValue) -> {
        updateSubmitButtonState(gameNameTextField.getText().trim());
      });

      submitGameNameButton.setOnAction(e -> {
        gameNameStage.close();
        authoringProxy.setGameName(gameNameTextField.getText());
        try {
          authoringProxy.saveGameDescription(gameDescriptionTextField.getText());
        } catch (AuthoringException error) {
          WARNING.showAlert(scene, AlertType.ERROR, "Error Saving Game Description", null,
              error.getMessage());
        }
        authoringProxy.setGamePermission(permissionSelection.getValue());
        authoringProxy.completeAuthoring(scene);
      });
    }

    return submitGameNameButton;
  }

  private void updateSubmitButtonState(String gameName) {
    submitGameNameButton.setDisable(gameName.isEmpty() || permissionSelection.getValue() == null);
  }

  private void createScreenSelectionDropDown(List<AuthoringScreenType> screenOptions) {
    screensDropDown.getItems().addAll(screenOptions);
    screensDropDown.getSelectionModel().select(AuthoringScreenType.GAMEOBJECTS);
    screensDropDown.setPromptText(resourceBundle.getString("authoringScreenDropdownPrompt"));
    AnchorPane.setTopAnchor(screensDropDown, 10.0);
    AnchorPane.setLeftAnchor(screensDropDown, 10.0);
    screensDropDown.setPrefSize(300, 30);
    rootPane.getChildren().add(screensDropDown);
  }

  private void handleScreenSelectionDropDown() {
    screensDropDown.valueProperty().addListener((obs, oldVal, selectedScreen) -> {
      if (selectedScreen != null) {
        if (oldVal.equals(AuthoringScreenType.GAMEOBJECTS)) {
          shapeProxy.setFinalShapeDisplay();
          try {
            GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer()
                .clone();
            authoringProxy.setGameObject(shapeProxy.getShape(), copy);
            shapeProxy.resetGameObjectAttributesContainer();
          } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
          }
        }
        resetScene();
        setScene(selectedScreen);
        authoringProxy.setCurrentScreenTitle(selectedScreen.toString());
        authoringProxy.updateScreen();
      }
    });
  }

  /**
   * Gets the authoring proxy
   *
   * @return AuthoringProxy used by the current authoring env
   */
  public AuthoringProxy getAuthoringProxy() {
    return authoringProxy;
  }

}
