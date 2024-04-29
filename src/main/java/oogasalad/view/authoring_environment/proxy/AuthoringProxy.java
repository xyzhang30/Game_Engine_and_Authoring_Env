package oogasalad.view.authoring_environment.proxy;

import static oogasalad.view.Warning.showAlert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Shape;
import oogasalad.model.api.exception.AuthoringException;
import oogasalad.model.api.exception.InCompleteRulesAuthoringException;
import oogasalad.model.api.exception.IncompletePlayerStrikeableAuthoringException;
import oogasalad.model.database.Database;
import oogasalad.view.Warning;
import oogasalad.view.api.enums.CollidableType;
import oogasalad.view.api.exception.MissingInteractionException;
import oogasalad.view.api.exception.MissingNonControllableTypeException;
import oogasalad.view.authoring_environment.util.GameObjectAttributesContainer;
import oogasalad.view.controller.AuthoringController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AuthoringProxy acts as an intermediary between the authoring environment and the authoring
 * controller, keeping track of data such as interactions, game objects, policies, and commands
 * necessary for creating JSON file for configuring a new game.
 *
 * @author Judy He
 */
public class AuthoringProxy {

  private static final Warning WARNING = new Warning();
  private static final Logger LOGGER = LogManager.getLogger(AuthoringProxy.class);
  private final Map<String, String> keyPreferences = new HashMap<>();
  private final Map<String, Map<String, List<Integer>>> conditionsCommands = new HashMap<>();
  private final Map<String, String> policies = new HashMap<>();
  private final Map<List<Integer>, Map<String, List<Integer>>> interactionMap = new HashMap<>();
  private final Map<Shape, GameObjectAttributesContainer> gameObjectMap = new HashMap<>();
  private Map<Integer, Map<CollidableType, List<Integer>>> playersMap
      = new HashMap<>();
  private final Map<String, List<Integer>> multiCommandCheckedIdx = new HashMap<>(); //checkComboBoxId mapped to checkedIndices
  private String gameName;
  private String currentScreenTitle;
  private AuthoringController authoringController;
  private int numPlayers = 1;
  private String gamePermission;

  /**
   * Adds an interaction for a given list of shapes.
   *
   * @param shapes      The list of shapes involved in the interaction.
   * @param interaction The interaction map specifying the interaction details.
   */
  public void addShapeInteraction(List<Integer> shapes,
      Map<String, List<Integer>> interaction) {
    interactionMap.put(shapes, interaction);
  }

  /**
   * Returns a map of player configurations.
   *
   * @return A map containing player configurations.
   */
  public Map<Integer, Map<CollidableType, List<Integer>>> getPlayers() {
    return playersMap;
  }

  /**
   * Adds a policy without parameters.
   *
   * @param type    The type of policy.
   * @param command The command associated with the policy.
   */
  public void addNoParamPolicies(String type, String command) {
    policies.put(type, command);
  }

  /**
   * Adds conditions and commands with parameters.
   *
   * @param type        The type of the condition or command.
   * @param commandName The name of the command.
   * @param params      The parameters for the command.
   */
  public void addConditionsCommandsWithParam(String type, String commandName,
      List<Integer> params) {
    if (!conditionsCommands.containsKey(type)) {
      conditionsCommands.put(type, new HashMap<>());
    }
    conditionsCommands.get(type).put(commandName, params);
  }

  /**
   * Replaces conditions and commands with parameters.
   *
   * @param type        The type of the condition or command.
   * @param commandName The name of the command.
   * @param params      The parameters for the command.
   */
  public void replaceConditionsCommandsWithParam(String type, String commandName,
      List<Integer> params) {
    conditionsCommands.put(type, new HashMap<>());
    conditionsCommands.get(type).put(commandName, params);
  }

  /**
   * Removes conditions and commands with parameters.
   *
   * @param type        The type of the condition or command.
   * @param commandName The name of the command.
   */
  public void removeConditionsCommandsWithParam(String type, String commandName) {
    if (!conditionsCommands.containsKey(type)) {
      return;
    }
    conditionsCommands.get(type).remove(commandName);
  }

  public void addKeyPreference(String keyType, String keyCode) {
    keyPreferences.put(keyType, keyCode);
  }

  public boolean keyAlreadyUsed(String key) {
    for (String keyCode : keyPreferences.values()) {
      if (key.equals(keyCode)) {
        return true;
      }
    }
    return false;
  }

  public boolean keyTypeAlreadySelected(String keyType) {
    return (this.keyPreferences.containsKey(keyType));
  }

  public String getSelectedKey(String keyType) {
    return this.keyPreferences.get(keyType);
  }

  public boolean ruleAlreadySelected(String ruleType) {
    return (this.policies.containsKey(ruleType) || this.conditionsCommands.containsKey(ruleType));
  }


  /**
   * Returns the map of game objects.
   *
   * @return A map of game objects and their attributes.
   */
  public Map<Shape, GameObjectAttributesContainer> getGameObjectMap() {
    return gameObjectMap;
  }

  /**
   * Sets a game object with the specified shape and attributes.
   *
   * @param shape                         The shape representing the game object.
   * @param gameObjectAttributesContainer The attributes of the game object.
   */
  public void setGameObject(Shape shape,
      GameObjectAttributesContainer gameObjectAttributesContainer) {
    this.gameObjectMap.put(shape, gameObjectAttributesContainer);
  }

  /**
   * Completes the authoring process by writing rules, players, variables, and game objects.
   *
   * @throws MissingInteractionException         If there is a missing interaction.
   * @throws MissingNonControllableTypeException If there is a missing non-controllable type.
   */
  public void completeAuthoring(Scene scene)
      throws MissingInteractionException, MissingNonControllableTypeException {
    try {
      authoringController.writeRules(interactionMap, conditionsCommands, policies);
      authoringController.writePlayers(playersMap);
      authoringController.writeVariables();
      authoringController.writeGameObjects(gameObjectMap);
      authoringController.writeKeyPreferences(keyPreferences);
      boolean saveGameSuccess = authoringController.submitGame(gameName);
      if (saveGameSuccess) {
        saveGameToDatabase();
        WARNING.showAlert(scene, AlertType.INFORMATION, "Save Game Success", null,
            "Game successfully saved!");
      } else {
        throw new AuthoringException("Save game failed :(");
      }
    } catch (AuthoringException | InCompleteRulesAuthoringException |
             IncompletePlayerStrikeableAuthoringException e) {
      WARNING.showAlert(scene, AlertType.ERROR, "Authoring Error", null, e.getMessage());
    }
  }

//  private void showSaveGameError(String errorMessage) {
//    Alert alert = new Alert(AlertType.ERROR);
//    alert.setTitle("Save Game Error");
//    alert.setHeaderText(null);
//    alert.setContentText(errorMessage);
//    alert.showAndWait();
//  }

//  private void showSuceessMessage(String message) {
//    Alert alert = new Alert(AlertType.INFORMATION);
//    alert.setTitle("Save Game Success");
//    alert.setHeaderText(null);
//    alert.setContentText(message);
//    alert.showAndWait();
//  }

  /**
   * Updates the authoring screen.
   */
  public void updateScreen() {
    authoringController.updateAuthoringScreen();
  }

  /**
   * Returns the game name.
   *
   * @return The name of the game.
   */
  public String getGameName() {
    return gameName;
  }

  /**
   * Sets the game name.
   *
   * @param gameName The name of the game.
   */
  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  /**
   * Writes the game description into properties file
   *
   * @param gameDescription The description of the game.
   */
  public void saveGameDescription(String gameDescription) {
    // Load existing properties from file
    Properties properties = new Properties();
    try (InputStream inputStream = new FileInputStream(
        "src/main/resources/view/properties/GameDescriptions.properties")) {
      properties.load(inputStream);
    } catch (IOException e) {
      LOGGER.error("Error loading game description properties file: " + e.getMessage());
      throw new AuthoringException("properties file not found");
    }

    properties.setProperty(gameName, gameDescription);

    try (OutputStream outputStream = new FileOutputStream(
        "src/main/resources/view/properties/GameDescriptions.properties")) {
      properties.store(outputStream, "Updated Properties");
    } catch (IOException e) {
      System.err.println("Error adding new properties: " + e.getMessage());
    }
  }

  /**
   * Returns the current screen title.
   *
   * @return The current screen title.
   */
  public String getCurrentScreenTitle() {
    return currentScreenTitle;
  }

  /**
   * Sets the current screen title.
   *
   * @param currentScreenTitle The current screen title.
   */
  public void setCurrentScreenTitle(String currentScreenTitle) {
    this.currentScreenTitle = currentScreenTitle;
  }

  /**
   * Sets the authoring controller.
   *
   * @param authoringController The authoring controller.
   */
  public void setAuthoringController(
      AuthoringController authoringController) {
    this.authoringController = authoringController;
  }

  /**
   * Returns the number of players.
   *
   * @return The number of players.
   */
  public int getNumPlayers() {
    return numPlayers;
  }

  /**
   * Increases the number of players.
   */
  public void increaseNumPlayers() {
    numPlayers++;
  }

  /**
   * Decreases the number of players.
   */
  public void decreaseNumPlayers() {
    numPlayers--;
  }

  /**
   * Returns the current player ID.
   *
   * @return The current player ID.
   */
  public int getCurrentPlayerId() {
    return numPlayers - 1;
  }

  /**
   * Removes an object from all players' game object lists.
   *
   * @param shapeId The ID of the shape to remove.
   */
  public void removeObjectFromPlayersAllLists(Integer shapeId) {
    removeCollidableFromAllPlayers(CollidableType.STRIKEABLE, shapeId);
    removeCollidableFromAllPlayers(CollidableType.CONTROLLABLE, shapeId);
    removeCollidableFromAllPlayers(CollidableType.SCOREABLE, shapeId);
  }

  /**
   * Removes a collidable object from all players.
   *
   * @param collidableType The type of collidable object.
   * @param shapeId        The ID of the shape to remove.
   */
  public void removeCollidableFromAllPlayers(CollidableType collidableType, Integer shapeId) {
    for (Integer player : playersMap.keySet()) {
      removeCollidableFromPlayer(player, collidableType, shapeId);
    }
  }

  /**
   * Removes a collidable object from a specific player.
   *
   * @param playerId       The ID of the player.
   * @param collidableType The type of collidable object.
   * @param shapeId        The ID of the shape to remove.
   */
  public void removeCollidableFromPlayer(int playerId, CollidableType collidableType,
      Integer shapeId) {
    playersMap.get(playerId).get(collidableType).remove(shapeId);
  }

  /**
   * Adds a new player to the game.
   */
  public void addNewPlayer() {
    playersMap.putIfAbsent(getCurrentPlayerId(), new HashMap<>());
    playersMap.get(getCurrentPlayerId()).putIfAbsent(CollidableType.STRIKEABLE, new ArrayList<>());
    playersMap.get(getCurrentPlayerId())
        .putIfAbsent(CollidableType.CONTROLLABLE, new ArrayList<>());
    playersMap.get(getCurrentPlayerId()).putIfAbsent(CollidableType.SCOREABLE, new ArrayList<>());
  }

  /**
   * Removes the most recently added player from the game.
   */
  public void removeMostRecentAddedPlayer() {
    playersMap.remove(getCurrentPlayerId());
  }

  /**
   * Adds a collidable object to a specific player.
   *
   * @param selectedPlayerId   The ID of the player.
   * @param collidableType     The type of collidable object.
   * @param shapeId            The ID of the shape to add.
   * @param isControllable     Whether the object is controllable.
   * @param controllableXSpeed The controllable object's speed on the X axis (if applicable).
   * @param controllableYSpeed The controllable object's speed on the Y axis (if applicable).
   */
  public void addCollidableToPlayer(int selectedPlayerId, CollidableType collidableType,
      Integer shapeId, boolean isControllable, int controllableXSpeed, int controllableYSpeed) {
    System.out.println("players:" + playersMap);
    if (selectedPlayerId >= 0) {
      if (isControllable) {
        playersMap.get(selectedPlayerId)
            .put(collidableType, List.of(shapeId, controllableXSpeed, controllableYSpeed));
      } else if (!playersMap.get(selectedPlayerId).get(collidableType).contains(shapeId)) {
        playersMap.get(selectedPlayerId).get(collidableType).add(shapeId);
      }
    }
  }

  public String getSelectedSingleChoiceCommands(String ruleType) {
    if (policies.containsKey(ruleType)) {
      return policies.get(ruleType);
    } else {
      String cmd = "";
      for (String s : conditionsCommands.get(ruleType).keySet()) {
        cmd = s;
      }
      return cmd;
    }
  }

  public Map<String, List<Integer>> getMultiCommandCheckedIdx() {
    return multiCommandCheckedIdx;
  }

  public void updateMultiCommandCheckedIdx(String key, List<Integer> newIndices) {
    multiCommandCheckedIdx.put(key, newIndices);
  }

  public void setGamePermission(String permission) {
    this.gamePermission = permission;
  }

  public void saveGameToDatabase() {
    String hostPlayer = authoringController.getHostPlayer();
    int numPlayers = playersMap.size();
    Database database = new Database();
    try {
      database.registerGame(gameName, hostPlayer, numPlayers, gamePermission);
    }
    catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "Cannot Register Game", e.getMessage());
    }
  }

  public void setPlayersMap (Map<Integer, Map<CollidableType, List<Integer>>> playersMap){
    this.playersMap = playersMap;
  }

}
