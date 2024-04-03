package oogasalad.model.gameparser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import java.io.IOException;

import oogasalad.model.api.exception.InvalidFileException;


public abstract class GameLoader {

  protected GameData gameData;

  public GameLoader(int id){
    parseJSON("data/miniGolfDemo.json");
  }
  public GameLoader(String filePath){
    parseJSON(filePath);
  }

  private void parseJSON(String filePath) throws InvalidFileException {
    //find the file according to id (for database)
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      this.gameData = objectMapper.readValue(new File(filePath), GameData.class);

    } catch (IOException e) {
      throw new InvalidFileException("Error: Invalid File", e);
    }
  }

  // testing
//  public static void main(String[] args) {
//    GameLoader gameLoader = new GameLoaderModel(0);
//    System.out.println("Game Name: " + gameLoader.gameData.gameName());
//    System.out.println("Number of Collidable Objects: " + gameLoader.gameData.collidableObjects().size());
//    for (CollidableObject co: gameLoader.gameData.collidableObjects()) {
//      System.out.println(co.collidableId());
//      System.out.println(co.mass());
//
//    }
//  }

}
