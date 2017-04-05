The file reader runs all 11 of the files through the reader. Supply the file names as args[], 
I did it in intelliJ so i added the arguements in the config, if you don't want to type
java db_files.Main.java arg1 arg2.....arg 11, feel free to comment out all but the one arg you wish
to suppy at a time. Also note********* you need to go to the bottom of the file and enter
your db username and password, i have left these blank. takes about 2 hrs on my mbp so,
find something to do while it runs, or watch it....

EDITED
******************
You can run each of the files from the seeds folder in mysql workbench or whatver you want to generate the database, it should be much faster than using the java file to do so as reported by Ben.

**You have to run the MOVIE and TAGS files first because of foreign key constraints. It probably won't work otherwise.**
After that, any order should be fine.
******************
