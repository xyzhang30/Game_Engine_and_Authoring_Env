package oogasalad.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an annotation used to indicate the expected size of the parameter list for each
 * command/condition; Used to validate command during game parsing
 *
 * @author Alisha Zhang
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface ExpectedParamNumber {

  /**
   * @return int value of the expected size of the param list
   */
  int value();
}