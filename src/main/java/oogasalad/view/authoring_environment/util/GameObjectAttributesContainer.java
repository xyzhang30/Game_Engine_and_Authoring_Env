package oogasalad.view.authoring_environment.util;

import java.util.ArrayList;
import java.util.List;

public class GameObjectAttributesContainer implements Cloneable{
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

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
  public List<Integer> getColor() {
    return color;
  }

  public void setColor(List<Integer> color) {
    this.color = color;
  }

  public List<String> getProperties() {
    return properties;
  }
  public void setProperties(List<String> properties) {
    this.properties = properties;
  }

  public void setElasticity(boolean elasticity) {
    this.elasticity = elasticity;
  }

  public double getMass() {
    return mass;
  }

  public void setMass(double mass) {
    this.mass = mass;
  }

  public Coordinate getPosition() {
    return position;
  }

  public void setPosition(Coordinate position) {
    this.position = position;
  }

  public double getsFriction() {
    return sFriction;
  }

  public void setsFriction(double sFriction) {
    this.sFriction = sFriction;
  }

  public double getkFriction() {
    return kFriction;
  }

  public void setkFriction(double kFriction) {
    this.kFriction = kFriction;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isElasticity() {
    return elasticity;
  }

  public int getControllableXSpeed() {
    return controllableXSpeed;
  }

  public void setControllableXSpeed(int controllableXSpeed) {
    this.controllableXSpeed = controllableXSpeed;
  }

  public int getControllableYSpeed() {
    return controllableYSpeed;
  }

  public void setControllableYSpeed(int controllableYSpeed) {
    this.controllableYSpeed = controllableYSpeed;
  }
  @Override
  public Object clone() throws CloneNotSupportedException {
     GameObjectAttributesContainer clone = (GameObjectAttributesContainer) super.clone();
     clone.properties = new ArrayList<>(this.properties);
     return clone;
  }
}
