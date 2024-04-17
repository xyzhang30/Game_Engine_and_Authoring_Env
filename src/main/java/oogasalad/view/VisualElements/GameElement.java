package oogasalad.view.VisualElements;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;

/**
 * Class for a single View Element for the Game engine.
 */
public class GameElement implements VisualElement {

  private final Node myNode;
  private final int id;

  public GameElement(ViewGameObjectRecord viewData) throws InvalidShapeException {
    id = viewData.id();
    myNode = makeShape(viewData);
    myNode.setTranslateX(viewData.startXpos());
    myNode.setTranslateY(viewData.startYpos());
  }

  private Node makeShape(ViewGameObjectRecord data) throws InvalidShapeException {
    try {
      if (data.image().isEmpty()) {
        List<Integer> rgb = data.color();
        Color color = Color.rgb(rgb.get(0), rgb.get(1), rgb.get(2));
        switch (data.shape()) { // Convert to reflection at later date
          case ELLIPSE -> {
            Ellipse ellipse = new Ellipse(data.width(), data.height());
            ellipse.setFill(color);
            return ellipse;
          }
          case RECTANGLE -> {
            return new Rectangle(data.width(), data.height(), color);
          }
          default -> throw new InvalidShapeException("Invalid shape");
        }
      } else {
        Path imgPath = Paths.get(data.image());
        Image image = new Image(imgPath.toUri().toString());
        switch (data.shape()) {
          case ELLIPSE -> {
            Ellipse ellipse = new Ellipse(data.width(), data.height());
            ellipse.setFill(new ImagePattern(image));
            return ellipse;
          }
          case RECTANGLE -> {
            Rectangle rectangle = new Rectangle(data.width(), data.height());
            rectangle.setFill(new ImagePattern(image));
            return rectangle;
          }
          default -> throw new InvalidShapeException("Invalid shape");
        }
      }
    } catch (IllegalArgumentException e) {

      throw new InvalidImageException(e.getMessage());
    }

  }

  /**
   * getter for element id
   *
   * @return element id
   */
  public int getId() {
    return id;
  }

  /**
   * Updates the Element properties.
   *
   * @param model This Element's corresponding model object.
   */
  @Override
  public void update(GameObjectRecord model) {
    myNode.setTranslateX(model.x());
    myNode.setTranslateY(model.y());
    myNode.setVisible(model.visible());
  }

  /**
   * Returns the Node housed by this Element.
   *
   * @return Node  This Element's Node.
   */
  @Override
  public Node getNode() {
    return myNode;
  }
}
