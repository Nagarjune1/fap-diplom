#!/bin/bash
# converts given word files into corresponding automaton
# usage: automaton-of-words-bulk..sh WORD_FILES ...

for FILE in $@; do
	OUTFILE=$FILE"-automaton.timf"
	
	echo "$FILE -> $OUTFILE"
	mvn exec:java -Dexec.mainClass="cz.upol.fapapp.fa.mains.AutomatonOfWordApp" -Dexec.args="$FILE $OUTFILE"
done

