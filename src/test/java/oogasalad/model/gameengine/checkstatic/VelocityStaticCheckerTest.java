package oogasalad.model.gameengine.checkstatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.api.GameObjectRecord;

public class VelocityStaticCheckerTest {

  private VelocityStaticChecker velocityStaticChecker;

  @BeforeEach
  public void setUp() {
    List<Integer> arguments = new ArrayList<>();
    arguments.add(2); // example argument
    velocityStaticChecker = new VelocityStaticChecker(arguments);
  }

  @Test
  public void testIsStatic_StaticObject() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 0.0, 0.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertTrue(velocityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticMovingObject() {
    GameObjectRecord record = new GameObjectRecord(2, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertFalse(velocityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticInvisibleObject() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 0.0, 0.0,
        false, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertTrue(velocityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticArgumentsEmpty() {
    List<Integer> emptyArguments = new ArrayList<>();
    velocityStaticChecker = new VelocityStaticChecker(emptyArguments);
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertFalse(velocityStaticChecker.isStatic(record));
  }

  @Test
  public void testIsStaticArgumentsNotContainingRecordId() {
    GameObjectRecord record = new GameObjectRecord(1, 10.0, 0.0, 0.0, 2.0, 2.0,
        true, 0.5, 0.3, 0.0, 1.0, 1.0, false, false);

    assertTrue(velocityStaticChecker.isStatic(record));
  }
}
