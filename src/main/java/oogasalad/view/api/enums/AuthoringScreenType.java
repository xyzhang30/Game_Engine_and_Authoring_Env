package oogasalad.view.api.enums;

/**
 * Enumeration representing different types of authoring screens in the authoring environment.
 * Each enum constant has an associated string representation.
 *
 * @author Judy He
 */
public enum AuthoringScreenType {
  /**
   * Screen type for configuring game objects.
   */
  GAMEOBJECTS("Game Objects"),
  /**
   * Screen type for configuring interactions between different game elements.
   */
  INTERACTIONS("Interactions"),
  /**
   * Screen type for configuring policies such as game rules and mechanics.
   */
  POLICIES("Policies");

  private final String string;

  /**
   * Constructs a new AuthoringScreenType with the specified string representation.
   *
   * @param name the string representation of the enum constant.
   */
  AuthoringScreenType(String name){string = name;}

  /**
   * Returns the string representation of the enum constant.
   *
   * @return the string representation of the enum constant.
   */
  @Override
  public String toString() {
    return string;
  }
}
