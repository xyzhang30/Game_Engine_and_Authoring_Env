/**
 * @author Doga Ozmen
 */


interface GameViewExternal {

  /**
   * Displays the game objects on the screen.
   */
  interface DrawableObjects {

    /**
     * Renders objects on the screen.
     */
    void draw();

    /**
     * Refreshes the display to reflect any changes.
     */
    void refresh();
  }

  /**
   * Provides an interface for interactable game entities.
   */
  interface Interactable {

    /**
     * Displays an interactive element for the entity.
     */
    void showInteractiveElement();

    /**
     * Processes user input or interactions with the entity.
     *
     * @param inputType The type of input received.
     */
    void onInputReceived(InputType inputType);

    /**
     * Highlights the entity on hover or selection.
     */
    void highlightOnHover();
  }

  /**
   * Manages the display of static game states, like menus or dialogs.
   */
  interface StaticViewManager {

    /**
     * Displays a static state, such as a menu or a pause screen.
     */
    void displayStaticState();

    /**
     * Cycles through different static views based on user input or game conditions.
     */
    void cycleStaticViews();
  }

  /**
   * Handles the visualization of player-related information.
   */
  interface PlayerViewManager {

    /**
     * Updates and displays the player's score.
     *
     * @param playerId The ID of the player whose score is to be displayed.
     * @param score The score to display.
     */
    void displayScore(int playerId, double score);

    /**
     * Displays an indicator of whose turn it is in a multiplayer game.
     */
    void indicateTurn();

    /**
     * Displays a list or grid of active players in the game.
     */
    void displayActivePlayers();
  }

  /**
   * Manages permissions from a user interface perspective.
   */
  interface PermissionsView {

    /**
     * Displays prompts or indicators based on permission checks for game actions.
     *
     * @param action The action the player is attempting to perform.
     * @param allowed Whether the action is allowed or not.
     */
    void displayPermissionFeedback(GameAction action, boolean allowed);
  }
}
