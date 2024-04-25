package oogasalad.model.gameengine.checkstatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.GameObjectRecord;

public class VisibilityStaticCheckerTest {

  private VisibilityStaticChecker visibilityStaticChecker;

  @BeforeEach
  public void setUp() {
    List<Integer> arguments = new ArrayList<>();
    arguments.add(1); // example argument
    visibilityStaticChecker = new VisibilityStaticChecker(arguments);
  }

  @Test
  public void testIsStaticVisibleObject() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 0.0, 0.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertFalse(visibilityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticVisibleMovingObject() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertFalse(visibilityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticInvisibleMovingObject() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 10.0, 0.0,
        false, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertTrue(visibilityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticArgumentsEmpty() {
    List<Integer> emptyArguments = new ArrayList<>();
    visibilityStaticChecker = new VisibilityStaticChecker(emptyArguments);
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertFalse(visibilityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticArgumentsNotContainingRecordId() {
    GameObjectRecord record = new GameObjectRecord(2, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertTrue(visibilityStaticChecker.isStatic(record));
  }
}
