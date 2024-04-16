package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrikePolicyFactory {

  private static final String STRIKE_POLICY_PATH = "strike.";
  private static final Logger LOGGER = LogManager.getLogger(StrikePolicyFactory.class);

  public static StrikePolicy createStrikePolicy(String policyName)
      throws InvalidCommandException {
    try {
      System.out.println("policyName:"+policyName);
      Class<?> clazz = Class.forName(BASE_PATH + STRIKE_POLICY_PATH + policyName);
      return (StrikePolicy) clazz.getDeclaredConstructor().newInstance();
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("strike policy command " + policyName + " is invalid" + e.getMessage());
      throw new InvalidCommandException("invalid command");
    }
  }
}


