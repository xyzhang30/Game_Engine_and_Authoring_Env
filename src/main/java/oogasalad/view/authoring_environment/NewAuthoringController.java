package oogasalad.view.authoring_environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.model.api.data.CollisionRule;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GlobalVariables;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.PlayerVariables;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.view.authoring_environment.authoring_screens.InteractionType;
import oogasalad.view.authoring_environment.authoring_screens.GameObjectType;
import oogasalad.view.authoring_environment.panels.GameObjectAttributesContainer;
import oogasalad.view.controller.BuilderDirector;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen, Alisha Zhang
 */
public class NewAuthoringController {

  private final Stage stage;
  private final AuthoringScreen authoringScreen = new AuthoringScreen();
  //  private Rectangle background;
  private final BuilderDirector builderDirector = new BuilderDirector();

  public NewAuthoringController() {
    stage = new Stage();
    authoringScreen.getAuthoringProxy().setAuthoringController(this);
  }

  public void updateAuthoringScreen() {
    stage.setScene(authoringScreen.getScene());
    stage.show();
  }

//  /**
//   * Starts the next selection process by creating the applicable scene and showing it on the stage
//   *
//   * @param imageType    the selection process that has just finished
//   * @param authoringBox holds the user's current game configurations
//   */
//  public void startNextSelection(ImageType imageType, StackPane authoringBox,
//      Map<Shape, List<Double>> posMap,
//      Map<Shape, NonControllableType> nonControllableMap, List<Shape> controllableList,
//      Map<Shape, String> imageMap) {
//    switch (imageType) {
//      case BACKGROUND -> {
//        ControllableElementSelectionScreen controllableElementSelectionScreen =
//            new ControllableElementSelectionScreen(this, authoringBox, posMap, nonControllableMap,
//                controllableList, imageMap);
//        // System.out.println("finished background, getting controllable");
//        stage.setScene(controllableElementSelectionScreen.getScene());
//      }
//      case CONTROLLABLE_ELEMENT -> {
//        NonControllableElementSelection nonControllableElementSelection =
//            new NonControllableElementSelection(this, authoringBox, posMap, nonControllableMap,
//                controllableList, imageMap);
//        //System.out.println("finished controllable, getting noncontrollable");
//        stage.setScene(nonControllableElementSelection.getScene());
//      }
//      case NONCONTROLLABLE_ELEMENT -> {
//        InteractionSelectionScreen interactionSelectionScreen
//            = new InteractionSelectionScreen(this, authoringBox, posMap, nonControllableMap,
//            controllableList, imageMap);
//        //System.out.println("finished noncontrollable, getting interaction");
//        stage.setScene(interactionSelectionScreen.getScene());
//      }
//    }
//  }

//  public void setBackground(Rectangle background) {
//    this.background = background;
//  }

  public void endAuthoring(String gameName,
      Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap,
      Map<Shape, GameObjectAttributesContainer> gameObjectMap, Map<Shape, String> imageMap,
      Map<Shape, Coordinate> posMap) {
    System.out.println(gameName);
    System.out.println(interactionMap);
    System.out.println(gameObjectMap);
    System.out.println(imageMap);
    System.out.println(posMap);

//    boolean saveGameSuccess = submitGame(gameName, interactionMap, controllables,
//        nonControllableTypeMap, imageMap, posMap);
//    Alert alert = new Alert(AlertType.INFORMATION);
//    if (saveGameSuccess) {
//      alert.setTitle("Save Game Success");
//      alert.setHeaderText(null);
//      alert.setContentText("Game saved successfully!");
//      alert.showAndWait();
//      stage.close();
//    } else {
//      alert.setTitle("Save Game Error");
//      alert.setHeaderText(null);
//      alert.setContentText("Saving game failed :(");
//      alert.showAndWait();
//      stage.close();
//    }
  }

  private boolean submitGame(String gameName,
      Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap,
      List<Shape> controllables, Map<Shape, GameObjectType> nonControllableTypeMap,
      Map<Shape, String> imageMap, Map<Shape, Coordinate> posMap) {
    try {
      Map<Shape, Integer> collidableIdMap = new HashMap<>();
      writeCollidables(collidableIdMap, controllables, nonControllableTypeMap, imageMap, posMap);
      writePlayer();
      writeVariables();
      writeRules(interactionMap, collidableIdMap);
      builderDirector.writeGame(gameName);
      return true;
    } catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }
  }

  private void writeVariables() {
    //HARD CODED FOR DEMO!
    Variables variables = new Variables(new GlobalVariables(1, 2), new PlayerVariables(0, 0));
    builderDirector.constructVaraibles(List.of(variables));
  }

  private void writePlayer() {
    //HARD CODED FOR DEMO!
    //QUESTION: WHICH COLLIDABLE WILL BE THE ONE PLAYER IS ASSIGNED TO IN THE DEMO WE DO??
    ParserPlayer player = new ParserPlayer(1, List.of(1));

    builderDirector.constructPlayers(List.of(player));
  }

  private void writeRules(Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap,
      Map<Shape, Integer> collidableIdMap) {
    //COLLISION RULE
    List<CollisionRule> collisionRules = new ArrayList<>();
    for (List<Shape> pair : interactionMap.keySet()) {
      Map<String, List<Double>> collisionCommand = new HashMap<>();
      for (InteractionType type : interactionMap.get(pair).keySet()) {
        if (type != InteractionType.CHANGE_SPEED) {
          collisionCommand.put(matchCommandName(type), interactionMap.get(pair).get(type));
        }
      }
      for (Shape shape : collidableIdMap.keySet()) {
        System.out.println("ID: " + collidableIdMap.get(shape));
      }
      System.out.println("FIRST ID: " + collidableIdMap.get(pair.get(0)));
      CollisionRule collisionRule = new CollisionRule(collidableIdMap.get(pair.get(0)),
          collidableIdMap.get(pair.get(1)), List.of(collisionCommand));

      collisionRules.add(collisionRule);
    }

    String turnPolicy = "StandardTurnPolicy";

    Map<String, List<Double>> roundPolicy = new HashMap<>();
    roundPolicy.put("AllPlayersCompletedRoundCondition", new ArrayList<>());

    Map<String, List<Double>> winCondition = new HashMap<>();
    winCondition.put("NRoundsCompletedCondition", List.of((double) 1));

//    List<Map<String, List<Double>>> advanceTurn = new ArrayList<>();
    Map<String, List<Double>> advanceTurn = new HashMap<>();
    Map<String, List<Double>> turnCommandOne = new HashMap<>();
    advanceTurn.put("AdvanceTurnCommand", new ArrayList<>());
//    advanceTurn.add(turnCommandOne);
    Map<String, List<Double>> turnCommandTwo = new HashMap<>();
    advanceTurn.put("AdjustActivePointsCommand", List.of(1.0));
//    advanceTurn.add(turnCommandTwo);

//    List<Map<String, List<Double>>> advanceRound = new ArrayList<>();
    Map<String, List<Double>> advanceRound = new HashMap<>();
    Map<String, List<Double>> roundCommandOne = new HashMap<>();
    advanceRound.put("AdvanceRoundCommand", new ArrayList<>());
//    advanceRound.add(roundCommandOne);
    Map<String, List<Double>> roundCommandTwo = new HashMap<>();
    advanceRound.put("AdjustActivePointsCommand", List.of(1.0));
//    advanceRound.add(roundCommandTwo);

    Rules rules = new Rules(collisionRules, turnPolicy, roundPolicy, winCondition,
        advanceTurn, advanceRound, "DoNothingStrikePolicy", "HighestScoreComparator",
        Map.of());

    builderDirector.constructRules(List.of(rules));
  }

  private String matchCommandName(InteractionType type) {
    return switch (type) {
      case RESET -> "UndoTurnCommand";
      case ADVANCE -> "AdvanceRoundCommand";
      case SCORE -> "AdjustPointsCommand";
      case CHANGE_SPEED -> null;
    };
  }

  private void writeCollidables(Map<Shape, Integer> collidableIdMap, List<Shape> controllables,
      Map<Shape, GameObjectType> nonControllableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, Coordinate> posMap) {
//    int collidableId = 0;
    List<GameObjectProperties> collidableObjects = new ArrayList<>();

//    //handling background first
//    List<Integer> colorRgb = List.of(0, 0, 0);
//    String imgPath = "";
//    if (background.getFill() instanceof Color) {
//      Color c = (Color) (background.getFill());
//      colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//          (int) c.getBlue() * 255);
//    } else {
//      imgPath = imageMap.get(background);
//    }
//    List<String> properties = new ArrayList<>();
//    properties.add("collidable");
//    properties.add("visible");
//    properties.add(nonControllableTypeMap.get(background).toString().toLowerCase());
//    //double friction = 0.8;
//    double staticFriction = 7;
//    double kineticFriction = 5;
//    String shapeName = "Rectangle";
//    GameObject collidableObject = new GameObject(collidableId,
//        properties, Float.POSITIVE_INFINITY,
//        new Position(posMap.get(background).x(), posMap.get(background).y()),
//        shapeName, new Dimension(background.getLayoutBounds().getWidth(),
//        background.getLayoutBounds().getHeight()), colorRgb, staticFriction, kineticFriction,
//        imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(background, collidableId);
//    collidableId++;
//
//    nonControllableTypeMap.remove(background);
//
//    //walls
//    Rectangle wall1 = new Rectangle(50, 50, 20, 990);
//    colorRgb = List.of(0, 0, 0);
//    imgPath = "";
//    properties = new ArrayList<>();
//    properties.add("collidable");
//    properties.add("visible");
//    properties.add("movable");
//    shapeName = "Rectangle";
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 50),
//        shapeName, new Dimension(20,
//        990), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall1, collidableId);
//    collidableId++;
//
//    Rectangle wall2 = new Rectangle(1020, 50, 20, 990);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(1020, 50),
//        shapeName, new Dimension(20,
//        990), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall2, collidableId);
//    collidableId++;
//
//    Rectangle wall3 = new Rectangle(50, 50, 990, 20);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 50),
//        shapeName, new Dimension(985,
//        20), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall3, collidableId);
//    collidableId++;
//
//    Rectangle wall4 = new Rectangle(50, 1020, 990, 20);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 1015),
//        shapeName, new Dimension(985,
//        20), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall4, collidableId);
//    collidableId++;
//
//    //noncontrollables
//    for (Shape shape : nonControllableTypeMap.keySet()) {
//      colorRgb = List.of(0, 0, 0);
//      imgPath = "";
//      if (shape.getFill() instanceof Color) {
//        Color c = (Color) (shape.getFill());
//        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//            (int) c.getBlue() * 255);
//      } else {
//        imgPath = imageMap.get(shape);
//      }
//      properties = new ArrayList<>();
//      properties.add("collidable");
//      properties.add("visible");
//      properties.add(nonControllableTypeMap.get(shape).toString().toLowerCase());
//      staticFriction =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 3.03873
//              : 0.0;
//      kineticFriction =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 2.03873
//              : 0.0;
//      double mass =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("Surface"))
//              ? Double.POSITIVE_INFINITY
//              : 10.0;
//      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
//      if (shape instanceof Ellipse) {
//        collidableObject = new CollidableObject(collidableId,
//            properties, mass,
//            new Position(posMap.get(shape).x(), posMap.get(shape).y()), shapeName,
//            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
//                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      } else {
//        collidableObject = new CollidableObject(collidableId,
//            properties, mass,
//            new Position(posMap.get(shape).x(), posMap.get(shape).y()), shapeName,
//            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
//                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      }
//
//      collidableObjects.add(collidableObject);
//      collidableIdMap.put(shape, collidableId);
//      collidableId++;
//    }
//
//    //controllables
//    for (Shape shape : controllables) {
//      colorRgb = List.of(0, 0, 0);
//      imgPath = "";
//      if (shape.getFill() instanceof Color) {
//        Color c = (Color) (shape.getFill());
//        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//            (int) c.getBlue() * 255);
//      } else {
//        imgPath = imageMap.get(shape);
//      }
//      properties = List.of("movable", "collidable", "controllable", "visible");
//      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
//      if (shape instanceof Ellipse) {
//        collidableObject = new CollidableObject(collidableId,
//            properties, 10,
//            new Position(posMap.get(shape).x(), posMap.get(shape).y()), shapeName,
//            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
//                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      } else {
//        collidableObject = new CollidableObject(collidableId,
//            properties, 10,
//            new Position(posMap.get(shape).x(), posMap.get(shape).y()), shapeName,
//            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
//                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      }
//
//      collidableObjects.add(collidableObject);
//      collidableIdMap.put(shape, collidableId);
//      collidableId++;
//    }
//
//    for (Shape shape : collidableIdMap.keySet()) {
//      System.out.println("ID in collidablewrite: " + collidableIdMap.get(shape));
//    }

    builderDirector.constructCollidableObjects(collidableObjects);

  }

}
