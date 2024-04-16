package oogasalad.model.gameengine.collidable;


import java.util.List;
import java.util.function.Supplier;

public class DefaultControllable implements Controllable {

  private final Collidable collidable;
  public DefaultControllable(Collidable c) {
    collidable = c;
  }

  @Override
  public void applyInitialVelocity(double magnitude, double direction) {
    Supplier<List<Double>> supplier = () -> {
      double speedX = magnitude * Math.cos(direction);
      double speedY = magnitude * Math.sin(direction);
      return List.of(speedX, speedY);
    };
    collidable.calculateSpeeds(supplier);
  }

  @Override
  public Collidable getCollidable() {
    return collidable;
  }

}