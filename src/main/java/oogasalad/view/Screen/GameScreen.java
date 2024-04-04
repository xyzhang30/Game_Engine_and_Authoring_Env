package oogasalad.view.Screen;

import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.model.api.CollidableRecord;
import oogasalad.view.Controller;
import oogasalad.view.VisualElements.CompositeElement;

/**
 * Manages the game's graphical interface, including user inputs for controlling hit strength and game actions.
 * Interacts with the game controller to reflect changes in game state and player actions.
 * 
 * @ author Jordan Haytaian, Doga Ozmen
 */

public class GameScreen extends UIScreen {
  private final int maxPower = 650;
  private boolean ableToHit;
  private Scene scene;
  private final BorderPane root;

  public GameScreen(Controller controller, CompositeElement compositeElement) {
    root = new BorderPane();
    this.controller = controller;
    ableToHit = true;
    setupFieldComponents(compositeElement); // BIG improvised here. There's a lot of refactoring to do first...

    createScene();
  }

  /**
   * Enable the ability to hit after objects have stopped moving from previous hit
   */
  public void enableHitting(){
    ableToHit = true;
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Deprecated
  public Scene getScene(){
    return scene;
  }
  private void createScene() {
    setupControlPane(); //This messes up the power bar key listening
    Rectangle powerIndicator = setupPowerBar();
    scene = new Scene(root, sceneWidth, sceneHeight);
    initiateListening(scene, powerIndicator);

  }


  private void setupControlPane() {
    root.setTop(new ControlPane());
  }

  @Deprecated
  private void setupFieldComponents(CompositeElement cm) {
    for (int i : cm.idList()) {
      root.getChildren().add(cm.getNode(i));
    }
  }

  private Rectangle setupPowerBar() {
    Rectangle outline = new Rectangle(sceneWidth - 200, 100, 100, 700);
    outline.setFill(Color.DARKGRAY);
    outline.setEffect(createDropShadow());

    Rectangle powerIndicator = new Rectangle(sceneWidth - 190, 780, 80, 10);
    powerIndicator.setFill(Color.DARKRED);
    powerIndicator.toFront();

    root.getChildren().addAll(outline, powerIndicator);
    return powerIndicator;
  }

  private void initiateListening(Scene scene, Rectangle powerIndicator) {
    scene.setOnKeyPressed(event -> {
      if(ableToHit){
        handleKeyInput(event.getCode(), powerIndicator);
      }
    });
  }

  private void handleKeyInput(KeyCode code, Rectangle powerIndicator) {
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
      case LEFT:{} //modify angle
      case RIGHT: {} //modify angle
      case ENTER: {
        ableToHit = false;
        double fractionalVelocity = powerIndicator.getHeight() / maxPower;
        controller.hitPointScoringObject(fractionalVelocity);
        break;
      }
    }
  }

  public void endRound(Boolean round){
    if (round){
      controller.openTransitionScreen();
    }

  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }

}
