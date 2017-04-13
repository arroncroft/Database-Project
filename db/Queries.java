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

         query10(con,"Toy Story",10,1);//query test
	}

   //search by top rated on RT
	private static void query1 (Connection conn, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
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

   //WIP
   private static void query2 (Connection conn, String title, int topNum, int pgNum) throws SQLException {
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

   //search by genre
   private static void query3 (Connection conn, String genre, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
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

   //search by director
   private static void query4 (Connection conn, String dName, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                     "FROM movie m, movie_directors md "+
                     "WHERE m.movieID = md.movieID and md.directorName LIKE '%"+dName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
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

   //search by actor
   private static void query5 (Connection conn, String aName, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                     "FROM movie m, movie_actors ma "+
                     "WHERE m.movieID = ma.movieID and ma.actorName LIKE '%"+aName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
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

   //search by tag
   private static void query6 (Connection conn, String tagName, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                     "FROM movie m, movie_tags mt, tags t "+
                     "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND t.tagValue LIKE '%"+tagName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
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

   //WIP
   //see top popular directors
   private static void query7 (Connection conn, int leastMovies, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT md.directorName "+
                     "FROM movie m, movie_directors md "+
                     "WHERE "+//WIP Line
                     "HAVING COUNT (*) > "+leastMovies+" "+
                     "ORDER BY AVG(m.rtAudienceRating) DESC "+
                     "LIMIT 10";
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


//WIP
   //SEE TOP ACTORS
   private static void query8 (Connection conn, int k, int topNum, int pgNum) throws SQLException
   {
	   String query = 	"SELECT DISTINCT a.actorName " +
			   			"FROM movie_actors a, movie m " +
			   			"GROUP BY a.actorID " +
			   			"HAVING COUNT (a.movieID) > " + k + " " +
			   			"ORDER BY DESC ( " +
			   							"SELECT average (m. rtAudienceRating) "
			   							"WHERE M.movieID = A.movieID) "
			   			"LIMIT " + topNum + " OFFSET " + ((pgnum -1 ) * topnum );

	   try {
	         //create the prepared statement
				PreparedStatement ps = conn.prepareStatement(query);
	         //process the results
				ResultSet rs = ps.executeQuery();
	         while (rs.next()) {
	            System.out.print(rs.getString("actorName");
				}
	         rs.close();
				ps.close();
			}
	      catch (SQLException se) {
				se.printStackTrace();
			}

   }

   //WIP
   //TIME LINE ALL MOVIES USER HAS RATED and Breakdown by Genere * (THIS STILL NEEDS TO BE DONE, BUT I HAVE NO CLUE HOW)
   private static void query9 (Connection conn, String title, int uid, int topNum, int pgNum) throws SQLException
   {
	   	String query = 	"SELECT DISTINCT m.title, m.movieYear, m.audienceRating, m.rtPictureURL, m.imdbPictureURL, u.rating " +
	   					"FROM movie m, user_Rating u " +
	   					"WHERE u.userID = " + uid + " and m.movieID = u.movieID "\
	   					//why do we have separate values for month date and year? We can simply use date rather than int.
	   					"ORDER BY (u.date_day), MONTH(u.date_month), YEAR(u.date_year)"
   }



   //see all tags attached to movie title
   private static void query10 (Connection conn, String title, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT t.tagValue "+
                     "FROM movie m, movie_tags mt, tags t "+
                     "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND m.title = '"+title+"' "+
                     "ORDER BY mt.tagWeight DESC "+
                     "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
      try {
         //create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
         //process the results
			ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            System.out.println(rs.getString("tagValue"));
			}
         rs.close();
			ps.close();
		}
      catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
