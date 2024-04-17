package oogasalad.model.gameengine.gameobject;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The GameObject class represents an object within the game environment that interacts with
 * physics and game mechanics.
 *
 * <p>Instances of GameObject have attributes such as mass, position, velocity, visibility, and
 * dimensions. They can also be associated with Strikeable and Scoreable behaviors for gameplay
 * interactions.
 *
 * <p>The class provides methods for handling the movement, velocity, visibility, and collision
 * behavior of GameObjects, as well as conversion to GameObjectRecords for when immutable access is
 * preferred.
 *
 * <p>Most setter methods are protected, so that they can only be updated by classes/interfaces
 * within the gameobject package, namely the PhysicsHandler and Strikeable (and their concrete
 * implementations), as well as the container for the GameObjects (which is used to encapsulate
 * the map from ids to GameObjects).
 *
 * @author Noah Loewy
 */


public class GameObject {

  private final double myMass;
  private final int myId;
  private final double myWidth;
  private final double myHeight;
  private final String myShape;
  private final double myStaticMu;
  private final double myKineticMu;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private double myNextX;
  private double myNextY;
  private double myNextVelocityX;
  private double myNextVelocityY;
  private boolean myVisible;
  private Strikeable strikeable;
  private Scoreable scoreable;

  /**
   * Initiates the GameObject
   *
   * @param id,        the unique id
   * @param mass,      the mass (in grams)
   * @param x,         the x position (in meters)
   * @param y,         the y position (in meters)
   * @param visible,   the visibility state
   * @param staticMu,  the static coefficient of friction
   * @param kineticMu, the kinetic coefficient of friction
   * @param width,     the width of the game object (in meters)
   * @param height,    the height of the game object (in meters)
   * @param shape,     the shape of the game object (string representation)
   */

  public GameObject(int id, double mass, double x, double y,
      boolean visible, double staticMu, double kineticMu, double width, double height,
      String shape) {
    myId = id;
    myMass = mass;
    myX = x;
    myY = y;
    myVelocityX = 0.0;
    myVelocityY = 0.0;
    myNextX = x;
    myNextY = y;
    myVisible = visible;
    myWidth = width;
    myHeight = height;
    myShape = shape;
    myStaticMu = staticMu;
    myKineticMu = kineticMu;
  }

  /**
   * Associates a Strikeable behavior with the GameObject.
   *
   * @param strikeable The Strikeable behavior to be associated with the GameObject.
   */
  public void addStrikeable(Strikeable strikeable) {
    this.strikeable = strikeable;
  }

  /**
   * Associates a Scoreable behavior with the GameObject.
   *
   * @param scoreable The Scoreable behavior to be associated with the GameObject.
   */
  public void addScoreable(Scoreable scoreable) {
    this.scoreable = scoreable;
  }

  /**
   * Retrieves the Strikeable behavior associated with the GameObject, if any.
   *
   * @return An Optional containing the Strikeable behavior associated with the GameObject, or an
   * empty Optional if no Strikeable behavior is associated.
   */
  public Optional<Strikeable> getStrikeable() {
    return Optional.ofNullable(strikeable);
  }

  /**
   * Retrieves the Scoreable behavior associated with the GameObject, if any.
   *
   * @return An Optional containing the Scoreable behavior associated with the GameObject, or an
   * empty Optional if no Scoreable behavior is associated.
   */
  public Optional<Scoreable> getScoreable() {
    return Optional.ofNullable(scoreable);
  }

  /**
   * Serializes the GameObject into an immutable GameObjectRecord
   *
   * @return GameObjectRecord, an immutable representation of the GameObject
   */

  public GameObjectRecord toGameObjectRecord() {
    return new GameObjectRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible,
        myStaticMu, myKineticMu
        , myWidth, myHeight);
  }

  /**
   * Updates the next position of the GameObject to the next position using a kinematics function
   * dependent on the current position, current velocity, and the timestep
   *
   * @param dt, the current timestep
   */
  protected void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  /**
   * Updates the current position of the GameObject to the next position, allowing for simultaneous
   * updating.
   */
  protected void update() {
    myX = myNextX;
    myY = myNextY;
  }

  /**
   * Retrieves the x component of the GameObject's velocity.
   *
   * @return The x component of the GameObject's velocity.
   */
  protected double getVelocityX() {
    return myVelocityX;
  }

  /**
   * Retrieves the y component of the GameObject's velocity.
   *
   * @return The y component of the GameObject's velocity.
   */
  protected double getVelocityY() {
    return myVelocityY;
  }

  /**
   * Retrieves the ID of the GameObject.
   *
   * @return The ID of the GameObject.
   */
  public int getId() {
    return myId;
  }

  /**
   * Retrieves the visibility of the GameObject.
   *
   * @return True if the GameObject is visible, otherwise false.
   */

  public boolean getVisible() {
    return myVisible;
  }

  /**
   * Sets the visibility of the GameObject.
   *
   * @param state the new visibility state of the GameObject
   */
  public void setVisible(boolean state) {
    myVisible = state;
  }

  /**
   * Multiplies the current velocity of the GameObject by a constant factor
   *
   * @param factor for velocity to be multiplied by
   */
  public void multiplySpeed(double factor) {
    myVelocityX *= factor;
    myVelocityY *= factor;
  }

  /**
   * Calls the strikeable's applyInitialVelocity function, which updates the current velocity of
   * the GameObject
   *
   * @param magnitude The magnitude of the new velocity.
   * @param direction The direction of the new velocity with respect to the positive x-axis
   *                      (in radians).
   */
  public void applyInitialVelocity(double magnitude, double direction) {
     strikeable.applyInitialVelocity(magnitude, direction);
  }

  protected void updatePostCollisionVelocity() {
    myVelocityY = myNextVelocityY;
    myVelocityX = myNextVelocityX;
  }

  /**
   * Retrieves the x component of the GameObject's position.
   *
   * @return The x component of the GameObject's position.
   */
  protected double getX() {
    return myX;
  }

  /**
   * Retrieves the y component of the GameObject's position.
   *
   * @return The y component of the GameObject's position.
   */
  protected double getY() {
    return myY;
  }

  /**
   * Retrieves the width of the GameObject.
   *
   * @return The width of the GameObject.
   */
  protected double getWidth() {
    return myWidth;
  }

  /**
   * Retrieves the height of the GameObject.
   *
   * @return The height of the GameObject.
   */
  protected double getHeight() {
    return myHeight;
  }

  /**
   * Retrieves the string representation of the shape of the GameObject.
   *
   * @return The shape of the GameObject.
   * @author Konur Nordberg
   */

  protected String getShape() {
    return myShape;
  }

  /**
   * Sets the attributes of the GameObject based on the information provided by a GameObjectRecord.
   * This is necessary for the LastStaticStateCommand
   *
   * @param record The GameObjectRecord containing the information to set.
   */
  protected void setFromRecord(GameObjectRecord record) {
    myX = record.x();
    myY = record.y();
    myVelocityY = record.velocityY();
    myVelocityX = record.velocityX();
    myNextVelocityX = myVelocityX;
    myNextVelocityY = myVelocityY;
    myNextX = myX;
    myNextY = myY;
    myVisible = record.visible();
  }

  /**
   * Calculates and sets the next speeds of the GameObject based on the information provided by the
   * given Supplier function.
   *
   * @param speedCalculator A Supplier function that provides information for calculating the next
   *                        speeds.
   */

  protected void calculateNextSpeeds(Supplier<List<Double>> speedCalculator) {
    List<Double> newSpeeds = speedCalculator.get();
    setNextSpeed(newSpeeds.get(0), newSpeeds.get(1));
  }

  /**
   * Calculates and sets both the current and next speeds of the GameObject based on the information
   * provided by the given Supplier function.
   *
   * @param speedCalculator A Supplier function that provides concrete implementations for
   *                        calculating the speeds.
   */
  protected void calculateSpeeds(Supplier<List<Double>> speedCalculator) {
    List<Double> newSpeeds = speedCalculator.get();
    setSpeed(newSpeeds.get(0), newSpeeds.get(1));
    setNextSpeed(newSpeeds.get(0), newSpeeds.get(1));
  }

  //Sets the next velocity of the GameObject.
  private void setNextSpeed(double speedX, double speedY) {
    myNextVelocityX = speedX;
    myNextVelocityY = speedY;
  }

  //Sets the current velocity of the GameObject.
  private void setSpeed(double speedX, double speedY) {
    myVelocityX = speedX;
    myVelocityY = speedY;
  }
}


