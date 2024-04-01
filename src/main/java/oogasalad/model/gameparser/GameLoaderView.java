package oogasalad.model.gameparser;

import java.io.PrintWriter;
import java.io.FileWriter;


public class GameLoaderView extends GameLoader{

  public GameLoaderView(int id) {
    super(id);
    generateStyleSheet();
  }


  private void generateStyleSheet(){
    //writes the color + dimension + filepath (if applicable) into css
    try (PrintWriter writer = new PrintWriter(new FileWriter(defaultFolderPath + gameName))) {
      for (int i = 0; i < collidables.length(); i++) {
        JSONObject collidable = collidables.getJSONObject(i);
        String id = collidable.getString("collidable_id");
        JSONArray colorArray = collidable.getJSONArray("color");

        int red = validateColorComponent(colorArray.getI(0));
        int green = validateColorComponent(colorArray.getInt(1));
        int blue = validateColorComponent(colorArray.getInt(2));

        String rgb = red + "," + green + "," + blue;      writer.println("#collidable_" + id + " {");
        writer.println("    -fx-background-color: rgb(" + rgb + ");");
        writer.println("}");
      }
    } catch (IOException e){
      System.out.println("Error generating CSS file: " + e.getMessage());
    }
  }

  private int validateColorComponent(int value) {
    if (value < 0) {
      return 0; // Set to 0 if value is negative
    } else if (value > 255) {
      return 255; // Set to 255 if value is greater than 255
    } else {
      return value; // Return original value if within valid range
    }
  }

  private void writeColor() {

  }

  private void writeDimension() {

  }

  private void writeCollidables() {

  }
}


