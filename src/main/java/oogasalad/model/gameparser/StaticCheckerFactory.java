package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.checkstatic.StaticChecker;

public class StaticCheckerFactory {

  private static final String STATIC_COMPARATOR_PATH = "checkstatic.";


  public static List<StaticChecker> createStaticChecker(Map<String, List<Integer>> params)
      throws InvalidCommandException {
    List<StaticChecker> checkers = new ArrayList<>();
    try {
      for (String key : params.keySet()) {
        Class<?> clazz = Class.forName(BASE_PATH + STATIC_COMPARATOR_PATH + key);
        checkers.add(
            (StaticChecker) clazz.getDeclaredConstructor(List.class).newInstance(params.get(key)));
      }
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("invalid command");
    }
    return checkers;
  }
}
