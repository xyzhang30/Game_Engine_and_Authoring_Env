package oogasalad.view.GameScreens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Controlling.GameController;
import oogasalad.view.VisualElements.CompositeElement;
import oogasalad.view.VisualElements.InputIndicators.Arrow;

/**
 * Manages the game's graphical interface, including user inputs for controlling hit strength and
 * game actions. Interacts with the game controller to reflect changes in game state and player
 * actions.
 *
 * @ author Jordan Haytaian, Doga Ozmen
 */

public class GameScreen extends UIScreen {

  private final double maxPower = SCREEN_HEIGHT*0.8;
  private final BorderPane root;
  private boolean ableToHit;
  private Arrow angleArrow;
  private Rectangle powerIndicator;

  private Text scoreboardTxt;

  public GameScreen(GameController controller, CompositeElement compositeElement) {
    root = new BorderPane();
    this.controller = controller;
    ableToHit = true;
    setupFieldComponents(
        compositeElement); // BIG improvised here. There's a lot of refactoring to do first...
    setupAngleIndicator();

    createScene();
  }

  private void setupAngleIndicator() {
    // Assume arrow starts at the middle bottom of the scene and points upwards initially
    angleArrow = new Arrow(
        SCREEN_WIDTH*0.85, SCREEN_HEIGHT*0.8, SCREEN_WIDTH*0.85,
        SCREEN_HEIGHT*0.7);

    root.getChildren().add(angleArrow.getLine()); // Add the arrow line to the root pane
  }

  /**
   * Enable the ability to hit after objects have stopped moving from previous hit
   */
  public void enableHitting() {
    ableToHit = true;
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  private void createScene() {
    setupControlPane(); //This messes up the power bar key listening
    powerIndicator = setupPowerBar();
    setupScoreBoard(0);
  }


  private void setupControlPane() {
    root.setTop(new ControlPane());
  }

  private void setupScoreBoard(int score) {
    Rectangle rectangle = new Rectangle(10, 50, 100, 50);
    rectangle.setFill(Color.LIMEGREEN);
    scoreboardTxt = new Text("Score: Coming Soon");
    scoreboardTxt.setX(50);
    scoreboardTxt.setY(100);
    scoreboardTxt.setFill(Color.BLACK);
    root.getChildren().addAll(rectangle, scoreboardTxt);
  }

  public void updateScoreBoard(double score) {
    scoreboardTxt.setText("Score: " + score);
  }


  private void setupFieldComponents(CompositeElement cm) {
    AnchorPane globalView = new AnchorPane();
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
    root.setCenter(globalView);
    globalView.setScaleX(0.5);
    globalView.setScaleY(0.5);
    globalView.setTranslateX(-globalView.getBoundsInParent().getWidth()/2);
    globalView.setTranslateY(-globalView.getBoundsInParent().getHeight()/2);
  }

  private Rectangle setupPowerBar() {
    Rectangle outline = new Rectangle(SCREEN_WIDTH*0.9, SCREEN_HEIGHT*0.1,
        SCREEN_WIDTH*0.07, SCREEN_HEIGHT*0.8);
    outline.setFill(Color.DARKGRAY);
    outline.setEffect(createDropShadow());

    Rectangle powerIndicator = new Rectangle(SCREEN_WIDTH*0.91, SCREEN_HEIGHT*0.89,
        SCREEN_WIDTH*0.05, 10);
    powerIndicator.setFill(Color.DARKRED);
    powerIndicator.toFront();

    root.getChildren().addAll(outline, powerIndicator);
    return powerIndicator;
  }

  public void initiateListening(Scene scene) {
    scene.setOnKeyPressed(event -> {
      if (ableToHit) {
        handleKeyInput(event.getCode());
      }
    });
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case UP: {
        if (powerIndicator.getHeight() < maxPower) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() - 10);
          powerIndicator.setHeight(powerIndicator.getHeight() + 10);
        }
        break;
      }
      case DOWN: {
        if (powerIndicator.getHeight() > 10) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() + 10);
          powerIndicator.setHeight(powerIndicator.getHeight() - 10);
        }
        break;
      }
      case LEFT: {
        if (angleArrow.getAngle() > -180) {
          angleArrow.decreaseAngle();
        }
        break;
      }
      case RIGHT: {
        if (angleArrow.getAngle() < 180) {
          angleArrow.increaseAngle();
        }
        break;
      }
      case ENTER: {
        ableToHit = false;
        double angle = Math.toRadians(angleArrow.getAngle() - 90);
        double fractionalVelocity = powerIndicator.getHeight() / maxPower;
        controller.hitPointScoringObject(fractionalVelocity, angle);
        break;
      }
    }
  }

  public void endRound(Boolean round) {
    if (round) {
      controller.openTransitionScreen();
    }

  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }

}
