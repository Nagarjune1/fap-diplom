%% compile with: mpost -tex=latex mpimg-apps-diags.mp
prologues := 3;
outputtemplate:="%j%c.mps";
input automata;

verbatimtex 
\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{amsmath}
\begin{document}
etex

  size := 40;
  incominglength := 25;
  loopsize := 40;
  lblpos := 0.5;

  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(0);	% automaton for power consumptions

  size := 40;
  lblposout := 0.5;

  with spacing((50,50)) matrix.a(6,6);
  
  node.a[1][1](btex \begin{tabular}{c} $f = \text{off}$ \\ $l = \text{off}$ \end{tabular} etex);
  node.a[1][3](btex \begin{tabular}{c} $f = \text{cold}$ \\ $l = \text{off}$ \end{tabular} etex);
  node.a[1][5](btex \begin{tabular}{c} $f = \text{hot}$ \\ $l = \text{off}$ \end{tabular} etex);
  node.a[3][1](btex \begin{tabular}{c} $f = \text{off}$ \\ $l = \text{on}$ \end{tabular} etex);
  node.a[3][3](btex \begin{tabular}{c} $f = \text{cold}$ \\ $l = \text{on}$ \end{tabular}  etex);
  node.a[3][5](btex \begin{tabular}{c} $f = \text{hot}$ \\ $l = \text{on}$ \end{tabular} etex);
  
  spl := 35;
  splout := 5;
  % top row (light off)
  arrow.top(lblposout, btex $ s+    $ etex) (a[1][1],a[1][3]) a[1][1].c{dir (+45-spl)}..a[1][3].c{dir (-45+spl)}; % 
  arrow.bot(lblposout, btex $ s-    $ etex) (a[1][3],a[1][1]) a[1][3].c{dir (+225-spl)}..a[1][1].c{dir (+135+spl)}; % 
  
  arrow.top(lblposout, btex $ m+    $ etex) (a[1][3],a[1][5]) a[1][3].c{dir (+45-spl)}..a[1][5].c{dir (-45+spl)}; % 
  arrow.bot(lblposout, btex $ m-    $ etex) (a[1][5],a[1][3]) a[1][5].c{dir (+225-spl)}..a[1][3].c{dir (+135+spl)}; % 
  
  arrow.bot(lblposout, btex $ b+    $ etex) (a[1][1],a[1][5]) a[1][1].c{dir (+45-splout)}..a[1][5].c{dir (-45+splout)}; % 
  arrow.top(lblposout, btex $ b-    $ etex) (a[1][5],a[1][1]) a[1][5].c{dir (+135-splout)}..a[1][1].c{dir (-135+splout)}; % 
  
  % bottom row (light on)
  arrow.top(lblposout, btex $ s+    $ etex) (a[3][1],a[3][3]) a[3][1].c{dir (+45-spl)}..a[3][3].c{dir (-45+spl)}; % 
  arrow.bot(lblposout, btex $ s-    $ etex) (a[3][3],a[3][1]) a[3][3].c{dir (+225-spl)}..a[3][1].c{dir (+135+spl)}; % 
  
  arrow.top(lblposout, btex $ m+    $ etex) (a[3][3],a[3][5]) a[3][3].c{dir (+45-spl)}..a[3][5].c{dir (-45+spl)}; % 
  arrow.bot(lblposout, btex $ m-    $ etex) (a[3][5],a[3][3]) a[3][5].c{dir (+225-spl)}..a[3][3].c{dir (+135+spl)}; % 
  
  arrow.bot(lblposout, btex $ b+    $ etex) (a[3][1],a[3][5]) a[3][1].c{dir (-45-splout)}..a[3][5].c{dir (+45+splout)}; % 
  arrow.top(lblposout, btex $ b-    $ etex) (a[3][5],a[3][1]) a[3][5].c{dir (-135-splout)}..a[3][1].c{dir (+135+splout)}; % 
  
  % vertical ones
  arrow.lft(lblposout, btex $ s+    $ etex) (a[1][1],a[3][1]) a[1][1].c{dir (-135+spl)}..a[3][1].c{dir (-45-spl)}; % 
  arrow.rt(lblposout,  btex $ s-    $ etex) (a[3][1],a[1][1]) a[3][1].c{dir (+45+spl)}..a[1][1].c{dir (-225-spl)}; % 
  
  arrow.lft(lblposout, btex $ s+    $ etex) (a[1][3],a[3][3]) a[1][3].c{dir (-135+spl)}..a[3][3].c{dir (-45-spl)}; % 
  arrow.rt(lblposout,  btex $ s-    $ etex) (a[3][3],a[1][3]) a[3][3].c{dir (+45+spl)}..a[1][3].c{dir (-225-spl)}; % 
    
  arrow.lft(lblposout, btex $ s+    $ etex) (a[1][5],a[3][5]) a[1][5].c{dir (-135+spl)}..a[3][5].c{dir (-45-spl)}; % 
  arrow.rt(lblposout,  btex $ s-    $ etex) (a[3][5],a[1][5]) a[3][5].c{dir (+45+spl)}..a[1][5].c{dir (-225-spl)}; % 
 

%   
%   arrow.top(lblposout, btex $ +m    $ etex) (a[1][1],a[1][3]) a[1][1].c{dir (+45-spl)}..a[1][3].c{dir (-45+spl)}; % 
%   arrow.bot(lblposout, btex $ -m    $ etex) (a[1][3],a[1][1]) a[1][3].c{dir (+225-spl)}..a[1][1].c{dir (+135+spl)}; % 
%   
%   arrow.top(lblposout, btex $       $ etex) (a[3][1],a[3][3]) a[3][1].c{dir (+45-spl)}..a[3][3].c{dir (-45+spl)}; % 
%   arrow.bot(lblposout, btex $       $ etex) (a[3][3],a[3][1]) a[3][3].c{dir (+225-spl)}..a[3][1].c{dir (+135+spl)}; % 
%   
%   arrow.lft(lblposout, btex $       $ etex) (a[1][1],a[3][1]) a[1][1].c{dir (-135+spl)}..a[3][1].c{dir (-45-spl)}; % 
%   arrow.rt(lblposout,  btex $ n     $ etex) (a[3][1],a[1][1]) a[3][1].c{dir (+45+spl)}..a[1][1].c{dir (-225-spl)}; % 
%   
%   arrow.lft(lblposout, btex $       $ etex) (a[1][3],a[3][3]) a[1][3].c{dir (-135+spl)}..a[3][3].c{dir (-45-spl)}; % 
%   arrow.rt(lblposout,  btex $ c     $ etex) (a[3][3],a[1][3]) a[3][3].c{dir (+45+spl)}..a[1][3].c{dir (-225-spl)}; % 
%   
  
endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


end.


