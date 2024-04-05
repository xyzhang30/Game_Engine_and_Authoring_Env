package oogasalad.model.gameengine.physicsengine;

import oogasalad.model.gameengine.collidable.Collidable;

public class PhysicsObject {
  private Collidable collidable;
  private Vector2D velocity;
  private Vector2D acceleration;
  private double mass;


  public PhysicsObject(Collidable collidable, double mass) {
    this.collidable = collidable;
    this.mass = mass;
    this.velocity = new Vector2D(0, 0);
    this.acceleration = new Vector2D(0, 0);
  }

  // Methods to apply forces, update velocity and position, etc.
  public void applyForce(Vector2D force) {
    Vector2D newAccel = force.multiply(1 / mass);
    acceleration = acceleration.add(newAccel);
  }

  // Call this method every frame to update the physics state
  public void update(double deltaTime) {
    velocity = velocity.add(acceleration.multiply(deltaTime));
    Vector2D displacement = velocity.multiply(deltaTime);
    collidable.move(deltaTime); // Update Collidable's position
    acceleration = new Vector2D(0, 0); // Reset acceleration after each update
  }

  // Getter and setter methods...
}

