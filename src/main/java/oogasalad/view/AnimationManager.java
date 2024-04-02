package oogasalad.view;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {
  private List<Animation> animations; // A list of all active animations

  public AnimationManager() {
    this.animations = new ArrayList<>();
  }

  public void addAnimation(Animation animation) {
    animations.add(animation); // Add a new animation to the manager
  }

  public void updateAnimations(double dt) {
    animations.forEach(animation -> animation.update(dt)); // Update all animations
    // Optionally, remove animations that are complete
    animations.removeIf(Animation::isComplete); // Assuming Animation has an isComplete method
  }

  public void renderAnimations() {
    animations.forEach(Animation::render); // Render all animations
  }
}