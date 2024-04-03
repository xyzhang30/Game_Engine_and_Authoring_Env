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

public class GameScreen extends UIScreen {
  private final int maxPower = 650;

  private final BorderPane root;

  public GameScreen(Controller controller) {
    root = new BorderPane();
    this.controller = controller;
    // Hard coded case
    CompositeElement cm = new CompositeElement();
    cm.update(List.of(new CollidableRecord(1, 10, 100, 100, 0, 0, true)));
    setupFieldComponents(cm); // BIG improvised here. There's a lot of refactoring to do first...

    createScene();
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  private void createScene() {
    //setupControlPane(); This messes up the power bar key listening
    Rectangle powerIndicator = setupPowerBar();
    scene = new Scene(root, sceneWidth, sceneHeight);
    initiateListening(scene, powerIndicator);
  }

  private void setupControlPane() {
    ControlPane controlPane = new ControlPane();
    controlPane.setLayoutX(10); // top of screen?
    controlPane.setLayoutY(10); // top of screen?

    root.setTop(controlPane);
  }

  @Deprecated
  private void setupFieldComponents(CompositeElement cm) {
    StackPane sp = new StackPane();
    for (int i : cm.idList()) {
      sp.getChildren().add(cm.getNode(i));
    }
    root.getChildren().add(sp);
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
      handleKeyInput(event.getCode(), powerIndicator);
    });
  }

  private void handleKeyInput(KeyCode code, Rectangle powerIndicator) {
    switch (code) {
      case UP: {
        if (powerIndicator.getHeight() < maxPower) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() - 10);
          powerIndicator.setHeight(powerIndicator.getHeight() + 10);
        }
      }
      case DOWN: {
        if (powerIndicator.getHeight() > 10) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() + 10);
          powerIndicator.setHeight(powerIndicator.getHeight() - 10);
        }
      }
      case ENTER: {
        double fractionalVelocity = powerIndicator.getHeight() / maxPower;
        controller.hitPointScoringObject(fractionalVelocity);
      }
    }
  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }

}
