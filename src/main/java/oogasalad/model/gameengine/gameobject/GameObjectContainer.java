package oogasalad.model.gameengine.gameobject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.Pair;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.checkstatic.StaticChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The GameObjectContainer class manages a collection of GameObjects within the game environment,
 * and provides efficient access to GameObjects to be accessed/manipulated via their unique IDs,
 * while also encapsulating the map from object IDs to GameObject instances, encapsulating the
 * implementation of the GameObjects.
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

  /**
   * Checks if all visible GameObjects in the container are static (not moving).
   *
   * @return True if all visible GameObjects are static, otherwise false.
   */

  public boolean checkStatic(List<StaticChecker> checkers) {
    for(StaticChecker checker : checkers) {
      boolean flag = true;
      for (GameObject go : myGameObjects.values()) {
        if (!checker.isStatic(go.toGameObjectRecord())){
          flag = false;
        }
      }
      if(flag) {
        return true;
      }
    }
    return false;
  }

  /**
   * Converts all GameObjects in the container to a list of immutable GameObjectRecords.
   *
   * @return A list of GameObjectRecords representing the current state of all GameObjects.
   */

  public List<GameObjectRecord> toGameObjectRecords() {
    List<GameObjectRecord> ret = new ArrayList<>();
    for (GameObject go : myGameObjects.values()) {
      ret.add(go.toGameObjectRecord());
    }
    return ret;
  }

  /**
   * Retrieves the immutable GameObjectRecord associated with the specified object ID.
   *
   * @param id The unique identifier of the GameObjectRecord to retrieve.
   * @return The GameObjectRecord corresponding to the given ID.
   */

  public GameObjectRecord getGameObjectRecord(int id) {
    List<GameObjectRecord> ret = toGameObjectRecords();
    for (GameObjectRecord record : ret) {
      if (record.id() == id) {
        return record;
      }
    }
    LOGGER.warn("No GameObject found for id " + id);
    return null;
  }


  /**
   * Restores each GameObject to its previous static state
   */

  public void toLastStaticStateGameObjects() {
    for (GameObject go : myGameObjects.values()) {
      go.toLastStaticStateGameObjects();
    }
  }


}
