package oogasalad.model.gameengine.gameobject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.Pair;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.checkstatic.StaticChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The GameObjectContainer class manages a collection of GameObjects within the game environment,
 * and provides efficient access to GameObjects to be accessed/manipulated via their unique IDs,
 * while also encapsulating the map from object IDs to GameObject instances.
 *
 * @author Noah Loewy
 */


public class GameObjectContainer {

  private static final Logger LOGGER = LogManager.getLogger(GameObjectContainer.class);
  private final Map<Integer, GameObject> myGameObjects;

  /**
   * Constructs a GameObjectContainer with the specified collection of GameObjects.
   *
   * @param gameObjects A map containing GameObject instances indexed by their unique IDs.
   */

  public GameObjectContainer(Map<Integer, GameObject> gameObjects) {
    myGameObjects = gameObjects;
    getGameObjects().forEach(GameObject::addStaticStateGameObject);
  }

  /**
   * Retrieves the GameObject associated with the specified ID.
   *
   * @return The GameObject corresponding to the given ID
   */

  public List<GameObject> getGameObjects() {
    return myGameObjects.values().stream().toList();
  }

  public GameObject getGameObject(int objectId) {
    if (!myGameObjects.containsKey(objectId)) {
      LOGGER.warn("Game Object " + objectId + " not found");
    }
    return myGameObjects.get(objectId);
  }

}
