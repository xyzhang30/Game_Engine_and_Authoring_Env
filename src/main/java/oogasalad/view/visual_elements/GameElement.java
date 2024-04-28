package oogasalad.view.visual_elements;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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

  public GameElement(ViewGameObjectRecord viewData)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    id = viewData.id();
    myNode = makeShape(viewData);
    myNode.setTranslateX(viewData.startXpos());
    myNode.setTranslateY(viewData.startYpos());
  }
  public void changeFill(ViewGameObjectRecord viewData){
    Shape shape = ((Shape)myNode);
    if (viewData.image().isEmpty()) {
      List<Integer> rgb = viewData.color();
      Color color = Color.rgb(rgb.get(0), rgb.get(1), rgb.get(2));
      shape.setFill(color);
    } else {
      Path imgPath = Paths.get(viewData.image());
      Image image = new Image(imgPath.toUri().toString());
      shape.setFill(new ImagePattern(image));
    }
  }

  public String matchShape(String shape) {
    String JAVAFX_SHAPE_CLASS_PATH = "javafx.scene.shape.";
    return switch (shape) {
      case "Circle", "circle" -> JAVAFX_SHAPE_CLASS_PATH + "Ellipse";
      case "Rectangle", "rectangle" -> JAVAFX_SHAPE_CLASS_PATH + "Rectangle";
      default -> {
        throw new InvalidShapeException("Shape " + shape + " is not supported");
      }
    };
  }

  private Node makeShape(ViewGameObjectRecord data)
      throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    String className = matchShape(data.shape());
    Class<?> classObj = Class.forName(className);
    Object obj = classObj.getDeclaredConstructor(double.class, double.class)
        .newInstance(data.width(), data.height());
    Shape shape = (Shape) obj;

    if (data.image().isEmpty()) {
      List<Integer> rgb = data.color();
      Color color = Color.rgb(rgb.get(0), rgb.get(1), rgb.get(2));
      shape.setFill(color);
    } else {
      Path imgPath = Paths.get(data.image());
      Image image = new Image(imgPath.toUri().toString());
      shape.setFill(new ImagePattern(image));
    }

    return shape;
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
