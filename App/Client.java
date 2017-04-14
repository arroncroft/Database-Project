import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.sql.*;
import javafx.stage.Screen;



public class Client extends Application {

    // JavaFX class declarations
    private int maxX;
    private int maxY;
    private final String bStyle = "-fx-background-color: #6ba3d3;"; //Style color for Buttons and such: bStyle
    private ScrollPane scrollpane;
    private GridPane displayBox;
    private ComboBox comboBox;
    private TextField searchbox;
    private TextField amountbox;
    private TextField pnum;
    private Text tempText;
    private Button increaseButton;
    private Button decreaseButton;

    //	JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/DATABASE_GROUP_PROJECT?useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args){
        launch(args);
    }

    @Override public void start(Stage stage){

        try{
            createConnection();
        }
        catch(Exception ex){
            System.out.println("Unable to create connection");
        }

        getScreenBounds();
        stage.setScene(mainScene());
        stage.setTitle("(VSU)ggester");
        stage.setMaximized(true);
        stage.show();
    }
    //-------------------------------------
    //createConnection:
    //-------------------------------------
    private void createConnection() throws Exception {
        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Database connected successfully");
        System.out.println("*******************************");
    }
    //-------------------------------------
    //getScreenBounds: Gets resolution for use in
    //                 setting panel percentages
    //-------------------------------------
    private void getScreenBounds(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        maxX = (int)primaryScreenBounds.getMaxX();
        maxY = (int)primaryScreenBounds.getMaxY();
        System.out.println("Screen Resolution: " + maxX + " x " + maxY);
    }
    //-------------------------------------
    //mainScene(): creates the main message scene
    //-------------------------------------
    private Scene mainScene(){

        BorderPane root2 = new BorderPane();
        root2.setPrefSize(maxX, maxY);
        root2.setStyle("-fx-background-color: #333333;");

        HBox topBox = new HBox();
        topBox.setPadding(new Insets(10,10,10,10));
        topBox.setSpacing(maxX/4);
        topBox.setStyle("-fx-background-color: #444444;");

        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(10, 10, 10, 10));
        hbox1.setStyle("-fx-background-color: #444444;");

        HBox hbox2 = new HBox();

        displayBox = new GridPane();
        displayBox.setMaxSize(maxX*0.75, maxY-10);
        displayBox.setPrefSize(maxX*0.75, maxY-10);
        displayBox.setHgap(50);
        displayBox.setVgap(50);
        displayBox.setStyle("-fx-background-color: #333333;");

        scrollpane = new ScrollPane();
        scrollpane.setContent(displayBox);
        scrollpane.setMaxSize(maxX*0.75, maxY-10);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setStyle("-fx-background-color: #333333;");

        VBox rightPanel = new VBox();
        rightPanel.setAlignment(Pos.TOP_LEFT);
        rightPanel.setPrefSize(maxX*0.25, maxY-10);
        rightPanel.setSpacing(20);
        rightPanel.setPadding(new Insets(10, 10, 10, 10));
        rightPanel.setStyle("-fx-background-color: #444444;");

        HBox pagebox = new HBox();
        pagebox.setAlignment(Pos.CENTER_LEFT);


        //create pagebox nodes
        decreaseButton = new Button("<");
        decreaseButton.setStyle(bStyle);
        decreaseButton.setOnAction(e -> {pagehandle(e);});

        pnum = new TextField("1");
        pnum.setEditable(false);
        pnum.setPrefWidth(50);

        increaseButton = new Button(">");
        increaseButton.setStyle(bStyle);
        increaseButton.setOnAction(e -> {pagehandle(e);});

        //===== Create ComboBox =====
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Movie",
                        "Genre",
                        "Director",
                        "Actor",
                        "Tag",
                        "Movie(tags)"
                );
        comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setStyle(bStyle);

        //===== SearchBox =====
        searchbox = new TextField();
        searchbox.setPrefSize(500, 10);
        searchbox.setPromptText("Search here");
        searchbox.setStyle("-fx-text-inner-color: #26247f;");

        //===== SearchButton =====
        Image image = new Image("search-icon.png", 10, 10, false, false);
        ImageView view = new ImageView(image);
        Button searchButton = new Button();
        searchButton.setGraphic(view);
        searchButton.setPrefSize(11,20);
        searchButton.setDefaultButton(true);
        searchButton.setOnAction(e -> {searchHandle(e);});
        searchButton.setStyle(bStyle);


        //===== Right Panel OBJECTS =====
        Button topMovies = new Button("Top Movies");
        topMovies.setStyle(bStyle);
        topMovies.setPrefWidth(250);
        topMovies.setOnAction(e -> {topMovieHandle(e);});

        Button topDirectors = new Button("Top Directors");
        topDirectors.setStyle(bStyle);
        topDirectors.setPrefWidth(250);

        Button topActors = new Button("Top Actors");
        topActors.setStyle(bStyle);
        topActors.setPrefWidth(250);
        topActors.setOnAction(e -> {topActorHandle(e);});

        Button rmGenre = new Button("Recommend by Genre");
        rmGenre.setStyle(bStyle);
        rmGenre.setPrefWidth(250);

        Button rmDirector = new Button("Recommend by Director");
        rmDirector.setStyle(bStyle);
        rmDirector.setPrefWidth(250);

        amountbox = new TextField("5");
        amountbox.setMaxWidth(100);

        //===== Add children to panels =====
        rightPanel.getChildren().addAll(topMovies, topDirectors, topActors, rmGenre, rmDirector, amountbox);
        hbox2.getChildren().addAll(scrollpane, rightPanel);
        pagebox.getChildren().addAll(decreaseButton, pnum, increaseButton);
        hbox1.getChildren().addAll(comboBox, searchbox, searchButton);
        topBox.getChildren().addAll(pagebox,hbox1);

        root2.setLeft(scrollpane);
        root2.setTop(topBox);
        root2.setRight(rightPanel);


        return new Scene(root2);
    }
    //----------------------------------------------------
    //searchHandle(): Replace logic in here with query logic
    //                regarding searches
    //----------------------------------------------------
    private void searchHandle(ActionEvent e){
        displayBox.getChildren().clear();
        int pagenumber = Integer.parseInt(pnum.getText());

        //--------------------
        //search by movie
        //--------------------
        if(comboBox.getValue().equals("Movie")) {
            for (int i = 0; i < 100; i++) {
                displayBox.add(new Text("displaying results for movie: " + searchbox.getText()), 0, i);
            }
        }
        //--------------------
        //search by director
        //--------------------
        else if(comboBox.getValue().equals("Director")) {
            try {
                String[][] tempArray = query4(con, searchbox.getText(), 10, pagenumber);//query test
                for(int i = 0; i < tempArray.length; i++){
                    Text titleText = new Text(tempArray[i][0]);
                    if(titleText.getText().isEmpty())
                        break;
                    titleText.setFill(Color.WHITE);
                    titleText.setOnMousePressed(f -> {titleText.setFill(Color.DEEPSKYBLUE); clickHandle(f, titleText.getText()); });
                    displayBox.add(titleText, 0, i);

                    tempText = new Text(tempArray[i][1]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 1 , i);

                    tempText = new Text(tempArray[i][2]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 2 , i);

                    displayBox.add(new ImageView(new Image(tempArray[i][3])), 3 , i);
                    displayBox.add(new ImageView(new Image(tempArray[i][4])), 4 , i);
                }
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        //--------------------
        //search by actor
        //--------------------
        else if(comboBox.getValue().equals("Actor")) {
            try {
                String[][] tempArray = query5(con, searchbox.getText(), 10, pagenumber);//query test
                for(int i = 0; i < tempArray.length; i++){
                    Text titleText = new Text(tempArray[i][0]);
                    if(titleText.getText().isEmpty())
                        break;
                    titleText.setFill(Color.WHITE);
                    titleText.setOnMousePressed(f -> {titleText.setFill(Color.DEEPSKYBLUE); clickHandle(f, titleText.getText()); });
                    displayBox.add(titleText, 0, i);

                    tempText = new Text(tempArray[i][1]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 1 , i);

                    tempText = new Text(tempArray[i][2]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 2 , i);

                    displayBox.add(new ImageView(new Image(tempArray[i][3])), 3 , i);
                    displayBox.add(new ImageView(new Image(tempArray[i][4])), 4 , i);
                }
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        //--------------------
        //search by genre
        //--------------------
        else if(comboBox.getValue().equals("Genre")) {
            try {
                String[][] tempArray = query3(con, searchbox.getText(), 10, pagenumber);//query test
                for(int i = 0; i < tempArray.length; i++){
                    Text titleText = new Text(tempArray[i][0]);
                    if(titleText.getText().isEmpty())
                        break;
                    titleText.setFill(Color.WHITE);
                    titleText.setOnMousePressed(f -> {titleText.setFill(Color.DEEPSKYBLUE); clickHandle(f, titleText.getText()); });
                    displayBox.add(titleText, 0, i);

                    tempText = new Text(tempArray[i][1]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 1 , i);

                    tempText = new Text(tempArray[i][2]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 2 , i);

                    displayBox.add(new ImageView(new Image(tempArray[i][3])), 3 , i);
                    displayBox.add(new ImageView(new Image(tempArray[i][4])), 4 , i);
                }
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        //--------------------
        //search by tag
        //--------------------
        else if(comboBox.getValue().equals("Tag")) {
            try {
                String[][] tempArray = query6(con, searchbox.getText(), 10, pagenumber);//query test
                for(int i = 0; i < tempArray.length; i++){
                    Text titleText = new Text(tempArray[i][0]);
                    if(titleText.getText().isEmpty())
                        break;
                    titleText.setFill(Color.WHITE);
                    titleText.setOnMousePressed(f -> {titleText.setFill(Color.DEEPSKYBLUE); clickHandle(f, titleText.getText()); });
                    displayBox.add(titleText, 0, i);

                    tempText = new Text(tempArray[i][1]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 1 , i);

                    tempText = new Text(tempArray[i][2]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 2 , i);

                    displayBox.add(new ImageView(new Image(tempArray[i][3])), 3 , i);
                    displayBox.add(new ImageView(new Image(tempArray[i][4])), 4 , i);
                }
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        else if(comboBox.getValue().equals("Movie(tags)")){
            try{
                String[] tempArray = query10(con, searchbox.getText(), 10, pagenumber);
                for(int i = 0; i < tempArray.length; i++){
                    tempText = new Text(tempArray[i]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 0, i);
                }
            }
            catch(Exception ex) { ex.printStackTrace();}
        }
    }
    //----------------------------------------------------
    //clickHandle: handles the clicking of titles on searches
    //----------------------------------------------------
    private void clickHandle(MouseEvent e, String title){
        System.out.println(title);
    }
    //----------------------------------------------------
    //pageHandle
    //----------------------------------------------------
    private void pagehandle(ActionEvent e){
        if(e.getSource() == decreaseButton && !pnum.getText().equals("1")){
            pnum.setText("" + (Integer.parseInt(pnum.getText()) - 1));
        }
        else if(e.getSource() == increaseButton){
            pnum.setText("" + (Integer.parseInt(pnum.getText()) + 1));
        }

    }
    //----------------------------------------------------
    //topMovieHandle: called when top movie button hit
    //----------------------------------------------------
    private void topMovieHandle(ActionEvent e){
        try {
            String[][] tempArray = query1(con, Integer.parseInt(amountbox.getText()), 1);//query test
            for(int i = 0; i < tempArray.length; i++){
                Text titleText = new Text(tempArray[i][0]);
                titleText.setFill(Color.WHITE);
                titleText.setOnMousePressed(f -> {titleText.setFill(Color.DEEPSKYBLUE); clickHandle(f, titleText.getText()); });
                displayBox.add(titleText, 0, i);

                tempText = new Text(tempArray[i][1]);
                tempText.setFill(Color.WHITE);
                displayBox.add(tempText, 1 , i);

                tempText = new Text(tempArray[i][2]);
                tempText.setFill(Color.WHITE);
                displayBox.add(tempText, 2 , i);

                displayBox.add(new ImageView(new Image(tempArray[i][3])), 3 , i);
                displayBox.add(new ImageView(new Image(tempArray[i][4])), 4 , i);
            }
        }
        catch(Exception ex){ ex.printStackTrace(); }
    }
    //----------------------------------------------------
    //topActorHandle: executed when top actor button hit
    //----------------------------------------------------
    private void topActorHandle(ActionEvent e){
        displayBox.getChildren().clear();
        try{
            String[] tempArray = query8(con, Integer.parseInt(amountbox.getText()),10, 1);
            for(int i = 0; i < tempArray.length; i++){
                tempText = new Text(tempArray[i]);
                tempText.setFill(Color.WHITE);
                displayBox.add(tempText, 0, i);
            }
        }
        catch(Exception ex) { ex.printStackTrace();}
    }
    //----------------------------------------------------
    //query1
    //----------------------------------------------------
    private String[][] query1(Connection con, int topNum, int pgNum) throws SQLException {
        displayBox.getChildren().clear();
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
            System.out.println(temp[0][3]);
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
    private String[][] query3 (Connection conn, String genre, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
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
    private String[][] query4 (Connection conn, String dName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
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
    private String[][] query5 (Connection conn, String aName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
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
    private String[][] query6 (Connection conn, String tagName, int topNum, int pgNum) throws SQLException {
        String[][] temp = new String[topNum][5];
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
    //query8: returns top actors
    //----------------------------------------------------
    private String[] query8 (Connection conn, int k, int topNum, int pgNum) throws SQLException {
        String[] temp = new String[topNum];

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
            int i = 0;
            while (rs.next()) {
                System.out.println(rs.getString("actorName"));
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
    //query10: Searches by movie, show tags
    //----------------------------------------------------
    private String[] query10 (Connection conn, String title, int topNum, int pgNum) throws SQLException {
        String[] A = new String[topNum];
        String query = "SELECT DISTINCT t.tagValue "+
                "FROM movie m, movie_tags mt, tags t "+
                "WHERE m.movieID = mt.movieID AND mt.tagID = t.tagID AND m.title = '"+title+"' "+
                "ORDER BY mt.tagWeight DESC "+
                "LIMIT "+topNum+" OFFSET "+((pgNum-1)*topNum);
        try {
            //create the prepared statement
            PreparedStatement ps = conn.prepareStatement(query);
            //process the results
            int i = 0;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //System.out.println(rs.getString("tagValue"));
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
}
