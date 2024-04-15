package oogasalad.model.gameengine.collidable;


import java.util.List;

public class DefaultControllable implements Controllable {

  private final Collidable collidable;
  public DefaultControllable(Collidable c) {
    collidable = c;
  }

  @Override
  public List<Double> applyInitialVelocity(double magnitude, double direction) {
      return List.of(magnitude * Math.cos(direction), magnitude * Math.sin(direction));
    }
}