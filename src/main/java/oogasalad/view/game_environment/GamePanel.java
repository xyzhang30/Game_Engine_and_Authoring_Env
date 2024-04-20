package oogasalad.view.game_environment;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import oogasalad.view.visual_elements.CompositeElement;

/**
 * Transforms a node
 */
public class GamePanel {

  private final TransformableNode mapTransformer;
  private final TransformableNode screenTransformer;
  private final Rectangle localMapFrame;
  private final int localMapWidth = 800;
  private final int localMapHeight = 800;

  public GamePanel(CompositeElement modelElements) {
    mapTransformer = new TransformableNode(buildMapView(modelElements));
    localMapFrame = new Rectangle(localMapWidth, localMapHeight);
    screenTransformer = new TransformableNode(buildLocalView());
  }

  private Pane buildMapView(CompositeElement elements) {
    AnchorPane mapViewBase = new AnchorPane();
    for (int id : elements.idList()) {
      mapViewBase.getChildren().add(elements.getNode(id));
    }
    return mapViewBase;
  }

  private Pane buildLocalView() {
    Pane gameMap = mapTransformer.getPane();
    gameMap.setClip(localMapFrame);
    AnchorPane localView = new AnchorPane(gameMap);
    mapTransformer.sizeToBounds(localMapFrame.getWidth(), localMapFrame.getHeight());

    // Add some local view indicator elements here
    gameMap.setStyle("-fx-background-color: #FF0000;");
    //localView.setStyle("-fx-background-color: #DDAAFF;");

    return localView;
  }

  public Pane getPane() {
    return screenTransformer.getPane();
  }

  public void setCamera(double x, double y, double w, double h) {
    screenTransformer.sizeToBounds(w, h);
  }

  public void zoomIn() {
    mapTransformer.zoom(1.05);
  }

  public void zoomOut() {
    mapTransformer.zoom(0.95);
  }
}
