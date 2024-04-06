package oogasalad.model.gameengine.physicsengine;

public class Vector2D {
  public double x, y;

  // Constructor to create a vector given its x and y components
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  // Adds the given vector to this vector and returns the result as a new Vector2D
  public Vector2D add(Vector2D other) {
    return new Vector2D(this.x + other.x, this.y + other.y);
  }

  // Subtracts the given vector from this vector and returns the result as a new Vector2D
  public Vector2D subtract(Vector2D other) {
    return new Vector2D(this.x - other.x, this.y - other.y);
  }

  // Multiplies this vector by a scalar and returns the result as a new Vector2D
  public Vector2D multiply(double scalar) {
    return new Vector2D(this.x * scalar, this.y * scalar);
  }

  // Calculates the dot product of this vector with another vector
  public double dot(Vector2D other) {
    return this.x * other.x + this.y * other.y;
  }

  // Returns the magnitude (length) of this vector
  public double magnitude() {
    return Math.sqrt(x * x + y * y);
  }

  // Returns a new Vector2D that is the normalized (unit length) form of this vector
  public Vector2D normalize() {
    double magnitude = magnitude();
    if (magnitude == 0) {
      return new Vector2D(0, 0); // Avoid division by zero
    }
    return new Vector2D(x / magnitude, y / magnitude);
  }

  // Returns the distance between this vector and another vector
  public double distanceTo(Vector2D other) {
    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }

  // Static method to calculate the angle in radians between two vectors
  public static double angleBetween(Vector2D a, Vector2D b) {
    double dotProd = a.dot(b);
    double magA = a.magnitude();
    double magB = b.magnitude();
    return Math.acos(dotProd / (magA * magB));
  }


  @Override
  public String toString() {
    return "Vector2D{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}


