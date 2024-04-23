package oogasalad.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import oogasalad.model.database.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

  private DatabaseConfig dbConfig;

  @BeforeEach
  public void setUp() {
    dbConfig = new DatabaseConfig();
  }

  @Test
  public void testDatabaseConnection() {
    try (Connection conn = dbConfig.getConnection()) {
      assertNotNull(conn, "Connection should not be null");
      assertFalse(conn.isClosed(), "Connection should be open");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "Connection failed";
    }
  }

  @Test
  public void testDatabaseQuery() {
    try (Connection conn = dbConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Players")) {
      assertTrue(rs.next(), "Query should return at least one row");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "Query failed";
    }
  }

  @Test
  public void testPlayerTableExists() {
    try (Connection conn = dbConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'players'")) {
      assertTrue(rs.next(), "Players table should exist in the database");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "Players table does not exist";
    }
  }

  @Test
  public void testGamesTableExists() {
    try (Connection conn = dbConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'games'")) {
      assertTrue(rs.next(), "Games table should exist in the database");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "Games table does not exist";
    }
  }

  @Test
  public void testGameScoresTableExists() {
    try (Connection conn = dbConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'gamescores'")) {
      assertTrue(rs.next(), "GameScores table should exist in the database");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "GameScores table does not exist";
    }
  }

  @Test
  public void testPermissionsTableExists() {
    try (Connection conn = dbConfig.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'permissions'")) {
      assertTrue(rs.next(), "Permissions table should exist in the database");
    } catch (Exception e) {
      e.printStackTrace();
      assert false : "Permissions table does not exist";
    }
  }
}
