package oogasalad.model.gameengine.collidable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import oogasalad.Pair;
import oogasalad.model.api.GameObjectRecord;

public class GameObjectContainer {

  private final Map<Integer, GameObject> myGameObjects;
  private final Stack<List<GameObjectRecord>> gameObjectHistory;
  private final CollisionDetector collisionDetector;

  public GameObjectContainer(Map<Integer, GameObject> collidables) {
    myGameObjects = collidables;
    gameObjectHistory = new Stack<>();
    gameObjectHistory.add(toGameObjectRecords());
    collisionDetector = new CollisionDetector();
  }

  public GameObject getGameObject(int objectId) {
    return myGameObjects.get(objectId);
  }


  public boolean checkStatic() {
    for (GameObject go : myGameObjects.values()) {
      if (go.getVisible() && (go.getVelocityX() != 0 || go.getVelocityY() != 0)) { //should it be
        return false;
      }
    }

    return true;
  }

  public void update(double dt) {
    for (GameObject go : myGameObjects.values()) {
      go.move(dt);
      go.update();
    }
  }

  public List<GameObjectRecord> toGameObjectRecords() {
    List<GameObjectRecord> ret = new ArrayList<>();
    for (GameObject go : myGameObjects.values()) {
      ret.add(go.toGameObjectRecord());
    }
    return ret;
  }

  public GameObjectRecord getCollidableRecord(int id) {
    List<GameObjectRecord> ret = toGameObjectRecords();
    for (GameObjectRecord record : ret) {
      if (record.id() == id) {
        return record;
      }
    }
    return null;
  }


  public void addStaticStateCollidables() {
    gameObjectHistory.push(toGameObjectRecords());
  }

  public void toLastStaticStateCollidables() {
    for (GameObjectRecord record : gameObjectHistory.peek()) {
      callSetFromRecord(record);
    }
  }

  private void callSetFromRecord(GameObjectRecord record) {
    getGameObject(record.id()).setFromRecord(record);
  }

  public Set<Pair> getCollisionPairs() {
    Set<Pair> collisionPairs = new HashSet<>();
    List<GameObjectRecord> records = toGameObjectRecords();

    for (int i = 0; i < records.size(); i++) {
      GameObjectRecord record1 = records.get(i);
      GameObject gameObject1 = myGameObjects.get(record1.id());


      for (int j = i + 1; j < records.size(); j++) {
        GameObjectRecord record2 = records.get(j);
        GameObject gameObject2 = myGameObjects.get(record2.id());

        if (gameObject2.getVisible() && gameObject1.getVisible() && collisionDetector.isColliding(
            gameObject1, gameObject2)) {
          collisionPairs.add(new Pair(record1.id(), record2.id()));
        }
      }
    }
    return collisionPairs;
  }


  public void makeStatic() {
    for (GameObject c : myGameObjects.values()) {
      c.stop();
    }
  }

  public void setVisible(int controllableId) {
    getGameObject(controllableId).setVisible(true);
  }
}
