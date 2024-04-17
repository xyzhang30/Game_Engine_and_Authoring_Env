package oogasalad.view.savegame;

import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import oogasalad.view.AuthoringScreens.NonStrikeableType;

public interface RecordWriter {

  public void writeFieldData();
  
  public void writeFieldData(Map<Shape, Integer> collidableIdMap, List<Shape> strikeables,
      Map<Shape, NonStrikeableType> nonStrikeableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap);
}
