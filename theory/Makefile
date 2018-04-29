# #######################
# compiles and outputs the thesis into fapa-text-ki.pdf
# Martin Jasek, 29. 4. 2018
# use with `make` tool
# 

generate:
	# wget http://www.inf.upol.cz/downloads/dokumenty/kidiplom.zip
	# unzip kidiplom.zip
	cp -r kidiplom/kidiplom.cls kidiplom/kibase.sty kidiplom/graphics .
	mpost -tex=latex mpimg-theory-diags.mp
	mpost -tex=latex mpimg-apps-diags.mp
	mpost -tex=latex mpimg-charts.mp
	mpost -tex=latex mpimg-rest.mp
	# vlna fapa-text.tex
	pdflatex fapa-text-ki.tex
	bibtex fapa-text-ki.aux
	pdflatex fapa-text-ki.tex
	pdflatex fapa-text-ki.tex

clean:
	rm *.aux *.bbl *.blg *.log *.toc *.mpx *.mps *-eps-converted-to.pdf
