package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.rank.PlayerRecordComparator;
import oogasalad.model.gameengine.player.PlayerContainer;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PolicyFactory {

  private static final String RANK_COMPARATOR_PATH = "rank.";
  private static final String STRIKE_POLICY_PATH = "strike.";
  private static final String TURN_POLICY_PATH = "turn.";

  private static final Logger LOGGER = LogManager.getLogger(PolicyFactory.class);

  private static <T> T createInstance(String path, String name, Class<T> returnType, Object... args)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + path + name);
      return returnType.cast(clazz.getDeclaredConstructor(getParameterTypes(args)).newInstance(args));
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error(returnType.getSimpleName() + " " + name + " is invalid");
      throw new InvalidCommandException("invalid");
    }
  }

  public static PlayerRecordComparator createRankComparator(String compName)
      throws InvalidCommandException {
    return PolicyFactory.createInstance(RANK_COMPARATOR_PATH, compName, PlayerRecordComparator.class);
  }
  public static TurnPolicy createTurnPolicy(String compName, PlayerContainer container)
      throws InvalidCommandException {
    return PolicyFactory.createInstance(TURN_POLICY_PATH, compName, TurnPolicy.class, container);
  }
  public static StrikePolicy createStrikePolicy(String compName)
      throws InvalidCommandException {
    return PolicyFactory.createInstance(STRIKE_POLICY_PATH, compName, StrikePolicy.class);
  }

private static Class<?>[] getParameterTypes(Object[] args) {
  Class<?>[] parameterTypes = new Class[args.length];
  for (int i = 0; i < args.length; i++) {
    parameterTypes[i] = args[i].getClass();
  }
  return parameterTypes;
}



}