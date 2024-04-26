package oogasalad.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConfig {
  private static final String URL = "jdbc:postgresql://fysicsfun.c7m0ky02uhmn.us-east-2.rds"
      + ".amazonaws.com:5432/fysicsfundatabase2";

  private static final String USER = "konurnordberg";
  private static final String PASSWORD = "wehavethebestprojectbyfar";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

}
