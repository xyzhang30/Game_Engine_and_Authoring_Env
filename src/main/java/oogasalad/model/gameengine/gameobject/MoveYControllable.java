package oogasalad.model.gameengine.gameobject;

import java.util.List;

public class MoveYControllable {
  private GameObject go;
  public MoveYControllable(GameObject go) {
    this.go = go;
  }

  public void setYSpeed() {
    go.calculateNextSpeeds(() -> List.of(0.0,20.0));
  }

}
