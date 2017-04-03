

create schema DATABASE_GROUP_PROJECT;

create table MOVIE(
  movieID int Not Null,  #1
  title varchar(150),
  imdbID int,
  spanishTitle varchar(150),
  imdbPictureURL varchar(150),  #5
  movieYear int,  #6
  rtID varchar(150),
  rtAllCriticsrating varchar(4),#8
  rtAllCriticsNumReviews varchar(10),
  rtallCriticsNumFresh varchar(4),    #10
  rtAllCriticsNumRotten varchar(4),
  rtAllCriticsScore varchar(4),#12
  rtTopCriticsRating varchar(4),
  rtTopCriticsNumReviews varchar(10), #14
  rtTopCriticsNumFresh varchar(10),	#15
  rtTopCriticsNumRotten varchar(10),
  rtTopCriticsScore varchar(4),
  rtAudienceRating varchar(4), #18
  rtAudienceNumRatings varchar(10),
  rtAudienceScore varchar(4), #20
  rtPictureURL varchar(100),
  primary key(movieID)
);

create table TAGS(
  tagID int Not Null,
  tagValue varchar(100),
  primary key(tagID)
);

CREATE TABLE MOVIE_ACTORS(
  movieID INT Not Null,
  actorID varchar(70) Not Null,
  actorName VarChar(80),
  ranks int,
  Primary Key(movieID, actorID),
  CONSTRAINT fk_movie Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

CREATE TABLE MOVIE_COUNTRIES(
  movieID INT Not Null,
  country varchar(30) Not Null,
  primary key(movieID, country),
  CONSTRAINT fk_movie1 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

CREATE TABLE MOVIE_DIRECTORS(
  movieID INT Not Null,
  directorID varchar(40) Not NUll,
  directorName varchar(40),
  primary key (movieID, directorID),
  CONSTRAINT fk_movie2 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

CREATE TABLE MOVIE_GENRES(
  movieID INT Not Null,
  genre varchar(20) Not Null,
  primary key(movieID, genre),
  CONSTRAINT fk_movie3 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

create table MOVIE_LOCATIONS(
  movieID int Not NUll,
  location1 varchar(100),
  location2 varchar(100),
  location3 varchar(100),
  location4 varchar(200),
  primary key(movieId),
  CONSTRAINT fk_movie4 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

create table MOVIE_TAGS(
  movieID int Not Null,
  tagID int Not Null,
  tagWeight int,
  primary key(movieID, tagID),
  CONSTRAINT fk_movie5 Foreign Key(movieID) REFERENCES MOVIE(movieID),
  CONSTRAINT fk_tag Foreign Key(tagID) References TAGS(tagID)
);

CREATE table USER_RATED_MOVIES(
  userID int Not Null,
  movieID int Not Null,
  rating float,
  date_day int,
  date_month int,
  date_year int,
  date_hour int,
  date_minute int,
  date_second int,
  primary key(userID, movieID),
  CONSTRAINT fk_movie6 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

CREATE TABLE USER_RATED_MOVIES_TIMESTAMP(
  userID int Not Null,
  movieID int Not Null,
  rating float,
  timestamp long,
  primary key(userID, movieID),
  CONSTRAINT fk_movie7 Foreign Key(movieID) REFERENCES MOVIE(movieID)
);

CREATE table USER_TAGGED_MOVIES(
  userID int Not Null,
  movieID int Not Null,
  tagID int Not Null,
  date_day int,
  date_month int,
  date_year int,
  date_hour int,
  date_minute int,
  date_second int,
  primary key(userID, movieID, tagID),
  CONSTRAINT fk_movie8 Foreign Key(movieID) REFERENCES MOVIE(movieID),
  CONSTRAINT fk_tag1 Foreign Key(tagID) References TAGS(tagID)
);

create table USER_TAGGED_MOVIES_TIMESTAMP(
  userID int Not Null,
  movieID int Not Null,
  tagID int Not Null,
  timestamp long,
  primary key(userID, movieID, tagID),
  CONSTRAINT fk_movie9 Foreign Key(movieID) REFERENCES MOVIE(movieID),
  CONSTRAINT fk_tag2 Foreign Key(tagID) References TAGS(tagID)
);




