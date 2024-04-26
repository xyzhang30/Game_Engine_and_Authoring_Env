package oogasalad.view.authoring_environment.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.authoring_environment.util.Coordinate;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ShapeProxy class acts as a proxy for managing and manipulating shapes within the authoring
 * environment. It maintains a stack of shapes, allowing for selection, duplication, and other shape
 * manipulations.
 *
 * @author Judy He, Doga Ozmen
 */
public class ShapeProxy {

  private final Stack<Shape> shapeStack = new Stack<>();
  private final ListProperty<Integer> shapesListProperty = new SimpleListProperty<>(
      FXCollections.observableArrayList());
  private GameObjectAttributesContainer gameObjectAttributesContainer =
      new GameObjectAttributesContainer();
  private int shapeCount;
  private final List<Shape> templates = new ArrayList<>();
  String RESOURCE_FOLDER_PATH = "view.";
  String VIEW_PROPERTIES_PATH = "properties.";
  String DEFAULT_VALUES_FILE = "DefaultAuthoringValues";
  ResourceBundle defaultValuesResourceBundle = ResourceBundle.getBundle(
      RESOURCE_FOLDER_PATH + VIEW_PROPERTIES_PATH + DEFAULT_VALUES_FILE);
  private int numberOfMultiSelectAllowed = Integer.parseInt(
      defaultValuesResourceBundle.getString("defaultNumShapesSelectedAllowed"));

  /**
   * Gets the most recently selected shape.
   *
   * @return the most recently selected shape.
   */
  public Shape getShape() {
    if (shapeStack.isEmpty()) {
      return null;
    }
    return shapeStack.peek();
  }

  /**
   * Selects a shape.
   *
   * @param shape The shape to be selected.
   */
  public void selectShape(Shape shape) {
    if (shape != null && !shapeStack.isEmpty() && shapeStack.contains(shape)) {
      deselectShape(shape);
    }
    shapeStack.push(shape);
    shapesListProperty.setAll(getSelectedShapeIds());
    resetGameObjectAttributesContainer();
  }

  /**
   * Gets the GameObjectAttributesContainer associated with the selected shape.
   *
   * @return The GameObjectAttributesContainer of the selected shape.
   */
  public GameObjectAttributesContainer getGameObjectAttributesContainer() {
    return gameObjectAttributesContainer;
  }

  /**
   * Creates templates for game objects such as rectangles and ellipses.
   */
  public void createGameObjectTemplates() {
    Rectangle rectangle = new Rectangle(100, 50, Color.BLACK);
    AnchorPane.setRightAnchor(rectangle, 150.0);
    AnchorPane.setTopAnchor(rectangle, 300.0);
    rectangle.setStrokeWidth(3);

    Ellipse ellipse = new Ellipse(30, 30);
    ellipse.setFill(Color.BLACK);
    AnchorPane.setRightAnchor(ellipse, 50.0);
    AnchorPane.setTopAnchor(ellipse, 300.0);
    ellipse.setStrokeWidth(3);

    templates.clear();
    templates.addAll(List.of(rectangle, ellipse));

  }

  /**
   * Sets a selected template on click, creating a clone of the template and adjusting its
   * properties.
   *
   * @param template The shape template to be set on click.
   * @return The newly created cloned shape.
   * @throws NoSuchMethodException     If the cloning process encounters an error.
   * @throws InvocationTargetException If an error occurs during shape creation.
   * @throws InstantiationException    If an instance cannot be created for the shape.
   * @throws IllegalAccessException    If an illegal access occurs during shape creation.
   */
  public Shape setTemplateOnClick(Shape template)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException,
      IllegalAccessException {

    // Set stroke color of the template to pink
    template.setStroke(Color.PINK);

    // Reset the stroke color of other templates to transparent
    templates.stream()
        .filter(t -> t != template)
        .forEach(t -> t.setStroke(Color.TRANSPARENT));

    // Clone the template shape based on its type
    Shape clonedShape = cloneShape(template);

    // Set properties for the cloned shape
    clonedShape.setFill(template.getFill());
    clonedShape.setStrokeWidth(template.getStrokeWidth());
    clonedShape.setStroke(Color.LIGHTGREEN);
    clonedShape.setId(String.valueOf(shapeCount++)); // Update ID

    // Anchor the cloned shape in the desired position
    AnchorPane.setRightAnchor(clonedShape, 100.0);
    AnchorPane.setBottomAnchor(clonedShape, 200.0);

    if (this.shapeStack.isEmpty()) {
      selectShape(clonedShape);
    }

    return clonedShape;
  }

  private Shape cloneShape(Shape template) {
    // Determine shape type and clone accordingly
    String shapeType = template.getClass().getSimpleName();
    return switch (shapeType) {
      case "Rectangle" -> new Rectangle(template.getLayoutBounds().getWidth(),
          template.getLayoutBounds().getHeight(), template.getFill());
      case "Ellipse" -> new Ellipse(template.getLayoutBounds().getWidth() / 2,
          template.getLayoutBounds().getHeight() / 2);
      default -> throw new IllegalStateException("Unexpected value: " + shapeType);
    };
  }

  /**
   * Updates the display settings for the final shape.
   */
  public void setFinalShapeDisplay() {
    if (!shapeStack.isEmpty()) {
      gameObjectAttributesContainer.setWidth(
          shapeStack.peek().getLayoutBounds().getWidth() * shapeStack.peek().getScaleX());
      gameObjectAttributesContainer.setHeight(
          shapeStack.peek().getLayoutBounds().getHeight() * shapeStack.peek().getScaleY());
      Bounds boundsInScene = shapeStack.peek().localToScene(shapeStack.peek().getBoundsInLocal());
      gameObjectAttributesContainer.setPosition(new Coordinate(boundsInScene.getCenterX(),
          boundsInScene.getCenterY()));
    }
  }

  /**
   * Deselects the specified shape.
   *
   * @param shape The shape to be deselected.
   */
  public void deselectShape(Shape shape) {
    if (!shapeStack.isEmpty()) {
      shapeStack.remove(shape);
      shapesListProperty.remove((Integer) Integer.parseInt(shape.getId()));
    }
  }

  /**
   * Updates the display of the shape selection based on the number of multi-selects allowed.
   */
  public void updateShapeSelectionDisplay() {
    for (int i = 0; i < shapeStack.size() - numberOfMultiSelectAllowed; i++) {
      shapeStack.get(i).setStroke(Color.TRANSPARENT);
    }
  }

  /**
   * Sets the number of multi-selects allowed.
   *
   * @param numberOfMultiSelectAllowed The number of multi-selects allowed.
   */
  public void setNumberOfMultiSelectAllowed(int numberOfMultiSelectAllowed) {
    this.numberOfMultiSelectAllowed = numberOfMultiSelectAllowed;
  }

  /**
   * Gets a list of selected shape IDs.
   *
   * @return A list of selected shape IDs.
   */
  public List<Integer> getSelectedShapeIds() {
    List<Integer> selectedShapesIds = new ArrayList<>();
    for (int i = shapeStack.size() - 1; i >= shapeStack.size() - numberOfMultiSelectAllowed; i--) {
      selectedShapesIds.add(Integer.valueOf(shapeStack.get(i).getId()));
    }
    return selectedShapesIds;
  }

  /**
   * Resets the GameObjectAttributesContainer due to new shape selection.
   */
  public void resetGameObjectAttributesContainer() {
    gameObjectAttributesContainer = new GameObjectAttributesContainer();
    if (!shapeStack.isEmpty()) {
      Shape currentShape = shapeStack.peek();
      gameObjectAttributesContainer.setId(Integer.parseInt(currentShape.getId()));
      gameObjectAttributesContainer.setWidth(
          currentShape.getLayoutBounds().getWidth() * currentShape.getScaleX());
      gameObjectAttributesContainer.setHeight(
          currentShape.getLayoutBounds().getHeight() * currentShape.getScaleY());
      Bounds bounds = currentShape.localToScene(currentShape.getBoundsInLocal());
      gameObjectAttributesContainer.setPosition(new Coordinate(bounds.getMinX(), bounds.getMinY()));
    }
  }

  /**
   * Retrieves the list of templates available for shape creation.
   *
   * @return A list of shape templates.
   */
  public List<Shape> getTemplates() {
    return templates;
  }

  /**
   * Gets the ListProperty of shapes.
   *
   * @return The list property of shapes.
   */
  public ListProperty<Integer> getShapesListProperty() {
    return shapesListProperty;
  }

}
