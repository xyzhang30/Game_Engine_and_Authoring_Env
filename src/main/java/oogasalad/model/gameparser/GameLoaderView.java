package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.ControllablesView;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.CollidableShape;
import oogasalad.model.api.exception.InvalidShapeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private static final Logger LOGGER = LogManager.getLogger(GameLoaderView.class);

//  private static final String RESOURCE_FOLDER_PATH = "src/main/resources/";
//  private static final String PROPERTIES_FILE_EXTENSION = ".properties";
//  private static final String COLLIDABLE_PROPERTIES_COMMENT = "collidable objects shape";
//  private static final String COLLIDABLE_CSS_ID_PREFIX = "collidable";

  private List<ViewGameObjectRecord> viewGameObjectRecords;
  private ControllablesView controllablesView;

  public GameLoaderView(String gameName) throws InvalidShapeException {
    super(gameName);
    createViewRecord();
  }

  private void createViewRecord() throws InvalidShapeException {
    List<Integer> controllableIds = new ArrayList<>();
    viewGameObjectRecords = new ArrayList<>();
    for (GameObjectProperties o : gameData.getGameObjects()) {
      if (o.properties().contains("controllable")) {
        controllableIds.add(o.collidableId());
      }
      int id = o.collidableId();
      CollidableShape shape = matchShape(o.shape());
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
    controllablesView = new ControllablesView(controllableIds);
  }

  private CollidableShape matchShape(String shape) throws InvalidShapeException {
    return switch (shape){
      case "Circle" -> CollidableShape.ELLIPSE;
      case "Rectangle" -> CollidableShape.RECTANGLE;
      default ->{
        LOGGER.error("Shape" + shape + " is not supported");
        throw new InvalidShapeException("Shape " + shape + " is not supported");
      }
    };
  }

  public List<ViewGameObjectRecord> getViewCollidableInfo() {
    return viewGameObjectRecords;
  }

  public ControllablesView getControllableIds() {
    return controllablesView;
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