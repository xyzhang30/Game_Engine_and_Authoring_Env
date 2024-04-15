package oogasalad.view.GameScreens.GameplayPanel;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.VisualElements.CompositeElement;

public class GamePanelEmbed {
  private final Pane panelView;
  private final Rectangle clip;

  public GamePanelEmbed(CompositeElement elements){
    GamePanel localView = new GamePanel(elements);
    panelView = new AnchorPane(localView.getPane());
    clip = new Rectangle();
    panelView.setClip(clip);
  }
  public Pane getPane(){
    return panelView;
  }
  public void setBoundary(double width,double height){
    clip.setWidth(width);
    clip.setHeight(height);
  }
}
