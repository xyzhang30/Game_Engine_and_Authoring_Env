package oogasalad.view.authoring_environment;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.Window;
import oogasalad.view.authoring_environment.authoring_screens.NonControllableType;
import oogasalad.view.authoring_environment.panels.AuthoringProxy;
import oogasalad.view.authoring_environment.panels.ShapeProxy;

public class AuthoringScreen {

  // initialize canvas, stagnant elements
  // initialize different panel depending on current screen
  // different endSelection strategy depending on current screen

  // FIND OUT HOW IT WORKS:
  // endSelection() -> controller.startNextSelection(ImageType.BACKGROUND, canvas, posMap, nonControllableMap, new ArrayList<>(), imageMap);
  //

  private final AnchorPane rootPane = new AnchorPane();
  private final StackPane canvas = new StackPane();
  private final ShapeProxy shapeProxy = new ShapeProxy();
  private final AuthoringProxy authoringProxy = new AuthoringProxy();
  private final Scene scene;

  public AuthoringScreen() {
    scene = new Scene(rootPane, Window.SCREEN_WIDTH, Window.SCREEN_HEIGHT);
  }

  private void createTitle(String title) {
    Text titleText = new Text(title);
    titleText.setId("titleText");
    // TODO: REMOVE HARD-CODING
    titleText.setFont(Font.font("Arial", 30));
    AnchorPane.setTopAnchor(titleText, 5.0);
    AnchorPane.setLeftAnchor(titleText, 50.0);
    rootPane.getChildren().add(titleText);
  }

  private void createAuthoringBox() {
    // TODO: REMOVE HARD-CODING
    int canvasWidth = 980;
    int canvasHeight = 980;
    canvas.setMaxSize(canvasWidth, canvasHeight);
    canvas.setId("canvas");
    AnchorPane.setTopAnchor(canvas, 50.0);
    AnchorPane.setLeftAnchor(canvas, 50.0);

    Rectangle background = new Rectangle(canvasWidth, canvasHeight);
    background.setId("background");
    background.setStroke(Color.BLACK);
    background.setFill(Color.WHITE);
    background.setStrokeWidth(10);
    StackPane.setAlignment(background, Pos.TOP_LEFT);

    shapeProxy.setShape(background);
    authoringProxy.addNonControllableShape(background, NonControllableType.SURFACE);
//    authoringProxy.getAuthoringController().setBackground(background);

    canvas.getChildren().add(background);
    rootPane.getChildren().add(canvas);
  }

  private void createFinishButton() {
    // TODO: REMOVE HARD-CODING
    Button nextButton = new Button("Finish");
    nextButton.setId("finishButton");
    nextButton.setPrefSize(100, 50);
    nextButton.setOnMouseClicked(event -> authoringProxy.completeAuthoring());
    AnchorPane.setBottomAnchor(nextButton, 50.0);
    AnchorPane.setRightAnchor(nextButton, 50.0);
    rootPane.getChildren().add(nextButton);
  }


}
