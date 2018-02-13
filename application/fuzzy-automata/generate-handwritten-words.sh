#!/bin/bash
# converts SVG file with words into words files
# usage: generate-handwritten-words.sh TABLE.csv name-of-resulting-timf-file-prefix-
# where TABLE.csv should look like:
#  "symbol-a";"u ul l"
#  "symbol-b";"l u d t"
# In this case will be generated two files:
#  name-of-resulting-timf-file-prefix-symbol-a.timf
#  name-of-resulting-timf-file-prefix-symbol-b.timf

TABLE=$1
OUTPFX=$2

while read LINE; do
	LINE="$(echo $LINE | sed 's/"//g')"
	NAME="$(awk -F\; '{print $1}' <<< $LINE)"
	SYMBOLS="$(awk -F\; '{print $2}' <<< $LINE)"

	OUTFILE="$OUTPFX""$NAME"".timf"

	echo -n "" > "$OUTFILE"

	echo "type:" >> "$OUTFILE"
	echo "	word" >> "$OUTFILE"
	echo "alphabet:" >> "$OUTFILE"
	echo "	u, ul, l, dl, d, dr, r, ur" >> "$OUTFILE"
	echo "word:" >> "$OUTFILE"
	echo "	$SYMBOLS" >> "$OUTFILE"
	echo "" >> "$OUTFILE"

	echo word $NAME is $SYMBOLS
done <$TABLE

