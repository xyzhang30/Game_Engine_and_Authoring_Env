package oogasalad.model.gameengine.gameobject;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Supplier;
import oogasalad.model.api.GameObjectRecord;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.gameengine.gameobject.controllable.Controllable;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The GameObject class represents an object within the game environment that interacts with physics
 * and game mechanics.
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
 * implementations), as well as the container for the GameObjects (which is used to encapsulate the
 * map from ids to GameObjects).
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
  private final double myInclineAngle;
  private final Stack<GameObjectRecord> gameObjectHistory;
  private final boolean inelastic;
  private final boolean phaser;
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
  private Controllable controllable;

  /**
   * Initiates the GameObject.
   *
   * @param co the properties of the game object being created.
   */

  public GameObject(GameObjectProperties co) {
    myId = co.collidableId();
    myMass = co.mass();
    myX = co.position().xPosition();
    myY = co.position().yPosition();
    myVelocityX = 0;
    myVelocityY = 0;
    myVisible = co.properties().contains("visible") && !co.properties().contains("strikeable");
    myStaticMu = co.staticFriction();
    myKineticMu = co.kineticFriction();
    myInclineAngle = co.inclineAngle();
    myWidth = co.dimension().xDimension();
    myHeight = co.dimension().yDimension();
    myShape = co.shape();
    inelastic = co.inelastic();
    phaser = co.phaser();
    gameObjectHistory = new Stack<>();
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
   * Associates a Controllable behavior with the GameObject.
   *
   * @param controllable The Controllable behavior to be associated with the GameObject.
   */
  public void addControllable(Controllable controllable) {
    this.controllable = controllable;
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
   * Retrieves the Scoreable behavior associated with the GameObject, if any.
   *
   * @return An Optional containing the Scoreable behavior associated with the GameObject, or an
   * empty Optional if no Scoreable behavior is associated.
   */
  public Optional<Controllable> getControllable() {
    return Optional.ofNullable(controllable);
  }

  /**
   * Serializes the GameObject into an immutable GameObjectRecord
   *
   * @return GameObjectRecord, an immutable representation of the GameObject
   */

  public GameObjectRecord toGameObjectRecord() {
    return new GameObjectRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible,
        myStaticMu, myKineticMu, myInclineAngle, myWidth, myHeight, inelastic, phaser);
  }

  /**
   * Updates the next position of the GameObject to the next position using a kinematics function
   * dependent on the current position, current velocity, and the timestep.
   *
   * @param dt the current timestep.
   */
  public void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  /**
   * Updates the current position of the GameObject to the next position, allowing for simultaneous
   * updating.
   */
  public void update() {
    myX = myNextX;
    myY = myNextY;
  }


  /**
   * Returns id instance variable of gameObject
   *
   * @return unique identifier of object.
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
   * Calls the strikeable's applyInitialVelocity function, which updates the current velocity of the
   * GameObject
   *
   * @param magnitude The magnitude of the new velocity.
   * @param direction The direction of the new velocity with respect to the positive x-axis (in
   *                  radians).
   */
  public void applyInitialVelocity(double magnitude, double direction) {
    strikeable.applyInitialVelocity(magnitude, direction);
  }

  /**
   * Allows for simultaneous updates of velocity after a collision
   */
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

  /**
   * Restores the previous state of the gameObject (as a record) to the history of the gameObject
   */

  public void toLastStaticStateGameObjects() {
    GameObjectRecord record = gameObjectHistory.peek();
    assignValuesFromRecord(record);
  }


  //assign values to instance variables from a record, typically an old static state
  private void assignValuesFromRecord(GameObjectRecord record) {
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
   * Adds the current state of the gameObject (as a record) to the history of the gameObject
   */

  public void addStaticStateGameObject() {
    gameObjectHistory.push(toGameObjectRecord());
  }

  /**
   * Update y component of position based on prompt from controllable
   *
   * @param positive, true if movement in the positive x direction, false otherwise
   */
  public void moveControllableX(boolean positive) {
    Optional<Controllable> controllable = getControllable();
    controllable.ifPresent(value -> myX += value.moveX(positive));
  }

  /**
   * Update y component of position based on prompt from controllable.
   *
   * @param positive true if movement in the positive x direction, false otherwise.
   */
  public void moveControllableY(boolean positive) {
    Optional<Controllable> controllable = getControllable();
    controllable.ifPresent(value -> myY += value.moveY(positive));
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

  /**
   * Sets state of Game Object to its state at the beginning of the round.
   */
  public void toStartingState() {
    assignValuesFromRecord(gameObjectHistory.get(0));
  }

  /**
   * Sets the velocity of game object to zero.
   */
  public void stop() {
    myNextVelocityY = 0;
    myVelocityY = 0;
    myVelocityX = 0;
    myNextVelocityX = 0;
  }

  /**
   * Moves the location of a gameObject to place it on top of another game object.
   *
   * @param gameObject the game object representing where *this* should be teleported to.
   */
  public void teleportTo(GameObject gameObject) {
    myX = gameObject.getX();
    myY = gameObject.getY();
  }
}


