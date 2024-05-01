package oogasalad.view.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.List;
import oogasalad.model.database.Database;
import oogasalad.view.api.exception.IncorrectPasswordException;
import oogasalad.view.api.exception.UserNotFoundException;
import oogasalad.view.controller.DatabaseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import oogasalad.view.database.Leaderboard;

public class DatabaseLoginTest {

  private DatabaseController databaseController;
  private Database databaseView;
  private List<String> currentPlayersManager;
  private Leaderboard leaderboard;

  @BeforeEach
  public void setUp() {
    databaseView = mock(Database.class);
    databaseController = new DatabaseController(leaderboard, currentPlayersManager);
  }

  @Test
  public void testLoginUserSuccess() throws SQLException {
    when(databaseView.registerUser("validUser", "validPassword", "data/nonstrikable_images/flappyBackground")).thenReturn(true);
    assertDoesNotThrow(() -> databaseController.canCreateUser("validUser", "validPassword", "data/nonstrikable_images/flappyBackground"));
  }

  @Test
  public void testLoginUserNonExistentUsername() throws SQLException {
    when(databaseView.loginUser("nonexistentUser", "anyPassword")).thenReturn(false);
    assertThrows(UserNotFoundException.class, () -> databaseController.loginUser("nonexistentUser", "anyPassword"));
  }

  @Test
  public void testLoginUserIncorrectPassword() throws SQLException {
    when(databaseView.loginUser("validUser", "wrongPassword")).thenReturn(false);
    assertThrows(IncorrectPasswordException.class, () -> databaseController.loginUser("validUser", "wrongPassword"));
  }

  @Test
  public void testLoginUserNonExistentUsernameWithAKnownPassword() throws SQLException {
    when(databaseView.loginUser("nonexistentUser", "validPassword")).thenReturn(false);
    assertThrows(UserNotFoundException.class, () -> databaseController.loginUser("nonexistentUser", "validPassword"));
  }


}
