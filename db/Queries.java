import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Queries {

	//	JDBC URL, username and password of MySQL server
	private static final String url = "jdbc:mysql://localhost:3306/DATABASE_GROUP_PROJECT?useSSL=false";
	private static final String user = "root";
	private static final String password = "root";

	// JDBC variables for opening and managing connection
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
   
   public static void main(String[] args) throws Exception {
         // opening database connection to MySQL server
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Database connected successfully");
         System.out.println("*******************************");
         
         query3(con,"action",10,1);//query test (top 10 action films)
	}

	private static void query1 (Connection conn, int topNum, int pgNum) throws SQLException {
      String query = "SELECT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+ 
                     "FROM movie m "+
                     "WHERE m.rtAudienceRating NOT LIKE '%N%' "+
                     "ORDER BY m.rtAudienceRating DESC "+
                     "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
      try {
         //create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
         //process the results
			ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            System.out.print(rs.getString("title")+" | "+
                             rs.getInt("movieYear")+" | "+
                             rs.getString("rtAudienceRating")+" | "+
                             rs.getString("rtPictureURL")+" | "+
                             rs.getString("imdbPictureURL")+"\n");
			}
         rs.close();
			ps.close();
		}
      catch (SQLException se) {
			se.printStackTrace();
		}
	}
   
   /*private static void query2 (Connection conn, String title, int pgNum) throws SQLException {
      String query = "SELECT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+ 
                     "FROM movie m "+
                     "WHERE m.rtAudienceRating NOT LIKE '%N%' "+
                     "ORDER BY m.rtAudienceRating DESC "+
                     "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
      try {
         //create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
         //process the results
			ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            System.out.print(rs.getString("title")+" | "+
                             rs.getInt("movieYear")+" | "+
                             rs.getString("rtAudienceRating")+" | "+
                             rs.getString("rtPictureURL")+" | "+
                             rs.getString("imdbPictureURL")+"\n");
			}
         rs.close();
			ps.close();
		}
      catch (SQLException se) {
			se.printStackTrace();
		}
	}*/
   
   private static void query3 (Connection conn, String genre, int topNum, int pgNum) throws SQLException {
      String query = "SELECT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+ 
                     "FROM movie m, movie_genres mg "+
                     "WHERE m.movieID = mg.movieID and mg.genre = '"+genre.toUpperCase()+"' AND m.rtAudienceRating NOT LIKE '%N%' "+
                     "ORDER BY m.rtAudienceRating DESC "+
                     "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
      try {
         //create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
         //process the results
			ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            System.out.print(rs.getString("title")+" | "+
                             rs.getInt("movieYear")+" | "+
                             rs.getString("rtAudienceRating")+" | "+
                             rs.getString("rtPictureURL")+" | "+
                             rs.getString("imdbPictureURL")+"\n");
			}
         rs.close();
			ps.close();
		}
      catch (SQLException se) {
			se.printStackTrace();
		}
	}

}
