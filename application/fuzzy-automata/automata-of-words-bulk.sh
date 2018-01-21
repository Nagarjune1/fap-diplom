#!/bin/bash
# converts given word files into corresponding automata
# usage: automata-of-words-bulk..sh WORD_FILES ...

for FILE in $@; do
	OUTFILE=$FILE"-automata.timf"
	
	echo "$FILE -> $OUTFILE"
	mvn exec:java -Dexec.mainClass="cz.upol.fapapp.fa.mains.AutomataOfWordApp" -Dexec.args="$FILE $OUTFILE"
done

