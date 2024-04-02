package oogasalad.model;
/**
 * Represents a point in a two-dimensional coordinate system.
 *
 * @author Judy He
 */
public class Point {

  private double x; // The x-coordinate of the point
  private double y; // The y-coordinate of the point

  /**
   * Constructs a point with the specified x and y coordinates.
   *
   * @param x The x-coordinate of the point
   * @param y The y-coordinate of the point
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate of the point.
   *
   * @return The x-coordinate of the point
   */
  public double getX() {
    return x;
  }

  /**
   * Sets the x-coordinate of the point.
   *
   * @param x The new x-coordinate of the point
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Gets the y-coordinate of the point.
   *
   * @return The y-coordinate of the point
   */
  public double getY() {
    return y;
  }

  /**
   * Sets the y-coordinate of the point.
   *
   * @param y The new y-coordinate of the point
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Checks if this point is equal to another object.
   *
   * @param obj The object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Point other = (Point) obj;
    return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
  }

  /**
   * Returns a string representation of the point.
   *
   * @return A string representation of the point in the format "(x,y)"
   */
  @Override
  public String toString() {
    return "(" + x + "," + y + ")";
  }
}
