import javax.xml.transform.Result;
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

         query9(con, 170, 10, 1);
	}

   //search by top rated on RT
	private static void query1 (Connection conn, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                     "FROM MOVIE m "+
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

   private static void query2 (Connection conn, String title, int topNum, int pgNum) throws SQLException {
      String query = "SELECT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL, t.tagValue "+
                     "FROM MOVIE m, MOVIE_TAGS mt, TAGS t "+
                     "WHERE m.rtAudienceRating NOT LIKE '%N%' and m.movieID = mt.movieID and mt.tagID = t.tagID and m.title like '"+ title +"' "+
                     "ORDER BY mt.tagWeight DESC "+

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
                             rs.getString("imdbPictureURL")+
                             rs.getString( "tagValue")+"\n");
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
                     "FROM MOVIE m, MOVIE_GENRES mg "+
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
                     "FROM MOVIE m, MOVIE_DIRECTORS md "+
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
                     "FROM MOVIE m, MOVIE_ACTORS ma "+
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
                     "FROM MOVIE m, MOVIE_TAGS mt, TAGS t "+
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

   //see top popular directors
   private static void query7 (Connection conn, int k, int topNum, int pgNum) throws SQLException {

       String query = "SELECT md.directorName, AVG(rtAudienceRating)\n" +
               "FROM MOVIE m, MOVIE_DIRECTORS md\n" +
               "WHERE m.movieID=md.movieID\n" +
               "GROUP BY md.directorName\n" +
               "HAVING count(md.movieID) >" + k + "\n" +
               "ORDER BY AVG(rtAudienceRating) DESC\n" +
               "LIMIT " + topNum + " OFFSET " + ((pgNum -1 ) * topNum ) + ";";

      try {
         //create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
         //process the results
			ResultSet rs = ps.executeQuery();
         while (rs.next()) {
            System.out.println(rs.getString("directorName"));
		}
	 	rs.close();
			ps.close();
		}
      catch (SQLException se) {
			se.printStackTrace();
		}
	}


   //See top ACTORS by Rotten Tomato User Score having starred in at least k movies
   private static void query8 (Connection conn, int k, int topNum, int pgNum) throws SQLException
   {

	   String query = "SELECT a.actorName, AVG(rtAudienceRating)\n" +
                      "FROM MOVIE m, MOVIE_ACTORS a\n" +
                      "WHERE m.movieID=a.movieID\n" +
                      "GROUP BY a.actorName\n" +
                      "HAVING count(a.movieID) >" + k + "\n" +
                      "ORDER BY AVG(rtAudienceRating) DESC\n" +
                      "LIMIT " + topNum + " OFFSET " + ((pgNum -1 ) * topNum ) + ";";


		try {
			//create the prepared statement
			PreparedStatement ps = conn.prepareStatement(query);
			//process the results
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("actorName"));
			}
			rs.close();
			ps.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}

   }

   //TIME LINE ALL MOVIES USER HAS RATED and Breakdown by Genre
   private static void query9 (Connection conn, int uid, int topNum, int pgNum) throws SQLException
   {
       // Gets the movie titles/ratings/dates for the given user id
	   	String query1 = "SELECT m.title, urmt.rating, DATE_FORMAT(FROM_UNIXTIME(urmt.timestamp/1000), '%e %b %Y') AS 'date_formatted'\n" +
                        "FROM MOVIE m, USER_RATED_MOVIES_TIMESTAMP urmt\n" +
                        "WHERE urmt.userID = " + uid + " AND m.movieID = urmt.movieID\n" +
                        "ORDER BY urmt.timestamp\n" +
                        "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);

        // Gets the the genres and percentages each genre appears in the user ratings for the specified user
	   	String query2 ="SELECT mg.genre, 100 * count(mg.genre) / (SELECT count(m.title)\n" +
                                                                 "FROM MOVIE m, USER_RATED_MOVIES_TIMESTAMP urmt\n" +
                                                                 "WHERE urmt.userID =" + uid + " AND m.movieID = urmt.movieID\n" +
                                                                 ") AS 'average'\n" +
                       "FROM MOVIE m, USER_RATED_MOVIES_TIMESTAMP urmt, MOVIE_GENRES mg\n" +
                       "WHERE urmt.userID =" + uid + " AND m.movieID = urmt.movieID AND m.movieID = mg.movieID\n" +
                       "GROUP BY mg.genre;";


       try {
           //create the prepared statement
           PreparedStatement ps = conn.prepareStatement(query1);
           PreparedStatement ps2 = conn.prepareStatement((query2));

           //process the results
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               System.out.println(rs.getString("rating") + " | " + rs.getString("date_formatted"));
           }

           ResultSet rs2 = ps2.executeQuery();
           while (rs2.next()) {
               System.out.printf("%s | %s%%\n", rs2.getString("genre"),rs2.getString("average"));
           }
           rs.close();
           ps.close();
       }
       catch (SQLException se) {
           se.printStackTrace();
       }


   }

   //see all tags attached to movie title
   private static void query10 (Connection conn, String title, int topNum, int pgNum) throws SQLException {
      String query = "SELECT DISTINCT t.tagValue "+
                     "FROM MOVIE m, MOVIE_TAGS mt, TAGS t "+
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
