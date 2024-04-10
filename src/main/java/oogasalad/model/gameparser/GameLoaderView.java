package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.api.data.CollidableObject;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private static final String RESOURCE_FOLDER_PATH = "src/main/resources/";
  private static final String PROPERTIES_FILE_EXTENSION = ".properties";
  private static final String COLLIDABLE_PROPERTIES_COMMENT = "collidable objects shape";
  private static final String COLLIDABLE_CSS_ID_PREFIX = "collidable";

  private List<ViewCollidableRecord> viewCollidableRecords;

  public GameLoaderView(String gameName) {
    super(gameName);
    createViewRecord();
  }

  private void createViewRecord() {
    viewCollidableRecords = new ArrayList<>();
    for (CollidableObject o : gameData.getCollidableObjects()) {
      int id = o.collidableId();
      String shape = o.shape();
      List<Integer> colorRgb = new ArrayList<>();
      for (int i : o.color()) {
        colorRgb.add(validateRgbValue(i));
      }
      double xdimension = o.dimension().xDimension();
      double ydimension = o.dimension().yDimension();
      double startXpos = o.position().xPosition();
      double startYpos = o.position().yPosition();
      ViewCollidableRecord viewCollidable = new ViewCollidableRecord(id, colorRgb, shape,
          xdimension,
          ydimension, startXpos, startYpos);
      viewCollidableRecords.add(viewCollidable);
    }
  }

  public List<ViewCollidableRecord> getViewCollidableInfo() {
    return viewCollidableRecords;
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