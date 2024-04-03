package oogasalad.view.VisualElements;

import oogasalad.model.api.CollidableRecord;

/**
 * Interface for a Visual Element.
 * Represents a Node in a scene on view side.
 */
interface VisualElement {

  /**
   * Updates the Element properties.
   * @param record  Record for this Element's corresponding model object.
   */
  void update(CollidableRecord record);
//  void render(); // Renders the visual element
//  void updatePosition(double x, double y); // Updates the element's position
//  void setVisible(boolean visible); // Sets the element's visibility
}
