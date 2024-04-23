package oogasalad.view.authoring_environment.data;

import java.util.ArrayList;
import java.util.List;

public class GameObjectAttributesContainer {
  private int id;
  private String imagePath;
  private List<Integer> color;
  private final List<String> properties = new ArrayList<>();
  private boolean elasticity;
  private double mass;
  private Coordinate position;
  private double sFriction;
  private double kFriction;
  private double width;
  private double height;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
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

  public boolean getElasticity() {
    return elasticity;
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
}
