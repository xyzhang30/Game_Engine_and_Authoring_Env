package oogasalad.view.authoring_environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.shape.Ellipse;
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
import oogasalad.model.api.exception.InCompleteRulesAuthoringException;
import oogasalad.view.authoring_environment.data.GameObjectAttributesContainer;
import oogasalad.view.controller.BuilderDirector;
import oogasalad.view.enums.CollidableType;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Judy He, Jordan Haytaian, Doga Ozmen, Alisha Zhang
 */
public class NewAuthoringController {

  private final Stage stage;
  private final AuthoringScreen authoringScreen = new AuthoringScreen();
  private final BuilderDirector builderDirector = new BuilderDirector();

  public NewAuthoringController() {
    stage = new Stage();
    authoringScreen.getAuthoringProxy().setAuthoringController(this);
  }

  public void updateAuthoringScreen() {
    stage.setScene(authoringScreen.getScene());
    stage.show();
  }

  public boolean submitGame(){
    try {
      builderDirector.writeGame("testNewAuthoringEnv");
      return true;
    } catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }
  }

  public void writeVariables() {
    //player variables always start with 0, 0 for a new game
    Variables variables = new Variables(new GlobalVariables(0, 0), new PlayerVariables(0, 0));
    builderDirector.constructVaraibles(List.of(variables));
  }

  public void writePlayers(Map<Integer, Map<CollidableType, List<Integer>>> playersMap) {
    List<ParserPlayer> players = new ArrayList<>();
    playersMap.forEach((playerId, myGameObjects) -> {
      ParserPlayer player = new ParserPlayer(playerId, playersMap.get(playerId).get(CollidableType.STRIKABLE), playersMap.get(playerId).get(CollidableType.CONTROLLABLE));
      players.add(player);
    });

    builderDirector.constructPlayers(players);
  }

  public void writeRules(Map<String, Map<String, List<Double>>> commandsConditions, Map<String, String> policies) throws InCompleteRulesAuthoringException{
    List<CollisionRule> collisions = new ArrayList<>();
    //TODO: implement collisions
    String turnPolicy = policies.get("turnpolicy");
    Map<String, List<Double>> roundPolicy = commandsConditions.get("roundpolicy");
    Map<String, List<Double>> winCondition = commandsConditions.get("wincondition");
    Map<String, List<Double>> advanceTurn = commandsConditions.get("advanceturn");
    Map<String, List<Double>> advanceRound = commandsConditions.get("advanceround");
    String strikePolicy = policies.get("strikepolicy");
    String rankComparator = policies.get("rankcomparator");
    Map<String, List<Integer>> staticChecker = new HashMap<>();
    try {
      commandsConditions.get("staticchecker").forEach((commandName, params) -> {
        staticChecker.put(commandName, new ArrayList<>());
        params.forEach(argumentDouble -> {
          staticChecker.get(commandName).add((int)Math.round(argumentDouble));
        });
      });
    } catch (NullPointerException e){
      throw new InCompleteRulesAuthoringException("Please make a selection for all rule types");
    }

    if (turnPolicy == null || roundPolicy == null || winCondition == null || advanceTurn == null || advanceRound == null || strikePolicy == null || rankComparator == null) {
      throw new InCompleteRulesAuthoringException("Please make a selection for all rule types");
    }

    Rules rules = new Rules(collisions, turnPolicy, roundPolicy, winCondition, advanceTurn, advanceRound, strikePolicy, rankComparator, staticChecker);
    builderDirector.constructRules(List.of(rules));
  }

  public void writeGameObjects(Map<Shape, GameObjectAttributesContainer> gameObjectMap){
    List<GameObjectProperties> gameObjects = new ArrayList<>();
    gameObjectMap.forEach((gameObjectShape, properties) -> {
      List<String> objectProperties = properties.getProperties();
      objectProperties.add("visible");

      String shapeName = (gameObjectShape instanceof Ellipse) ? "Circle" : "Rectangle";

      Dimension objDimension = new Dimension(properties.getWidth(), properties.getHeight());

      Position objPosition = new Position(properties.getPosition().x(), properties.getPosition().y());

      GameObjectProperties gameObject = new GameObjectProperties(properties.getId(),
          objectProperties, properties.getMass(), objPosition, shapeName, objDimension,
          properties.getColor(), properties.getsFriction(), properties.getkFriction(), 0,
          properties.getImagePath(), 0, false, false);

      gameObjects.add(gameObject);
    });
    builderDirector.constructCollidableObjects(gameObjects);
  }

}
