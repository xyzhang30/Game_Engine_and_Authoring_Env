package oogasalad.view.Controlling;

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
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.CollisionRule;
import oogasalad.model.api.data.Dimension;
import oogasalad.model.api.data.GlobalVariables;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.PlayerVariables;
import oogasalad.model.api.data.Position;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.view.AuthoringScreens.BackgroundSelectionScreen;
import oogasalad.view.AuthoringScreens.ControllableElementSelectionScreen;
import oogasalad.view.AuthoringScreens.ImageType;
import oogasalad.view.AuthoringScreens.InteractionSelectionScreen;
import oogasalad.view.AuthoringScreens.InteractionType;
import oogasalad.view.AuthoringScreens.NonControllableElementSelection;
import oogasalad.view.AuthoringScreens.NonControllableType;
import oogasalad.view.savegame.BuilderDirector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen, Alisha Zhang
 */
public class AuthoringController {

  private Stage stage;
  private Rectangle background;
  private BuilderDirector builderDirector = new BuilderDirector();
  private static final Logger LOGGER = LogManager.getLogger(AuthoringController.class);


//  private List<Shape> controllables;
//  private Map<Shape, NonControllableType> nonControllableTypeMap;

  public AuthoringController() {
    stage = new Stage();
//    controllables = new ArrayList<>();
//    nonControllableTypeMap = new HashMap<>();
  }

  public void startAuthoring() {
    Map<Shape, NonControllableType> map = new HashMap<>();
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
      Map<Shape, NonControllableType> nonControllableMap, List<Shape> controllableList,
      Map<Shape, String> imageMap) {
    switch (imageType) {
      case BACKGROUND -> {
        ControllableElementSelectionScreen controllableElementSelectionScreen =
            new ControllableElementSelectionScreen(this, authoringBox, posMap, nonControllableMap,
                controllableList, imageMap);
        // System.out.println("finished background, getting controllable");
        stage.setScene(controllableElementSelectionScreen.getScene());
      }
      case CONTROLLABLE_ELEMENT -> {
        NonControllableElementSelection nonControllableElementSelection =
            new NonControllableElementSelection(this, authoringBox, posMap, nonControllableMap,
                controllableList, imageMap);
        //System.out.println("finished controllable, getting noncontrollable");
        stage.setScene(nonControllableElementSelection.getScene());
      }
      case NONCONTROLLABLE_ELEMENT -> {
        InteractionSelectionScreen interactionSelectionScreen
            = new InteractionSelectionScreen(this, authoringBox, posMap, nonControllableMap,
            controllableList, imageMap);
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
      List<Shape> controllables, Map<Shape, NonControllableType> nonControllableTypeMap,
      Map<Shape, String> imageMap, Map<Shape, List<Double>> posMap) {
    boolean saveGameSuccess = submitGame(gameName, interactionMap, controllables,
        nonControllableTypeMap, imageMap, posMap);
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
      List<Shape> controllables, Map<Shape, NonControllableType> nonControllableTypeMap,
      Map<Shape, String> imageMap, Map<Shape, List<Double>> posMap) {
    try {
      Map<Shape, Integer> collidableIdMap = new HashMap<>();
      writeCollidables(collidableIdMap, controllables, nonControllableTypeMap, imageMap, posMap);
      writePlayer();
      writeVariables();
      writeRules(interactionMap, collidableIdMap);
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

    List<Map<String, List<Double>>> advanceTurn = new ArrayList<>();
    Map<String, List<Double>> turnCommandOne = new HashMap<>();
    turnCommandOne.put("AdvanceTurnCommand", new ArrayList<>());
    advanceTurn.add(turnCommandOne);
    Map<String, List<Double>> turnCommandTwo = new HashMap<>();
    turnCommandTwo.put("AdjustActivePointsCommand", List.of(1.0));
    advanceTurn.add(turnCommandTwo);

    List<Map<String, List<Double>>> advanceRound = new ArrayList<>();
    Map<String, List<Double>> roundCommandOne = new HashMap<>();
    roundCommandOne.put("AdvanceRoundCommand", new ArrayList<>());
    advanceRound.add(roundCommandOne);
    Map<String, List<Double>> roundCommandTwo = new HashMap<>();
    roundCommandTwo.put("AdjustActivePointsCommand", List.of(1.0));
    advanceRound.add(roundCommandTwo);

    String strikePolicy = "DoNothingStrikePolicy";

    Rules rules = new Rules(collisionRules, turnPolicy, roundPolicy, winCondition, advanceTurn,
        advanceRound, strikePolicy);

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
      Map<Shape, NonControllableType> nonControllableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap) {
    int collidableId = 0;
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
    properties.add("collidable");
    properties.add("visible");
    properties.add(nonControllableTypeMap.get(background).toString().toLowerCase());
    //double friction = 0.8;
    double staticFriction = 7;
    double kineticFriction = 5;
    String shapeName = "Rectangle";
    GameObjectProperties gameObjectProperty = new GameObjectProperties(collidableId,
        properties, Float.POSITIVE_INFINITY,
        new Position(posMap.get(background).get(0), posMap.get(background).get(1)),
        shapeName, new Dimension(background.getLayoutBounds().getWidth(),
        background.getLayoutBounds().getHeight()), colorRgb, staticFriction, kineticFriction,
        imgPath, background.getRotate());
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(background, collidableId);
    collidableId++;

    nonControllableTypeMap.remove(background);

    //walls
    Rectangle wall1 = new Rectangle(50, 50, 20, 990);
    colorRgb = List.of(0, 0, 0);
    imgPath = "";
    properties = new ArrayList<>();
    properties.add("collidable");
    properties.add("visible");
    properties.add("movable");
    shapeName = "Rectangle";
    gameObjectProperty = new GameObjectProperties(collidableId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 50),
        shapeName, new Dimension(20,
        990), colorRgb, staticFriction, kineticFriction, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall1, collidableId);
    collidableId++;

    Rectangle wall2 = new Rectangle(1020, 50, 20, 990);
    gameObjectProperty = new GameObjectProperties(collidableId,
        properties, Double.POSITIVE_INFINITY,
        new Position(1020, 50),
        shapeName, new Dimension(20,
        990), colorRgb, staticFriction, kineticFriction, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall2, collidableId);
    collidableId++;

    Rectangle wall3 = new Rectangle(50, 50, 990, 20);
    gameObjectProperty = new GameObjectProperties(collidableId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 50),
        shapeName, new Dimension(985,
        20), colorRgb, staticFriction, kineticFriction, imgPath, 0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall3, collidableId);
    collidableId++;

    Rectangle wall4 = new Rectangle(50, 1020, 990, 20);
    gameObjectProperty = new GameObjectProperties(collidableId,
        properties, Double.POSITIVE_INFINITY,
        new Position(50, 1015),
        shapeName, new Dimension(985,
        20), colorRgb, staticFriction, kineticFriction, imgPath,0);
    gameObjectProperties.add(gameObjectProperty);
    collidableIdMap.put(wall4, collidableId);
    collidableId++;

    //noncontrollables
    for (Shape shape : nonControllableTypeMap.keySet()) {
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
      properties.add("collidable");
      properties.add("visible");
      properties.add(nonControllableTypeMap.get(shape).toString().toLowerCase());
      staticFriction =
          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 3.03873
              : 0.0;
      kineticFriction =
          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 2.03873
              : 0.0;
      double mass =
          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("Surface"))
              ? Double.POSITIVE_INFINITY
              : 10.0;
      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
      if (shape instanceof Ellipse) {
        gameObjectProperty = new GameObjectProperties(collidableId,
            properties, mass,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, imgPath, shape.getRotate());
      } else {
        gameObjectProperty = new GameObjectProperties(collidableId,
            properties, mass,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, imgPath, shape.getRotate());
      }

      gameObjectProperties.add(gameObjectProperty);
      collidableIdMap.put(shape, collidableId);
      collidableId++;
    }

    //controllables
    for (Shape shape : controllables) {
      colorRgb = List.of(0, 0, 0);
      imgPath = "";
      if (shape.getFill() instanceof Color) {
        Color c = (Color) (shape.getFill());
        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
            (int) c.getBlue() * 255);
      } else {
        imgPath = imageMap.get(shape);
      }
      properties = List.of("movable", "collidable", "controllable", "visible");
      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
      if (shape instanceof Ellipse) {
        gameObjectProperty = new GameObjectProperties(collidableId,
            properties, 10,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, imgPath, shape.getRotate());
      } else {
        gameObjectProperty = new GameObjectProperties(collidableId,
            properties, 10,
            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
            colorRgb, 0.0, 0.0, imgPath, shape.getRotate());
      }

      gameObjectProperties.add(gameObjectProperty);
      collidableIdMap.put(shape, collidableId);
      collidableId++;
    }

    for (Shape shape : collidableIdMap.keySet()) {
      System.out.println("ID in collidablewrite: " + collidableIdMap.get(shape));
    }

    builderDirector.constructCollidableObjects(gameObjectProperties);

  }

}
