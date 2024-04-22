package oogasalad.view.authoring_environment.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Shape;
import oogasalad.model.api.exception.InCompleteRulesAuthoringException;
import oogasalad.view.api.exception.MissingInteractionException;
import oogasalad.view.api.exception.MissingNonControllableTypeException;
import oogasalad.view.authoring_environment.data.GameObjectAttributesContainer;
import oogasalad.view.authoring_environment.NewAuthoringController;
import oogasalad.view.enums.CollidableType;

/**
 * AuthoringProxy acts as an intermediary between the authoring environment and the authoring
 * controller, keeping track of data such as interactions, game objects, policies, and commands
 * necessary for creating JSON file for configuring a new game.
 *
 * @author Judy He
 */
public class AuthoringProxy {

  private final Map<String, Map<String, List<Double>>> conditionsCommands = new HashMap<>();
  private final Map<String, String> policies = new HashMap<>();
  private final Map<List<Integer>, Map<String, List<Double>>> interactionMap = new HashMap<>();
  private final Map<Shape, GameObjectAttributesContainer> gameObjectMap = new HashMap<>();
  private final Map<Integer, Map<CollidableType, List<Integer>>> playersMap = new HashMap<>();
  private String gameName;
  private String currentScreenTitle;
  private NewAuthoringController authoringController;
  private int numPlayers = 1;

  public void addShapeInteraction(List<Integer> shapes,
      Map<String, List<Double>> interaction) {
    interactionMap.put(shapes, interaction);
  }

  public Map<Integer, Map<CollidableType,List<Integer>>> getPlayers() {
    return playersMap;
  }

  public void addNoParamPolicies(String type, String command) {
    policies.put(type, command);
    System.out.println("ALL POLICIES:" + policies);
  }

  public void addConditionsCommandsWithParam(String type, String commandName, List<Double> params) {
    if (!conditionsCommands.containsKey(type)) {
      conditionsCommands.put(type, new HashMap<>());
    }
    conditionsCommands.get(type).put(commandName, params);
    System.out.println("ALL CONDITIONS:" + conditionsCommands);
  }

  public void replaceConditionsCommandsWithParam(String type, String commandName,
      List<Double> params) {
    conditionsCommands.put(type, new HashMap<>());
    conditionsCommands.get(type).put(commandName, params);
    System.out.println("ALL CONDITIONS:" + conditionsCommands);
  }

  public void removeConditionsCommandsWithParam(String type, String commandName) {
    if (!conditionsCommands.containsKey(type)) {
      return;
    }
    conditionsCommands.get(type).remove(commandName);
    System.out.println("ALL CONDITIONS:" + conditionsCommands);
  }

  public Map<Shape, GameObjectAttributesContainer> getGameObjectMap() {
    return gameObjectMap;
  }

  public void setGameObject(Shape shape,
      GameObjectAttributesContainer gameObjectAttributesContainer) {
    this.gameObjectMap.put(shape, gameObjectAttributesContainer);
  }

  public void completeAuthoring()
      throws MissingInteractionException, MissingNonControllableTypeException {
//    authoringController.endAuthoring(gameName, gameObjectMap, interactionMap, conditionsCommands,
//        policies, playersMap);
    try {
      authoringController.writeRules(conditionsCommands, policies);
      authoringController.writePlayers(playersMap);
      authoringController.writeVariables();
      authoringController.writeGameObjects(gameObjectMap);
      boolean saveGameSuccess = authoringController.submitGame();
      if (saveGameSuccess) {
        showSuceessMessage("Game successfully saved!");
      } else {
        showSaveGameError("Save game failed :(");
      }
    } catch (InCompleteRulesAuthoringException e){
      showSaveGameError(e.getMessage());
    }
  }

  private void showSaveGameError(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Save Game Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

  private void showSuceessMessage(String message){
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Save Game Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void updateScreen() {
    authoringController.updateAuthoringScreen();
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public String getCurrentScreenTitle() {
    return currentScreenTitle;
  }

  public void setCurrentScreenTitle(String currentScreenTitle) {
    this.currentScreenTitle = currentScreenTitle;
  }

  public NewAuthoringController getAuthoringController() {
    return authoringController;
  }

  public void setAuthoringController(
      NewAuthoringController authoringController) {
    this.authoringController = authoringController;
  }

  public Map<List<Integer>, Map<String, List<Double>>> getInteractionMap() {
    return interactionMap;
  }

  public int getNumPlayers() {
    return numPlayers;
  }

  public void increaseNumPlayers() {
    numPlayers++;
  }

  public void decreaseNumPlayers() {
    numPlayers--;
  }

}
