input automata;
outputtemplate:="%j%c.mps";




size := 25;
incominglength := 30;
loopsize := 20;
lblpos := 0.5;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(0);	% fuzzy automata recognising word hello
  
  with spacing((60,60)) matrix.a(2,7);
  
  node.a[1][1](btex $q_0 / 0$ etex);
  node.a[1][2](btex $q_1 / 0$ etex);
  node.a[1][3](btex $q_2 / 0$ etex);
  node.a[1][4](btex $q_3 / 0$ etex);
  node.a[1][5](btex $q_4 / 0$ etex);
  node.a[1][6](btex $q_5 / 1$ etex);
 
  incoming.top(lblpos,  btex $1$ etex) (a[1][1]) 180;  
  arrow.top(lblpos, btex $ h / 1$ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ e / 1$ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ l / 1$ etex) (a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
  arrow.top(lblpos, btex $ l / 1$ etex) (a[1][4],a[1][5]) a[1][4].c..a[1][5].c;
  arrow.top(lblpos, btex $ o / 1$ etex) (a[1][5],a[1][6]) a[1][5].c..a[1][6].c;
  
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(1);	% deformation: insertion of $y$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q / f$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][2]) 0;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  loop.top(lblpos, btex $ x / R(\epsilon, x) $ etex) (a[1][2]) 90;
    
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(2);	% deformation: deletion of $x$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q / f$ etex);
  node.a[1][3](btex $q' / f'$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][3]) 0;
  arrow.top(lblpos, btex $ x / d $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ \epsilon / R(x, \epsilon) $ etex) (a[1][2],a[1][3]) a[1][2].c{up}..{down}a[1][3].c;
  
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(3);	% deformation: replacement of $x$ by $y$

  with spacing((60,60)) matrix.a(2,4);
  
  node.a[1][2](btex $q / f$ etex);
  node.a[1][3](btex $q' / f'$ etex);
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ \dots $ etex) (a[1][2]) 180;
  outgoing.top(lblpos, btex $ \dots $ etex) (a[1][3]) 0;
  arrow.top(lblpos, btex $ x / d$ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ y / R(x, y)$ etex) (a[1][2],a[1][3]) a[1][2].c{up}..{down}a[1][3].c;
  
endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(4);	% deformed automata for hello|hi

  with spacing((60,60)) matrix.a(5,9);
  
  node.a[3][2](btex $q_0 / 0$ etex); % q0 - h -> q1
  node.a[2][3](btex $q_1 / 0$ etex); % q1 - e -> q2 
  node.a[2][4](btex $q_2 / 0$ etex); % q2 - l -> q3
  node.a[2][5](btex $q_3 / 0$ etex); % q3 - l -> q4
  node.a[2][6](btex $q_4 / 0$ etex); % q4 - o -> q5
  node.a[2][7](btex $q_5 / 1$ etex); % q4 - o -> q5
  node.a[4][3](btex $q_6 / 1$ etex); % q0 - i -> q6
  
  arrowcolor:=black; arrowlabelcolor:=black;
  incoming.top(lblpos, btex $ 1 $ etex) (a[3][2]) 180;
  arrow.lrt(lblpos, btex $ h / 1 $ etex) (a[3][2],a[2][3]) a[3][2].c..a[2][3].c;
  arrow.top(lblpos, btex $ e / 1 $ etex) (a[2][3],a[2][4]) a[2][3].c..a[2][4].c;
  arrow.top(lblpos, btex $ l / 1 $ etex) (a[2][4],a[2][5]) a[2][4].c..a[2][5].c;
  arrow.top(lblpos, btex $ l / 1 $ etex) (a[2][5],a[2][6]) a[2][5].c..a[2][6].c;
  arrow.top(lblpos, btex $ o / 1 $ etex) (a[2][6],a[2][7]) a[2][6].c..a[2][7].c;
  arrow.llft(lblpos, btex $ i / 0,9 $ etex) (a[3][2],a[4][3]) a[3][2].c..a[4][3].c;
 
  arrowcolor:=red; arrowlabelcolor:=red;
  arrow.top(lblpos, btex $ a / 0,5$ etex) (a[2][3],a[2][4]) a[2][3].c{up}..{down}a[2][4].c;
  arrow.bot(lblpos, btex $ \epsilon / 0,6$ etex) (a[2][4],a[2][5]) a[2][4].c{down}..{up}a[2][5].c;
  arrow.bot(lblpos, btex $ \epsilon / 0,6$ etex) (a[2][5],a[2][6]) a[2][5].c{down}..{up}a[2][6].c;
  
  loop.top(lblpos, btex $ - / 0,7$ etex) (a[3][2]) 90;
  loop.ulft(lblpos, btex $ - / 0,7$ etex) (a[2][3]) 90;
  loop.urt(lblpos, btex $ - / 0,7$ etex) (a[2][4]) 90;
  loop.top(lblpos, btex $ - / 0,7$ etex) (a[2][5]) 90;
  loop.top(lblpos, btex $ - / 0,7$ etex) (a[2][6]) 90;
  loop.top(lblpos, btex $ - / 0,7$ etex) (a[2][7]) 90;
  loop.top(lblpos, btex $ - / 0,7$ etex) (a[4][3]) 90;
endfig;




end;
