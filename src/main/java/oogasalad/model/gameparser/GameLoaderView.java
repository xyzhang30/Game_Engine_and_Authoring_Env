package oogasalad.model.gameparser;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.ViewCollidableRecord;
import oogasalad.model.gameparser.data.CollidableObject;

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
    for (CollidableObject o : gameData.collidableObjects()) {
      int id = o.collidableId();
      String shape = o.shape();
      List<Integer> colorRgb = new ArrayList<>();
      for (int i : o.color()){
        colorRgb.add(validateRgbValue(i));
      }
      double xdimension = o.dimension().xDimension();
      double ydimension = o.dimension().yDimension();
      double startXpos = o.position().xPosition();
      double startYpos = o.position().yPosition();
      ViewCollidableRecord viewCollidable = new ViewCollidableRecord(id, colorRgb, shape, xdimension,
          ydimension, startXpos, startYpos);
      viewCollidableRecords.add(viewCollidable);
    }
  }

  public List<ViewCollidableRecord> getViewCollidableInfo(){
    return viewCollidableRecords;
  }

  private int validateRgbValue(int colorValue){
    if (colorValue < 0){
      return 0;
    } else if (colorValue > 255) {
      return 255;
    } else {
      return colorValue;
    }
  }

  //alisha
//  private void generateCollidableShapeConfig(String gameName) {
//    Properties properties = new Properties();
//    for (CollidableObject o : gameData.collidableObjects()){
//      int id = o.collidableId();
//      String shape = o.shape();
//      properties.setProperty(String.valueOf(id), shape);
//    }
//    String filePath = RESOURCE_FOLDER_PATH + gameName + PROPERTIES_FILE_EXTENSION;
//
//    try (OutputStream output = new FileOutputStream(filePath)) {
//      properties.store(output, COLLIDABLE_PROPERTIES_COMMENT);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//  }
}
