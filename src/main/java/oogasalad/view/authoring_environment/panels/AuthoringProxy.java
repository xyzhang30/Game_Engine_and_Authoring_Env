package oogasalad.view.authoring_environment.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import oogasalad.view.api.exception.MissingInteractionException;
import oogasalad.view.api.exception.MissingNonControllableTypeException;
import oogasalad.view.authoring_environment.Coordinate;
import oogasalad.view.authoring_environment.NewAuthoringController;
import oogasalad.view.authoring_environment.authoring_screens.InteractionType;
import oogasalad.view.authoring_environment.authoring_screens.NonControllableType;

public class AuthoringProxy {
  private String gameName;
  private String currentScreenTitle;
  private NewAuthoringController authoringController;
  private final Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap = new HashMap<>();
  private final List<Shape> controllables = new ArrayList<>();
  private final Map<Shape, NonControllableType> nonControllableMap = new HashMap<>();
  private final Map<Shape, String> imageMap = new HashMap<>();
  private final Map<Shape, Coordinate> shapePositionMap = new HashMap<>();

  public List<Shape> getControllables() {
    return controllables;
  }
  public void setGameName(String gameName) {
    this.gameName = gameName;
  }
  public void setCurrentScreenTitle(String currentScreenTitle) {
    this.currentScreenTitle = currentScreenTitle;
  }
  public void setAuthoringController(
      NewAuthoringController authoringController) {
    this.authoringController = authoringController;
  }
  public void addShapeInteraction(List<Shape> shapes, Map<InteractionType, List<Double>> interaction) {
    interactionMap.put(shapes, interaction);
  }
  public void addControllableShape(Shape controllable) {
    controllables.add(controllable);
  }
  public Map<Shape, NonControllableType> getNonControllableMap() {
    return nonControllableMap;
  }
  public void addNonControllableShape(Shape shape, NonControllableType nonControllableType) {
    nonControllableMap.put(shape, nonControllableType);
  }
  public void addImage(Shape shape, String relativePath) {
   imageMap.put(shape, relativePath);
  }
  public void addShapePosition(Shape shape, Coordinate position) {
    shapePositionMap.put(shape, position);
  }
  public void completeAuthoring() throws MissingInteractionException, MissingNonControllableTypeException {
    authoringController.endAuthoring(gameName, interactionMap, controllables, nonControllableMap, imageMap, shapePositionMap);
  }
  public void updateScreen() {
    authoringController.updateAuthoringScreen();
  }

  public String getGameName() {
    return gameName;
  }

  public String getCurrentScreenTitle() {
    return currentScreenTitle;
  }

  public NewAuthoringController getAuthoringController() {
    return authoringController;
  }

  public Map<List<Shape>, Map<InteractionType, List<Double>>> getInteractionMap() {
    return interactionMap;
  }

  public Map<Shape, String> getImageMap() {
    return imageMap;
  }

  public Map<Shape, Coordinate> getShapePositionMap() {
    return shapePositionMap;
  }
}
