package oogasalad.model.gameengine.physicsengine;

import oogasalad.model.gameengine.collidable.Collidable;


public class PhysicsObject {
  private Collidable collidable; // The associated Collidable object
  private Vector2D velocity;
  private Vector2D acceleration;
  private Vector2D forces;
  private double mass;
  private boolean isStatic; // Indicates if the object is static or dynamic

  // Constructor
  public PhysicsObject(Collidable collidable, double mass, boolean isStatic) {
    this.collidable = collidable;
    this.mass = mass;
    this.isStatic = isStatic;
    this.velocity = new Vector2D(0, 0);
    this.acceleration = new Vector2D(0, 0);
    this.forces = new Vector2D(0, 0);
  }

  // Apply a force to this object
  public void applyForce(Vector2D force) {
    if (!isStatic) {
      this.forces = this.forces.add(force);
    }
  }

  // Update the physics state of this object
  public void update(double deltaTime) {
    if (!isStatic) {
      // F = m*a => a = F/m
      this.acceleration = this.forces.multiply(1 / mass);
      // v = v0 + a*t
      this.velocity = this.velocity.add(this.acceleration.multiply(deltaTime));
      // s = s0 + v*t
      double newX = collidable.getX() + velocity.getX() * deltaTime;
      double newY = collidable.getY() + velocity.getY() * deltaTime;

      // Update the Collidable's position
      collidable.move(deltaTime); // Assuming move method updates position based on current velocity

      // Reset forces for the next update
      this.forces = new Vector2D(0, 0);
    }
  }

  // Getter methods
  public Vector2D getVelocity() {
    return velocity;
  }

  public double getMass() {
    return mass;
  }

  // Collision response can be customized here, possibly as a callback to PhysicsEngine
  public void onCollision(PhysicsObject other, double deltaTime) {
    // Implement collision response based on the types and properties of collidables
  }

  // Method to set the velocity directly (e.g., for initial velocity or collision responses)
  public void setVelocity(Vector2D velocity) {
    if (!isStatic) {
      this.velocity = velocity;
    }
  }

  // Utility methods if needed
  public boolean isStatic() {
    return isStatic;
  }

  public Collidable getCollidable() {
    return collidable;
  }

  // Update or other utility methods can be added here
}

