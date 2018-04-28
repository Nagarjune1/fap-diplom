%% compile with: mpost -tex=latex  mpimg-charts.mp
prologues := 3;
outputtemplate:="%j%c.mps";
input graph;

verbatimtex 
\documentclass{article}
\usepackage[utf8]{inputenc}
\begin{document}
etex

beginfig(0);	% lingusitic variables
 draw begingraph(8cm,3cm)
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 1.0pt;
    glabel.top(btex $X_m$ etex, 10);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 1.2pt;
    glabel.top(btex $X_s$ etex, 14);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 1.4pt;
    glabel.top(btex $X_v$ etex, 18);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 1.8pt;
    glabel.top(btex $X_{vv}$ etex, 22);
    
    grid.lft(format("%g", 0), 0) withcolor .5white;
    grid.lft(format("%g", 1), 1) withcolor .5white;
    autogrid(grid.bot,) withcolor .9white;
    
    frame.llft;
    glabel.bot(btex Výška osoby (cm) etex, OUT);
    glabel.lft(btex Stupeň etex rotated 90, OUT);
  endgraph;

endfig;
  
  
end