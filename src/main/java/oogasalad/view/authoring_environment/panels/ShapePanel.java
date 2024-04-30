package oogasalad.view.authoring_environment.panels;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import oogasalad.view.api.authoring.AuthoringFactory;
import oogasalad.view.api.authoring.Panel;
import oogasalad.view.api.authoring.UIElementFactory;
import oogasalad.view.api.enums.AuthoringScreenType;
import oogasalad.view.api.enums.CollidableType;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.authoring_environment.util.Coordinate;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;

/**
 * ShapePanel is responsible for handling shape-related events in the authoring environment,
 * including the creation and management of game objects and their interactions within a visual
 * interface.
 *
 * @author Judy He
 */
public class ShapePanel implements Panel {

  private final ShapeProxy shapeProxy;
  private final AuthoringProxy authoringProxy;
  private final AuthoringFactory authoringFactory;
  private final StackPane canvas;
  private final AnchorPane rootPane;
  private final AnchorPane containerPane;
  private Coordinate startPos;
  private Coordinate translatePos;

  /**
   * Constructs a ShapePanel with the specified authoring factory, shape proxy, and authoring proxy,
   * as well as the specified layout panes (canvas, root pane, and container pane).
   *
   * @param authoringFactory The factory used to create authoring components.
   * @param shapeProxy       The shape proxy for handling shape-related operations.
   * @param authoringProxy   The authoring proxy for managing authoring operations.
   * @param canvas           The canvas pane.
   * @param rootPane         The root pane.
   * @param containerPane    The container pane.
   */
  public ShapePanel(AuthoringFactory authoringFactory, ShapeProxy shapeProxy,
      AuthoringProxy authoringProxy, StackPane canvas,
      AnchorPane rootPane, AnchorPane containerPane) {
    this.shapeProxy = shapeProxy;
    this.authoringProxy = authoringProxy;
    this.authoringFactory = authoringFactory;
    this.canvas = canvas;
    this.rootPane = rootPane;
    this.containerPane = containerPane;
    shapeProxy.setNumberOfMultiSelectAllowed(1);
    createElements();
    handleEvents();
  }

  /**
   * Creates elements for the shape panel, including configurations and templates for game objects,
   * surfaces, collidables, and players.
   */
  @Override
  public void createElements() {
    containerPane.getChildren().addAll(authoringFactory.createGameObjectsConfiguration());
    containerPane.getChildren().addAll(authoringFactory.createSurfacesConfiguration());
    containerPane.getChildren().addAll(authoringFactory.createCollidablesConfiguration());
    containerPane.getChildren().addAll(authoringFactory.createPlayersConfiguration());
    shapeProxy.createGameObjectTemplates();
    containerPane.getChildren().addAll(shapeProxy.getTemplates());
  }


  /**
   * Handles events for the shapes in the authoring panel.
   */
  @Override
  public void handleEvents() {
    for (Shape shape : shapeProxy.getTemplates()) {
      handleGameObjectTemplateEvents(shape);
    }
  }

  private void handleGameObjectTemplateEvents(Shape shape) {
    shape.setOnMouseClicked(event -> {
      try {
        Shape clonedShape = shapeProxy.setTemplateOnClick((Shape) event.getSource());
        handleGameObjectEvents(clonedShape, authoringProxy.getPlayers());
        rootPane.getChildren().add(clonedShape);
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
               IllegalAccessException e) {
        LOGGER.error("cloning error");
        throw new RuntimeException(e);
      }
    });
  }

  private void handleGameObjectEvents(Shape shape, Map<Integer, Map<CollidableType, List<Integer>>> oldPlayers) {
    shape.setOnMouseClicked(event -> setShapeOnClick((Shape) event.getSource(),
        authoringProxy.getGameObjectMap().get(shape),oldPlayers));
    shape.setOnMousePressed(this::handleMousePressed);
    shape.setOnMouseDragged(event -> setShapeOnCompleteDrag((Shape) event.getSource(), event));
    shape.setOnMouseReleased(event -> setShapeOnRelease((Shape) event.getSource()));
  }

  private void handleMousePressed(MouseEvent event) {
    Shape shape = (Shape) event.getSource();
    try {
      setShapeBeginDrag(shape, event);
    } catch (ReflectiveOperationException e) {
      LOGGER.error(e);
    }
  }

  private void setShapeBeginDrag(Shape shape, MouseEvent event)
      throws ReflectiveOperationException {
    if (shape.getParent() != null) {
      ((Pane) shape.getParent()).getChildren().remove(shape);
    }
    rootPane.getChildren().add(shape);
    startPos = new Coordinate(event.getSceneX(), event.getSceneY());
    translatePos = new Coordinate(shape.getTranslateX(), shape.getTranslateY());
  }

  private void setShapeOnCompleteDrag(Shape shape, MouseEvent event) {
    Coordinate offset = new Coordinate(event.getSceneX() - startPos.x(),
        event.getSceneY() - startPos.y());
    Coordinate newTranslatePos = new Coordinate(translatePos.x() + offset.x(),
        translatePos.y() + offset.y());
    shape.setTranslateX(newTranslatePos.x());
    shape.setTranslateY(newTranslatePos.y());
  }

  private void setShapeOnRelease(Shape shape) {
    if (isInAuthoringBox(shape)) {
      Double leftAnchor = AnchorPane.getLeftAnchor(shape);
      Double topAnchor = AnchorPane.getTopAnchor(shape);
      if (leftAnchor == null) {
        leftAnchor = 0.0;
      }
      if (topAnchor == null) {
        topAnchor = 0.0;
      }
      shapeProxy.getGameObjectAttributesContainer()
          .setPosition(new Coordinate(leftAnchor, topAnchor));
    } else {
      shape.setVisible(false);
      rootPane.getChildren().remove(shape);
      shapeProxy.deselectShape(shape);
      authoringProxy.getGameObjectMap().remove(shape);
      authoringProxy.removeObjectFromPlayersAllLists(Integer.valueOf(shape.getId()));
    }
  }

  private void setShapeOnClick(Shape shape, GameObjectAttributesContainer gameObj,
      Map<Integer, Map<CollidableType, List<Integer>>> playersMap) {
    System.out.println("on click:" + playersMap);
    if (shapeProxy.getShape() == null) {
      return;
    }

    if (authoringProxy.getCurrentScreenTitle().equals(AuthoringScreenType.GAMEOBJECTS.toString())) {
      shapeProxy.setFinalShapeDisplay();
      try {
        GameObjectAttributesContainer copy =
            (GameObjectAttributesContainer) shapeProxy.getGameObjectAttributesContainer()
                .clone();
        authoringProxy.setGameObject(shapeProxy.getShape(), copy);
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
      }
    }
    shapeProxy.selectShape(shape, gameObj);
    shape.setStroke(Color.YELLOW);
    shapeProxy.updateShapeSelectionDisplay();
    authoringFactory.resetAuthoringElements(gameObj, playersMap);
    System.out.println("AFTER EVERYTHING:"+authoringProxy.getPlayers());
//    authoringProxy.setPlayersMap(playersMap);
  }

  private boolean isInAuthoringBox(Shape shape) {
    Bounds shapeBounds = shape.getBoundsInParent();
    Bounds authoringBoxBounds = canvas.getBoundsInParent();
    return authoringBoxBounds.contains(shapeBounds);
  }

}
