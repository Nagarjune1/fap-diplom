#!/bin/bash
# outputs resources from CSV table into bibtex file
# 29. 4. 2018

# input is in format: ID;TYPE;Author;Title;Year;Publisher;Journal
# (warning: no "s, causes problem with F\"{u}nf etc)

# to check non-ascii characters, type:
#grep --color='auto' -P -n "[^a-zA-Z0-9 .,:\"+-;()&{}~\\\\]"  resources-in-csv.csv
# or 
#perl -ane '{ if(m/[[:^ascii:]]/) { print  } }' resources-in-csv.csv

REGEX='([^;]+);([^;]+);([^;]+);([^;]+);([^;]+);([^;]*);([^;]*);?([^;]*)'

echo "" > resources-from-csv.bib

while read LINE;  do
	## echo $LINE
	# LINE=$(echo $LINE | sed 's/\\/\\\\/g')

	ID=$(					echo $LINE | sed -E 's/'$REGEX'/\1/')
	TYPE=$(				echo $LINE | sed -E 's/'$REGEX'/\2/')
	AUTHOR=$(			echo $LINE | sed -E 's/'$REGEX'/\3/')
	TITLE=$(			echo $LINE | sed -E 's/'$REGEX'/\4/')
	YEAR=$(				echo $LINE | sed -E 's/'$REGEX'/\5/')
	PUBLISHER=$(	echo $LINE | sed -E 's/'$REGEX'/\6/')
	JOURNAL=$(		echo $LINE | sed -E 's/'$REGEX'/\7/')
	NOTE=$(				echo $LINE | sed -E 's/'$REGEX'/\8/')

	echo "@$TYPE{$ID," >> resources-from-csv.bib
	echo "	author = {"$(echo $AUTHOR | sed 's/,/ and/g' )"}," >> resources-from-csv.bib
	echo "	title = {$TITLE}," >> resources-from-csv.bib
	echo "	year = {$YEAR}," >> resources-from-csv.bib
	if [ "ARTICLE" == "$TYPE" ] ; then
		echo "	journal = {$JOURNAL}," >> resources-from-csv.bib
	elif [ "PHDTHESIS" == "$TYPE" ] ; then
		echo "  school = {$PUBLISHER}," >> resources-from-csv.bib
	elif [ "MASTERSTHESIS" == "$TYPE" ] ; then
		echo "  school = {$PUBLISHER}," >> resources-from-csv.bib
	else
		echo "	publisher = {$PUBLISHER}," >> resources-from-csv.bib
	fi
	echo "	note = {$NOTE}" >> resources-from-csv.bib
	echo "}" >> resources-from-csv.bib
	echo "" >> resources-from-csv.bib

	## echo -e " - $ID\n - $TYPE\n - $AUTHOR\n - $TITLE\n - $YEAR\n - $PUBLISHER\n - $JOURNAL\n\n"

done < resources-in-csv.csv



