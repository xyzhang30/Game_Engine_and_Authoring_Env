package oogasalad.view.authoring_environment;

import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.GameWindow;
import oogasalad.view.authoring_environment.data.GameObjectAttributesContainer;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.panels.ColorPanel;
import oogasalad.view.authoring_environment.panels.ImagePanel;
import oogasalad.view.authoring_environment.panels.InteractionPanel;
import oogasalad.view.authoring_environment.panels.Panel;
import oogasalad.view.authoring_environment.panels.PolicyPanel;
import oogasalad.view.authoring_environment.panels.ShapePanel;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.enums.AuthoringScreenType;

/**
 * Represents the authoring screen for the authoring environment in the application, providing the user interface for creating and managing various game elements.
 *
 * @author Judy He
 */
public class AuthoringScreen {

  private final AnchorPane rootPane = new AnchorPane();
  private final AnchorPane containerPane = new AnchorPane(); // PASSED TO PANELS
  private final StackPane canvasPane = new StackPane();
  private final ShapeProxy shapeProxy = new ShapeProxy();
  private final AuthoringProxy authoringProxy = new AuthoringProxy();
  private final Scene scene;
  private final Container container = new Container();
  ComboBox<AuthoringScreenType> screensDropDown = new ComboBox<>();
  Text titleText = new Text();
  private static final String RESOURCE_FOLDER_PATH = "view.";
  private static final String UI_FILE_PREFIX = "UIElements";
  private final String language = "English"; // PASS IN LANGUAGE
  private final ResourceBundle resourceBundle;

  public AuthoringScreen() {
    this.resourceBundle = ResourceBundle.getBundle(
        RESOURCE_FOLDER_PATH + UI_FILE_PREFIX + language);
    createCanvas();
    createContainerPane();
    createScreenSelectionDropDown(List.of(AuthoringScreenType.GAMEOBJECTS, AuthoringScreenType.INTERACTIONS,
        AuthoringScreenType.POLICIES));
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

  public Scene getScene() {
    return this.scene;
  }

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

    }
  }

  private List<Panel> createGameObjectsPanel() {
    return List.of(
        new ColorPanel(shapeProxy, containerPane),
        new ImagePanel(authoringProxy, shapeProxy, containerPane),
        new ShapePanel(authoringProxy, shapeProxy, rootPane, containerPane, canvasPane)
    );
  }

  private List<Panel> createInteractionsPanel() {
    return List.of(new InteractionPanel(authoringProxy, shapeProxy, rootPane, containerPane, canvasPane));
  }

  private List<Panel> createPoliciesPanel() {
    return List.of(new PolicyPanel(authoringProxy, shapeProxy, rootPane, containerPane, canvasPane));
  }

  private void setTitle(String title) {
    titleText.setText(title);
    titleText.setId("titleText");
    titleText.setFont(Font.font("Arial", 30));
    AnchorPane.setTopAnchor(titleText, 5.0);
    AnchorPane.setLeftAnchor(titleText, 400.0);
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
    int canvasWidth = 900;
    int canvasHeight = 900;
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
      if (screensDropDown.getSelectionModel().getSelectedItem().equals(AuthoringScreenType.GAMEOBJECTS)) {
        shapeProxy.setFinalShapeDisplay();
        try {
          GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer().clone();
          authoringProxy.setGameObject(shapeProxy.getShape(), copy);
        } catch (CloneNotSupportedException e) {
          throw new RuntimeException(e);
        }
      }
      authoringProxy.completeAuthoring();
    });
    AnchorPane.setBottomAnchor(finishButton, 50.0);
    AnchorPane.setRightAnchor(finishButton, 50.0);
    rootPane.getChildren().add(finishButton);
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
            GameObjectAttributesContainer copy = (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer().clone();
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

  public AuthoringProxy getAuthoringProxy() {
    return authoringProxy;
  }

}
