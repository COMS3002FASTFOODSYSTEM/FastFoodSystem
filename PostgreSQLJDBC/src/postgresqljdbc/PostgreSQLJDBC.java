package postgresqljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PostgreSQLJDBC {
   public static void main(String args[]) {
      Connection c = null;
       Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/FFMS",
            "ffms", "FFMS00");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");
         
        // stmt = c.createStatement();
         String sql = "INSERT INTO "+'"'+"CUSTOMER" +'"'+ "VALUES(?,?,?,?,? );";
          PreparedStatement statement = c.prepareStatement(sql);
          statement.setInt(1, 3);
          statement.setString(2, "Paul");
          statement.setString(3, "P32");
          statement.setString(4, "0839625874");
          statement.setString(5, "email@mail.com");
         statement.executeUpdate();
         
         statement.close();
         c.commit();
         c.close();
         
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      System.out.println("Inserted to database successfully");
   }
}