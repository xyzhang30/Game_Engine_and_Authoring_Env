package oogasalad.view.GameScreens.GameplayPanel;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * Transforms a node
 */
public class GamePanelLocalView {
  private final Bounds sourceBounds;
  private final Pane localDisplay;
  private final Rectangle sourceView;
  private final Rectangle destinationView;
  public GamePanelLocalView(Node source){
    sourceBounds = source.getBoundsInParent();
    localDisplay = new AnchorPane(source);
    sourceView = new Rectangle();
    destinationView = new Rectangle(0,0,800,800);
    setCamera(0,0,sourceBounds.getWidth(),sourceBounds.getHeight());
    localDisplay.setClip(destinationView);
  }
  public Pane getPane(){
    return localDisplay;
  }
  public void setCamera(double x, double y, double w, double h){
    sourceView.setX(x);
    sourceView.setY(y);
    sourceView.setWidth(w);
    sourceView.setHeight(h);
    TransformToLocal();
  }
  public void TransformToLocal(){
    double scaleX = destinationView.getWidth()/sourceView.getWidth();
    double scaleY = destinationView.getHeight()/sourceView.getHeight();
    localDisplay.setScaleX(scaleX);
    localDisplay.setScaleY(scaleY);
    localDisplay.setTranslateX(sourceBounds.getCenterX()*(scaleX-1)-sourceView.getX());
    localDisplay.setTranslateY(sourceBounds.getCenterY()*(scaleY-1)-sourceView.getY());
  }
}
