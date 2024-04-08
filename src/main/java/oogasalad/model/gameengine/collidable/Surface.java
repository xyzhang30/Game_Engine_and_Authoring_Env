package oogasalad.model.gameengine.collidable;

public class Surface extends Collidable {

  private static final double g = 10;
  private static final double C = 40;

  public Surface(int id, double mass, double x, double y, boolean visible, double mu, double width,
      double height, String shape) {
    super(id, mass, x, y, visible, mu, width, height, shape);
  }

}
