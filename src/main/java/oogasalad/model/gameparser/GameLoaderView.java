package oogasalad.model.gameparser;

import java.io.PrintWriter;
import java.io.FileWriter;
import org.json.simple.JSONArray;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  public GameLoaderView(String gameName) {
    super(gameName);
    generateStyleSheet();
  }

  //alisha
  private void generateStyleSheet() {
    //writes the color + dimension + filepath (if applicable) into css
    try (PrintWriter writer = new PrintWriter(new FileWriter(defaultFolderPath + gameName))) {
      for (int i = 0; i < this.collidables.length(); i++) {
        String collidable = collidables.getJSONObject(i);
        String id = collidable.getString("collidable_id");
        JSONArray colorArray = collidable.getJSONArray("color");

        int red = validateColorComponent(colorArray.getI(0));
        int green = validateColorComponent(colorArray.getInt(1));
        int blue = validateColorComponent(colorArray.getInt(2));

        String rgb = red + "," + green + "," + blue;
        writer.println("#collidable_" + id + " {");
        writer.println("    -fx-background-color: rgb(" + rgb + ");");
        //separate this into method / config for the syntax
        writer.println("}");
      }
    } catch (IOException e) {
      System.out.println("Error generating CSS file: " + e.getMessage());
    }
  }

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
  private void generateCollidableShapeConfig() {

  }
}



