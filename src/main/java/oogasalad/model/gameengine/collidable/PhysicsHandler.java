package oogasalad.model.gameengine.collidable;

public interface PhysicsHandler {
  void handleCollision(CollidableContainer container, double dt);
}
