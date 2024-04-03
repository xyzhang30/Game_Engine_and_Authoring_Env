package oogasalad.view;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import oogasalad.model.gameparser.GameLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Enhanced GameLoader for generating CSS for game view components.
 */
public class FrontendParser extends GameLoader {

  // Assuming the super class GameLoader properly initializes a game with a name
  public FrontendParser(String gameName) {
    super(gameName);
    generateStyleSheet();
  }

  /**
   * Generates a CSS file with styles for each collidable object.
   */
  private void generateStyleSheet() {
    String gameName = "sampleMiniGolf";
    try (PrintWriter writer = new PrintWriter(new FileWriter(gameName + ".css"))) {
      JSONArray collidables = loadCollidables(); // Assuming this method retrieves your JSONArray of collidables
      for (Object o : collidables) {
        if (o instanceof JSONObject) {
          JSONObject collidable = (JSONObject) o;
          writeCollidableStyle(writer, collidable);
        }
      }
    } catch (IOException e) {
      System.out.println("Error generating CSS file: " + e.getMessage());
    }
  }

  /**
   * Loads and returns the JSONArray of collidable objects.
   * This method assumes you have a way to load or access your collidables JSONArray.
   */
  private JSONArray loadCollidables() {
    // Placeholder method body - you should implement this based on how your game data is loaded
    return new JSONArray(); // This should be replaced with actual loading logic
  }

  /**
   * Writes CSS for a single collidable object.
   */
  private void writeCollidableStyle(PrintWriter writer, JSONObject collidable) {
    String id = String.valueOf(collidable.get("collidable_id"));
    JSONArray colorArray = (JSONArray) collidable.get("color");

    String rgb = formatColor(colorArray);
    writer.println("#collidable_" + id + " {");
    writer.println("    -fx-background-color: rgb(" + rgb + ");");
    // Here you could add dimensions, shapes, or other properties as needed
    writer.println("}");
  }

  /**
   * Formats the color information into a CSS-compatible string.
   */
  private String formatColor(JSONArray colorArray) {
    int red = validateColorComponent(((Long) colorArray.get(0)).intValue());
    int green = validateColorComponent(((Long) colorArray.get(1)).intValue());
    int blue = validateColorComponent(((Long) colorArray.get(2)).intValue());
    return red + "," + green + "," + blue;
  }

  /**
   * Validates and adjusts a color component to be within the valid range.
   */
  private int validateColorComponent(int value) {
    return Math.min(255, Math.max(0, value));
  }

  // Future expansion methods for shape and dimension handling could be added here
}
