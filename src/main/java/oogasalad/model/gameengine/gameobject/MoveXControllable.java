package oogasalad.model.gameengine.gameobject;

import java.util.List;

public class MoveXControllable {
  private GameObject go;
  public MoveXControllable(GameObject go) {
    this.go = go;
  }

  public void setXSpeed() {
    go.calculateNextSpeeds(() -> List.of(20.0,0.0));
  }

}
