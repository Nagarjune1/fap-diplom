input automata;
outputtemplate:="%j%c.mps";

verbatimtex
\documentclass{article}
\usepackage[utf8]{inputenc}
\begin{document}
etex

beginfig(0);
  size := 30;
  incominglength := 25;
  loopsize := 40;
  lblpos := 0.2;

  with spacing((90,60)) matrix.a(4,5);
  node.a[1][1](btex $ q_0 / 1 $ etex);
  node.a[2][1](btex $ q_1 / 0{,}1 $ etex);
  node.a[3][1](btex $ q_2 / 0 $ etex);

  node.a[1][2](btex $ q_0 / 1 $ etex);
  node.a[2][2](btex $ q_1 / 0{,}8 $ etex);
  node.a[3][2](btex $ q_2 / 0 $ etex);

  node.a[1][3](btex $ q_0 / 0{,}9 $ etex);
  node.a[2][3](btex $ q_1 / 0 $ etex);
  node.a[3][3](btex $ q_2 / 0{,}8 $ etex);

  node.a[1][4](btex $ q_0 / 0{,}9 $ etex);
  node.a[2][4](btex $ q_1 / 0{,}8 $ etex);
  node.a[3][4](btex $ q_2 / 0 $ etex);


	% the grayed out ones
	arrowcolor:=0.8white; arrowlabelcolor:=0.9white;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[1][1],a[3][2]) a[1][1].c..a[3][2].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[2][1],a[1][2]) a[2][1].c..a[1][2].c;
 	arrow.top(lblpos, btex $ a / 0 $ etex)    (a[2][1],a[3][2]) a[2][1].c..a[3][2].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[3][1],a[2][2]) a[3][1].c..a[2][2].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[3][1],a[3][2]) a[3][1].c..a[3][2].c;
  
	arrow.top(lblpos, btex $ b / 0 $ etex)    (a[1][2],a[2][3]) a[1][2].c..a[2][3].c;
  arrow.top(lblpos, btex $ b / 0 $ etex)    (a[1][2],a[3][3]) a[1][2].c..a[3][3].c;
  arrow.top(lblpos, btex $ b / 0 $ etex)    (a[2][2],a[1][3]) a[2][2].c..a[1][3].c;
  arrow.top(lblpos, btex $ b / 0 $ etex)    (a[2][2],a[2][3]) a[2][2].c..a[2][3].c;
  arrow.top(lblpos, btex $ b / 0 $ etex)    (a[3][2],a[1][3]) a[3][2].c..a[1][3].c;
 	arrow.top(lblpos, btex $ b / 0 $ etex)    (a[3][2],a[3][3]) a[3][2].c..a[3][3].c;
  
	arrow.top(lblpos, btex $ a / 0 $ etex)    (a[1][3],a[3][4]) a[1][3].c..a[3][4].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[2][3],a[1][4]) a[2][3].c..a[1][4].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[2][3],a[3][4]) a[2][3].c..a[3][4].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[3][3],a[2][4]) a[3][3].c..a[2][4].c;
  arrow.top(lblpos, btex $ a / 0 $ etex)    (a[3][3],a[3][4]) a[3][3].c..a[3][4].c;

	% the real ones
	arrowcolor:=black; arrowlabelcolor:=black;
 
	% proces a
  arrow.top(lblpos, btex $ a / 1 $ etex)			(a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
  arrow.urt(lblpos, btex $ a / 0{,}8 $ etex)	(a[1][1],a[2][2]) a[1][1].c..a[2][2].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[1][1],a[3][2]) a[1][1].c..a[3][2].c;

	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[2][1],a[1][2]) a[2][1].c..a[1][2].c;
  arrow.top(lblpos, btex $ a / 1 $ etex)			(a[2][1],a[2][2]) a[2][1].c..a[2][2].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[2][1],a[3][2]) a[2][1].c..a[3][2].c;

 	arrow.lrt(lblpos, btex $ a / 0{,}5 $ etex)	(a[3][1],a[1][2]) a[3][1].c..a[1][2].c;
  %arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[3][1],a[2][2]) a[3][1].c..a[2][2].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[3][1],a[3][2]) a[3][1].c..a[3][2].c;

	% proces b
  arrow.top(lblpos, btex $ b / 0{,}9 $ etex)	(a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
  %arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[1][2],a[2][3]) a[1][2].c..a[2][3].c;
	%arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[1][2],a[3][3]) a[1][2].c..a[3][3].c;

	%arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[2][2],a[1][3]) a[2][2].c..a[1][3].c;
  %arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[2][2],a[2][3]) a[2][2].c..a[2][3].c;
	arrow.urt(lblpos, btex $ b / 1 $ etex)			(a[2][2],a[3][3]) a[2][2].c..a[3][3].c;

 	%arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[3][2],a[1][3]) a[3][2].c..a[1][3].c;
  arrow.lrt(lblpos, btex $ b / 0{,}7 $ etex)	(a[3][2],a[2][3]) a[3][2].c..a[2][3].c;
	%arrow.ulft(lblpos, btex $ b / 0 $ etex)		(a[3][2],a[3][3]) a[3][2].c..a[3][3].c;

	% proces a
  arrow.top(lblpos, btex $ a / 1 $ etex)			(a[1][3],a[1][4]) a[1][3].c..a[1][4].c;
  arrow.urt(lblpos, btex $ a / 0{,}8 $ etex)	(a[1][3],a[2][4]) a[1][3].c..a[2][4].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[1][3],a[3][4]) a[1][3].c..a[3][4].c;

	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[2][3],a[1][4]) a[2][3].c..a[1][4].c;
  arrow.top(lblpos, btex $ a / 1 $ etex)			(a[2][3],a[2][4]) a[2][3].c..a[2][4].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[2][3],a[3][4]) a[2][3].c..a[3][4].c;

 	arrow.lrt(lblpos, btex $ a / 0{,}5 $ etex)	(a[3][3],a[1][4]) a[3][3].c..a[1][4].c;
  %arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[3][3],a[2][4]) a[3][3].c..a[2][4].c;
	%arrow.ulft(lblpos, btex $ a / 0 $ etex)		(a[3][3],a[3][4]) a[3][3].c..a[3][4].c;




endfig

end;
