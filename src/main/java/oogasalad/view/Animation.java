package oogasalad.view;

interface Animation {
  void update(double dt); // Updates the animation state based on dt
  void render(); // Renders the animation

  boolean isComplete();
}
