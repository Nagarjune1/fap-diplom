#!/bin/bash
# infers all \cite keys from tmp-fapa.tex, removes duplicities and stores into resources-list.csv
# 28. 4. 2018

cat ../tmp-fapa.tex \
	| sed -E 's/\\cite\{([^\}]+)\}/\n\\cite{\1}\n/g' \
	| grep "\cite" \
	| sed -E 's/\\cite\{([^\}]+)\}/\1/g' \
	| awk '!x[$0]++' \
	> resources-list.csv


