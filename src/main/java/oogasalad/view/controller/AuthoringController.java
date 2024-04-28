package oogasalad.view.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import oogasalad.model.api.data.KeyPreferences;
import oogasalad.model.api.data.ParserPlayer;
import oogasalad.model.api.data.PlayerVariables;
import oogasalad.model.api.data.Position;
import oogasalad.model.api.data.Rules;
import oogasalad.model.api.data.Variables;
import oogasalad.model.api.exception.InCompleteRulesAuthoringException;
import oogasalad.model.api.exception.IncompletePlayerStrikeableAuthoringException;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.view.authoring_environment.AuthoringScreen;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;
import oogasalad.view.api.authoring.AuthoringFactory;
import oogasalad.view.authoring_environment.factories.DefaultAuthoringFactory;
import oogasalad.view.authoring_environment.factories.DefaultUIElementFactory;
import oogasalad.view.api.authoring.UIElementFactory;
import oogasalad.view.authoring_environment.proxy.AuthoringProxy;
import oogasalad.view.authoring_environment.proxy.ShapeProxy;
import oogasalad.view.api.enums.AuthoringImplementationType;
import oogasalad.view.api.enums.CollidableType;
import oogasalad.view.api.enums.SupportedLanguage;
import oogasalad.view.api.enums.UITheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to handle transitions between authoring environment scenes and communications with backend
 *
 * @author Judy He, Jordan Haytaian, Doga Ozmen, Alisha Zhang
 */
public class AuthoringController {
  static final Logger LOGGER = LogManager.getLogger(AuthoringController.class);
  private final Stage stage;
  private final AuthoringScreen authoringScreen;
  private final BuilderDirector builderDirector = new BuilderDirector();
  private final ShapeProxy shapeProxy = new ShapeProxy();
  private final AuthoringProxy authoringProxy = new AuthoringProxy();
  private int firstActiveStrikeableId;
  private String hostPlayer;

  public AuthoringController(SupportedLanguage language, UITheme uiTheme, AuthoringImplementationType authoringFactoryType, String hostPlayer) {
    stage = new Stage();
    UIElementFactory uiElementFactory = new DefaultUIElementFactory();
    AuthoringFactory authoringFactory = new DefaultAuthoringFactory(uiElementFactory, language, shapeProxy, authoringProxy);
    this.authoringScreen = new AuthoringScreen(language, authoringFactory, shapeProxy, authoringProxy);
    authoringScreen.getAuthoringProxy().setAuthoringController(this);
    this.hostPlayer = hostPlayer;
  }

  public String getHostPlayer(){
    return hostPlayer;
  }

  public void updateAuthoringScreen() {
    stage.setScene(authoringScreen.getScene());
    stage.show();
  }

  public boolean submitGame(String gameName){
    try {
      builderDirector.writeGame(gameName);
      return true;
    } catch (RuntimeException e) {
      LOGGER.error(e);
      return false;
    }
  }

  public void writeVariables() {
    //player variables always start with 0, 0 for a new game
    Variables variables = new Variables(new GlobalVariables(1, 1), new PlayerVariables(0, 0));
    builderDirector.constructVaraibles(List.of(variables));
  }

  public void writePlayers(Map<Integer, Map<CollidableType, List<Integer>>> playersMap) throws IncompletePlayerStrikeableAuthoringException {
    List<ParserPlayer> players = new ArrayList<>();
    playersMap.forEach((playerId, myGameObjects) -> {
      System.out.println("collidables:"+playersMap.get(playerId).get(CollidableType.STRIKABLE));
      ParserPlayer player;
      try {
        player = new ParserPlayer(playerId,
            playersMap.get(playerId).get(CollidableType.STRIKABLE),
            playersMap.get(playerId).get(CollidableType.SCOREABLE),
            playersMap.get(playerId).get(CollidableType.CONTROLLABLE),0, playersMap.get(playerId).get(CollidableType.STRIKABLE).get(0));
      } catch (IndexOutOfBoundsException e) {
        throw new IncompletePlayerStrikeableAuthoringException("Please assign a strikeable game object to each player.");
      }
      players.add(player);
      if (playerId == 1){
        firstActiveStrikeableId = playersMap.get(playerId).get(CollidableType.STRIKABLE).get(0);
      }
    });
    builderDirector.constructPlayers(players);
  }

  public void writeRules(Map<List<Integer>, Map<String, List<Integer>>> interactions, Map<String, Map<String, List<Integer>>> commandsConditions, Map<String,
      String> policies) throws InCompleteRulesAuthoringException{
    List<CollisionRule> collisions = new ArrayList<>();

    interactions.forEach((gameObjects, commands) -> {
      CollisionRule collisionRule = new CollisionRule(gameObjects.get(0), gameObjects.get(1), commands);
      collisions.add(collisionRule);
    });

    String turnPolicy = policies.get("turnpolicy");
    Map<String, List<Integer>> roundCondition = commandsConditions.get("roundcondition");
    Map<String, List<Integer>> winCondition = commandsConditions.get("wincondition");
    Map<String, List<Integer>> advanceTurn = commandsConditions.get("advanceturn");
    Map<String, List<Integer>> advanceRound = commandsConditions.get("advanceround");
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
      LOGGER.error(e);
      throw new InCompleteRulesAuthoringException("Please make a selection for all rule types");
    }

    if (turnPolicy == null || roundCondition == null || winCondition == null || advanceTurn == null || advanceRound == null || strikePolicy == null || rankComparator == null) {
      LOGGER.error("InCompleteRulesAuthoringException: Please make a selection for all rule types");
      throw new InCompleteRulesAuthoringException("Please make a selection for all rule types");
    }

    Rules rules = new Rules(collisions, turnPolicy, roundCondition, winCondition, advanceTurn, advanceRound, strikePolicy, rankComparator, staticChecker);
    builderDirector.constructRules(List.of(rules));
  }

  public void writeGameObjects(Map<Shape, GameObjectAttributesContainer> gameObjectMap){
    List<GameObjectProperties> gameObjects = new ArrayList<>();
    gameObjectMap.forEach((gameObjectShape, properties) -> {
      List<String> objectProperties = properties.getProperties();
      if (!properties.getProperties().contains("strikeable") || properties.getId() == firstActiveStrikeableId){
        objectProperties.add("visible");
      }
      String shapeName = (gameObjectShape instanceof Ellipse) ? "Circle" : "Rectangle";

      Dimension objDimension = new Dimension(properties.getWidth(), properties.getHeight());

      Position objPosition = new Position(properties.getPosition().x(), properties.getPosition().y());

      GameObjectProperties gameObject = new GameObjectProperties(properties.getId(),
          objectProperties, properties.getMass(), objPosition, shapeName, objDimension,
          null, properties.getsFriction(),
          properties.getkFriction(), 0,
          null, 0, properties.isElasticity(), false, 0);

      gameObjects.add(gameObject);
    });
    builderDirector.constructCollidableObjects(gameObjects);
  }

  public void writeKeyPreferences(Map<String, String> keyPreferences) {
    String angleLeft = keyPreferences.get("angle_left");
    String angleRight = keyPreferences.get("angle_right");
    String powerUp = keyPreferences.get("power_up");
    String powerDown = keyPreferences.get("power_down");
    String controllableLeft = keyPreferences.get("controllable_left");
    String controllableRight = keyPreferences.get("controllable_right");
    String controllableUp = keyPreferences.get("controllable_up");
    String controllableDown = keyPreferences.get("controllable_down");
    String striking = keyPreferences.get("striking");

    KeyPreferences keys = new KeyPreferences(angleLeft, angleRight, powerUp, powerDown, controllableLeft, controllableRight, controllableUp, controllableDown, striking);
    builderDirector.constructKeys(List.of(keys));
  }

}
