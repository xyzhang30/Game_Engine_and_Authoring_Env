package oogasalad.view.Screen;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class GameScreen extends UIScreen {

  public GameScreen() {
    root = new Group();
    createScene();
  }

  private void createScene() {
    setupControlPane();
    setupPowerBar();
    scene = new Scene(root, sceneWidth, sceneHeight);
  }

  private void setupControlPane() {
    ControlPane controlPane = new ControlPane();
    controlPane.setLayoutX(10); // top of screen?
    controlPane.setLayoutY(10); // top of screen?

    root.getChildren().add(controlPane);
  }

  private void setupPowerBar() {
    Rectangle outline = new Rectangle(sceneWidth - 200, 100, 100, 700);
    outline.setFill(Color.DARKGRAY);
    outline.setEffect(createDropShadow());

    Rectangle powerIndicator = new Rectangle(sceneWidth - 190, 780, 80, 10);
    powerIndicator.setFill(Color.DARKRED);
    powerIndicator.toFront();

    initiatePowerListening(powerIndicator);

    root.getChildren().addAll(outline, powerIndicator);
  }

  private void initiatePowerListening(Rectangle powerIndicator) {
    powerIndicator.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.UP) {
        if (powerIndicator.getHeight() < 650) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() - 10);
          powerIndicator.setHeight(powerIndicator.getHeight() + 10);
        }
      } else if (event.getCode() == KeyCode.DOWN) {
        if (powerIndicator.getHeight() > 10) {
          powerIndicator.setLayoutY(powerIndicator.getLayoutY() + 10);
          powerIndicator.setHeight(powerIndicator.getHeight() - 10);
        }
      }
    });
  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }

}
