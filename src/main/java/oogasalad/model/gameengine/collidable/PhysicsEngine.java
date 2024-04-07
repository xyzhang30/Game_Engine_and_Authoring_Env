package oogasalad.model.gameengine.collidable;

import oogasalad.Pair;

public class PhysicsEngine {

  public boolean isColliding(Collidable collidable1, Collidable collidable2) {
    String shape1 = collidable1.getShape();
    String shape2 = collidable2.getShape();
    // circle v. rectangle
    if ("circle".equals(shape1) && "rectangle".equals(shape2)) {
      return checkCircleRectangleCollision(collidable1, collidable2);
    } else if ("rectangle".equals(shape1) && "circle".equals(shape2)) {
      return checkCircleRectangleCollision(collidable2, collidable1);
    }
    // circle v. circle
    else if ("circle".equals(shape1) && "circle".equals(shape2)) {
      return checkCircleCircleCollision(collidable1, collidable2);
    }
    // rectangle v. rectangle
    else if ("rectangle".equals(shape1) && "rectangle".equals(shape2)) {
      return checkRectangleRectangleCollision(collidable1, collidable2);
    }
    //other shape combinations as we go on
    else {
      // other shapes, or default
    }


    return false;
  }

  private boolean checkCircleRectangleCollision(Collidable circle, Collidable rectangle) {

    double circleCenterX = circle.getX();
    double circleCenterY = circle.getY();
    double circleRadius = circle.getWidth();

    double rectX = rectangle.getX();
    double rectY = rectangle.getY();
    double rectWidth = rectangle.getWidth();
    double rectHeight = rectangle.getHeight();

    // closest point on rectangle to center of circle
    double closestX = clamp(circleCenterX, rectX, rectX + rectWidth);
    double closestY = clamp(circleCenterY, rectY, rectY + rectHeight);

    // distance from the circle's center to this closest point
    double distanceX = circleCenterX - closestX;
    double distanceY = circleCenterY - closestY;

    // collision happens if this distance is less than the radius squared
    return (distanceX * distanceX + distanceY * distanceY) <= (circleRadius * circleRadius);
  }

  private boolean checkCircleCircleCollision(Collidable circle1, Collidable circle2) {
    double x1 = circle1.getX() ; // circle 1 x
    double y1 = circle1.getY() ; // circle 2 x
    double radius1 = circle1.getWidth(); // circle 1 radius

    double x2 = circle2.getX() ; // circle 2 x
    double y2 = circle2.getY() ; // circle 2 y
    double radius2 = circle2.getWidth(); // circle2 radius

    // distance between the centers of the two circles
    double distanceX = x1 - x2;
    double distanceY = y1 - y2;
    double distanceCenters = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

    // compare distance to the sum of the radii to determine collision
    return distanceCenters <= (radius1 + radius2);
  }

  private boolean checkRectangleRectangleCollision(Collidable rect1, Collidable rect2) {
    double x1 = rect1.getX();
    double y1 = rect1.getY();
    double width1 = rect1.getWidth();
    double height1 = rect1.getHeight();

    double x2 = rect2.getX();
    double y2 = rect2.getY();
    double width2 = rect2.getWidth();
    double height2 = rect2.getHeight();

    // Check for overlap on the X axis
    boolean overlapX = (x1 + width1 > x2) && (x2 + width2 > x1);

    // Check for overlap on the Y axis
    boolean overlapY = (y1 + height1 > y2) && (y2 + height2 > y1);

    // If there's overlap on both axes, then the rectangles are colliding
    System.out.println(rect1.getId());
    System.out.println(rect2.getId());
    return overlapX && overlapY;
  }

  // Helper method to clamp a value between a min and max
  private double clamp(double value, double min, double max) {
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }




}
