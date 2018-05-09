REM Spustí spustitelnou tridu v z uvedeneho modulu
REM Martin Jasek, 7.5.2018
REM Pouziti: run.bat MODUL TRIDA ARGUMENTY...
REM  kde modul je jeden z: fa, fta a cfa
REM  a trida je nazev spustitelne tridy 
REM  (viz README.txt pro jejich seznam)


SET MODULE=%1
SET CLASS=%1

SET ALLARGS=%*
CALL SET TMPARGS=%%ALLARGS:*%1=%%
CALL SET ARGS=%%TMPARGS:*%2=%%



echo Running %CLASS% from %MODULE% ...

java -cp "core.jar;%MODULE%.jar" "cz.upol.fapapp.%MODULE%.mains.%CLASS%" %ARGS%

