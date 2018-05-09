#!/bin/bash
# runs COMPLETE hanwritten text recognition
# in fact just a bulk of commands to be run separatelly

# clean
rm test-data/handwritten/gen-word-*.timf

# table -> words
./generate-handwritten-words.sh test-data/handwritten/all-words.csv test-data/handwritten/gen-word-

# words -> automata
./automata-of-words-bulk.sh test-data/handwritten/gen-word-*.timf

# automata -> deformed
./deform-bulk.sh "replace relation test-data/handwritten/similarity.timf insert-one degree 0.6 insert-more degree 0.1" test-data/handwritten/gen-word-*-automaton.timf

# run on deformed automata
mvn exec:java -Dexec.mainClass="cz.upol.fapapp.fa.mains.HandwrittenTextGuiApp" -Dexec.args="$(ls test-data/handwritten/gen-word-*.timf-automaton.timf-deformed.timf | tr '\n' ' ')"
