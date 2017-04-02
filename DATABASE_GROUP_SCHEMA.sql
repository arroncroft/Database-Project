

create schema DATABASE_GROUP_PROJECT;

CREATE TABLE MOVIE_ACTORS(
movieID INT,
actorID varchar(70),
actorName VarChar(80),
ranks int);

CREATE TABLE MOVIE_COUNTRIES(
movieID INT,
country varchar(30));

CREATE TABLE MOVIE_DIRECTORS(
movieID INT,
directorID varchar(40),
directorName varchar(40));

CREATE TABLE MOVIE_GENRES(
movieID INT,
genre varchar(20));




create table MOVIE_LOCATIONS(
movieID int,
location1 varchar(100),
location2 varchar(100),
location3 varchar(100),
location4 varchar(200));

create table MOVIE_TAGS(
movieID int,
tagID int,
tagWeight int);

create table MOVIE(
movieID int,  #1
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
rtPictureURL varchar(100));

create table TAGS(
tagID int,
tagValue varchar(100));


CREATE table USER_RATED_MOVIES(
userID int,
movieID int,
rating float,
date_day int,
date_month int,
date_year int,
date_hour int,
date_minute int,
date_second int);

CREATE TABLE USER_RATED_MOVIES_TIMESTAMP(
userID int,
movieID int,
rating float,
timestamp long);

CREATE table USER_TAGGED_MOVIES(
userID int,
movieID int,
tagID int,
date_day int,
date_month int,
date_year int,
date_hour int,
date_minute int,
date_second int);

create table USER_TAGGED_MOVIES_TIMESTAMP(
userID int,
movieID int,
tagID int,
timestamp long);



 