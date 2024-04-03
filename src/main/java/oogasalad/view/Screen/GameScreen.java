package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Node;
import oogasalad.view.VisualElements.CompositeElement;

public class GameScreen extends UIScreen{

  public GameScreen(CompositeElement compositeElement) {
    super();
    root = new Group();
    setupControlPane();
  }

  private void setupControlPane() {
    ControlPane controlPane = new ControlPane();
    controlPane.setLayoutX(10); // top of screen?
    controlPane.setLayoutY(10); // top of screen?

    root.getChildren().add(controlPane);
  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }

}
