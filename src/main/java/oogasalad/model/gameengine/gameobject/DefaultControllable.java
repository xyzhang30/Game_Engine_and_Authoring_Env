package oogasalad.model.gameengine.gameobject;


public class DefaultControllable implements Controllable {

  private int xMovement;
  private int yMovement;
  private GameObject go;

  public DefaultControllable(GameObject go) {
    this.go = go;
    xMovement = 0;
    yMovement = 0;
  }

  @Override
  public double moveX(boolean positive) {
      return xMovement*(positive?1:-1);
    }

  @Override
  public double moveY(boolean positive) {
    return yMovement*(positive?1:-1);
  }

  @Override
  public GameObject asGameObject() {
    return go;
  }

  @Override
  public void setMovement(int xMovement, int yMovement) {
    this.xMovement = xMovement;
    this.yMovement = yMovement;
  }
}




