/**
 * @author Noah Loewy
 */
interface GameEngineInternal {

  interface CollidableObjects {

    /**
     * Checks if the state is static.
     *
     * @return True if the state is static, false otherwise.
     */
    boolean isStateStatic();

    /**
     * Updates the state of collidable objects.
     */
    void update();
  }

  /**
   * Represents a collidable entity in the game.
   */
  interface Collidable {

    /**
     * Sets the visibility of the collidable.
     */
    void setVisible();

    /**
     * Handles the application of force on the collidable.
     *
     * @param magnitude The magnitude of the force.
     * @param direction The direction of the force.
     */
    void onForceApplied(double magnitude, double direction);

    /**
     * Handles collision with another collidable.
     *
     * @param other The other collidable object.
     */
    void onCollision(Collidable other);

    /**
     * Retrieves the x-coordinate of the collidable.
     *
     * @return The x-coordinate of the collidable.
     */
    double getX();

    /**
     * Retrieves the y-coordinate of the collidable.
     *
     * @return The y-coordinate of the collidable.
     */
    double getY();

    /**
     * Retrieves the x-component of the velocity of the collidable.
     *
     * @return The x-component of the velocity.
     */
    double getVelocityx();

    /**
     * Retrieves the y-component of the velocity of the collidable.
     *
     * @return The y-component of the velocity.
     */
    double getVelocityy();

    /**
     * Retrieves the mass of the collidable.
     *
     * @return The mass of the collidable.
     */
    double getMass();
  }

  /**
   * Manages static states in the game.
   */
  interface StaticStateHandler {

    /**
     * Checks if a condition is met for the static state.
     *
     * @return True if the condition is met, false otherwise.
     */
    boolean isCondition();

    /**
     * Retrieves the next handler for the static state.
     *
     * @return The next handler for the static state.
     */
    StaticStateHandler getNextHandler();
  }

  /**
   * Manages players in the game.
   */
  interface PlayerManager {

    /**
     * Changes the turn or incorporates a turn policy.
     */
    void changeTurn();

    /**
     * Retrieves the player with the specified ID.
     *
     * @param id The ID of the player to retrieve.
     * @return The player with the specified ID.
     */
    Player getPlayer(int id);

    /**
     * Retrieves a list of active player IDs.
     *
     * @return A list of active player IDs.
     */
    ObservableList<Integer> getActiveIds();
  }

  /**
   * Represents a player in the game.
   */
  interface Player {

    /**
     * Retrieves the score of the player.
     *
     * @return The score of the player.
     */
    double getScore();

    /**
     * Sets the score of the player.
     *
     * @param score The score to set.
     */
    void setScore(double score);

    /**
     * Retrieves the subturn of the player.
     *
     * @return The subturn of the player.
     */
    int getSubturn();

    /**
     * Sets the subturn of the player.
     *
     * @param subturn The subturn to set.
     */
    void setSubturn(int subturn);
  }

  /**
   * Represents permissions in the game.
   */
  interface Permissions {

    /**
     * Checks if the specified player is allowed in the current game state.
     *
     * @param playerId  The ID of the player to check.
     * @param gameState The current game state.
     * @return True if the player is allowed, false otherwise.
     */
    boolean isAllowed(int playerId, int gameState);
  }
}