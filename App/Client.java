import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.sql.*;
import java.util.ArrayList;
import javafx.stage.Screen;



public class Client extends Application {

    // Java declarations
    private int maxX;
    private int maxY;
    private ArrayList<String> clickedList;

    // JavaFX class declarations
    private Stage stage2;
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
    private Button topDirectorsButton;
    private Button topActorsButton;
    private Button timelineButton;
    private Button rbdButton;
    private Button rbgButton;

    //declaring querymachine
    private QueryMachine qm;

    public static void main(String[] args){
        launch(args);
    }

    @Override public void start(Stage stage){
        //provide url, user, password for QueryMachine params if
        //you want to connect to database with different settings
        qm = new QueryMachine();
        qm.createConnection();
        clickedList = new ArrayList<>();
        getScreenBounds();
        stage2 = new Stage();

        stage.setScene(mainScene());
        stage.setTitle("(VSU)ggester");
        stage.setMaximized(true);
        stage.show();
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
        decreaseButton.setOnAction(e -> {pageHandle(e);});

        pnum = new TextField("1");
        pnum.setEditable(false);
        pnum.setPrefWidth(50);

        increaseButton = new Button(">");
        increaseButton.setStyle(bStyle);
        increaseButton.setOnAction(e -> {pageHandle(e);});

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

        topDirectorsButton = new Button("Top Directors");
        topDirectorsButton.setStyle(bStyle);
        topDirectorsButton.setPrefWidth(250);
        topDirectorsButton.setOnAction(e -> {topPersonHandle(e);});

        topActorsButton = new Button("Top Actors");
        topActorsButton.setStyle(bStyle);
        topActorsButton.setPrefWidth(250);
        topActorsButton.setOnAction(e -> {topPersonHandle(e);});

        rbgButton = new Button("Recommend by Genre");
        rbgButton.setStyle(bStyle);
        rbgButton.setPrefWidth(250);
        rbgButton.setOnAction(e -> {recommendHandle(e);});

        rbdButton = new Button("Recommend by Director");
        rbdButton.setStyle(bStyle);
        rbdButton.setPrefWidth(250);
        rbdButton.setOnAction(e -> {recommendHandle(e);});

        timelineButton = new Button("User Timeline");
        timelineButton.setStyle(bStyle);
        timelineButton.setPrefWidth(250);
        timelineButton.setOnAction(e -> {timelineHandle(e);});


        amountbox = new TextField("5");
        amountbox.setMaxWidth(100);

        //===== Add children to panels =====
        rightPanel.getChildren().addAll(topMovies, topDirectorsButton, topActorsButton, rbgButton, rbdButton, timelineButton, amountbox);
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
            try {
                String[][] tempArray = qm.query2(searchbox.getText(), 10, pagenumber);//query test
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

                    tempText = new Text(tempArray[i][5]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 5 , i);
                }
            }
            catch(Exception ex){ ex.printStackTrace(); }
        }
        //--------------------
        //search by director
        //--------------------
        else if(comboBox.getValue().equals("Director")) {
            try {
                String[][] tempArray = qm.query4(searchbox.getText(), 10, pagenumber);//query test
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
                String[][] tempArray = qm.query5(searchbox.getText(), 10, pagenumber);//query test
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
                String[][] tempArray = qm.query3(searchbox.getText(), 10, pagenumber);//query test
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
                String[][] tempArray = qm.query6(searchbox.getText(), 10, pagenumber);//query test
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
                String[] tempArray = qm.query10(searchbox.getText(), 10, pagenumber);
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
        clickedList.add(title);
    }
    //----------------------------------------------------
    //pageHandle: handles changing page number
    //----------------------------------------------------
    private void pageHandle(ActionEvent e){
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
        displayBox.getChildren().clear();
        int pagenumber = Integer.parseInt(pnum.getText());
        try {
            String[][] tempArray = qm.query1(Integer.parseInt(amountbox.getText()), pagenumber);//query test
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
    //topPersonHandle: executed when top actor/director button hit
    //----------------------------------------------------
    private void topPersonHandle(ActionEvent e){
        displayBox.getChildren().clear();
        String[] tempArray;
        int pagenumber = Integer.parseInt(pnum.getText());
        try{
            if(e.getSource() == topActorsButton)
                tempArray = qm.query8(Integer.parseInt(amountbox.getText()),10, pagenumber);
            else
                tempArray = qm.query7(Integer.parseInt(amountbox.getText()),10, pagenumber);

            for(int i = 0; i < tempArray.length; i++){
                tempText = new Text(tempArray[i]);
                tempText.setFill(Color.WHITE);
                displayBox.add(tempText, 0, i);
            }
        }
        catch(Exception ex) { ex.printStackTrace();}
    }
    //----------------------------------------------------
    //recommendHandle: called when either recommend button clicked
    //----------------------------------------------------
    private void recommendHandle(ActionEvent e){
        if(clickedList.size() > 0) {
            String[][] tempArray;
            int pagenumber = Integer.parseInt(pnum.getText());
            displayBox.getChildren().clear();
            String list = "";
            for (String item : clickedList) {
                item = item.replace("'", "''");
                list = list + "'" + item + "', ";
            }
            clickedList.clear();
            list = list.substring(0, list.length() - 2);
            System.out.println(list);
            try {
                if (e.getSource() == rbdButton)
                    tempArray = qm.queryRBD(list, 5, pagenumber);
                else
                    tempArray = qm.queryRBG(list, 5, pagenumber);

                for (int i = 0; i < tempArray.length; i++) {
                    Text titleText = new Text(tempArray[i][0]);
                    if (titleText.getText().isEmpty())
                        break;
                    titleText.setFill(Color.WHITE);
                    titleText.setOnMousePressed(f -> {
                        titleText.setFill(Color.DEEPSKYBLUE);
                        clickHandle(f, titleText.getText());
                    });
                    displayBox.add(titleText, 0, i);

                    tempText = new Text(tempArray[i][1]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 1, i);

                    tempText = new Text(tempArray[i][2]);
                    tempText.setFill(Color.WHITE);
                    displayBox.add(tempText, 2, i);

                    displayBox.add(new ImageView(new Image(tempArray[i][3])), 3, i);
                    displayBox.add(new ImageView(new Image(tempArray[i][4])), 4, i);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //----------------------------------------------------
    //timelineHandle: called when user timeline button hit
    //----------------------------------------------------
    private void timelineHandle(ActionEvent e){
        displayBox.getChildren().clear();
        int pagenumber = Integer.parseInt(pnum.getText());
        try {
            String[][] tempArray = qm.query9(Integer.parseInt(amountbox.getText()),10, pagenumber);
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

                stage2.setScene(new Scene(new HBox(4, qm.getBarChart())));
                stage2.show();
            }
        }
        catch(Exception ex){ ex.printStackTrace(); }
    }
}