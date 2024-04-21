package oogasalad.model.gameengine.gameobject;


public class DefaultControllable implements Controllable {

  private MoveXControllable xc;
  private MoveYControllable yc;
  private GameObject go;

  public DefaultControllable(GameObject go) {
    this.go = go;
  }

  public void addXControllable() {
    xc =  new MoveXControllable(go);
  }
  public void addYControllable() {
    yc =  new MoveYControllable(go);
  }
  @Override
  public void moveX() {
    if (xc != null) {
      xc.setXSpeed();
    }
  }

  @Override
  public void moveY() {
    if (yc != null) {
      yc.setYSpeed();
    }
  }

  @Override
  public GameObject asGameObject() {
    return go;
  }
}
