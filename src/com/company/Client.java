package com.company;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.geometry.*;


public class Client extends Application {


    private ScrollPane scrollpane;
    private GridPane displayBox;
    private ComboBox comboBox;
    private TextField searchbox;


    public static void main(String[] args){
        launch(args);
    }
    @Override
     public void start(Stage stage){
        stage.setScene(mainScene());
        stage.setTitle("Rotten Potatoes");
        stage.show();
    }
    //-------------------------------------
    //mainScene(): creates the main message scene
    //-------------------------------------
    private Scene mainScene(){
        VBox root = new VBox();
        root.setPrefSize(800,800);
        root.setStyle("-fx-background-color: #333333;");

        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(10, 10, 10, 10));
        hbox1.setStyle("-fx-background-color: #444444;");

        HBox hbox2 = new HBox();

        displayBox = new GridPane();
        displayBox.setPrefSize(600, 770);
        displayBox.setStyle("-fx-background-color: #333333;");

        scrollpane = new ScrollPane();
        scrollpane.setContent(displayBox);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setStyle("-fx-background-color: #333333;");

        VBox rightPanel = new VBox();
        rightPanel.setPrefSize(200, 770);
        rightPanel.setSpacing(20);
        rightPanel.setStyle("-fx-background-color: #444444;");


        //===== Create ComboBox =====
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Movie",
                        "Genre",
                        "Director",
                        "Actor",
                        "Tag"
                );
        comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setStyle("-fx-background-color: #337ab7;");

        //===== SearchBox =====
        searchbox = new TextField();
        searchbox.setPrefSize(300, 10);
        searchbox.setPromptText("Search here");
        //searchbox.setStyle("-fx-text-inner-color: blue;");

        //===== SearchButton =====
        Image image = new Image("/assets/search-icon.png", 10, 10, false, false);
        ImageView view = new ImageView(image);
        Button searchButton = new Button();
        searchButton.setGraphic(view);
        searchButton.setPrefSize(11,20);
        searchButton.setDefaultButton(true);
        searchButton.setOnAction(e -> {searchHandle(e);});
        searchButton.setStyle("-fx-background-color: #337ab7;");


        //===== Right Panel Hyperlinks (act as buttons) =====
        Hyperlink topMovies = new Hyperlink("Top Movies");
        Hyperlink topDirectors = new Hyperlink("Top Directors");
        Hyperlink rmGenre = new Hyperlink("Recommend by Genre");
        Hyperlink rmDirector = new Hyperlink("Recommend by Director");


        //===== Add children to panels =====
        rightPanel.getChildren().addAll(topMovies, topDirectors, rmGenre, rmDirector);
        hbox2.getChildren().addAll(scrollpane, rightPanel);
        hbox1.getChildren().addAll(comboBox, searchbox, searchButton);
        root.getChildren().addAll(hbox1, hbox2);


        return new Scene(root);
    }
    //----------------------------------------------------
    //searchHandle(): Replace logic in here with query logic
    //                regarding searches
    //----------------------------------------------------
    private void searchHandle(ActionEvent e){
        displayBox.getChildren().clear();

        if(comboBox.getValue().equals("Movie")) {
            for (int i = 0; i < 100; i++) {
                displayBox.add(new Text("displaying results for movie: " + searchbox.getText()), 0, i);
            }
        }
        else if(comboBox.getValue().equals("Director")) {
            for (int i = 0; i < 10; i++) {
                displayBox.add(new Text("displaying results for director: " + searchbox.getText()), 0, i);
            }
        }
        else if(comboBox.getValue().equals("Actor")) {
            for (int i = 0; i < 10; i++) {
                displayBox.add(new Text("displaying results for Actor: " + searchbox.getText()), 0, i);
            }
        }
        else if(comboBox.getValue().equals("Genre")) {
            for (int i = 0; i < 10; i++) {
                displayBox.add(new Text("displaying results for Genre: " + searchbox.getText()), 0, i);
            }
        }
        else if(comboBox.getValue().equals("Tag")) {
            for (int i = 0; i < 10; i++) {
                displayBox.add(new Text("displaying results for Tag: " + searchbox.getText()), 0, i);
            }
        }
    }
}
