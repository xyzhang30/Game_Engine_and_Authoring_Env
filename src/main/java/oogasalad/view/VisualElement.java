package oogasalad.view;

interface VisualElement {
  void render(); // Renders the visual element
  void updatePosition(double x, double y); // Updates the element's position
  void setVisible(boolean visible); // Sets the element's visibility
}
