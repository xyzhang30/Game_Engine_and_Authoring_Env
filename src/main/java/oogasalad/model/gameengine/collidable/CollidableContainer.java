package oogasalad.model.gameengine.collidable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.HashSet;
import java.util.Set;
import oogasalad.Pair;
import oogasalad.model.api.CollidableRecord;

public class CollidableContainer {

  private final Map<Integer, Collidable> myCollidables;
  private final Stack<List<CollidableRecord>> collidableHistory;
  private final CollisionDetector collisionDetector;

  public CollidableContainer(Map<Integer, Collidable> collidables) {
    myCollidables = collidables;
    collidableHistory = new Stack<>();
    collidableHistory.add(getCollidableRecords());
    collisionDetector = new CollisionDetector();
  }

  public Collidable getCollidable(int objectId) {
    return myCollidables.get(objectId);
  }


  public boolean checkStatic() {
    for (Collidable c : myCollidables.values()) {
      if (c.getVelocityX() != 0 || c.getVelocityY() != 0) { //should it be getting current or
        return false;
      }
    }
    return true;
  }

  public void update(double dt) {
    for (Collidable c : myCollidables.values()) {
      c.move(dt);
      c.update();
    }
  }

  public List<CollidableRecord> getCollidableRecords() {
    List<CollidableRecord> ret = new ArrayList<>();
    for (Collidable collidable : myCollidables.values()) {
      ret.add(new CollidableRecord(collidable.getId(), collidable.getMass(), collidable.getX(),
          collidable.getY(), collidable.getVelocityX(), collidable.getVelocityY(),
          collidable.getVisible()));
    }
    return ret;
  }

  public void addStaticStateCollidables() {
    collidableHistory.push(getCollidableRecords());
  }

  public void toLastStaticStateCollidables() {
    for (CollidableRecord record : collidableHistory.peek()) {
      getCollidable(record.id()).setFromRecord(record);
    }

  }

  public Set<Pair> getCollisionPairs() {
    Set<Pair> collisionPairs = new HashSet<>();
    List<CollidableRecord> records = getCollidableRecords();

    for (int i = 0; i < records.size(); i++) {
      CollidableRecord record1 = records.get(i);
      Collidable collidable1 = myCollidables.get(record1.id());

      for (int j = i + 1; j < records.size(); j++) {
        CollidableRecord record2 = records.get(j);
        Collidable collidable2 = myCollidables.get(record2.id());

        if (collisionDetector.isColliding(collidable1, collidable2)) {
          collisionPairs.add(new Pair(record1.id(), record2.id()));
        }
      }
    }
    return collisionPairs;
  }





}
