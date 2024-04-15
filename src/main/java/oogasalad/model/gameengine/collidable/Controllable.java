package oogasalad.model.gameengine.collidable;

import java.util.List;

public interface Controllable {
  List<Double> applyInitialVelocity(double magnitude, double direction);
}
