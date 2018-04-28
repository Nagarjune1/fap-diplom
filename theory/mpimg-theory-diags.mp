%% compile with: mpost -tex=latex mpimg-theory-diags.mp
prologues := 3;
outputtemplate:="%j%c.mps";
input automata;

verbatimtex 
\documentclass{article}
\usepackage[utf8]{inputenc}
\begin{document}
etex

  size := 50;
  incominglength := 25;
  loopsize := 40;
  lblpos := 0.5;

  
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(0);	% some fuzzy automaton

  with spacing((60,60)) matrix.a(4,4);
  %\begin{tabular}{@{}c@{}} $q_0$ \\ $\sigma(q_0) = 1, \eta(q_0) = 0$ \end{tabular}
  node.a[2][1](btex {\Large $q_0$}, $1, 0$ etex);
  node.a[1][3](btex {\Large $q_1$}, $0{,}1, 0{,}9$ etex);
  node.a[3][3](btex {\Large $q_2$}, $0, 0{,}6$ etex);
 
  arrow.ulft(lblpos, btex $ (a, 0{,}8) $ etex) 	(a[2][1],a[1][3]) a[2][1].c{dir 90}..{dir 0}a[1][3].c;
  arrow.urt (lblpos, btex $ (b, 1) $ etex) (a[1][3],a[3][3]) a[1][3].c{dir -45}..a[3][3].c;
  arrow.ulft(lblpos, btex $ (b, 0{,}7) $ etex) (a[3][3],a[1][3]) a[3][3].c{dir 135}..a[1][3].c;
  arrow.llft(lblpos, btex $ (a, 0{,}5) $ etex) (a[3][3],a[2][1]) a[3][3].c{dir 180}..{dir 90}a[2][1].c;
  
  loop.lft(lblpos, btex $ (a, 1)$, $(b, 0{,}9) $ etex) (a[2][1]) 180;
  loop.top(lblpos, btex $ (a, 1) $ etex) (a[1][3]) 90;
  
  
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(1);	% fuzzy automata recognising word hello
  
  size := 35;
  
  with spacing((60,60)) matrix.a(2,7);
  
  node.a[1][1](btex $q_0, 1, 0$ etex);
  node.a[1][2](btex $q_1, 0, 0$ etex);
  node.a[1][3](btex $q_2, 0, 0$ etex);
  node.a[1][4](btex $q_3, 0, 0$ etex);
  node.a[1][5](btex $q_4, 0, 0$ etex);
  node.a[1][6](btex $q_5, 0, 1$ etex);
 
  arrow.top(lblpos, btex $ (h, 1)$ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ (e, 1)$ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ (l, 1)$ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
  arrow.top(lblpos, btex $ (l, 1)$ etex) (a[1][4],a[1][5]) a[1][4].c..a[1][5].c;
  arrow.top(lblpos, btex $ (o, 1)$ etex) (a[1][5],a[1][6]) a[1][5].c..a[1][6].c;
  
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

  incominglength := 50;
  outgoinglength := 50;
  loopsize := 20;

beginfig(2);	% deformation: insertion of $y$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q, f, e$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][2]) 0;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  loop.top(lblpos, btex $ (x,  R(\epsilon, x)) $ etex) (a[1][2]) 90;
    
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(3);	% deformation: deletion of $x$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q, f, e$ etex);
  node.a[1][3](btex $q', f', e'$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][3]) 0;
  arrow.top(lblpos, btex $ (x, d) $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ (\epsilon, R(x, \epsilon)) $ etex) (a[1][2],a[1][3]) a[1][2].c{up}..{down}a[1][3].c;
  
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(4);	% deformation: replacement of $x$ by $y$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q, f, e$ etex);
  node.a[1][3](btex $q', f', e$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][3]) 0;
  arrow.top(lblpos, btex $ (x, d)$ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ (y, R(x, y))$ etex) (a[1][2],a[1][3]) a[1][2].c{up}..{down}a[1][3].c;
  
endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(5);	% deformed automata for hello|hi

  size := 30;
  with spacing((60,60)) matrix.a(5,9);
  
  node.a[3][2](btex $q_0, 1, 0$ etex); % q0 - h -> q1
  node.a[3][3](btex $q_1, 0, 0$ etex); % q1 - e -> q2 
  node.a[2][4](btex $q_2, 0, 0$ etex); % q2 - l -> q3
  node.a[2][5](btex $q_3, 0, 0$ etex); % q3 - l -> q4
  node.a[2][6](btex $q_4, 0, 0$ etex); % q4 - o -> q5
  node.a[2][7](btex $q_5, 0, 1$ etex); % q4 - o -> q5
  node.a[4][4](btex $q_6, 0, 1$ etex); % q1 - i -> q6
  
  arrowcolor:=black; arrowlabelcolor:=black;
  arrow.top(lblpos, btex $ (h, 1 )$ etex) (a[3][2],a[3][3]) a[3][2].c..a[3][3].c;
  arrow.ulft(lblpos, btex $ (e, 1 )$ etex) (a[3][3],a[2][4]) a[3][3].c..a[2][4].c;
  arrow.top(lblpos, btex $ (l, 1 )$ etex) (a[2][4],a[2][5]) a[2][4].c..a[2][5].c;
  arrow.top(lblpos, btex $ (l, 1 )$ etex) (a[2][5],a[2][6]) a[2][5].c..a[2][6].c;
  arrow.top(lblpos, btex $ (o, 1 )$ etex) (a[2][6],a[2][7]) a[2][6].c..a[2][7].c;
  arrow.llft(lblpos, btex $ (i, 0{,}9 )$ etex) (a[3][3],a[4][4]) a[3][3].c..a[4][4].c;
 
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.lrt(lblpos, btex $ (a, 0{,}5)$ etex) (a[3][3],a[2][4]) a[3][3].c{right}..{up}a[2][4].c;
  
  arrowcolor:=green; arrowlabelcolor:=green;
  arrow.bot(lblpos, btex $ (\epsilon, 0{,}6)$ etex) (a[2][4],a[2][5]) a[2][4].c{down}..{up}a[2][5].c;
  arrow.bot(lblpos, btex $ (\epsilon, 0{,}6)$ etex) (a[2][5],a[2][6]) a[2][5].c{down}..{up}a[2][6].c;
  
  arrowcolor:=blue; arrowlabelcolor:=blue;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[3][2]) 90;
  loop.ulft(lblpos, btex $ (-, 0{,}7)$ etex) (a[3][3]) 90;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[2][4]) 90;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[2][5]) 90;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[2][6]) 90;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[2][7]) 90;
  loop.top(lblpos, btex $ (-, 0{,}7)$ etex) (a[4][4]) 90;
endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


end.
