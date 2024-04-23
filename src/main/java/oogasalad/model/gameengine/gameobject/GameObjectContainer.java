package oogasalad.model.gameengine.gameobject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

  private final Collection<GameObject> myGameObjects;

  /**
   * Constructs a GameObjectContainer with the specified collection of GameObjects.
   *
   * @param gameObjects A map containing GameObject instances indexed by their unique IDs.
   */

  public GameObjectContainer(Collection<GameObject> gameObjects) {
    myGameObjects = gameObjects;
    myGameObjects.forEach(GameObject::addStaticStateGameObject);
  }

  /**
   * Retrieves the GameObjects.
   *
   * @return The GameObject corresponding to the given ID
   */

  public Collection<GameObject> getGameObjects() {
    return myGameObjects;
  }


}
