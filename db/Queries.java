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


	private static void query1 (Connection conn, int topNum, int pgNum) throws SQLException {

		String query = "SELECT m.rtAudienceRating, m.title, m.movieYear, m.rtPictureURL, m.imdbPictureURL "+ 
                     "FROM movie m "+
                     "WHERE m.rtAudienceRating NOT LIKE '%N%' "+
                     "ORDER BY m.rtAudienceRating DESC "+
                     "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);

		try {
         //create the prepared statement and add the criteria
			PreparedStatement ps = conn.prepareStatement(query);

			//process the results
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
            System.out.println(rs.getString("rtAudienceRating"));
			}
         rs.close();
			ps.close();

		}

		catch (SQLException se) {
			se.printStackTrace();
		}
		}



	public static void main(String[] args) throws Exception {
         System.out.println("testing");
         
			// opening database connection to MySQL server
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Database connected successfully");
         
         query1(con,10,1);
	}






}
