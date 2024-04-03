package oogasalad.model.gameparser;

import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.PlayerContainer;
import oogasalad.model.gameengine.RulesRecord;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.CollidableContainer;
import oogasalad.model.gameengine.command.Command;

public class GameLoaderModel extends GameLoader {

  private PlayerContainer playerContainer;
  private CollidableContainer collidableContainer;
  private RulesRecord rulesRecord;

  public GameLoaderModel(int id) {
    super(id);
    createPlayerContainer();
    createCollidableContainer();
    createRulesRecord();
  }

  // alisha
  private void createPlayerContainer(){

  }

  public PlayerContainer getPlayerContainer(){
    return playerContainer;
  }

//  public LogicManager getLogicManager(){
//    return new LogicManager();
//  }

  // judy
  private void createCollidableContainer(){

  }


  public CollidableContainer getCollidableContainer(){
    return collidableContainer;
  }

  // alisha
  private void createRulesRecord(){

  }

  public RulesRecord getRulesRecord() {
    return rulesRecord;
  }

//  // judy
//  public Map<Pair, Command> getCollisionHandlers() {
//
//  }


//  // getting firstName and lastName
//  String firstName = (String) jo.get("firstName");
//  String lastName = (String) jo.get("lastName");
//
//    System.out.println(firstName);
//    System.out.println(lastName);
//
//  // getting age
//  long age = (long) jo.get("age");
//    System.out.println(age);
//
//  // getting address
//  Map address = ((Map)jo.get("address"));
//
//  // iterating address Map
//  Iterator<Map.Entry> itr1 = address.entrySet().iterator();
//    while (itr1.hasNext()) {
//    Map.Entry pair = itr1.next();
//    System.out.println(pair.getKey() + " : " + pair.getValue());
//  }
//
//  // getting phoneNumbers
//  JSONArray ja = (JSONArray) jo.get("phoneNumbers");
//
//  // iterating phoneNumbers
//  Iterator itr2 = ja.iterator();
//
//    while (itr2.hasNext())
//  {
//    itr1 = ((Map) itr2.next()).entrySet().iterator();
//    while (itr1.hasNext()) {
//      Map.Entry pair = itr1.next();
//      System.out.println(pair.getKey() + " : " + pair.getValue());
//    }
//  }
//}
}
