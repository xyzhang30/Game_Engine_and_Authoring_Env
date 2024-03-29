/**
 * Interface representing the internal components of the game engine.
 *
 * @author Noah Loewy
 */
interface GameEngineInternal {


  /**
   * Interface for managing the game state and logic.
   */
  interface GameManager {

    /**
     * Handles collision between two collidables.
     *
     * @param id1 The ID of the first collidable.
     * @param id2 The ID of the second collidable.
     */
    void onCollision(int id1, int id2);

    /**
     * Applies initial velocity to a collidable object.
     *
     * @param magnitude The magnitude of the velocity.
     * @param direction The direction of the velocity.
     * @param id        The ID of the collidable.
     */
    void applyInitialVelocity(double magnitude, double direction, int id);

    /**
     * Updates the game state and returns the updated record.
     *
     * @return The updated game record.
     */
    GameRecord update();

    /**
     * Retrieves the logic manager responsible for game logic.
     *
     * @return The logic manager.
     */
    LogicManager getLogicManager();

    /**
     * Retrieves the player manager responsible for managing players.
     *
     * @return The player manager.
     */
    PlayerManager getPlayerManager();

  }

  /**
   * Interface for managing game logic.
   */
  interface LogicManager {
  //QUESTION:
    //does this also need to handle the resetting of everything?
    //some sort of Turn Policy / user-defined lambdas created

    /**
     * Updates the stage of the game based on handlers defined by users.
     */
    void endStage();

    /**
     * Retrieves the current turn number.
     *
     * @return The current turn number.
     */
    int getTurn();

    /**
     * Retrieves the current sub-turn number.
     *
     * @return The current sub-turn number.
     */
    int getSubTurn();

    /**
     * Retrieves the current stage of the game.
     *
     * @return The current stage of the game.
     */
    int getStage();

  }

  /**
   * Interface for managing players in the game.
   */
  interface PlayerManager {

    /**
     * Retrieves the list of active players.
     *
     * @return The list of active players.
     */
    List<Player> getActivePlayers();

    /**
     * Sets the active players in the game.
     */
    void setActivePlayers();

    /**
     * Retrieves a player by their ID.
     *
     * @param id The ID of the player.
     * @return The player object.
     */
    Player getPlayer(int id);

  }

  /**
   * Interface representing a player in the game.
   */
  interface Player {
    //QUESTION
    //WHAT OTHER METHODS WOULD BE HERE? USER DEFINED STUFF????
    //double getUserDefinedVariable(String key)????
    double getScore();
    void setScore();
    List<Collidable> getPrimary();
  }

  /**
   * Interface for managing collidable objects.
   */
  interface CollidableContainer {

    /**
     * Retrieves a collidable object by its ID.
     *
     * @param objectId The ID of the collidable object.
     * @return The collidable object.
     */
    Collidable getCollidable(int objectId);


    /**
     * Checks if the collidable container is static.
     *
     * @return True if the container is static, false otherwise.
     */
    boolean isStatic();
  }

  /**
   * Interface representing a collidable object in the game.
   */
  interface Collidable {

    /**
     * Sets the visibility of the collidable object.
     */
    void setVisible();

    /**
     * Applies a force to the collidable object.
     *
     * @param magnitude The magnitude of the force.
     * @param direction The direction of the force.
     */
    void onForceApplied(double magnitude, double direction);

    /**
     * Handles collision with another collidable object.
     *
     * @param other The other collidable object.
     */
    void onCollision(Collidable other);

    /**
     * Retrieves the x-coordinate of the collidable object.
     *
     * @return The x-coordinate of the collidable object.
     */
    double getX();

    /**
     * Retrieves the y-coordinate of the collidable object.
     *
     * @return The y-coordinate of the collidable object.
     */
    double getY();

    /**
     * Retrieves the x-component of the velocity of the collidable object.
     *
     * @return The x-component of the velocity.
     */
    double getVelocityx();

    /**
     * Retrieves the y-component of the velocity of the collidable object.
     *
     * @return The y-component of the velocity.
     */
    double getVelocityy();

    /**
     * Retrieves the mass of the collidable object.
     *
     * @return The mass of the collidable object.
     */
    double getMass();
  }

  /**
   * Interface for managing physics-related calculations and interactions.
   */
  interface PhysicsEngine {

    //QUESTION:
    //IS THERE ANYTHING ELSE WE'D WANT TO ABSTRACT AWAY
    /**
     * Retrieves the force exerted on this collidable object by another collidable object.
     *
     * @param other The other collidable object.
     * @return The force exerted.
     */
    double getForceExerted(Collidable other);
  }

  record GameRulesRecord(int maxRounds, Map<Integer, Consumer<GameManager>> collisionHandlers,
                         TurnPolicy turnPolicy) {}

}
