package oogasalad.model.gameengine.collidable;

import java.util.List;
import java.util.Stack;
import oogasalad.model.api.CollidableRecord;

public abstract class Collidable {

  private final double myMass;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private final int myId;
  private double myNextX;
  private double myNextY;
  private double myNextVelocityX;
  private double myNextVelocityY;
  private boolean myVisible;
  private final double myWidth;
  private final double myHeight;
  private final String myShape;
  private double myMu;
  private Stack<List<Integer>> locationHistory;

  public Collidable(int id, double mass, double x, double y,
      boolean visible, double mu, double width, double height, String shape) {
    myId = id;
    myMass = mass;
    myX = x;
    myY = y;
    myVelocityX = 0.0;
    myVelocityY = 0.0;
    myNextX = x;
    myNextY = y;
    myVisible = visible;
    myWidth = width;
    myHeight = height;
    myShape = shape;
    myMu = mu;

  }

  public void onCollision(Collidable other, double dt) {
    double[] result = other.calculateNewSpeed(this, dt);
    myNextVelocityX = result[0];
    myNextVelocityY = result[1];
  }

  public void updatePostCollisionVelocity() {
    myVelocityY = myNextVelocityY;
    myVelocityX = myNextVelocityX;
    if (Math.abs(myVelocityX) < .00001) {
      myVelocityX = 0;
    }
    if (Math.abs(myVelocityY) < .00001) {
      myVelocityY = 0;
    }
  }

  public abstract double[] calculateNewSpeed(Collidable other, double dt);

  public CollidableRecord getCollidableRecord() {
    return new CollidableRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible);
  }

  public void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  public void update() {
    myX = myNextX;
    myY = myNextY;
  }

  public void applyInitialVelocity(double magnitude, double direction) {
    myVelocityX = magnitude * Math.cos(direction);
    myNextVelocityX = myVelocityX;
    myVelocityY = magnitude * Math.sin(direction);
    myNextVelocityY = myVelocityY;

  }

  protected double getMu() { return myMu; }

  protected double getVelocityX() {
    return myVelocityX;
  }

  protected double getVelocityY() {
    return myVelocityY;
  }

  protected double getMass() {
    return myMass;
  }

  public int getId() {
    return myId;
  }

  protected boolean getVisible() {
    return myVisible;
  }

  protected double getX() {
    return myX;
  }

  protected double getY() {
    return myY;
  }

  protected double getWidth() {
    return myWidth;
  }

  protected double getHeight() {
    return myHeight;
  }

  protected String getShape() {
    return myShape;
  }

  protected void setFromRecord(CollidableRecord record) {
    myX = record.x();
    myY = record.y();
    myVelocityY = record.velocityY();
    myVelocityX = record.velocityX();
    myNextVelocityX = myVelocityX;
    myNextVelocityY = myVelocityY;
    myNextX = myX;
    myNextY = myY;
    myVisible = record.visible();
  }

  //for debugging
  @Override
  public String toString() {
    String sb = "{\n"
        + "  \"myMass\": " + myMass + ",\n"
        + "  \"myX\": " + myX + ",\n"
        + "  \"myY\": " + myY + ",\n"
        + "  \"myVelocityX\": " + myVelocityX + ",\n"
        + "  \"myVelocityY\": " + myVelocityY + ",\n"
        + "  \"myId\": " + myId + ",\n"
        + "  \"myNextX\": " + myNextX + ",\n"
        + "  \"myNextY\": " + myNextY + ",\n"
        + "  \"myNextVelocityX\": " + myNextVelocityX + ",\n"
        + "  \"myNextVelocityY\": " + myNextVelocityY + ",\n"
        + "  \"myVisible\": " + myVisible + ",\n"
        + "  \"myWidth\": " + myWidth + ",\n"
        + "  \"myHeight\": " + myHeight + ",\n"
        + "}\n";
    return sb;
  }

  protected void setSpeed(double speedX, double speedY) {
    myNextVelocityX = speedX;
    myNextVelocityY = speedY;
  }
}
