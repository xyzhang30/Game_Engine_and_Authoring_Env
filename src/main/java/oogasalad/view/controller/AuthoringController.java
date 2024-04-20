package oogasalad.view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import oogasalad.model.api.data.CollisionRule;
import oogasalad.model.api.data.Dimension;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GlobalVariables;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.PlayerVariables;
import oogasalad.model.api.data.Position;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.view.authoring_environment.authoring_screens.BackgroundSelectionScreen;
import oogasalad.view.authoring_environment.authoring_screens.ImageType;
import oogasalad.view.authoring_environment.authoring_screens.InteractionSelectionScreen;
import oogasalad.view.authoring_environment.authoring_screens.InteractionType;
import oogasalad.view.authoring_environment.authoring_screens.NonStrikeableElementSelection;
import oogasalad.view.authoring_environment.authoring_screens.NonStrikeableType;
import oogasalad.view.authoring_environment.authoring_screens.StrikeableElementSelectionScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen, Alisha Zhang
 */
public class AuthoringController {

  private static final Logger LOGGER = LogManager.getLogger(AuthoringController.class);
  private final Stage stage;
  private Rectangle background;
  private final BuilderDirector builderDirector = new BuilderDirector();


  public AuthoringController() {
    stage = new Stage();
  }

  public void startAuthoring() {
    Map<Shape, NonStrikeableType> map = new HashMap<>();
    BackgroundSelectionScreen scene = new BackgroundSelectionScreen(
        this, new StackPane(), new HashMap<>(), map, new ArrayList<>(),
        new HashMap<>());
    stage.setScene(scene.getScene());
    stage.show();
  }

  /**
   * Starts the next selection process by creating the applicable scene and showing it on the stage
   *
   * @param imageType    the selection process that has just finished
   * @param authoringBox holds the user's current game configurations
   */
  public void startNextSelection(ImageType imageType, StackPane authoringBox,
      Map<Shape, List<Double>> posMap,
      Map<Shape, NonStrikeableType> nonStrikeableMap, List<Shape> strikeableList,
      Map<Shape, String> imageMap) {
    switch (imageType) {
      case BACKGROUND -> {
        StrikeableElementSelectionScreen strikeableElementSelectionScreen =
            new StrikeableElementSelectionScreen(this, authoringBox, posMap, nonStrikeableMap,
                strikeableList, imageMap);
        // System.out.println("finished background, getting controllable");
        stage.setScene(strikeableElementSelectionScreen.getScene());
      }
      case STRIKEABLE_ELEMENT -> {
        NonStrikeableElementSelection nonStrikeableElementSelection =
            new NonStrikeableElementSelection(this, authoringBox, posMap, nonStrikeableMap,
                strikeableList, imageMap);
        //System.out.println("finished controllable, getting noncontrollable");
        stage.setScene(nonStrikeableElementSelection.getScene());
      }
      case NONSTRIKEABLE_ELEMENT -> {
        InteractionSelectionScreen interactionSelectionScreen
            = new InteractionSelectionScreen(this, authoringBox, posMap, nonStrikeableMap,
            strikeableList, imageMap);
        //System.out.println("finished noncontrollable, getting interaction");
        stage.setScene(interactionSelectionScreen.getScene());
      }
    }
  }

  public void setBackground(Rectangle background) {
    this.background = background;
  }

  public void endAuthoring(String gameName,
      Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap,
      List<Shape> strikeables, Map<Shape, NonStrikeableType> nonStrikeableTypeMap,
      Map<Shape, String> imageMap, Map<Shape, List<Double>> posMap) {
    boolean saveGameSuccess = submitGame(gameName, interactionMap, strikeables,
        nonStrikeableTypeMap, imageMap, posMap);
    Alert alert = new Alert(AlertType.INFORMATION);
    if (saveGameSuccess) {
      alert.setTitle("Save Game Success");
      alert.setHeaderText(null);
      alert.setContentText("Game saved successfully!");
      alert.showAndWait();
      stage.close();
    } else {
      alert.setTitle("Save Game Error");
      alert.setHeaderText(null);
      alert.setContentText("Saving game failed :(");
      alert.showAndWait();
      stage.close();
    }
  }

  private boolean submitGame(String gameName,
      Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap,
      List<Shape> strikeables, Map<Shape, NonStrikeableType> nonStrikeableTypeMap,
      Map<Shape, String> imageMap, Map<Shape, List<Double>> posMap) {
    try {
      Map<Shape, Integer> gameObjectIdMap = new HashMap<>();
      writeGameObjects(gameObjectIdMap, strikeables, nonStrikeableTypeMap, imageMap, posMap);
      writePlayer();
      writeVariables();
      writeRules(interactionMap, gameObjectIdMap);
      builderDirector.writeGame(gameName);
      return true;
    } catch (RuntimeException e) {
      LOGGER.error("error when writing game game data field");
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

//    List<Map<String, List<Double>>> advanceRound = new ArrayList<>();
    Map<String, List<Double>> advanceRound = new HashMap<>();
    Map<String, List<Double>> roundCommandOne = new HashMap<>();
    advanceRound.put("AdvanceRoundCommand", new ArrayList<>());
//    advanceRound.add(roundCommandOne);
    Map<String, List<Double>> roundCommandTwo = new HashMap<>();

    String strikePolicy = "DoNothingStrikePolicy";

    Rules rules = new Rules(collisionRules, turnPolicy, roundPolicy, winCondition,
        advanceTurn,
        advanceRound, strikePolicy, "HighestScoreComparator", "VelocityStaticChecker", List.of());

    builderDirector.constructRules(List.of(rules));
  }

  private String matchCommandName(InteractionType type) {
    return switch (type) {
      case RESET -> "LastStaticStateCommand";
      case ADVANCE -> "AdvanceRoundCommand";
      case SCORE -> "AdjustPointsCommand";
      case CHANGE_SPEED -> null;
    };
  }

  private void writeGameObjects(Map<Shape, Integer> collidableIdMap, List<Shape> strikeables,
      Map<Shape, NonStrikeableType> nonStrikeableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap) {
    int gameObjectId = 0;
    List<GameObjectProperties> gameObjectProperties = new ArrayList<>();

    //handling background first
    List<Integer> colorRgb = List.of(0, 0, 0);
    String imgPath = "";
    if (background.getFill() instanceof Color) {
      Color c = (Color) (background.getFill());
      colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
          (int) c.getBlue() * 255);
    } else {
      imgPath = imageMap.get(background);
    }
    List<String> properties = new ArrayList<>();
    properties.add("visible");
    properties.add(nonStrikeableTypeMap.get(background).toString().toLowerCase());
    //double friction = 0.8;
    double staticFriction = 7;
    double kineticFriction = 5;
    double inclineAngle = 0;
    String shapeName = "Rectangle";
    GameObjectProperties gameObjectProperty = new GameObjectProperties(gameObjectId,
        properties, Float.POSITIVE_INFINITY,
        new Position(posMap.get(background).get(0), posMap.get(background).get(1)),
        shapeName, new Dimension(background.getLayoutBounds().getWidth(),
        background.getLayoutBounds().getHeight()), colorRgb, staticFriction, kineticFriction, inclineAngle,
        imgPath, background.getRotate());
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(background, gameObjectId);
    gameObjectId++;

    nonStrikeableTypeMap.remove(background);

    //walls
    Rectangle wall1 = new Rectangle(50, 50, 20, 990);
    colorRgb = List.of(0, 0, 0);
    imgPath = "";
    properties = new ArrayList<>();
    properties.add("visible");
    shapeName = "Rectangle";
    gameObjectProperty = new GameObjectProperties(gameObjectId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 50),
        shapeName, new Dimension(20,
        990), colorRgb, staticFriction, kineticFriction, inclineAngle, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall1, gameObjectId);
    gameObjectId++;

    Rectangle wall2 = new Rectangle(1020, 50, 20, 990);
    gameObjectProperty = new GameObjectProperties(gameObjectId,
        properties, Double.POSITIVE_INFINITY,
        new Position(1020, 50),
        shapeName, new Dimension(20,
        990), colorRgb, staticFriction, kineticFriction, inclineAngle, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall2, gameObjectId);
    gameObjectId++;

    Rectangle wall3 = new Rectangle(50, 50, 990, 20);
    gameObjectProperty = new GameObjectProperties(gameObjectId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 50),
        shapeName, new Dimension(985,
        20), colorRgb, staticFriction, kineticFriction, inclineAngle, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall3, gameObjectId);
    gameObjectId++;

    Rectangle wall4 = new Rectangle(50, 1020, 990, 20);
    gameObjectProperty = new GameObjectProperties(gameObjectId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 1015),
        shapeName, new Dimension(985,
        20), colorRgb, staticFriction, kineticFriction, inclineAngle, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall4, gameObjectId);
    gameObjectId++;

    //noncontrollables
    for (Shape shape : nonStrikeableTypeMap.keySet()) {
      colorRgb = List.of(0, 0, 0);
      imgPath = "";
      if (shape.getFill() instanceof Color) {
        Color c = (Color) (shape.getFill());
        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
            (int) c.getBlue() * 255);
      } else {
        imgPath = imageMap.get(shape);
      }
      properties = new ArrayList<>();
      properties.add("visible");
      properties.add(nonStrikeableTypeMap.get(shape).toString().toLowerCase());
      staticFriction =
          (nonStrikeableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 3.03873
              : 0.0;
      kineticFriction =
          (nonStrikeableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 2.03873
              : 0.0;
      double mass =
          (nonStrikeableTypeMap.get(shape).toString().equalsIgnoreCase("Surface"))
              ? Double.POSITIVE_INFINITY
              : 10.0;
      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
      if (shape instanceof Ellipse) {
        gameObjectProperty = new GameObjectProperties(gameObjectId,
            properties, mass,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, inclineAngle, imgPath, shape.getRotate());
      } else {
        gameObjectProperty = new GameObjectProperties(gameObjectId,
            properties, mass,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, inclineAngle, imgPath, shape.getRotate());
      }

      gameObjectProperties.add(gameObjectProperty);
      collidableIdMap.put(shape, gameObjectId);
      gameObjectId++;
    }

    for (Shape shape : strikeables) {
      colorRgb = List.of(0, 0, 0);
      imgPath = "";
      if (shape.getFill() instanceof Color) {
        Color c = (Color) (shape.getFill());
        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
            (int) c.getBlue() * 255);
      } else {
        imgPath = imageMap.get(shape);
      }
      properties = List.of("collidable", "strikeable", "visible");
      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
      if (shape instanceof Ellipse) {
        gameObjectProperty = new GameObjectProperties(gameObjectId,
            properties, 10,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, inclineAngle, imgPath, shape.getRotate());
      } else {
        gameObjectProperty = new GameObjectProperties(gameObjectId,
            properties, 10,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, inclineAngle, imgPath, shape.getRotate());
      }

      gameObjectProperties.add(gameObjectProperty);
      collidableIdMap.put(shape, gameObjectId);
      gameObjectId++;
    }

    for (Shape shape : collidableIdMap.keySet()) {
      System.out.println("ID in collidablewrite: " + collidableIdMap.get(shape));
    }

    builderDirector.constructCollidableObjects(gameObjectProperties);

  }

}
