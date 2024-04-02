package oogasalad.view;

import oogasalad.model.api.CollidableRecord;

interface VisualElement {
  void update(CollidableRecord record); // Update based on a collidable record
  void render(); // Renders the visual element
  void updatePosition(double x, double y); // Updates the element's position
  void setVisible(boolean visible); // Sets the element's visibility
}
