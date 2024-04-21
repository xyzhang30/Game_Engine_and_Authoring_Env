package oogasalad.view.authoring_environment.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.view.authoring_environment.Coordinate;

/**
 * The ShapeProxy class acts as a proxy for managing and manipulating shapes within the authoring
 * environment. It maintains a stack of shapes, allowing for selection, duplication, and other shape
 * manipulations.
 *
 * @author Judy He, Doga Ozmeng
 */
public class ShapeProxy {
  private final Stack<Shape> shapeStack = new Stack<>(); // Top of stack = most recently selected shape
  private GameObjectAttributesContainer gameObjectAttributesContainer = new GameObjectAttributesContainer();
  private int shapeCount;
  private int numberOfMultiSelectAllowed = 1;

  public Shape getShape() {
    if (shapeStack.isEmpty()) return null;
    return shapeStack.peek();
  }
  public void setShape(Shape shape) {
    if (shape != null && !shapeStack.isEmpty() && shapeStack.contains(shape)) {
      removeFromShapeStack(shape);
    }
    addToShapeStack(shape);
    resetGameObjectAttributesContainer();
  }
  public int getShapeCount() {
    return shapeCount;
  }

  public void setShapeCount(int shapeCount) {
    this.shapeCount = shapeCount;
  }

  public GameObjectAttributesContainer getGameObjectAttributesContainer() {
    return gameObjectAttributesContainer;
  }


  public List<Shape> createTemplateShapes() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    rectangle.setId(String.valueOf(shapeCount++));
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);
    rectangle.setStrokeWidth(5);

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setId(String.valueOf(shapeCount++));
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);
    ellipse.setStrokeWidth(5);

    return List.of(rectangle, ellipse);
  }


  public void setFinalShapeDisplay() {
    if (!shapeStack.isEmpty()) {
      gameObjectAttributesContainer.setWidth(shapeStack.peek().getLayoutBounds().getWidth());
      gameObjectAttributesContainer.setHeight(shapeStack.peek().getLayoutBounds().getHeight());
      Bounds boundsInScene = shapeStack.peek().localToScene(shapeStack.peek().getBoundsInLocal());
      gameObjectAttributesContainer.setPosition(new Coordinate(boundsInScene.getCenterX(),
          boundsInScene.getCenterY()));
    }
  }

  public Stack<Shape> getShapeStack() {
    return shapeStack;
  }
  public void removeFromShapeStack(Shape shape) {
    if (!shapeStack.isEmpty()) {
      shapeStack.remove(shape);
    }
  }
  public void addToShapeStack(Shape shape) {
    shapeStack.push(shape);
  }

  public void updateShapeSelectionDisplay()  {
    for (int i = 0; i < shapeStack.size() - numberOfMultiSelectAllowed; i++) {
      shapeStack.get(i).setStroke(Color.TRANSPARENT);
    }
  }

  public int getNumberOfMultiSelectAllowed() {
    return numberOfMultiSelectAllowed;
  }

  public void setNumberOfMultiSelectAllowed(int numberOfMultiSelectAllowed) {
    this.numberOfMultiSelectAllowed = numberOfMultiSelectAllowed;
  }

  public List<Integer> getSelectedShapeIds() {
    List<Integer> selectedShapesIds = new ArrayList<>();
    for (int i = shapeStack.size() - 1 ; i >= shapeStack.size() - numberOfMultiSelectAllowed; i--) {
      selectedShapesIds.add(Integer.valueOf(shapeStack.get(i).getId()));
    }
    return selectedShapesIds;
  }

  public Shape duplicateShape(Shape originalShape) {
    try {
      Shape newShape = originalShape.getClass().getDeclaredConstructor().newInstance();
      newShape.setFill(originalShape.getFill());
      newShape.setStroke(originalShape.getStroke());
      newShape.setStrokeWidth(originalShape.getStrokeWidth());
      newShape.setId(String.valueOf(shapeCount++));
      return newShape;
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void resetGameObjectAttributesContainer() {
    if (!shapeStack.isEmpty()) {
      Shape currentShape = shapeStack.peek();
      gameObjectAttributesContainer.setId(Integer.parseInt(currentShape.getId()));
      gameObjectAttributesContainer.setWidth(currentShape.getLayoutBounds().getWidth());
      gameObjectAttributesContainer.setHeight(currentShape.getLayoutBounds().getHeight());
      Bounds bounds = currentShape.localToScene(currentShape.getBoundsInLocal());
      gameObjectAttributesContainer.setPosition(new Coordinate(bounds.getMinX(), bounds.getMinY()));
    }
  }
}
