package oogasalad.model.gameengine.gameobject;

import java.util.List;

public class MoveYControllable {
  private GameObject go;

  public double moveY(boolean positive) {
    return 20*(positive?1:-1);
  }
}
