package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;

public class ImportData {

    public static void main(String[] args) throws Exception {
          // Show the arguements provided
        System.out.println("THE PROVIDED ARGUEMENTS: ");
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line;
        String[] tokens;
        int count = 0;
        final int COUNTLIMIT = 15;
        final int LOCATIONS = 4;


        Connection conn = getConnection();


        String sql;
        PreparedStatement stmt;
        ResultSet results;
        System.out.println();

//************************Read in Movie Actors********************************************************


        if(args[0] != null) {
            try {
                System.out.println("Attempting to insert Movie Actors....");
                while ((line = reader.readLine()) != null) {
                    //skips the first line containing headers
                    if(count > 0) {
                        tokens = line.split("\t");
                        //System.out.println("MOVie Actors: " + "\t" +tokens[0] + "\t" + tokens[1] + "\t" + tokens[2] + "\t" + tokens[3]);

                        sql = "INSERT INTO MOVIE_ACTORS(movieID,actorID,actorname,ranks)" +
                                "VALUES (?,?,?,?);";

                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tokens[0]));
                        stmt.setString(2, tokens[1]);
                        stmt.setString(3, tokens[2]);
                        stmt.setInt(4, Integer.parseInt(tokens[3]));
                        stmt.execute();
                    }
                    count++;
                    }

            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }


        //************************Read in Movie Countries********************************************************

        count =0;
        reader = new BufferedReader(new FileReader(args[1]));
        if(args[1] != null) {
            System.out.println("Attempting to insert movie countries....");
            try {
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");
                        sql = "INSERT INTO MOVIE_COUNTRIES(movieID,country)" +
                                "VALUES (?,?);";

                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tokens[0]));
                        //some of the data is missing , like line 985, this takes care of that
                        try {
                            stmt.setString(2, tokens[1]);
                        }catch(Exception e){
                            stmt.setString(2,null);
                        };
                        stmt.execute();
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Finished uploading Movie Countries");
        }

        //**********************Enter Movie Directors**********************************************

        count =0;
        reader = new BufferedReader(new FileReader(args[2]));
        if(args[2] != null) {
            try {
                System.out.println("Attempting to insert Movie Directors...");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");
                        //System.out.println("Movie Directors: " + line + "\n" + tokens[0] + "\t" + tokens[1]);
                        sql = "INSERT INTO MOVIE_DIRECTORS(movieID,directorID,directorName)" +
                                "VALUES (?,?,?);";
                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tokens[0]));
                        stmt.setString(2,tokens[1]);
                        stmt.setString(3,tokens[2]);
                        stmt.execute();

                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }
//******************************Movie Genres******************************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[3]));
        if(args[3] != null) {
            try {
                System.out.println("Attempting to upload Movie Genres.....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");
                         //System.out.println("Movie Genres: " + line + "\n" + tokens[0] + "\t"   + tokens[1]);

                        sql = "INSERT INTO MOVIE_GENRES(movieID,genre)" +
                                "VALUES (?,?);";
                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tokens[0]));
                        stmt.setString(2,tokens[1]);
                        stmt.execute();
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }
//**********************************Movie Locations **********************************************

        count =0;
        reader = new BufferedReader(new FileReader(args[4]));
        if(args[4] != null) {
            try {
                System.out.println("Attempting to upload Movie Locations");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");

                        //find out how many locations locations = length - 1
                        int tokencount = tokens.length;

                        sql = "INSERT INTO MOVIE_LOCATIONS(movieID,location1,location2,location3,location4)" +
                                "VALUES (?,?,?,?,?);";
                        stmt = conn.prepareStatement(sql);

                        //first spot always filled, it movie id
                        stmt.setInt(1, Integer.parseInt(tokens[0]));

                        //get rest of locations if any
                        for (int i = 1; i < tokencount; i++) {
                            stmt.setString(i +1,tokens[i]);
                        }
                        //use this to fill locations spaces when extra
                        for(int j = tokencount+1; j < 6; j++){
                            stmt.setString(j,null);
                        }

                        stmt.execute();

                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }


//********************GET MOVIE TAGS*******************************************

        count =0;
        reader = new BufferedReader(new FileReader(args[5]));
        if(args[5] != null) {
            try {
                System.out.println("attempting to upload movie tags....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");
                        //System.out.println("Movie Tags: " + line + "\n"
                         //       + tokens[0] + "\t" + tokens[1] + "\t" + tokens[2]);
                        sql = "INSERT INTO MOVIE_TAGS(movieID,tagID, tagWeight)" +
                                "VALUES (?,?, ?);";
                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tokens[0]));
                        stmt.setInt(2,Integer.parseInt(tokens[1]));
                        stmt.setInt(3,Integer.parseInt(tokens[2]));
                        stmt.execute();

                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }

//********************GET MOVIEs*******************************************

        count =0;
        reader = new BufferedReader(new FileReader(args[6]));
        if(args[6] != null) {
            try {
                System.out.println("Attempting to upload your movies....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");


                        sql = "INSERT INTO MOVIE(movieID,title,imdbID,spanishTitle,imdbPictureURL,movieYear," +
                                "rtID,rtAllCriticsRating,rtAllCriticsNumReviews,rtAllCriticsNumFresh," +
                                "rtAllCriticsNumRotten,rtAllCriticsScore,rtTopCriticsRating,rtTopCriticsNumReviews," +
                                "rtTopCriticsNumFresh,rtTopCriticsNumRotten,rtTopCriticsScore,rtAudienceRating," +
                                "rtAudienceNumRatings,rtAudienceScore,rtPictureURL)" +
                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                       // System.out.print("\n" + count + ") ");
                       // for(int i = 0; i < tokens.length; i++){
                            //System.out.print((i+1) + " " + tokens[i] + "   " );
                        //}


                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1,Integer.parseInt(tokens[0]));//id
                        stmt.setString(2,tokens[1]);
                        stmt.setString(3,(tokens[2]));
                        stmt.setString(4,tokens[3]);
                        stmt.setString(5,tokens[4]);//pictureurl
                        stmt.setString(6,(tokens[5]));
                        stmt.setString(7,tokens[6]);
                        stmt.setString(8, tokens[7]);//critics rating
                        stmt.setString(9, (tokens[8]));
                        stmt.setString(10,(tokens[9]));
                        stmt.setString(11,(tokens[10]));
                        stmt.setString(12,(tokens[11]));
                        stmt.setString(13,(tokens[12]));
                        stmt.setString(14,(tokens[13]));
                        stmt.setString(15,(tokens[14]));
                        stmt.setString(16,(tokens[15]));
                        stmt.setString(17,(tokens[16]));
                        stmt.setString(18,(tokens[17]));
                        stmt.setString(19,(tokens[18]));
                        stmt.setString(20,(tokens[19]));
                        stmt.setString(21,tokens[20]);
                        stmt.execute();

                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }

        //********************GET TAGS*******************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[7]));
        if(args[7] != null) {
            try {
                System.out.println("attempting to insert tags....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");
                        sql = "INSERT INTO TAGS(tagID, tagValue)" +
                                "VALUES (?,?);";

                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1,Integer.parseInt(tokens[0]));
                        stmt.setString(2,tokens[1]);
                        stmt.execute();
                        }

                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }


//********************User Rated Movies*******************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[8]));
        if(args[8] != null) {
            try {
                System.out.println("Attempting to insert User rated movies....");
                System.out.println("This will take a while.....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");


                        sql = "INSERT INTO USER_RATED_MOVIES(userID,movieID,rating,date_day,date_month,date_year," +
                                "date_hour,date_minute,date_second)" +
                                "VALUES (?,?,?,?,?,?,?,?,?);";
                        //System.out.println(line);
                        stmt = conn.prepareStatement(sql);
                        for(int i = 0; i < tokens.length; i++) {
                            //the third parameter is rating which is a decimal number sometimes...
                            if((i+1) != 3) {
                                stmt.setInt((i + 1), Integer.parseInt(tokens[i]));//id
                            }
                            else{
                                stmt.setDouble(3,Double.parseDouble(tokens[i]));
                            }
                        }
                        stmt.execute();
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }

         //********************User Rated Movies Timestamps*******************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[9]));
        if(args[9] != null) {
            try {
                System.out.println("Attempting to insert user rated movie timestamps...");
                while ((line = reader.readLine()) != null) {
                    if(count > 0){
                    tokens = line.split("\t");

                    sql = "INSERT INTO USER_RATED_MOVIES_TIMESTAMP(userID,movieID,rating,timestamp)" +
                                "VALUES (?,?,?,?);";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1,Integer.parseInt(tokens[0]));
                    stmt.setInt(2,Integer.parseInt(tokens[1]));
                    stmt.setFloat(3,Float.parseFloat(tokens[2]));
                    stmt.setLong(4,Long.parseLong(tokens[3]));
                    stmt.execute();
                    }

                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }

        //********************User Tagged Movies*******************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[10]));
        if(args[10] != null) {
            try {
                System.out.println("Attempting to insert user tagged movies....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0){
                    tokens = line.split("\t");

                    sql = "INSERT INTO USER_TAGGED_MOVIES(userID,movieID,tagID,date_day,date_month,date_year," +
                            "date_hour,date_minute,date_second)" +
                                "VALUES (?,?,?,?,?,?,?,?,?);";
                    stmt = conn.prepareStatement(sql);
                    for(int i = 0; i < tokens.length; i ++){
                        stmt.setInt((i+1),Integer.parseInt(tokens[i]));
                    }
                    stmt.execute();

                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            System.out.println("Success");
        }

//********************User Tagged Movies Timestamps*******************************************
        count =0;
        reader = new BufferedReader(new FileReader(args[11]));
        if(args[11] != null) {
            try {
                System.out.print("Attempting to insert user tagged movies timestamp....");
                while ((line = reader.readLine()) != null) {
                    if(count > 0) {
                        tokens = line.split("\t");

                        sql = "INSERT INTO USER_TAGGED_MOVIES_TIMESTAMP(userID,movieID,tagID,timestamp)" +
                                "VALUES (?,?,?,?);";
                        stmt = conn.prepareStatement(sql);
                        stmt.setInt(1,Integer.parseInt(tokens[0]));
                        stmt.setInt(2,Integer.parseInt(tokens[1]));
                        stmt.setInt(3,Integer.parseInt(tokens[2]));
                        stmt.setLong(4,Long.parseLong(tokens[3]));
                        stmt.execute();
                    }
                    count++;
                }
                System.out.println("Success");
            } catch (IOException e) {
                System.out.println("Couldn't find file");
            }
            conn.close();
            System.out.println("Upload Complete :)");
        }



    }
    public static Connection getConnection() throws Exception{

        Connection conn;

        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/DATABASE_GROUP_PROJECT?useSSL=false";
            String username = "******";
            String password = "**********";
            Class.forName(driver);
            System.out.println("Connecting to Database.....");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database Successfully");
            return conn;
        }
        catch (Exception e){
            System.out.print(e);
        }
        return  null;
    }
}
