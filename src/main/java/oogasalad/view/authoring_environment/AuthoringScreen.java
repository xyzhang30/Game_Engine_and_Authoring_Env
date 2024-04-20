package oogasalad.view.authoring_environment;

import java.util.List;
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
import oogasalad.view.Window;
import oogasalad.view.authoring_environment.panels.AuthoringProxy;
import oogasalad.view.authoring_environment.panels.ColorPanel;
import oogasalad.view.authoring_environment.panels.ImagePanel;
import oogasalad.view.authoring_environment.panels.InteractionPanel;
import oogasalad.view.authoring_environment.panels.PolicyPanel;
import oogasalad.view.authoring_environment.panels.ShapePanel;
import oogasalad.view.authoring_environment.panels.ShapeProxy;

public class AuthoringScreen {

  private final AnchorPane rootPane = new AnchorPane();
  //  private final StackPane rootPane = new StackPane(); // PASSED TO PANELS
  private final AnchorPane containerPane = new AnchorPane(); // PASSED TO PANELS
  private final StackPane canvasPane = new StackPane();
  private final ShapeProxy shapeProxy = new ShapeProxy();
  private final AuthoringProxy authoringProxy = new AuthoringProxy();
  private final Scene scene;
  private final Container container = new Container();
  ComboBox<String> screensDropDown = new ComboBox<>();
  Text titleText = new Text();

  public AuthoringScreen() {
    createCanvas();
//    createShapesPane();
    createContainerPane();
    createScreenSelectionDropDown(List.of("Background", "Game Objects", "Interactions",
        "Policies"));
    handleScreenSelectionDropDown();
    createFinishButton();
    containerPane.getChildren().add(titleText);
    scene = new Scene(rootPane, Window.SCREEN_WIDTH, Window.SCREEN_HEIGHT);
    setScene("Background");
  }

  private void resetScene() {
    containerPane.getChildren().clear();
    containerPane.getChildren().add(titleText);
  }

  public Scene getScene() {
    return this.scene;
  }

  private void setScene(String screenTitle) {
    setTitle(screenTitle);
    // TODO: BAD DESIGN -> WHERE and HOW to set the Container?
    switch (screenTitle) {
      case "Background" -> container.setPanels(List.of(new ColorPanel(shapeProxy, containerPane),
          new ImagePanel(authoringProxy, shapeProxy,
              containerPane)));
      case "Game Objects" -> container.setPanels(
          List.of(new ColorPanel(shapeProxy, containerPane),
              new ImagePanel(authoringProxy, shapeProxy,
                  containerPane),
              new ShapePanel(authoringProxy, shapeProxy, rootPane,
                  containerPane, canvasPane)));
      case "Interactions" ->
          container.setPanels(List.of(new InteractionPanel(authoringProxy, shapeProxy, rootPane,
              containerPane, canvasPane)));
      case "Policies" -> container.setPanels(List.of(new PolicyPanel(authoringProxy, shapeProxy, rootPane, containerPane, canvasPane)));
    }

  }

  private void setTitle(String title) {
    titleText.setText(title);
    titleText.setId("titleText");
    // TODO: REMOVE HARD-CODING
    titleText.setFont(Font.font("Arial", 30));
    AnchorPane.setTopAnchor(titleText, 5.0);
    AnchorPane.setLeftAnchor(titleText, 400.0);
  }

//  private void createShapesPane() {
//    // TODO: REMOVE HARD-CODING
//    int width = 980;
//    int height = 980;
//    rootPane.setMaxSize(width, height);
//    rootPane.setId("rootPane");
//    AnchorPane.setTopAnchor(rootPane, 50.0);
//    AnchorPane.setLeftAnchor(rootPane, 50.0);
//    rootPane.getChildren().add(rootPane);
//  }

  private void createContainerPane() {
    // TODO: REMOVE HARD-CODING
    int width = 500;
    int height = 980;
    containerPane.setMaxSize(width, height);
    containerPane.setId("rootPane");
    AnchorPane.setTopAnchor(containerPane, 30.0);
    AnchorPane.setRightAnchor(containerPane, 30.0);
    rootPane.getChildren().add(containerPane);
  }

  private void createCanvas() {
    // TODO: REMOVE HARD-CODING
    int canvasWidth = 900;
    int canvasHeight = 900;
    canvasPane.setMaxSize(canvasWidth, canvasHeight);
    canvasPane.setId("canvasPane");
    AnchorPane.setTopAnchor(canvasPane, 50.0);
    AnchorPane.setLeftAnchor(canvasPane, 70.0);

    Rectangle background = new Rectangle(canvasWidth, canvasHeight);
    background.setId(String.valueOf(shapeProxy.getShapeCount()));
    shapeProxy.setShapeCount(shapeProxy.getShapeCount()+1);
    background.setStroke(Color.BLACK);
    background.setFill(Color.WHITE);
    background.setStrokeWidth(10);
    StackPane.setAlignment(background, Pos.TOP_LEFT);

    shapeProxy.setShape(background);
    //authoringProxy.addNonControllableShape(background, GameObjectType.SURFACE);
//    authoringProxy.getAuthoringController().setBackground(background);
    rootPane.getChildren().add(canvasPane);
    canvasPane.getChildren().add(background);
  }

  private void createFinishButton() {
    // TODO: REMOVE HARD-CODING
    Button finishButton = new Button("Finish");
    finishButton.setId("finishButton");
    finishButton.setPrefSize(100, 50);
    finishButton.setOnMouseClicked(event -> {
      authoringProxy.setGameObject(shapeProxy.getShape(), shapeProxy.getGameObjectAttributesContainer());
      authoringProxy.completeAuthoring();
    });
    AnchorPane.setBottomAnchor(finishButton, 50.0);
    AnchorPane.setRightAnchor(finishButton, 50.0);
    rootPane.getChildren().add(finishButton);
  }

  private void createScreenSelectionDropDown(List<String> screenOptions) {
    screensDropDown.getItems().addAll(screenOptions);
    screensDropDown.setPromptText("Select Screen Type");
    AnchorPane.setTopAnchor(screensDropDown, 10.0);
    AnchorPane.setLeftAnchor(screensDropDown, 10.0);
    screensDropDown.setPrefSize(300, 30);
    rootPane.getChildren().add(screensDropDown);
  }

  private void handleScreenSelectionDropDown() {
    screensDropDown.valueProperty().addListener((obs, oldVal, selectedScreen) -> {
      if (selectedScreen != null) {
        authoringProxy.setGameObject(shapeProxy.getShape(), shapeProxy.getGameObjectAttributesContainer());
        resetScene();
        shapeProxy.setShape(null);
        setScene(selectedScreen);
        authoringProxy.setCurrentScreenTitle(selectedScreen);
        authoringProxy.updateScreen();
      }
    });
  }

  public AuthoringProxy getAuthoringProxy() {
    return authoringProxy;
  }

}
