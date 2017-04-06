

create schema DATABASE_GROUP_PROJECT;

CREATE TABLE MOVIE (
    movieID INT NOT NULL,
    title VARCHAR(150),
    imdbID INT,
    spanishTitle VARCHAR(150),
    imdbPictureURL VARCHAR(150),
    movieYear INT,
    rtID VARCHAR(150),
    rtAllCriticsrating VARCHAR(4),
    rtAllCriticsNumReviews VARCHAR(10),
    rtallCriticsNumFresh VARCHAR(4),
    rtAllCriticsNumRotten VARCHAR(4),
    rtAllCriticsScore VARCHAR(4),
    rtTopCriticsRating VARCHAR(4),
    rtTopCriticsNumReviews VARCHAR(10),
    rtTopCriticsNumFresh VARCHAR(10),
    rtTopCriticsNumRotten VARCHAR(10),
    rtTopCriticsScore VARCHAR(4),
    rtAudienceRating VARCHAR(4),
    rtAudienceNumRatings VARCHAR(10),
    rtAudienceScore VARCHAR(4),
    rtPictureURL VARCHAR(100),
    PRIMARY KEY (movieID)
);

CREATE TABLE TAGS (
    tagID INT NOT NULL,
    tagValue VARCHAR(100),
    PRIMARY KEY (tagID)
);

CREATE TABLE MOVIE_ACTORS (
    movieID INT NOT NULL,
    actorID VARCHAR(70) NOT NULL,
    actorName VARCHAR(80),
    ranks INT,
    PRIMARY KEY (movieID , actorID),
    CONSTRAINT fk_movie FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE MOVIE_COUNTRIES (
    movieID INT NOT NULL,
    country VARCHAR(30),
    PRIMARY KEY (movieID),
    CONSTRAINT fk_movie1 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE MOVIE_DIRECTORS (
    movieID INT NOT NULL,
    directorID VARCHAR(40) NOT NULL,
    directorName VARCHAR(40),
    PRIMARY KEY (movieID , directorID),
    CONSTRAINT fk_movie2 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE MOVIE_GENRES (
    movieID INT NOT NULL,
    genre VARCHAR(20) NOT NULL,
    PRIMARY KEY (movieID , genre),
    CONSTRAINT fk_movie3 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE MOVIE_LOCATIONS (
    locationID INT NOT NULL,
    movieID INT,
    location1 VARCHAR(100),
    location2 VARCHAR(100),
    location3 VARCHAR(100),
    location4 VARCHAR(200),
    PRIMARY KEY (locationID),
    CONSTRAINT fk_movie4 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE MOVIE_TAGS (
    movieID INT NOT NULL,
    tagID INT NOT NULL,
    tagWeight INT,
    PRIMARY KEY (movieID , tagID),
    CONSTRAINT fk_movie5 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID),
    CONSTRAINT fk_tag FOREIGN KEY (tagID)
        REFERENCES TAGS (tagID)
);

CREATE TABLE USER_RATED_MOVIES (
    userID INT NOT NULL,
    movieID INT NOT NULL,
    rating FLOAT,
    date_day INT,
    date_month INT,
    date_year INT,
    date_hour INT,
    date_minute INT,
    date_second INT,
    PRIMARY KEY (userID , movieID),
    CONSTRAINT fk_movie6 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE USER_RATED_MOVIES_TIMESTAMP (
    userID INT NOT NULL,
    movieID INT NOT NULL,
    rating FLOAT,
    timestamp LONG,
    PRIMARY KEY (userID , movieID),
    CONSTRAINT fk_movie7 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID)
);

CREATE TABLE USER_TAGGED_MOVIES (
    userID INT NOT NULL,
    movieID INT NOT NULL,
    tagID INT NOT NULL,
    date_day INT,
    date_month INT,
    date_year INT,
    date_hour INT,
    date_minute INT,
    date_second INT,
    PRIMARY KEY (userID , movieID , tagID),
    CONSTRAINT fk_movie8 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID),
    CONSTRAINT fk_tag1 FOREIGN KEY (tagID)
        REFERENCES TAGS (tagID)
);

CREATE TABLE USER_TAGGED_MOVIES_TIMESTAMP (
    userID INT NOT NULL,
    movieID INT NOT NULL,
    tagID INT NOT NULL,
    timestamp LONG,
    PRIMARY KEY (userID , movieID , tagID),
    CONSTRAINT fk_movie9 FOREIGN KEY (movieID)
        REFERENCES MOVIE (movieID),
    CONSTRAINT fk_tag2 FOREIGN KEY (tagID)
        REFERENCES TAGS (tagID)
);




