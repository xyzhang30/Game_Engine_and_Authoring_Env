package oogasalad.model.gameengine.gameobject;

/**
 * The Strikeable interface represents objects in the game engine that can be hit during a static
 * state.
 *
 * <p>Objects implementing this interface can have an initial velocity applied through means
 * other than a collision. They also have the capability to be converted into a GameObject.
 *
 * <p> Objects that implement this interface would include a golf ball, shuffle boarding puck, or
 * pinball, as you can apply a velocity to them by striking them with an external force (such as a
 * club, your hand, or spring), that is not represented in the game system.
 *
 * <p>The adapter pattern is used to bridge the gap between general GameObjects and GameObjects
 * that are Strikeable. By holding a reference to the Strikeable, GameObjects have the ability to
 * have an initial velocity applied to them and be treated as Strikeable objects. This allows for a
 * flexible design, where GameObjects can seamlessly add and remove strikeable behaviors.
 *
 * @author Noah Loewy
 */

public interface Strikeable {

  /**
   * Applies a velocity to the strikeable with the provided ID.
   *
   * @param magnitude The magnitude of the new velocity.
   * @param direction The direction of the new velocity with respect to the positive x-axis (in
   *                  radians).
   */

  void applyInitialVelocity(double magnitude, double direction);

  /**
   * Converts the Scoreable object into a GameObject.
   *
   * @return The GameObject representation of the Scoreable object.
   */

  GameObject asGameObject();
}
