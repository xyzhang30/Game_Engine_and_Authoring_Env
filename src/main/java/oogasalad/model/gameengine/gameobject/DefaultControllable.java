package oogasalad.model.gameengine.gameobject;


public class DefaultControllable implements Controllable {

  private MoveXControllable xc;
  private MoveYControllable yc;
  private GameObject go;

  public DefaultControllable(GameObject go) {
    this.go = go;
  }

  @Override
  public void allowMoveX() {
    xc =  new MoveXControllable();
  }
  @Override
  public void allowMoveY() {
    yc =  new MoveYControllable();
  }
  @Override
  public double moveX(boolean positive) {
    if (xc != null) {
      return xc.moveX(positive);
    }
    return 0.0;
  }

  @Override
  public double moveY(boolean positive) {
    if (yc != null) {
      return yc.moveY(positive);
    }
    return 0.0;
  }

  @Override
  public GameObject asGameObject() {
    return go;
  }
}
