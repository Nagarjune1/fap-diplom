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

beginfig(1);	% automata representing faces emotions of chess player
  
  size := 20;
  lblposout := 0.5;
  lblposin := 0.3;
  
  with spacing((60,60)) matrix.a(4,4);
  
  node.a[1][1](btex $q_F$ etex);
  node.a[1][3](btex $q_S$ etex);
  node.a[3][1](btex $q_H$ etex);
  node.a[3][3](btex $q_A$ etex);
  
  spl := 35;
  
 
  arrow.top(lblposout, btex $ c     $ etex) (a[1][1],a[1][3]) a[1][1].c{dir (+45-spl)}..a[1][3].c{dir (-45+spl)}; % F -> S
  arrow.bot(lblposout, btex $ n     $ etex) (a[1][3],a[1][1]) a[1][3].c{dir (+225-spl)}..a[1][1].c{dir (+135+spl)}; % S -> F
  
  arrow.top(lblposout, btex $ p_{-} $ etex) (a[3][1],a[3][3]) a[3][1].c{dir (+45-spl)}..a[3][3].c{dir (-45+spl)}; % H -> A
  arrow.bot(lblposout, btex $ p_{+} $ etex) (a[3][3],a[3][1]) a[3][3].c{dir (+225-spl)}..a[3][1].c{dir (+135+spl)}; % A -> H
  
  arrow.lft(lblposout, btex $ p_{+} $ etex) (a[1][1],a[3][1]) a[1][1].c{dir (-135+spl)}..a[3][1].c{dir (-45-spl)}; % F -> H
  arrow.rt(lblposout,  btex $ n     $ etex) (a[3][1],a[1][1]) a[3][1].c{dir (+45+spl)}..a[1][1].c{dir (-225-spl)}; % H -> F
  
  arrow.lft(lblposout, btex $ p_{-} $ etex) (a[1][3],a[3][3]) a[1][3].c{dir (-135+spl)}..a[3][3].c{dir (-45-spl)}; % S -> A
  arrow.rt(lblposout,  btex $ c     $ etex) (a[3][3],a[1][3]) a[3][3].c{dir (+45+spl)}..a[1][3].c{dir (-225-spl)}; % A -> S
  
  arrow.lft(lblposin, btex $ p_{-} $ etex) (a[1][1],a[3][3]) a[1][1].c{dir (-90+spl)}..a[3][3].c{dir (0-spl)}; % F -> A
  arrow.rt(lblposin,  btex $ n     $ etex) (a[3][3],a[1][1]) a[3][3].c{dir (+90+spl)}..a[1][1].c{dir (+180-spl)}; % A -> F
  
  arrow.lft(lblposin, btex $ p_{+} $ etex) (a[1][3],a[3][1]) a[1][3].c{dir (-180+spl)}..a[3][1].c{dir (270-spl)}; % S -> H
  arrow.rt(lblposin, btex $ c     $ etex) (a[3][1],a[1][3]) a[3][1].c{dir (0+spl)}..a[1][3].c{dir (+90-spl)}; % H -> S
  
endfig;

end.


