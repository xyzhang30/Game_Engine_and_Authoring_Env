package oogasalad.view.savegame;

import java.util.List;
import java.util.Map;
import javafx.scene.shape.Shape;
import oogasalad.view.api.enums.NonStrikeableType;

public interface RecordWriter {

  void writeFieldData();

  void writeFieldData(Map<Shape, Integer> collidableIdMap, List<Shape> strikeables,
      Map<Shape, NonStrikeableType> nonStrikeableTypeMap, Map<Shape, String> imageMap,
      Map<Shape, List<Double>> posMap);
}
