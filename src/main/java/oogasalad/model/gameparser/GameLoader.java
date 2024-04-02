package oogasalad.model.gameparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.InvalidFileException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class GameLoader {

  protected JSONObject jsonObject;
  protected List<Map<String, String>> collidables;

  public GameLoader(int id){
    //find the file according to id (for database)
    parseJSON("id");
  }

  public GameLoader(String filePath){
    parseJSON(filePath);
  }

  public void parseJSON(String filePath) throws InvalidFileException {
      try {
        // typecasting obj to JSONObject
        this.jsonObject = (JSONObject) new JSONParser().parse(new FileReader(filePath));
      } catch (IOException | ParseException e) {
        throw new InvalidFileException("FileNotFound", e); // ADD ERROR MESSAGES IN RESOURCE FILES !!!
      }
  }

  // judy
  protected List<Map<String, String>> parseCollidables() {
    // TODO
    return null;
  }

  // should there be a separate parser then for the view stuff since model is calling this one and if
  // model is holding an instance of it then it probably shouldn't be parsing view stuff (?)





  //make the game area and the rules separate in the json??


}
