package oogasalad.model.gameengine.gameobject;


import java.util.List;
import java.util.function.Supplier;

public class DefaultStrikeable implements Strikeable {

  private final GameObject gameObject;
  public DefaultStrikeable(GameObject go) {
    gameObject = go;
  }

  @Override
  public void applyInitialVelocity(double magnitude, double direction) {
    Supplier<List<Double>> supplier = () -> {
      double speedX = magnitude * Math.cos(direction);
      double speedY = magnitude * Math.sin(direction);
      return List.of(speedX, speedY);
    };
    gameObject.calculateSpeeds(supplier);
  }

  @Override
  public GameObject asGameObject() {
    return gameObject;
  }

}