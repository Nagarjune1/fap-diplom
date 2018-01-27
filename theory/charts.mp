%% compile with: mpost -tex=latex  charts.mp
prologues := 3;
outputtemplate:="%j%c.mps";
input graph

verbatimtex 
\documentclass{article}
\usepackage[utf8]{inputenc}
\begin{document}
etex

beginfig(0);	% lingusitic variables


  draw begingraph(8cm,3cm)
    gdraw "ling-vars-height-meanings.txt" withpen pencircle scaled 1.0pt;
    glabel.top(btex malá etex, 2);
    
    gdraw "ling-vars-height-meanings.txt" withpen pencircle scaled 1.2pt;
    glabel.ulft(btex střední etex, 4);
    
    gdraw "ling-vars-height-meanings.txt" withpen pencircle scaled 1.4pt;
    glabel.top(btex velká etex, 5);
    
    gdraw "ling-vars-height-meanings.txt" withpen pencircle scaled 1.8pt;
    glabel.top(btex velmi velká etex, 7);
    
    frame.llft;
    glabel.bot(btex Výška osoby (cm) etex, OUT);
    glabel.lft(btex Stupeň etex rotated 90, OUT);
  endgraph;

%draw (0, 0)--(10,10)--(5,8);

endfig;
  
  
end