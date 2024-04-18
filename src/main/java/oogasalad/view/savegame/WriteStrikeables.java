package oogasalad.view.savegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.view.authoring_environment.authoring_screens.NonStrikeableType;

public class WriteStrikeables {


  public void writeGameObjectProperties(Map<Shape, Integer> gameObjectIdMap,
      List<Shape> strikeables,
      Map<Shape, NonStrikeableType> nonStrikeableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap) {

    int gameObjectId = 0;
    List<GameObjectProperties> gameObjectProperties = new ArrayList<>();
    writeBackGround();
  }

  private void writeBackGround() {
//    List<Integer> colorRgb = List.of(0, 0, 0);
//    String imgPath = "";
//    if (background.getFill() instanceof Color) {
//      Color c = (Color) (background.getFill());
//      colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//          (int) c.getBlue() * 255);
//    } else {
//      imgPath = imageMap.get(background);
//    }
//    List<String> properties = new ArrayList<>();
//    properties.add("collidable");
//    properties.add("visible");
//    properties.add(nonStrikeableTypeMap.get(background).toString().toLowerCase());
//    //double friction = 0.8;
//    double staticFriction = 7;
//    double kineticFriction = 5;
//    String shapeName = "Rectangle";
//    CollidableObject collidableObject = new CollidableObject(collidableId,
//        properties, Float.POSITIVE_INFINITY,
//        new Position(posMap.get(background).get(0), posMap.get(background).get(1)),
//        shapeName, new Dimension(background.getLayoutBounds().getWidth(),
//        background.getLayoutBounds().getHeight()), colorRgb, staticFriction, kineticFriction,
//        imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(background, collidableId);
//    collidableId++;
//
//    nonControllableTypeMap.remove(background);
//
  }

  //
//  private void writeCollidables(Map<Shape, Integer> collidableIdMap, List<Shape> controllables,
//      Map<Shape, GameObjectType> nonControllableTypeMap, Map<Shape, String> imageMap,
//      Map<Shape, List<Double>> posMap) {
//    int collidableId = 0;
//    List<CollidableObject> collidableObjects = new ArrayList<>();
//
//    //handling background first
//    List<Integer> colorRgb = List.of(0, 0, 0);
//    String imgPath = "";
//    if (background.getFill() instanceof Color) {
//      Color c = (Color) (background.getFill());
//      colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//          (int) c.getBlue() * 255);
//    } else {
//      imgPath = imageMap.get(background);
//    }
//    List<String> properties = new ArrayList<>();
//    properties.add("collidable");
//    properties.add("visible");
//    properties.add(nonControllableTypeMap.get(background).toString().toLowerCase());
//    //double friction = 0.8;
//    double staticFriction = 7;
//    double kineticFriction = 5;
//    String shapeName = "Rectangle";
//    CollidableObject collidableObject = new CollidableObject(collidableId,
//        properties, Float.POSITIVE_INFINITY,
//        new Position(posMap.get(background).get(0), posMap.get(background).get(1)),
//        shapeName, new Dimension(background.getLayoutBounds().getWidth(),
//        background.getLayoutBounds().getHeight()), colorRgb, staticFriction, kineticFriction,
//        imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(background, collidableId);
//    collidableId++;
//
//    nonControllableTypeMap.remove(background);
//
//    //walls
//    Rectangle wall1 = new Rectangle(50, 50, 20, 990);
//    colorRgb = List.of(0, 0, 0);
//    imgPath = "";
//    properties = new ArrayList<>();
//    properties.add("collidable");
//    properties.add("visible");
//    properties.add("collidable");
//    shapeName = "Rectangle";
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 50),
//        shapeName, new Dimension(20,
//        990), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall1, collidableId);
//    collidableId++;
//
//    Rectangle wall2 = new Rectangle(1020, 50, 20, 990);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(1020, 50),
//        shapeName, new Dimension(20,
//        990), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall2, collidableId);
//    collidableId++;
//
//    Rectangle wall3 = new Rectangle(50, 50, 990, 20);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 50),
//        shapeName, new Dimension(985,
//        20), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall3, collidableId);
//    collidableId++;
//
//    Rectangle wall4 = new Rectangle(50, 1020, 990, 20);
//    collidableObject = new CollidableObject(collidableId,
//        properties, Double.POSITIVE_INFINITY,
//        new Position(50, 1015),
//        shapeName, new Dimension(985,
//        20), colorRgb, staticFriction, kineticFriction, imgPath);
//    collidableObjects.add(collidableObject);
//    collidableIdMap.put(wall4, collidableId);
//    collidableId++;
//
//    //noncontrollables
//    for (Shape shape : nonControllableTypeMap.keySet()) {
//      colorRgb = List.of(0, 0, 0);
//      imgPath = "";
//      if (shape.getFill() instanceof Color) {
//        Color c = (Color) (shape.getFill());
//        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//            (int) c.getBlue() * 255);
//      } else {
//        imgPath = imageMap.get(shape);
//      }
//      properties = new ArrayList<>();
//      properties.add("collidable");
//      properties.add("visible");
//      properties.add(nonControllableTypeMap.get(shape).toString().toLowerCase());
//      staticFriction =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 3.03873
//              : 0.0;
//      kineticFriction =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("surface")) ? 2.03873
//              : 0.0;
//      double mass =
//          (nonControllableTypeMap.get(shape).toString().equalsIgnoreCase("Surface"))
//              ? Double.POSITIVE_INFINITY
//              : 10.0;
//      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
//      if (shape instanceof Ellipse) {
//        collidableObject = new CollidableObject(collidableId,
//            properties, mass,
//            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
//            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
//                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      } else {
//        collidableObject = new CollidableObject(collidableId,
//            properties, mass,
//            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
//            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
//                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      }
//
//      collidableObjects.add(collidableObject);
//      collidableIdMap.put(shape, collidableId);
//      collidableId++;
//    }
//
//    //controllables
//    for (Shape shape : controllables) {
//      colorRgb = List.of(0, 0, 0);
//      imgPath = "";
//      if (shape.getFill() instanceof Color) {
//        Color c = (Color) (shape.getFill());
//        colorRgb = List.of((int) c.getRed() * 255, (int) c.getGreen() * 255,
//            (int) c.getBlue() * 255);
//      } else {
//        imgPath = imageMap.get(shape);
//      }
//      properties = List.of("collidable",  "strikeable", "visible");
//      shapeName = (shape instanceof Ellipse) ? "Circle" : "Rectangle";
//      if (shape instanceof Ellipse) {
//        collidableObject = new CollidableObject(collidableId,
//            properties, 10,
//            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
//            new Dimension(((Ellipse) shape).getRadiusX() * shape.getScaleX(),
//                ((Ellipse) shape).getRadiusY() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      } else {
//        collidableObject = new CollidableObject(collidableId,
//            properties, 10,
//            new Position(posMap.get(shape).get(0), posMap.get(shape).get(1)), shapeName,
//            new Dimension(shape.getLayoutBounds().getWidth() * shape.getScaleX(),
//                shape.getLayoutBounds().getHeight() * shape.getScaleY()),
//            colorRgb, 0.0, 0.0, imgPath);
//      }
//
//      collidableObjects.add(collidableObject);
//      collidableIdMap.put(shape, collidableId);
//      collidableId++;
//    }
//
//    for (Shape shape : collidableIdMap.keySet()) {
//      System.out.println("ID in collidablewrite: " + collidableIdMap.get(shape));
//    }
//
//    builderDirector.constructCollidableObjects(collidableObjects);
//
//  }
}
