package oogasalad.model.gameengine.collidable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import oogasalad.model.api.CollidableRecord;
import oogasalad.model.gameengine.collidable.ownable.Scoreable;

public class GameObject {

  private final double myMass;
  private final int myId;
  private final double myWidth;
  private final double myHeight;
  private final String myShape;
  private final double myStaticMu;
  private final double myKineticMu;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private double myNextX;
  private double myNextY;
  private double myNextVelocityX;
  private double myNextVelocityY;
  private boolean myVisible;


  private Strikeable strikeable;
  private Scoreable ownable;

  public GameObject(int id, double mass, double x, double y,
      boolean visible, double staticMu, double kineticMu, double width, double height,
      String shape) {
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
    myStaticMu = staticMu;
    myKineticMu = kineticMu;
  }

  public void addControllable(Strikeable strikeable) {
    this.strikeable = strikeable;
  }
  public void addOwnable(Scoreable ownable) {
    this.ownable = ownable;
  }
  public Optional<Strikeable> getControllable() {
    return Optional.ofNullable(strikeable);
  }

  public Optional<Scoreable> getOwnable() {
    return Optional.ofNullable(ownable);
  }

  protected void updatePostCollisionVelocity() {
    myVelocityY = myNextVelocityY;
    myVelocityX = myNextVelocityX;
  }


  public CollidableRecord getCollidableRecord() {
    return new CollidableRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible,
        myStaticMu, myKineticMu
        , myWidth, myHeight);
  }

  protected void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  protected void update() {
    myX = myNextX;
    myY = myNextY;
  }

  protected double getVelocityX() {
    return myVelocityX;
  }

  protected double getVelocityY() {
    return myVelocityY;
  }

  public int getId() {
    return myId;
  }

  public boolean getVisible() {
    return myVisible;
  }

  public void setVisible(boolean state) {
    myVisible = state;
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


  private void setNextSpeed(double speedX, double speedY) {
    myNextVelocityX = speedX;
    myNextVelocityY = speedY;
  }

  private void setSpeed(double speedX, double speedY) {
    myVelocityX = speedX;
    myVelocityY = speedY;
    myNextVelocityX = myVelocityX;
    myNextVelocityY = myVelocityY;
  }


  protected void calculateNextSpeeds(Supplier<List<Double>> firstInfo) {
    List<Double> newSpeeds = firstInfo.get();
    setNextSpeed(newSpeeds.get(0), newSpeeds.get(1));
  }

  protected void calculateSpeeds(Supplier<List<Double>> firstInfo) {
    List<Double> newSpeeds = firstInfo.get();
    setSpeed(newSpeeds.get(0), newSpeeds.get(1));
  }

  public void multiplySpeed(double factor) {//dont love that this is public
    myVelocityX *= factor;
    myVelocityY *= factor;
  }

  protected void stop() {
    myVelocityX = 0;
    myNextVelocityX = 0;
    myVelocityY = 0;
    myNextVelocityY = 0;
  }

  public void applyInitialVelocity(double magnitude, double direction) {
    strikeable.applyInitialVelocity(magnitude, direction);
  }
}


