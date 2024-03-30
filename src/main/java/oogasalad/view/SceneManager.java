package oogasalad.view;

import java.util.HashMap;
import java.util.Map;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.PlayerRecord;

public class SceneManager {
  private Map<Integer, VisualElement> elementsMap; // Maps object IDs to their visual representations
  private GameController gameController; // Controller to interact with the game logic
  private AnimationManager animationManager; // Manages animations within the scene

  /**
   * Constructor for SceneManager.
   *
   * @param gameController The controller for interacting with the game's logic and state.
   */
  public SceneManager(GameController gameController) {
    this.gameController = gameController;
    this.elementsMap = new HashMap<>();
    this.animationManager = new AnimationManager();
  }

  /**
   * Updates the scene based on the latest game state fetched from the controller.
   *
   * @param dt The time delta since the last update, for time-based calculations.
   */
  public void updateScene(double dt) {
    GameRecord gameRecord = gameController.getLatestGameState(dt);

    // Update collidable objects
    for (CollidableRecord collidable : gameRecord.collidables()) {
      VisualElement element = elementsMap.get(collidable.id());
      if (element != null) {
        // Update position and visibility based on the collidable's current state
        element.updatePosition(collidable.x(), collidable.y());
        element.setVisible(collidable.visible());
      } else {
        // Optionally, create a new VisualElement if one does not exist for this ID
        // This part is for dynamically adding elements to the scene, if applicable
      }
    }

    // Update player-specific elements, like scores
    for (PlayerRecord player : gameRecord.players()) {
      // Implementation details for updating player scores or other visual elements
      // This might involve fetching a specific UI element for the score and updating its value
      updatePlayerScore(player.playerId(), player.score());
    }

    // Render the updated scene
    render();
  }

  /**
   * Renders all elements and animations on the scene.
   */
  private void render() {
    elementsMap.values().forEach(VisualElement::render); // Render each visual element
    animationManager.renderAnimations(); // Render animations
  }

  /**
   * Updates the player score display.
   *
   * @param playerId The ID of the player.
   * @param score The new score of the player.
   */
  private void updatePlayerScore(int playerId, double score) {
    // Logic to update player scores visually
    // This could involve finding a specific UI element by player ID and setting its displayed score
  }

  // Additional methods for scene management, like handling user input, could be added here
}