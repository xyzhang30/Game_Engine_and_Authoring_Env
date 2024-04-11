package oogasalad.view.GameScreens;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.VisualElements.CompositeElement;

public class GameContentPanel {
  private final Pane globalView;
  private final Rectangle clip;
  public GameContentPanel(CompositeElement compositeElement){
    globalView = new AnchorPane();
    addNodes(compositeElement);
    clip = new Rectangle(500,500);
    globalView.setClip(clip);
  }
  public void addNodes(CompositeElement cm){
    for (int i : cm.idList()) {
      globalView.getChildren().add(cm.getNode(i));
    }
  }
  public void clipToDimensions(double width,double height){
    clip.setWidth(width);
    clip.setHeight(height);
  }
  public Pane getPane(){
    return globalView;
  }
}
