package oogasalad.model.gameengine.strike;

import java.util.Optional;
import java.util.function.BiConsumer;
import oogasalad.model.annotations.CommandHelpInfo;
import oogasalad.model.annotations.IsCommand;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.gameobject.scoreable.Scoreable;

/**
 * The IncrementPointStrikePolicy class is a concrete implementation of the StrikePolicy interface
 * which has a strike policy that increases the temporary score of a GameObject that is both
 * scoreable and strikeable.
 * <p>
 * This class utilizes the Optional type to handle cases where the strikeable might not have a
 * corresponding scoreable component. By using Optional, it avoids potential NullPointerExceptions
 * that might occur if attempting to directly invoke methods on a null reference. If the strikeable
 * is also a scoreable, the temporary score is incremented. Otherwise, no action is taken.
 */
@IsCommand(isCommand = true)
@CommandHelpInfo(description = "")
public class IncrementPointStrikePolicy implements StrikePolicy {

  /**
   * Returns a strike policy that increments the temporary score of a scoreable strikeable object,
   * if available.
   *
   * @return A BiConsumer representing that will increment the Game Object's temporary score (if it
   * has a scoreable), and otherwise will do nothing.
   */

  public IncrementPointStrikePolicy() {
  }

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (strikeableID, engine) -> {
      Optional<Scoreable> optionalScoreable = engine.getPlayerContainer().getActive()
          .getStrikeable().asGameObject().getScoreable();
      optionalScoreable.ifPresent(scoreable -> scoreable.incrementTemporaryScore(1.0));
    };
  }
}
