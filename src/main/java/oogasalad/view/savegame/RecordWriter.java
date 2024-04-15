package oogasalad.view.savegame;

import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import oogasalad.view.AuthoringScreens.NonControllableType;

public interface RecordWriter {

  public void writeFieldData();
  
  public void writeFieldData(Map<Shape, Integer> collidableIdMap, List<Shape> controllables,
      Map<Shape, NonControllableType> nonControllableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap);
}
