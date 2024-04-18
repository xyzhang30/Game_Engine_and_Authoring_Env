package oogasalad.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an annotation used to indicate the package for available commands for a specific type of rules.
 * Used for prompting user command/condition/policy selection during game authoing
 *
 * @author Alisha Zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AvailableCommands {

  /**
   * @return String, the package containing the available commands for the specified type of rule.
   */
  String commandPackage();
}