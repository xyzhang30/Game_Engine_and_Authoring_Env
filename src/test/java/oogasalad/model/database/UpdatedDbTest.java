import java.util.Map;
import javafx.collections.ObservableList;
import oogasalad.model.database.Database;
import oogasalad.model.database.GameScore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpdatedDbTest {

  @Test
  public void testGetPlayerPermissionsForGames() throws SQLException {
    // Mocks
    Connection conn = Mockito.mock(Connection.class);
    PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);

    // Mock behaviors
    when(conn.prepareStatement(any(String.class))).thenReturn(pstmt);
    when(pstmt.executeQuery()).thenReturn(rs);
    when(rs.next()).thenReturn(true, false); // Simulate single row result
    when(rs.getString("username")).thenReturn("testUser");
    when(rs.getString("permissions")).thenReturn("Player");

    // Test
    Database database = new Database();
    Map<String, Boolean> permissions = database.getPlayerPermissionsForGames("testGame");

    // Assertions
    assertEquals(1, permissions.size());
    assertEquals(true, permissions.get("testUser"));
  }

  @Test
  public void testGetGeneralHighScoresForGame() throws SQLException {
    // Mocks
    Connection conn = Mockito.mock(Connection.class);
    PreparedStatement pstmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);

    // Mock behaviors
    when(conn.prepareStatement(any(String.class))).thenReturn(pstmt);
    when(pstmt.executeQuery()).thenReturn(rs);
    when(rs.next()).thenReturn(true, false); // Simulate single row result
    when(rs.getString("playerusername")).thenReturn("testUser");
    when(rs.getInt("score")).thenReturn(100);
    when(rs.getBoolean("gameresult")).thenReturn(true);

    // Test
    Database database = new Database();
    ObservableList<GameScore> scores = database.getGeneralHighScoresForGame("testGame", true);

    // Assertions
    assertEquals(1, scores.size());
    assertEquals("testUser", scores.get(0).playerName());
    assertEquals(100, scores.get(0).score());
    assertEquals(true, scores.get(0).gameWon());
  }
}
