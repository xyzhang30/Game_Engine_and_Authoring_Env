package oogasalad.view;

import java.util.List;
import oogasalad.model.api.ExternalGameEngine;
import oogasalad.model.api.GameRecord;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.api.PlayerRecord;
import java.util.HashMap;
import java.util.Map;


// Represents the entire game scene, managing both static and dynamic elements
public class SceneManager {
  private ExternalGameEngine gameEngine; // Reference to the game engine for fetching game state
  private Map<Integer, VisualElement> elements; // Maps collidable IDs to their visual representations
  private AnimationManager animationManager;

  public SceneManager(ExternalGameEngine gameEngine) {
    this.gameEngine = gameEngine;
    this.elements = new HashMap<>();
    this.animationManager = new AnimationManager();
  }

  // Call this method periodically to update and render the scene based on the game state
  public void updateAndRender(double dt) {
    // Fetch the latest game state
    GameRecord gameRecord = gameEngine.update(dt);

    // Handle null gameRecord (e.g., when game is paused or static)
    if (gameRecord == null) {
      return;
    }

    // Update or create visual elements for each collidable
    for (CollidableRecord collidable : gameRecord.collidables()) {
      VisualElement element = elements.get(collidable.id());
      if (element == null) {
        // If no visual element exists for this ID, create it
        element = createVisualElementFor(collidable);
        elements.put(collidable.id(), element);
      }
      element.update(collidable);
    }

    // Remove any elements not in the current game state
    elements.keySet().retainAll(
        gameRecord.collidables().stream().map(CollidableRecord::id).toList()
    );

    // Update player-specific elements (e.g., scores)
    updatePlayerElements(gameRecord.players());

    // Render all visual elements
    elements.values().forEach(VisualElement::render);

    // Handle animations
    animationManager.renderAnimations();

    // Check for game over or other conditions
    handleGameConditions(gameRecord.gameOver());
  }

  private VisualElement createVisualElementFor(CollidableRecord collidable) {
    // Implement logic to create a visual element based on the collidable's properties
    return new CompositeElement(collidable);
  }

  private void updatePlayerElements(List<PlayerRecord> players) {
    // Implement logic to update player-specific UI elements, like scores or active states
  }

  private void handleGameConditions(boolean gameOver) {
    if (gameOver) {
      // Implement logic to handle game over condition, like showing a game over screen
    }
  }

}
