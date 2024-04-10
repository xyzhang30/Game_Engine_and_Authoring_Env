package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;

public class ConditionFactory {

  private static final String CONDITION_PATH = "condition.";
  public static Condition createCondition(String conditionName, List<Double> params) throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + CONDITION_PATH + conditionName);
      return (Condition) clazz.getDeclaredConstructor(List.class).newInstance(params);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      throw new InvalidCommandException("");
    }
  }
}


