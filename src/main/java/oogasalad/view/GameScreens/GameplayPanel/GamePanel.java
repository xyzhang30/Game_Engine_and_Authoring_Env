package oogasalad.view.GameScreens.GameplayPanel;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import oogasalad.view.VisualElements.CompositeElement;

public class GamePanel {
  private final Pane globalView;
  private double scaleX;
  private double scaleY;
  private double offsetX;
  private double offsetY;
  public GamePanel(){
    globalView = new AnchorPane();
    scaleX = 1;
    scaleY = 1;
    offsetX = 0;
    offsetY = 0;
  }
  public void addGameContentNodes(CompositeElement elements){
    for (int i : elements.idList()) {
      globalView.getChildren().add(elements.getNode(i));
    }
    Sheen sheen = new Sheen();
    sheen.setSize(globalView);
    globalView.getChildren().add(sheen.getNode());
  }
  public void TransformLocal(){
    globalView.setScaleX(scaleX);
    globalView.setScaleY(scaleY);
    globalView.setTranslateX(globalView.getBoundsInLocal().getCenterX()*(scaleX-1)-offsetX);
    globalView.setTranslateY(globalView.getBoundsInLocal().getCenterY()*(scaleY-1)-offsetY);
  }
  public void modifyScope(double del){
    scaleX *= del;
    scaleY *= del;
    TransformLocal();
  }
  public Pane getPane(){
    return globalView;
  }
}
