package oogasalad.model.gameengine.collidable;

import oogasalad.model.gameengine.GameEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Moveable extends Collidable {

  private static final Logger LOGGER = LogManager.getLogger(Moveable.class);

  public Moveable(int id, double mass, double x, double y,
      boolean visible, double width, double height, String shape) {
    super(id, mass, x, y, visible, width, height, shape);
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) { //gets speed of thing its
    //testing the logger
    LOGGER.info("collision:" + other.getId() + "," + this.getId());

    if (getMass() == Double.POSITIVE_INFINITY) {
      //if the ball is colliding from the top or bottom
      if (other.getY() + other.getWidth() / 2 <= this.getY() + other.getWidth() / 2
          || other.getY() - other.getWidth() / 2
          >= this.getY() + this.getHeight() - other.getWidth() / 2) {
        return new double[]{other.getVelocityX(), -other.getVelocityY()};
      } else { //if ball is colliding from the left or right
        return new double[]{-other.getVelocityX(), other.getVelocityY()};
      }
    }
    if (other.getMass() == Double.POSITIVE_INFINITY) {
      //if the ball is colliding from the top or bottom
      if (other.getY() + other.getWidth() / 2 <= this.getY() + other.getWidth() / 2
          || other.getY() - other.getWidth() / 2
          >= this.getY() + this.getHeight() - other.getWidth() / 2) {
        System.out.println("bounce x");
        return new double[]{other.getVelocityX(), -other.getVelocityY()};
      } else { //if ball is colliding from the left or right
        System.out.println("bounce y");
        return new double[]{-other.getVelocityX(), other.getVelocityY()};
      }
    }
    double massSum = other.getMass() + getMass();

    if (other.getVelocityX() == 0 && other.getVelocityY() == 0 && getVelocityX() == 0
        && getVelocityY() == 0) {
      return new double[]{0, 0};
    }
    double xv =
        (2 * getMass() * getVelocityX() + (other.getMass() - getMass()) * other.getVelocityX())
            / massSum;
    double yv =
        (2 * getMass() * getVelocityY() + (other.getMass() - getMass()) * other.getVelocityY())
            / massSum;
    return new double[]{xv, yv};
//    if (getMass() == Double.POSITIVE_INFINITY) {
//      // Calculate the centers of both objects
//      double thisCenterX = getX() + getWidth() / 2;
//      double thisCenterY = getY() + getHeight() / 2;
//      double otherCenterX = other.getX() + other.getWidth() / 2;
//      double otherCenterY = other.getY() + other.getHeight() / 2;
//
//      // Calculate the distances between centers along x and y axes
//      double dx = Math.abs(otherCenterX - thisCenterX);
//      double dy = Math.abs(otherCenterY - thisCenterY);
//
//      // Calculate the sum of half-widths of both objects
//      double sumHalfWidths = getWidth() / 2 + other.getWidth() / 2;
//      double sumHalfHeights = getHeight() / 2 + other.getHeight() / 2;
//
//      // Check for collision along x-axis
//      if (dx <= sumHalfWidths) {
//        // Check if the collision is from the top or bottom
//        if (dy <= sumHalfHeights) {
//          // Top or bottom collision
//          return new double[]{other.getVelocityX(), -other.getVelocityY()};
//        } else {
//          // Left or right collision
//          return new double[]{-other.getVelocityX(), other.getVelocityY()};
//        }
//      }
//    }
  }


}
