package oogasalad.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an annotation used to indicate whether a type of command/condition should have a singular
 * command or multiple commands Used for prompting user command/condition/policy selection during
 * game authoing
 *
 * @author Alisha Zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ChoiceType {

  /**
   * @return boolean, true if the specific type of commands takes a singular command (false if takes
   * in multiple)
   */
  boolean singleChoice();
}