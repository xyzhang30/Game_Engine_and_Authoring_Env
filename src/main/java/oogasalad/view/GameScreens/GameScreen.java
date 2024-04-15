package oogasalad.view.GameScreens;

import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Controlling.GameController;
import oogasalad.view.GameScreens.GameplayPanel.GamePanel;
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
  private final GamePanel gamePanel;
  private boolean ableToHit;
  private Arrow angleArrow;
  private Rectangle powerIndicator;

  private Text scoreboardTxt;
  private Text turnBoardTxt;

  public GameScreen(GameController controller, CompositeElement compositeElement) {
    root = new BorderPane();
    this.controller = controller;
    ableToHit = true;

    gamePanel = new GamePanel(compositeElement);
    root.setCenter(gamePanel.getPane());

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
    setupScoreBoard();
    setupTurnBoard();
  }


  private void setupControlPane() {
    root.setTop(new ControlPane());
  }

  private void setupScoreBoard() {
    Rectangle rectangle = new Rectangle(10, 50, 100, 50);
    rectangle.setFill(Color.LIMEGREEN);
    scoreboardTxt = new Text("SCOREBOARD");
    scoreboardTxt.setX(25);
    scoreboardTxt.setY(65);
    scoreboardTxt.setFill(Color.BLACK);
    root.getChildren().addAll(rectangle, scoreboardTxt);
  }
  private void setupTurnBoard() {
    Rectangle rectangle = new Rectangle(110, 50, 100, 50);
    rectangle.setFill(Color.LIMEGREEN);
    turnBoardTxt = new Text("");
    turnBoardTxt.setX(125);
    turnBoardTxt.setY(65);
    turnBoardTxt.setFill(Color.BLACK);
    root.getChildren().addAll(rectangle, turnBoardTxt);
  }
  public void updateTurnBoard(int turn, int round) {
    Rectangle rectangle = new Rectangle(110, 50, 100, 50);
    rectangle.setFill(Color.LIMEGREEN);
    turnBoardTxt = new Text("Round: " + round + "\n" + "Turn : " + turn);
    turnBoardTxt.setX(125);
    turnBoardTxt.setY(65);
    turnBoardTxt.setFill(Color.BLACK);
    root.getChildren().addAll(rectangle, turnBoardTxt);
  }

  public void updateScoreBoard(Map<Integer, Double> score) {
    StringBuilder scoreboardText = new StringBuilder("Score:\n");
    for (Map.Entry<Integer, Double> entry : score.entrySet()) {
      scoreboardText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    scoreboardTxt.setText(scoreboardText.toString());
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
      // Some silly scaling dev keys
      case Q: {
        gamePanel.zoomOut();
        break;
      }
      case E: {
        gamePanel.zoomIn();
        break;
      }
      case W: {
        gamePanel.setCamera(-100,-100,0,0);
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
