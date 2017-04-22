import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.sql.*;


public class QueryMachine {
    //declarations
    private Connection con;
    private BarChart<Number,String> bc;
    //	JDBC URL, username and password of MySQL server
    private String url = "jdbc:mysql://localhost:3306/DATABASE_GROUP_PROJECT?useSSL=false";
    private String user = "root";
    private String password = "root";
    //-------------------------------------
    //QueryMachine: default constructor
    //-------------------------------------
    public QueryMachine(){
    }
    //-------------------------------------
    //QueryMachine: overloaded with new connection variables
    //-------------------------------------
    public QueryMachine(String nurl, String nuser, String npassword){
        url = nurl;
        user = nuser;
        password = npassword;
    }
    //-------------------------------------
    //createConnection:
    //-------------------------------------
    public void createConnection(){
        // opening database connection to MySQL server
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully");
            System.out.println("*******************************");
        }
        catch(Exception ex){
            System.out.println("Unable to connect to database");
        }
    }
    public BarChart<Number, String> getBarChart(){
        return bc;
    }
    //----------------------------------------------------
    //query1
    //----------------------------------------------------
    public String[][] query1(int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m "+
                "WHERE m.rtAudienceRating NOT LIKE '%N%' "+
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");

                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query2: search by movie and also display movie tag
    //----------------------------------------------------
    public String[][] query2 (String title, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][6];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM MOVIE m "+
                "WHERE m.title like '%"+ title +"%' "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);

        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                System.out.println(temp[i][0]);
                String tagHolder = "";
                String query2 = "SELECT t.tagValue "+
                        "FROM movie m, movie_tags mt, tags t "+
                        "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND m.title = '"+temp[i][0]+"' "+
                        "ORDER BY mt.tagWeight DESC "+
                        "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);

                PreparedStatement ps2 = con.prepareStatement(query2);
                ResultSet rs2 = ps2.executeQuery();
                int j = 1;
                while(rs2.next()){
                    if(j%5 == 0)
                        tagHolder = tagHolder + "[" +  rs2.getString("tagValue") + "]\n";
                    else
                        tagHolder = tagHolder + "[" + rs2.getString("tagValue") + "] ";
                    j++;
                }
                temp[i][5] = tagHolder;
                i++;
            }

            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query3: searches by genre
    //----------------------------------------------------
    public String[][] query3 (String genre, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_genres mg "+
                "WHERE m.movieID = mg.movieID and mg.genre = '"+genre.toUpperCase()+"' AND m.rtAudienceRating NOT LIKE '%N%' "+
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query4: searches by director
    //----------------------------------------------------
    public String[][] query4 (String dName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_directors md "+
                "WHERE m.movieID = md.movieID and md.directorName LIKE '%"+dName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query5: Searches by Actor
    //----------------------------------------------------
    public String[][] query5 (String aName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_actors ma "+
                "WHERE m.movieID = ma.movieID and ma.actorName LIKE '%"+aName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {

                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query6: Searches by Tag
    //----------------------------------------------------
    public String[][] query6 (String tagName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_tags mt, tags t "+
                "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND t.tagValue LIKE '%"+tagName.toUpperCase()+"%' AND m.rtAudienceRating NOT LIKE '%N%' "+
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query7: returns top directors
    //----------------------------------------------------
    public String[] query7 (int k, int topNum, int pgNum) throws SQLException {
        String[] temp = new String[topNum];

        String query = "SELECT md.directorName, AVG(rtAudienceRating)\n" +
                "FROM MOVIE m, MOVIE_DIRECTORS md\n" +
                "WHERE m.movieID=md.movieID\n" +
                "GROUP BY md.directorName\n" +
                "HAVING count(md.movieID) >=" + k + "\n" +
                "ORDER BY AVG(rtAudienceRating) DESC\n" +
                "LIMIT " + topNum + " OFFSET " + ((pgNum -1 ) * topNum ) + ";";


        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i] = rs.getString("directorName");
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;

    }
    //----------------------------------------------------
    //query8: returns top actors
    //----------------------------------------------------
    public String[] query8 (int k, int topNum, int pgNum) throws SQLException {
        String[] temp = new String[topNum];

        String query = "SELECT a.actorName, AVG(rtAudienceRating)\n" +
                "FROM MOVIE m, MOVIE_ACTORS a\n" +
                "WHERE m.movieID=a.movieID\n" +
                "GROUP BY a.actorName\n" +
                "HAVING count(a.movieID) >=" + k + "\n" +
                "ORDER BY AVG(rtAudienceRating) DESC\n" +
                "LIMIT " + topNum + " OFFSET " + ((pgNum -1 ) * topNum ) + ";";


        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i] = rs.getString("actorName");
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;

    }
    //----------------------------------------------------
    //query9: Timeline of user rated movies and breakdown
    //        by genre
    //----------------------------------------------------
    public String[][] query9 (int uid, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][3];
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
                "GROUP BY mg.genre\n" +
                "ORDER BY average DESC;";


        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement((query2));

            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                System.out.println(rs.getString("title"));
                temp[i][0] = rs.getString("title");
                temp[i][1] = rs.getString("rating");
                temp[i][2] = rs.getString("date_formatted");
                i++;
            }

            final NumberAxis xAxis = new NumberAxis();
            final CategoryAxis yAxis = new CategoryAxis();
            bc = new BarChart<>(xAxis,yAxis);
            bc.setPrefSize(500,500);
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Percentage");


            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                System.out.printf("%s | %s%%\n", rs2.getString("genre"),rs2.getString("average"));
                double average = Double.parseDouble((rs2.getString("average")));
                String genre = rs2.getString("genre");
                dataSeries1.getData().add(new XYChart.Data(average  , genre));

            }
            bc.getData().add(dataSeries1);
            //stage2.setScene(new Scene(new HBox(4, bc)));
            //stage2.show();
            rs.close();
            ps.close();
            rs2.close();
            ps2.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //query10: Searches by movie, show tags
    //----------------------------------------------------
    public String[] query10 (String title, int topNum, int pgNum) throws SQLException {
        String[] A = new String[topNum];
        String query = "SELECT t.tagValue "+
                "FROM movie m, movie_tags mt, tags t "+
                "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND m.title = '"+title+"' "+
                "ORDER BY mt.tagWeight DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            int i = 0;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                A[i] = rs.getString("tagValue");
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return A;
    }
    //----------------------------------------------------
    //queryRBD: recommend by directors of movies selected
    //----------------------------------------------------
    public String[][] queryRBD (String list, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_directors md "+
                "WHERE m.movieID = md.movieID AND m.rtAudienceRating NOT LIKE '%N%' "+
                "AND md.directorID IN (SELECT directorID " +
                "FROM movie_directors mdd, movie mm " +
                "WHERE mm.movieID = mdd.movieID and mm.title IN (" + list + ")) " +//should be like ('a', 'b', 'c') add parethesis and stuff
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
    //----------------------------------------------------
    //queryRBG: recommend by genre of movies selected
    //----------------------------------------------------
    public String[][] queryRBG (String list, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
        String query = "SELECT DISTINCT m.title, m.movieYear, m.rtAudienceRating, m.rtPictureURL, m.imdbPictureURL "+
                "FROM movie m, movie_genres mg "+
                "WHERE m.movieID = mg.movieID AND m.rtAudienceRating NOT LIKE '%N%' "+
                "AND mg.genre IN (SELECT mgg.genre " +
                "FROM movie_genres mgg, movie mm " +
                "WHERE mm.movieID = mgg.movieID and mm.title IN (" + list + ")) " +//should be like ('a', 'b', 'c') add parethesis and stuff
                "ORDER BY m.rtAudienceRating DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = con.prepareStatement(query);
            //process the results
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                temp[i][0] = rs.getString("title");
                temp[i][1] = "" + rs.getInt("movieYear");
                temp[i][2] = rs.getString("rtAudienceRating");
                if(!rs.getString("rtPictureURL").isEmpty())
                    temp[i][3] = rs.getString("rtPictureURL");
                else {
                    temp[i][3] = "noimage.png";
                }
                if(!rs.getString("imdbPictureURL").isEmpty())
                    temp[i][4] = rs.getString("imdbPictureURL");
                else
                    temp[i][4] = "noimage.png";
                i++;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        return temp;
    }
}
