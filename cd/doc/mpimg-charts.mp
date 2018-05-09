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
 draw begingraph(10cm,3cm)
    
 
 
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 2pt withcolor .3blue;
    glabel.top(btex $M(X_m)$ etex, 30);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 2pt withcolor .3green;
    glabel.top(btex $M(X_s)$ etex, 70);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 2pt withcolor .3red;
    glabel.top(btex $M(X_v)$ etex, 95);
    
    gdraw "chartdata-ling-vars-height-meanings.csv" withpen pencircle scaled 2pt withcolor .3[red,blue];
    glabel.top(btex $M(X_{vv})$ etex, 130);
    
    grid.lft(format("%g", 0), 0) withcolor .5white;
    grid.lft(format("%g", 1), 1) withcolor .5white;
    autogrid(grid.bot,) withcolor .9white;
    
    frame.llft;
    glabel.bot(btex Výška osoby (cm) etex, OUT);
    glabel.lft(btex Stupeň etex rotated 90, OUT);
  endgraph;

endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(1);	% analogue signal
  draw begingraph(4cm,3cm)
    
%     grid.lft(format("%g", 0), 0) withcolor .5white;
%     grid.lft(format("%g", 1), 1) withcolor .5white;
%     autogrid(grid.bot,) withcolor .9white;
 
    gdraw "chartdata-signal-proces.csv" withpen pencircle scaled 2pt;
    
    frame.llft;
    glabel.bot(btex čas etex, OUT);
    glabel.lft(btex veličina etex rotated 90, OUT);
  endgraph;

endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(2);	% analogue signal, quanted
    interim linecap:=2;
    draw begingraph(4cm,3cm)
  
   
      gdata("chartdata-signal-proces.csv", v,
	    path p;
	    augment p (v1,0);
	    augment p (v1,v2);
	    gdraw p withpen pencircle scaled 4bp;
      );
	  
      frame.llft;
      glabel.bot(btex čas etex, OUT);
      glabel.lft(btex veličina etex rotated 90, OUT);
      
      for y=0,1,2,3,4:
	grid.lft(format("%g", y), y) withcolor .8white;
      endfor
      autogrid(grid.bot,);
  
    endgraph;

endfig;
  
end