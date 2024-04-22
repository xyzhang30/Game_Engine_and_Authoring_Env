package oogasalad.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConfig {
  private static final String URL = "jdbc:postgresql://localhost/fysicsfundatabase";
  private static final String USER = "konurnordberg";
  private static final String PASSWORD = "mysecretpassword";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}
