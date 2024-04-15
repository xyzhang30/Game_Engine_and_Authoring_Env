package oogasalad.model.gameengine.collidable;

import java.util.List;

public class NullControllable implements Controllable {

  @Override
  public List<Double> applyInitialVelocity(double magnitude, double direction) {
    // No operation
    return null;
  }

  // Implement other Controllable interface methods if needed
}