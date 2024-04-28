package oogasalad.model.gameparser;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.model.api.StrikeablesView;
import oogasalad.model.api.ViewGameObjectRecord;
import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.api.data.GameObjectShape;
import oogasalad.model.api.data.KeyPreferences;
import oogasalad.model.api.exception.InvalidColorParsingException;
import oogasalad.model.api.exception.InvalidImageException;
import oogasalad.model.api.exception.InvalidShapeException;
import oogasalad.model.api.exception.MissingJsonGameInfoException;
import oogasalad.view.api.enums.KeyInputType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Concrete implementation of GameLoader for passing game data necessary for the View.
 *
 * @author Judy He, Alisha Zhang
 */
public class GameLoaderView extends GameLoader {

  private static final Logger LOGGER = LogManager.getLogger(GameLoaderView.class);
  private List<ViewGameObjectRecord> viewGameObjectRecords;
  private StrikeablesView strikeablesView;
  private Map<KeyInputType, String> keys;

  /**
   * constructor, creates view side game object record and key map for view upon initialization
   * @param gameName String, name (path) of the game being parsed
   * @throws InvalidShapeException Shape in game data file cannot be recognized
   * @throws InvalidColorParsingException Color in game data file cannot be recognized
   */
  public GameLoaderView(String gameName) throws InvalidShapeException, InvalidColorParsingException{
    super(gameName);
    createViewRecord("Default");
    createKeysMap();
  }

  /**
   * Gets the available mods for the game
   * @return List, names of all available mods for the game
   */
  public List<String> getMods() {
    Set<String> mods = new HashSet<>();
    for (GameObjectProperties go : gameData.getGameObjectProperties()) {
      for (String s : go.image().keySet()) {
        mods.add(s);
      }
      for (String s : go.color().keySet()) {
        mods.add(s);
      }
    }
    mods.remove("Default");
    List<String> finalMods = new ArrayList<>(mods);
    finalMods.add(0, "Default");
    return finalMods;

  }

  private void createKeysMap() {
    keys = new HashMap<>();
    KeyPreferences keyRecord = gameData.getKeyPreferences();
    for (KeyInputType keyInputType : KeyInputType.values()) {
      String typeName = keyInputType.toString().toLowerCase(); //enum object name string
      try {
        Field field = keyRecord.getClass()
            .getDeclaredField(typeName); //get that field in the record
        field.setAccessible(true);
        Object value = field.get(keyRecord);
        keys.put(keyInputType,
            (String) value); //passing as a string bc can't have javafx stuff outside view
      } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
        LOGGER.error("Missing key preference field in game JSON file");
        throw new MissingJsonGameInfoException("Missing key preference field in game JSON file");
      }
    }
  }

  /**
   * creates game object record with info of the game object the view needs
   * @param mod String, name of the current mod
   * @throws InvalidShapeException
   * @throws InvalidColorParsingException
   */
  public void createViewRecord(String mod) throws InvalidShapeException, InvalidColorParsingException {
    List<Integer> strikeableIDs = new ArrayList<>();
    viewGameObjectRecords = new ArrayList<>();
    for (GameObjectProperties o : gameData.getGameObjectProperties()) {
      if (o.properties().contains("strikeable")) {
        strikeableIDs.add(o.collidableId());
      }
      int id = o.collidableId();
      String shape = o.shape();
      List<Integer> colorRgb = new ArrayList<>();
      try {
        for (int i : o.color().getOrDefault(mod, o.color().get("Default"))) {
          colorRgb.add(validateRgbValue(i));
        }
      } catch (NullPointerException e){
        LOGGER.error("Color error during parsing - " +e.getMessage());
        throw new InvalidColorParsingException(e.getMessage());
      }
      double xdimension = o.dimension().xDimension();
      double ydimension = o.dimension().yDimension();
      double startXpos = o.position().xPosition();
      double startYpos = o.position().yPosition();
      ViewGameObjectRecord viewCollidable = new ViewGameObjectRecord(id, colorRgb, shape,
          xdimension,
          ydimension, startXpos, startYpos, o.image().getOrDefault(mod, ""), o.direction());
      viewGameObjectRecords.add(viewCollidable);
    }
    strikeablesView = new StrikeablesView(strikeableIDs);
  }

  public Map<KeyInputType, String> getInputKeys() {
    return keys;
  }

  public List<ViewGameObjectRecord> getViewCollidableInfo() {
    return viewGameObjectRecords;
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


  public String getGameName(){
    return gameData.getGameName();
  }

}