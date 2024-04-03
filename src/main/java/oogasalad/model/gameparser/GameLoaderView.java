package oogasalad.model.gameparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import oogasalad.model.gameparser.data.CollidableObject;
import org.json.simple.JSONArray;
import java.util.Properties;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private static final String RESOURCE_FOLDER_PATH = "src/main/resources";
  private static final String PROPERTIES_FILE_EXTENSION = ".properties";
  private static final String COLLIDABLE_PROPERTIES_COMMENT = "collidable objects shape";

  public GameLoaderView(String gameName) {
    super(gameName);
//    generateStyleSheet(gameName);
    generateCollidableShapeConfig(gameName);
  }

  //alisha
//  private void generateStyleSheet(String gameName) {
//    //writes the color + dimension + filepath (if applicable) into css
//    try (PrintWriter writer = new PrintWriter(new FileWriter(defaultFolderPath + gameName))) {
//      for (int i = 0; i < this.collidables.length(); i++) {
//        String collidable = collidables.getJSONObject(i);
//        String id = collidable.getString("collidable_id");
//        JSONArray colorArray = collidable.getJSONArray("color");
//
//        int red = validateColorComponent(colorArray.getI(0));
//        int green = validateColorComponent(colorArray.getInt(1));
//        int blue = validateColorComponent(colorArray.getInt(2));
//
//        String rgb = red + "," + green + "," + blue;
//        writer.println("#collidable_" + id + " {");
//        writer.println("    -fx-background-color: rgb(" + rgb + ");");
//        //separate this into method / config for the syntax
//        writer.println("}");
//      }
//    } catch (IOException e) {
//      System.out.println("Error generating CSS file: " + e.getMessage());
//    }
//  }

  //alisha
  private int validateColorComponent(int value) {
    if (value < 0) {
      return 0; // Set to 0 if value is negative
    } else if (value > 255) {
      return 255; // Set to 255 if value is greater than 255
    } else {
      return value; // Return original value if within valid range
    }
  }

//  private void writeColor() {
//
//  }
//
//  private void writeDimension() {
//
//  }

  //alisha
  private void generateCollidableShapeConfig(String gameName) {
    Properties properties = new Properties();
    for (CollidableObject o : gameData.collidableObjects()){
      int id = o.collidableId();
      String shape = o.shape();
      properties.setProperty(String.valueOf(id), shape);
    }
    String filePath = RESOURCE_FOLDER_PATH + gameName + PROPERTIES_FILE_EXTENSION;

    try (OutputStream output = new FileOutputStream(filePath)) {
      properties.store(output, COLLIDABLE_PROPERTIES_COMMENT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}



>>>>>>> 8ac0c379acbe466ac98e6c87a6b9f32661693bb4
