package ua.com.foxminded.labs.task7.sqljdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

   private static BasicDataSource ds = new BasicDataSource();
   private static BasicDataSource test = new BasicDataSource();

   static {
      ds.setUrl("jdbc:postgresql://127.0.0.1:5432/school");
      ds.setUsername("postgres");
      ds.setPassword("root");
      ds.setMinIdle(5);
      ds.setMaxIdle(10);
      ds.setMaxOpenPreparedStatements(100);
   }

   static {
      test.setUrl("jdbc:h2:~/test");
      test.setUsername("root");
      test.setPassword("root");
      test.setMinIdle(5);
      test.setMaxIdle(10);
      test.setMaxOpenPreparedStatements(100);
   }

   private DBCPDataSource() {
   }

   public static Connection getConnection() throws SQLException {
      return ds.getConnection();
   }

   public static Connection getConnectionTest() throws SQLException {
      return test.getConnection();
   }
}