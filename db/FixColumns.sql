UPDATE MOVIE
SET rtAudienceRating=NULL
WHERE rtAudienceRating='\N';

ALTER TABLE MOVIE
MODIFY COLUMN rtAudienceRating FLOAT;