input automata;
outputtemplate:="%j%c.mps";


%verbatimtex
%btex
%% generic layout
%\documentclass{article}
%\usepackage[english]{babel}
%\usepackage[latin1]{inputenc}
%\selectlanguage{english}
%% additional packages
%\usepackage{amsmath}
%\begin{document}
%etex

beginfig(0);	% automat rozpoznávající hello
  
  size := 15;
  incominglength := 25;
  loopsize := 15;
  lblpos := 0.5;
  
  with spacing((40,60)) matrix.a(2,7);
  
  node.a[1][1](btex $q_0$ etex);
  node.a[1][2](btex $q_1$ etex);
  node.a[1][3](btex $q_2$ etex);
  node.a[1][4](btex $q_3$ etex);
  node.a[1][5](btex $q_4$ etex);
  node_double.a[1][6](btex $q_5$ etex);
 
  incoming(0, "") (a[1][1]) 180;  
  arrow.top(lblpos, btex $ h $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ e $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ l $ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
  arrow.top(lblpos, btex $ l $ etex) (a[1][4],a[1][5]) a[1][4].c..a[1][5].c;
  arrow.top(lblpos, btex $ o $ etex) (a[1][5],a[1][6]) a[1][5].c..a[1][6].c;
  
endfig;

beginfig(1);	% deformovaný automat, náhrada $x$ za $y$
  size := 20;
    
  with spacing((40,60)) matrix.a(2,3);
  
  node.a[1][1](btex $q_i$ etex);
  node.a[1][2](btex $q_{i+1}$ etex);
 
  arrowcolor:=black; arrowlabelcolor:=black;
  arrow.top(lblpos, btex $ x $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ y $ etex) (a[1][1],a[1][2]) a[1][1].c{up}..{down}a[1][2].c;
    
endfig;

beginfig(2);	% deformovaný automat, vložení ￼$￼￼￼x$
    
  with spacing((40,60)) matrix.a(2,4);
  
  node.a[1][1](btex $q_i$ etex);
  bordercolor:= red; nodelabelcolor:=red;
  node.a[1][2](btex $q'_i$ etex);
  bordercolor:= black; nodelabelcolor:=black;
  node.a[1][3](btex $q_{i+1}$ etex);
 
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ \epsilon $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ y $ etex) (a[1][1],a[1][2]) a[1][1].c{up}..{down}a[1][2].c;
  arrow.top(lblpos, btex $ x $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
    
endfig;


beginfig(3); % deformovaný automat, odebrání $x$
    
  with spacing((40,60)) matrix.a(2,3);
  
  node.a[1][1](btex $q_i$ etex);
  node.a[1][2](btex $q_{i+1}$ etex);
 
  arrowcolor:=black; arrowlabelcolor:=black;
  arrow.top(lblpos, btex $ x $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ \epsilon $ etex) (a[1][1],a[1][2]) a[1][1].c{up}..{down}a[1][2].c;
    
endfig;


beginfig(4);	% automat na bázi editačních operací rozpoznávající abc/1
    
  with spacing((80,60)) matrix.a(2,5);
  loopsize := 30;
  
  bordercolor := black; nodelabelcolor := black;
  node.a[1][1](btex $q_0$ etex);
  node.a[1][2](btex $q_1$ etex);
  node.a[1][3](btex $q_2$ etex);
  node.a[1][4](btex $q_3$ etex);
  
  arrowcolor := red; arrowlabelcolor := red;
  arrow.top(lblpos, btex $ a/1 $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ b/1 $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ c/1 $ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
  
  arrowcolor := black; arrowlabelcolor := black;
  loop.top(lblpos, btex $ a/0.2, \epsilon/1 $ etex) (a[1][1]) 90;
  loop.top(lblpos, btex $ a/0.2, \epsilon/1 $ etex) (a[1][2]) 90;
  loop.top(lblpos, btex $ a/0.2, \epsilon/1 $ etex) (a[1][3]) 90;
  loop.top(lblpos, btex $ a/0.2, \epsilon/1 $ etex) (a[1][4]) 90;
  
  arrow.bot(lblpos, btex $ a/0.5, c/0.5$ etex) (a[1][2],a[1][3]) a[1][2].c{down}..{up}a[1][3].c;
  arrow.bot(lblpos, btex $ b/0.5$ etex) (a[1][1],a[1][2]) a[1][1].c{down}..{up}a[1][2].c;
  arrow.bot(lblpos, btex $ b/0.5, \epsilon/0.1$ etex) (a[1][3],a[1][4]) a[1][3].c{down}..{up}a[1][4].c;
 
  %%arrow.bot(lblpos, btex $ \epsilon/0.1$ etex) (a[1][3],a[1][4]) a[1][3].c{0,-4}..{0,+4}a[1][4].c;
  
  
endfig;

beginfig(5);	% automat rozpoznávající abc pracující s g_s
  
  size := 15;
  incominglength := 25;
  loopsize := 15;
  lblpos := 0.5;
  
  with spacing((120,60)) matrix.a(2,5);
  
  node.a[1][1](btex $q_0$ etex);
  node.a[1][2](btex $q_1$ etex);
  node.a[1][3](btex $q_2$ etex);
  node.a[1][4](btex $q_3$ etex);
   
  arrow.top(lblpos, btex $ a/1$ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ b/1$ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ c/1$ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;

  arrow.bot(lblpos, btex $ b/0,5, c/0,3 $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.bot(lblpos, btex $ a/0,5, c/0,5, d/0,3 $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.bot(lblpos, btex $ a/0,3, b/0,5, d/0,5$ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
endfig;

% 
% beginfig(1);
% 
%   size := 15;
%   incominglength := 25;
%   loopsize := 15;
%   
% 	with spacing((40,60)) matrix.a(2,7);
%   
%   node.a[1][1](btex $q_0$ etex);
%   node.a[1][2](btex $q_1$ etex);
%   node.a[1][3](btex $q_2$ etex);
% 	node.a[1][4](btex $q_3$ etex);
%  	node.a[1][5](btex $q_4$ etex);
% 	node_double.a[1][6](btex $q_5$ etex);
%  
%   incoming(0, "") (a[1][1]) 180;
%   arrow.top(lblpos, btex $ h/1 $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
%   arrow.top(lblpos, btex $ e/1 $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
%   arrow.top(lblpos, btex $ l/1 $ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
%   arrow.top(lblpos, btex $ l/1 $ etex) (a[1][4],a[1][5]) a[1][4].c..a[1][5].c;
%   arrow.top(lblpos, btex $ o/1 $ etex) (a[1][5],a[1][6]) a[1][5].c..a[1][6].c;
%   
% endfig;
% 
% 
% beginfig(2);
% 
%   size := 15;
%   incominglength := 25;
%   loopsize := 15;
%   
% 	with spacing((40,60)) matrix.a(2,8);
%   
%   node.a[1][1](btex $q_0$ etex);
%   node.a[1][2](btex $q_1$ etex);
%   node.a[1][3](btex $q_2$ etex);
% 	node.a[1][4](btex $q_3$ etex);
%  	node.a[1][5](btex $q_4$ etex);
% 	node.a[1][6](btex $q_5$ etex);
%  	node_double.a[1][7](btex $q_6$ etex);
%  
% 
%   incoming(0, "") (a[1][1]) 180;
%   arrow.top(lblpos, btex $ h/1 $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
%   arrow.top(lblpos, btex $ e/1 $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
%   arrow.top(lblpos, btex $ a/0.6 $ etex) (a[1][2],a[1][3]) a[1][2].c{0, +10}..{0, -10}a[1][3].c;
% 
% 	arrow.top(lblpos, btex $ l/1 $ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
%   arrow.top(lblpos, btex $ l/1 $ etex) (a[1][4],a[1][5]) a[1][4].c..a[1][5].c;
%   arrow.bot(lblpos, btex $ \epsilon/0.7 $ etex) (a[1][4],a[1][5]) a[1][4].c{0, -10}..{0, +10}a[1][5].c;
%   
% 	arrow.top(lblpos, btex $ o/1 $ etex) (a[1][5],a[1][6]) a[1][5].c..a[1][6].c;
% 	arrow.top(lblpos, btex $ \epsilon/1 $ etex) (a[1][6],a[1][7]) a[1][6].c..a[1][7].c;
% 	arrow.top(lblpos, btex $ w/0lblpos $ etex) (a[1][6],a[1][7]) a[1][6].c{0, +10}..{0, -10}a[1][7].c;
%   
% endfig;
%  
% beginfig(3);
% 	ds:=3;
% 	
% 	pair ze[];
% 	ze[1]:=(0,0); ze[2]:=(10,0); ze[3]:=(20,10); ze[4]:=(15,20); ze[5]:=(10,10); ze[6]:=(20,0); ze[7]:=(30,10); ze[8]:=(40,20);
% 	
% 	draw ze[1]..ze[2]..ze[3]..ze[4]..ze[5]..ze[6]..ze[7]..ze[8];
% endfig;
% 
% beginfig(4);
% 	draw ze[1]..ze[2]..ze[3]..ze[4]..ze[5]..ze[6]..ze[7]..ze[8];
% 	for i=1 upto 8: fill fullcircle scaled ds shifted ze[i]; endfor;
% endfig;
% 
% beginfig(5);
% 	draw ze[1]--ze[2]--ze[3]--ze[4]--ze[5]--ze[6]--ze[7]--ze[8];
% 	for i=1 upto 8: fill fullcircle scaled ds shifted ze[i]; endfor;
% endfig;
% 
%  
% beginfig(6);
% 	pair zi[];
% 	zi[1]:=(0,0); zi[2]:=(10,0); zi[3]:=(15,10); zi[4]:=(15,20); zi[5]:=(15,10);zi[6]:=(20,0);zi[7]:=(30,10);zi[8]:=(40,20);
% 	
% 	draw zi[1]..zi[2]..zi[3]..{up}zi[4]{down}..zi[5]..zi[6]..zi[7]..zi[8];
% endfig;
% 
% beginfig(7);
% 	draw zi[1]..zi[2]..zi[3]..{up}zi[4]{down}..zi[5]..zi[6]..zi[7]..zi[8];
% 	for i=1 upto 8: fill fullcircle scaled ds shifted zi[i]; endfor;
% endfig;
% 
% beginfig(8);
% 	draw zi[1]--zi[2]--zi[3]--zi[4]--zi[5]--zi[6]--zi[7]--zi[8];
% 	for i=1 upto 8: fill fullcircle scaled ds shifted zi[i]; endfor;
% endfig;


end.


