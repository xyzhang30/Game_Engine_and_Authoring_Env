package oogasalad.view.authoring_environment.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameObjectAttributesContainer class serves as a container for storing attributes and properties
 * associated with a game object in the authoring environment. It includes fields such as ID, image path,
 * color, elasticity, mass, position, friction, dimensions, and controllable speeds. These attributes define
 * the behavior and appearance of the game object.
 */
public class GameObjectAttributesContainer implements Cloneable {

  private int id;
  private String imagePath;
  private List<Integer> color;
  private List<String> properties = new ArrayList<>();
  private boolean elasticity;
  private double mass;
  private Coordinate position;
  private double sFriction, kFriction;
  private double width, height;
  private int controllableXSpeed, controllableYSpeed;

  /**
   * Gets the image path associated with the game object.
   *
   * @return the image path as a string.
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Sets the image path for the game object.
   *
   * @param imagePath the new image path as a string.
   */
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Gets the RGB color of the game object as a list of integers.
   *
   * @return a list of integers representing the RGB color.
   */
  public List<Integer> getColor() {
    return color;
  }

  /**
   * Sets the RGB color of the game object.
   *
   * @param color a list of integers representing the RGB color.
   */
  public void setColor(List<Integer> color) {
    this.color = color;
  }

  /**
   * Gets the list of properties associated with the game object.
   *
   * @return a list of strings representing the properties.
   */
  public List<String> getProperties() {
    return properties;
  }

  /**
   * Sets the list of properties for the game object.
   *
   * @param properties a list of strings representing the properties.
   */
  public void setProperties(List<String> properties) {
    this.properties = properties;
  }

  /**
   * Sets the elasticity of the game object.
   *
   * @param elasticity true if the game object is elastic, false otherwise.
   */
  public void setElasticity(boolean elasticity) {
    this.elasticity = elasticity;
  }

  /**
   * Gets the mass of the game object.
   *
   * @return the mass as a double value.
   */
  public double getMass() {
    return mass;
  }

  /**
   * Sets the mass of the game object.
   *
   * @param mass the new mass as a double value.
   */
  public void setMass(double mass) {
    this.mass = mass;
  }

  /**
   * Gets the position of the game object as a Coordinate object.
   *
   * @return the position as a Coordinate object.
   */
  public Coordinate getPosition() {
    return position;
  }

  /**
   * Sets the position of the game object as a Coordinate object.
   *
   * @param position the new position as a Coordinate object.
   */
  public void setPosition(Coordinate position) {
    this.position = position;
  }

  /**
   * Gets the static friction coefficient of the game object.
   *
   * @return the static friction coefficient as a double.
   */
  public double getsFriction() {
    return sFriction;
  }

  /**
   * Sets the static friction coefficient of the game object.
   *
   * @param sFriction the new static friction coefficient as a double.
   */
  public void setsFriction(double sFriction) {
    this.sFriction = sFriction;
  }

  /**
   * Gets the kinetic friction coefficient of the game object.
   *
   * @return the kinetic friction coefficient as a double.
   */
  public double getkFriction() {
    return kFriction;
  }

  /**
   * Sets the kinetic friction coefficient of the game object.
   *
   * @param kFriction the new kinetic friction coefficient as a double.
   */
  public void setkFriction(double kFriction) {
    this.kFriction = kFriction;
  }

  /**
   * Gets the width of the game object.
   *
   * @return the width as a double.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Sets the width of the game object.
   *
   * @param width the new width as a double.
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * Gets the height of the game object.
   *
   * @return the height as a double.
   */
  public double getHeight() {
    return height;
  }

  /**
   * Sets the height of the game object.
   *
   * @param height the new height as a double.
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Gets the ID of the game object.
   *
   * @return the ID as an integer.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the game object.
   *
   * @param id the new ID as an integer.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Checks if the game object is elastic.
   *
   * @return true if the game object is elastic, false otherwise.
   */
  public boolean isElasticity() {
    return elasticity;
  }

  /**
   * Gets the controllable speed of the game object along the x-axis.
   *
   * @return the controllable x-axis speed as an integer.
   */
  public int getControllableXSpeed() {
    return controllableXSpeed;
  }

  /**
   * Sets the controllable speed of the game object along the x-axis.
   *
   * @param controllableXSpeed the new controllable x-axis speed as an integer.
   */
  public void setControllableXSpeed(int controllableXSpeed) {
    this.controllableXSpeed = controllableXSpeed;
  }

  /**
   * Gets the controllable speed of the game object along the y-axis.
   *
   * @return the controllable y-axis speed as an integer.
   */
  public int getControllableYSpeed() {
    return controllableYSpeed;
  }

  /**
   * Sets the controllable speed of the game object along the y-axis.
   *
   * @param controllableYSpeed the new controllable y-axis speed as an integer.
   */
  public void setControllableYSpeed(int controllableYSpeed) {
    this.controllableYSpeed = controllableYSpeed;
  }

  /**
   * Creates a clone of the GameObjectAttributesContainer instance.
   *
   * @return a cloned instance of GameObjectAttributesContainer.
   * @throws CloneNotSupportedException if the object's class does not support the Cloneable interface.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    GameObjectAttributesContainer clone = (GameObjectAttributesContainer) super.clone();
    clone.properties = new ArrayList<>(this.properties);
    return clone;
  }
}
