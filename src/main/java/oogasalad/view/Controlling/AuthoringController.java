package oogasalad.view.Controlling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Builder;
import oogasalad.model.api.data.CollidableObject;
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
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Jordan Haytaian, Doga Ozmen
 */
public class AuthoringController {

  private Stage stage;
  private BuilderDirector builderDirector = new BuilderDirector();
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
        this, new StackPane(), new HashMap<>(), map, new ArrayList<>());
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
      Map<Shape, NonControllableType> nonControllableMap, List<Shape> controllableList) {
    switch (imageType) {
      case BACKGROUND -> {
        ControllableElementSelectionScreen controllableElementSelectionScreen =
            new ControllableElementSelectionScreen(this, authoringBox, posMap, nonControllableMap,
                controllableList);
        System.out.println("finished background, getting controllable");
        stage.setScene(controllableElementSelectionScreen.getScene());
      }
      case CONTROLLABLE_ELEMENT -> {
        NonControllableElementSelection nonControllableElementSelection =
            new NonControllableElementSelection(this, authoringBox, posMap, nonControllableMap,
                controllableList);
        System.out.println("finished controllable, getting noncontrollable");
        stage.setScene(nonControllableElementSelection.getScene());
      }
      case NONCONTROLLABLE_ELEMENT -> {
        InteractionSelectionScreen interactionSelectionScreen
            = new InteractionSelectionScreen(this, authoringBox, posMap, nonControllableMap,
            controllableList);
        System.out.println("finished noncontrollable, getting interaction");
        stage.setScene(interactionSelectionScreen.getScene());
      }
    }
  }

  public void endAuthoring(String gameName, Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap, List<Shape> controllables, Map<Shape, NonControllableType> nonControllableTypeMap) {
    System.out.println("writing to json!");
    boolean saveGameSuccess = submitGame(gameName, interactionMap, controllables, nonControllableTypeMap);
    Alert alert = new Alert(AlertType.INFORMATION);
    if (saveGameSuccess) {
      alert.setTitle("Save Game Success");
      alert.setHeaderText(null);
      alert.setContentText("Game saved successfully!");
      alert.showAndWait();
    } else {
      alert.setTitle("Save Game Error");
      alert.setHeaderText(null);
      alert.setContentText("Saving game failed :(");
      alert.showAndWait();
      stage.close();
    }
  }

  private boolean submitGame(String gameName, Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap, List<Shape> controllables, Map<Shape, NonControllableType> nonControllableTypeMap) {
    try {
      Map<Shape, Integer> collidableIdMap = new HashMap<>();
      writeCollidables(collidableIdMap, controllables, nonControllableTypeMap);
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
    ParserPlayer player = new ParserPlayer(1, 1);

    builderDirector.constructPlayers(List.of(player));
  }

  private void writeRules(Map<List<Shape>, Map<InteractionType, List<Double>>> interactionMap, Map<Shape, Integer> collidableIdMap) {
    //COLLISION RULE
    List<CollisionRule> collisionRules = new ArrayList<>();
    for (List<Shape> pair : interactionMap.keySet()){
      Map<String, List<Double>> collisionCommand = new HashMap<>();
      for (InteractionType type : interactionMap.get(pair).keySet()){
        if (type != InteractionType.CHANGE_SPEED){
          collisionCommand.put(matchCommandName(type), interactionMap.get(pair).get(type));
        }
      }
      for (Shape shape : collidableIdMap.keySet()){
        System.out.println("ID: "+ collidableIdMap.get(shape));
      }
      System.out.println("FIRST ID: "+collidableIdMap.get(pair.get(0)));
      CollisionRule collisionRule = new CollisionRule(collidableIdMap.get(pair.get(0)), collidableIdMap.get(pair.get(1)), List.of(collisionCommand));

      collisionRules.add(collisionRule);
    }

    String turnPolicy = "StandardTurnPolicy";

    Map<String, List<Double>> roundPolicy = new HashMap<>();
    roundPolicy.put("AllPlayersCompletedRoundCondition", new ArrayList<>());

    Map<String, List<Double>> winCondition = new HashMap<>();
    winCondition.put("NRoundsCompletedCondition", List.of((double)1));

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

    Rules rules = new Rules(collisionRules, turnPolicy, roundPolicy, winCondition, advanceTurn, advanceRound);

    builderDirector.constructRules(List.of(rules));
  }

  private String matchCommandName(InteractionType type) {
    return switch (type){
      case RESET -> "UndoTurnCommand";
      case ADVANCE -> "AdvanceTurnCommand";
      case SCORE -> "AdjustPointsCommand";
      case CHANGE_SPEED -> null;
    };
  }

  private void writeCollidables(Map<Shape, Integer> collidableIdMap, List<Shape> controllables, Map<Shape, NonControllableType> nonControllableTypeMap) {
    int collidableId = 0;
    List<CollidableObject> collidableObjects = new ArrayList<>();

    System.out.println("controllables size:"+controllables.size());

    //controllables
    for (Shape shape : controllables) {
      List<Integer> colorRgb = List.of(0,0,0);
      String imgPath = "";
      if (shape.getFill() instanceof Color){
        Color c = (Color) (shape.getFill());
        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255, (int) c.getBlue() * 255);
      }
//      else {
////        imgPath = shape.getFill();
//      }


      List<String> properties = List.of("movable", "collidable", "controllable");
      String shapeName = (shape instanceof Circle) ? "Circle" : "Rectangle";
      CollidableObject collidableObject = new CollidableObject(collidableId,
          properties, 10,
          new Position(shape.getLayoutX(), shape.getLayoutY()), shapeName,
          new Dimension(shape.getLayoutBounds().getWidth(), shape.getLayoutBounds().getHeight()),
          colorRgb, 0.0, imgPath);
      collidableObjects.add(collidableObject);
      collidableIdMap.put(shape,collidableId);
      collidableId ++;
    }

    System.out.println("noncontrollables size:"+nonControllableTypeMap.size());

    //nonControllables
    for (Shape shape : nonControllableTypeMap.keySet()) {
      List<Integer> colorRgb = List.of(0,0,0);
      String imgPath = "";
      if (shape.getFill() instanceof Color){
        Color c = (Color) (shape.getFill());
        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255, (int) c.getBlue() * 255);
      }
      List<String> properties = new ArrayList<>();
      properties.add("collidable");
      properties.add(nonControllableTypeMap.get(shape).toString());
      double friction =
          (nonControllableTypeMap.get(shape).toString().equals("Surface")) ? 0.5 : 0.0;
      String shapeName = (shape instanceof Circle) ? "Circle" : "Rectangle";
      CollidableObject collidableObject = new CollidableObject(collidableId,
          properties, 10,
          new Position(shape.getLayoutX(), shape.getLayoutY()), shapeName,
          new Dimension(shape.getLayoutBounds().getWidth(), shape.getLayoutBounds().getHeight()),
          colorRgb, friction, imgPath);
      collidableObjects.add(collidableObject);
      collidableIdMap.put(shape,collidableId);
      collidableId ++;
    }

    for (Shape shape : collidableIdMap.keySet()){
      System.out.println("ID in collidablewrite: "+ collidableIdMap.get(shape));
    }

    builderDirector.constructCollidableObjects(collidableObjects);

  }

}
