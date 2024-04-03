package oogasalad.view.Screen;

import javafx.scene.Group;
import javafx.scene.Node;
import oogasalad.view.VisualElements.CompositeElement;

public class GameScreen extends UIScreen{

  public GameScreen(CompositeElement compositeElement) {
    super();
    root = new Group();
  }

  //this would need to be a for loop and loop through all the ids of elements
//  public void addCompElement(CompositeElement compositeElement){
//    root.getChildren().add(compositeElement.getNode());
//  }
}
