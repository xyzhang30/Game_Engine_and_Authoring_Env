package oogasalad.model.gameparser;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.StrikeablesView;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GameObjectShape;
import oogasalad.model.api.data.KeyPreferences;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.view.api.enums.KeyInputType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private static final String JAVAFX_SHAPE_CLASS_PATH = "javafx.scene.shape.";
  private static final Logger LOGGER = LogManager.getLogger(GameLoaderView.class);
  private List<ViewGameObjectRecord> viewGameObjectRecords;
  private StrikeablesView strikeablesView;
  private Map<KeyInputType, String> keys;

  public GameLoaderView(String gameName) throws InvalidShapeException {
    super(gameName);
    createViewRecord();
    createKeysMap();
    System.out.println("FINAL KEYS:"+keys);
  }

  private void createKeysMap() {
    keys = new HashMap<>();
    KeyPreferences keyRecord = gameData.getKeyPreferences();
    for (KeyInputType keyInputType : KeyInputType.values()){
      String typeName = keyInputType.toString().toLowerCase(); //enum object name string
      System.out.println("Record Type Name String:"+typeName);
      try {
        Field field = keyRecord.getClass().getDeclaredField(typeName); //get that field in the record
        System.out.println("Record field:"+field);
        field.setAccessible(true);
        Object value = field.get(keyRecord);
        System.out.println("the value:"+field.get(keyRecord));
        keys.put(keyInputType, (String) value); //passing as a string bc can't have javafx stuff outside view
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace(); // Handle the exception according to your application's logic
      }
    }
  }

  private void createViewRecord() throws InvalidShapeException {
    List<Integer> strikeableIDs = new ArrayList<>();
    viewGameObjectRecords = new ArrayList<>();
    for (GameObjectProperties o : gameData.getGameObjects()) {
      if (o.properties().contains("strikeable")) {
        strikeableIDs.add(o.collidableId());
      }
      int id = o.collidableId();
      String shape = matchShape(o.shape());
      List<Integer> colorRgb = new ArrayList<>();
      for (int i : o.color()) {
        colorRgb.add(validateRgbValue(i));
      }
      double xdimension = o.dimension().xDimension();
      double ydimension = o.dimension().yDimension();
      double startXpos = o.position().xPosition();
      double startYpos = o.position().yPosition();
      ViewGameObjectRecord viewCollidable = new ViewGameObjectRecord(id, colorRgb, shape,
          xdimension,
          ydimension, startXpos, startYpos, o.image(), o.direction());
      viewGameObjectRecords.add(viewCollidable);
    }
    strikeablesView = new StrikeablesView(strikeableIDs);
  }


  private String matchShape(String shape) throws InvalidShapeException {
    System.out.println(shape);
    return switch (shape) {
      case "Circle", "circle" -> JAVAFX_SHAPE_CLASS_PATH + "Ellipse";
      case "Rectangle", "rectangle" -> JAVAFX_SHAPE_CLASS_PATH + "Rectangle";
      default -> {
        LOGGER.error("Shape" + shape + " is not supported");
        throw new InvalidShapeException("Shape " + shape + " is not supported");
      }
    };
  }

  public Map<KeyInputType, String> getInputKeys(){
    return keys;
  }

  public List<ViewGameObjectRecord> getViewCollidableInfo() {
    return viewGameObjectRecords;
  }

  public StrikeablesView getStrikeableIDs() {
    return strikeablesView;
  }

  private int validateRgbValue(int colorValue) {
    if (colorValue < 0) {
      return 0;
    } else if (colorValue > 255) {
      return 255;
    } else {
      return colorValue;
    }
  }

}