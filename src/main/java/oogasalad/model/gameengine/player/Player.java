package oogasalad.model.gameengine.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.ldap.Control;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Controllable;
import oogasalad.model.gameengine.collidable.Ownable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

  private static final Logger LOGGER = LogManager.getLogger(Player.class);
  private final int playerId;
  private List<Controllable> myControllables;
  private List<Ownable> myOwnables;
  private final Map<String, Double> variables;
  private int activeControllableIndex;
  private boolean roundCompleted = false;
  private int turnsCompleted;
  private double temporaryScore;

  public Player(int id) {
    playerId = id;
    roundCompleted = false;
    turnsCompleted = 0;
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }

  public void addControllables(List<Controllable> controllables) {
    myControllables = controllables;
  }
  public void addOwnables(List<Ownable> ownables) {
    myOwnables = ownables;
  }
  public double getVariable(String variable) {
    return variables.getOrDefault(variable, 0.0);
  }

  //TODO
  //later make this an abstraction
  public void updateActiveControllableId() {
    activeControllableIndex = (activeControllableIndex + 1) % myControllables.size();
    while(!(myControllables.get(activeControllableIndex).canControl())) {
      activeControllableIndex = (activeControllableIndex + 1) % myControllables.size();
    }
//    activeControllable =
//        myControllables.get(
//            (myControllables.indexOf(activeControllable) + 1) % myControllables.size()).getCollidable().getId();
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected PlayerRecord getPlayerRecord(boolean active) {
    try {
//      System.out.println(variables.get("score"));
      double score = variables.get("score");
      for (Ownable o : myOwnables) {
        score += o.getTemporaryScore();
      }
      System.out.println();
      return new PlayerRecord(playerId, score,
          myControllables.get(activeControllableIndex).getCollidable().getId(),
          active);
    } catch (NullPointerException e) {
      LOGGER.warn("Invalid player");
      return null;
    }
  }

  public int getId() {
    return playerId;
  }


  //public String toString() {
  //   return "ID " + playerId + "\n\tRoundCompleted " + roundCompleted + "\n\t" + "Score "
  //    + variables.get("score")+"\n\t";
  //}
  public boolean isRoundCompleted() {
    return roundCompleted;
  }

  public void completeRound() {
    roundCompleted = true;
  }

  public int getControllableId() {
    return myControllables.get(activeControllableIndex).getCollidable().getId();
  }

  protected void setFromRecord(PlayerRecord record) {
    variables.put("score", record.score());
  }

  private void clearDelayedPoints() {
    for (Ownable o : myOwnables) {
      o.setTemporaryScore(0);
    }
  }

  protected void applyDelayedScore() {
    for (Ownable o : myOwnables) {
      variables.put("score", variables.get("score") + o.getTemporaryScore());
    }
  }

  public void completeTurn() {
    turnsCompleted++;
  }

  protected int getTurnsCompleted() {
    return turnsCompleted;
  }

  public void startRound() {
    roundCompleted = false;
    turnsCompleted = 0;
    clearDelayedPoints();
  }

//  @Override
//  public void setTemporaryScore(double tempScore) {
//    temporaryScore = tempScore;
//  }
}
