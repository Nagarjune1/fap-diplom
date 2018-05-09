#!/bin/bash
# Spustí spustitelnou tridu v z uvedeneho modulu
# Martin Jasek, 7.5.2018
# Pouziti: run.sh MODUL TRIDA ARGUMENTY...
#  kde modul je jeden z: fa, fta a cfa
#  a trida je nazev spustitelne tridy 
#  (viz README.txt pro jejich seznam)

MODULE=$1
shift

CLASS=$1
shift

echo Running $CLASS from $MODULE ...

java -cp "core.jar:$MODULE.jar" "cz.upol.fapapp.$MODULE.mains.$CLASS" $@
