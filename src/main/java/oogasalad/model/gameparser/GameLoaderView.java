package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.StrikeablesView;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GameObjectShape;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private final String JAVAFX_SHAPE_CLASS_PATH = "javafx.scene.shape.";
  private static final Logger LOGGER = LogManager.getLogger(GameLoaderView.class);

//  private static final String RESOURCE_FOLDER_PATH = "src/main/resources/";
//  private static final String PROPERTIES_FILE_EXTENSION = ".properties";
//  private static final String COLLIDABLE_PROPERTIES_COMMENT = "collidable objects shape";
//  private static final String COLLIDABLE_CSS_ID_PREFIX = "collidable";

  private List<ViewGameObjectRecord> viewGameObjectRecords;
  private StrikeablesView strikeablesView;

  public GameLoaderView(String gameName) throws InvalidShapeException {
    super(gameName);
    createViewRecord();
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