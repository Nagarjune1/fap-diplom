# #######################
# compiles and outputs the tehesis into fapa-text.pdf
# Martin Jasek, 29. 4. 2018
# use with `make` tool
# 

generate:
	mpost -tex=latex mpimg-theory-diags.mp
	mpost -tex=latex mpimg-apps-diags.mp
	mpost -tex=latex mpimg-charts.mp
	mpost -tex=latex mpimg-rest.mp
	pdflatex fapa-text.tex
	bibtex fapa-text.aux
	pdflatex fapa-text.tex
	pdflatex fapa-text.tex

clean:
	rm *.aux *.bbl *.blg *.log *.toc *.mpx *.mps
