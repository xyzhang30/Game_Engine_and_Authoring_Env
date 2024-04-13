package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.turn.TurnPolicy;

public class TurnPolicyFactory {

  private static final String TURN_POLICY_PATH = "turn.";

  public static TurnPolicy createTurnPolicy(String policyName, PlayerContainer playerContainer)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + TURN_POLICY_PATH + policyName);
      return (TurnPolicy) clazz.getDeclaredConstructor(PlayerContainer.class)
          .newInstance(playerContainer);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("");
    }
  }
}


