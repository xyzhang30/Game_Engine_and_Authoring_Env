package oogasalad.model.gameengine.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Controllable;
import oogasalad.model.gameengine.collidable.Ownable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

  private static final Logger LOGGER = LogManager.getLogger(Player.class);
  private final int playerId;
  private final List<Integer> myControllables;
  private List<Ownable> myOwnables;
  private final Map<String, Double> variables;
  private int activeControllable;
  private boolean roundCompleted = false;
  private int turnsCompleted;

  public Player(int id, List<Integer> controllables) {
    playerId = id;
    myControllables = controllables;
    roundCompleted = false;
    turnsCompleted = 0;
    activeControllable = myControllables.get(0);
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }


  public double getVariable(String variable) {
    return variables.getOrDefault(variable, 0.0);
  }

  //TODO
  //later make this an abstraction
  public void updateActiveControllableId() {
    activeControllable =
        myControllables.get(
            (myControllables.indexOf(activeControllable) + 1) % myControllables.size());
  }

  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected PlayerRecord getPlayerRecord(boolean active) {
    try {
      double score = variables.get("score");
      for (Ownable o : myOwnables) {
          score += o.getTemporaryScore();
      }
      return new PlayerRecord(playerId, score, activeControllable, active, myControllables);
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
    return myControllables.get(myControllables.indexOf(activeControllable));
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
    for(Ownable o : myOwnables) {
      variables.put("score", variables.get("score") + o.getTemporaryScore());
      o.setTemporaryScore(0);
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
}
