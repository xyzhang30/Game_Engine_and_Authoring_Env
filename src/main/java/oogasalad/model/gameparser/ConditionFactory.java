package oogasalad.model.gameparser;

import static oogasalad.model.gameparser.GameLoaderModel.BASE_PATH;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import oogasalad.model.annotations.ExpectedParamNumber;
import oogasalad.model.api.exception.InvalidCommandException;
import oogasalad.model.api.exception.InvalidParameterNumberException;
import oogasalad.model.gameengine.condition.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConditionFactory {

  private static final String CONDITION_PATH = "condition.";
  private static final Logger LOGGER = LogManager.getLogger(ConditionFactory.class);

  public static Condition createCondition(String conditionName, List<Double> params)
      throws InvalidCommandException {
    try {
      Class<?> clazz = Class.forName(BASE_PATH + CONDITION_PATH + conditionName);
      Constructor<?> constructor = clazz.getConstructor(List.class);
      ExpectedParamNumber annotation = constructor.getAnnotation(ExpectedParamNumber.class);

      System.out.println("is annotation null?: "+ annotation);
      if (annotation != null) {
        int expectedParamNumber = annotation.value();
        System.out.println("command: "+conditionName);
        System.out.println("annotation param number: " + expectedParamNumber);
        if (params.size() < expectedParamNumber) {
          LOGGER.error("missing parameters for condition " + conditionName);
          throw new InvalidParameterNumberException("Expected " + expectedParamNumber +
              " parameters for condition " + conditionName + " but found " + params.size());
        }
      }
      return (Condition) clazz.getDeclaredConstructor(List.class).newInstance(params);
    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
             NoSuchMethodException | IllegalAccessException e) {
      LOGGER.error("condition " + conditionName + " is invalid");
      throw new InvalidCommandException("invalid condition");
    }
  }
}


