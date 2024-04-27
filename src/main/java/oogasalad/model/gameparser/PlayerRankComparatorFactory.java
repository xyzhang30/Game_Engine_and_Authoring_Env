package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.rank.PlayerRecordComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerRankComparatorFactory {

  private static final String RANK_COMPARATOR_PATH = "rank.";
  private static final Logger LOGGER = LogManager.getLogger(PlayerRankComparatorFactory.class);

  public static PlayerRecordComparator createRankComparator(String compName)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + RANK_COMPARATOR_PATH + compName);
      return (PlayerRecordComparator) clazz.getDeclaredConstructor().newInstance();
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("rank comparator " + compName + " is invalid");
      throw new InvalidCommandException("invalid command");
    }
  }
}
