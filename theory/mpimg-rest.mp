%% compile with: mpost -tex=latex mpimg-rest.mp
prologues := 3;
outputtemplate:="%j%c.mps";

verbatimtex 
\documentclass{article}
\usepackage[utf8]{inputenc}
\usepackage{amsmath}
\begin{document}
etex

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(0);	%tree of M(x x)
  pair Cm, Cx, Cxx;
  Cm := (135, 60); Cx := (120, 40); Cxx := (150, 40); 
  
  dotlabel.ulft(btex $M$ etex, Cm);
  dotlabel.ulft(btex $x$ etex, Cx);
  dotlabel.urt(btex $x$ etex, Cxx);
  
  draw Cm--Cx;	draw Cm--Cxx;
endfig;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(1);	%tree of S(x)
  pair As, Ap;
  As := (15, 60); Ap := (15, 40);
  
  dotlabel.ulft(btex $S$ etex, As);
  dotlabel.ulft(btex $x$ etex, Ap);
  
  draw As--Ap;
endfig;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(2);	%tree of R(M(x S(y)) x)
  pair Bp, Bm, By, Bs, Bx, Byy;
  Bp := (75, 60); Bm := (60, 40); By := (45, 20); Bs := (75, 20); Bx := (75, 00); Byy := (90, 40); 
  
  dotlabel.ulft(btex $M$ etex, Bp);
  dotlabel.ulft(btex $M$ etex, Bm);
  dotlabel.urt(btex $S$ etex, Bs);
  dotlabel.urt(btex $y$ etex, Bx);
  dotlabel.ulft(btex $x$ etex, By);
  dotlabel.urt(btex $x$ etex, Byy);
  
  draw Bp--Bm;	draw Bp--Byy;
  draw Bm--Bs;	draw Bm--By;
  draw Bs--Bx;	
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(3);	% handwritten text recognition sample
   ds:=3;
  
  pair ze[];
  ze[1]:=(0,0); ze[2]:=(20,0); ze[3]:=(40,20); ze[4]:=(30,40); ze[5]:=(20,20); ze[6]:=(40,0); ze[7]:=(60,10); ze[8]:=(80,40);
  
  pair zel[];
  pair zem[];
  pair zer[];
  for i=1 upto 8: zel[i] := ze[i] shifted (0, 0); endfor;
  for i=1 upto 8: zem[i] := ze[i] shifted (100, 0); endfor;
  for i=1 upto 8: zer[i] := ze[i] shifted (200, 0); endfor;
  
  % first one
  draw      zel[1]..zel[2]..zel[3]..zel[4]..zel[5]..zel[6]..zel[7]..zel[8];
  
  % second one
  draw      zem[1]..zem[2]..zem[3]..zem[4]..zem[5]..zem[6]..zem[7]..zem[8];
  for i=1 upto 8: fill fullcircle scaled ds shifted zem[i]; endfor;
  
  % third one
  for i=1 upto 7: drawarrow zer[i]--zer[i+1]; endfor;
endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

beginfig(4);	% 8 dimms around the cell
  
  numeric s;
  s=50;
  
  vardef drawGrid(expr x) =
    for i = 0 upto 3:
      draw (-1.5 * s, (i - 1.5) * s)   -- (+1.5 * s, (i - 1.5) * s);
      draw ((i - 1.5) * s, -1.5 * s)   -- ((i - 1.5) * s, +1.5 * s);
    endfor;
  enddef;
  
  drawGrid(42);
   
  vardef dimm(expr i, j, shorthand, indexed, name) = 
    pair c;
    c := (i * s, (- j) * s);
    
    label.top(shorthand, c);
    label.bot(indexed, c);
    
    pair as, ae;
    as := ((i * 1.5) * s, ((- j) * 1.5) * s);
    ae := ((i * 2) * s, ((- j) * 2) * s);
    
    
    drawarrow as -- ae;
    
   enddef;
   
   dimm(-1, -1, btex $UL$ etex, btex $c_{i-1,j-1}$ etex, btex vlevo nahoře etex);
   dimm( 0, -1, btex $U$ etex, btex $c_{i,j-1}$ etex, btex nahoře etex);
   dimm(+1, -1, btex $UR$ etex, btex $c_{i+1,j-1}$ etex, btex vpravo nahoře etex);
   
   dimm(-1,  0, btex $L$ etex, btex $c_{i-1,j}$ etex, btex vlevo etex);
   dimm( 0,  0, btex $\ $ etex, btex $c_{i,j}$ etex, btex etex);
   dimm(+1,  0, btex $R$ etex, btex $c_{i+1,j}$ etex, btex vpravo etex);
   
   dimm(-1, +1, btex $BL$ etex, btex $c_{i-1,j+1}$ etex, btex vlevo dole etex);
   dimm( 0, +1, btex $B$ etex, btex $c_{i,j+1}$ etex, btex dole etex);
   dimm(+1, +1, btex $BR$ etex, btex $c_{i+1,j+1}$ etex, btex vpravo dole etex);

   %    label.top(btex $UL$ etex, 		(0.5 * s, 2.5 * s));
%    label.bot(btex $c_{i-1,j-1}$ etex, 	(0.5 * s, 2.5 * s));
%    label.top(btex $U$ etex, 		(1.5 * s, 2.5 * s));
%    label.bot(btex $c_{i,j-1}$ etex, 	(1.5 * s, 2.5 * s));
%    label.top(btex $UR$ etex, 		(2.5 * s, 2.5 * s));
%    label.bot(btex $c_{i+1,j-1}$ etex, 	(2.5 * s, 2.5 * s));
%   
%    label.top(btex $L$ etex, 		(0.5 * s, 1.5 * s));
%    label.bot(btex $c_{i-1,j}$ etex, 	(0.5 * s, 1.5 * s));
%    label.top(btex $ $ etex, 		(1.5 * s, 1.5 * s));
%    label.bot(btex $c_{i,j}$ etex, 	(1.5 * s, 1.5 * s));
%    label.top(btex $R$ etex, 		(2.5 * s, 1.5 * s));
%    label.bot(btex $c_{i+1,j}$ etex, 	(2.5 * s, 1.5 * s));
%    
%    label.top(btex $BL$ etex, 		(0.5 * s, 0.5 * s));
%    label.bot(btex $c_{i-1,j+1}$ etex, 	(0.5 * s, 0.5 * s));
%    label.top(btex $B$ etex, 		(1.5 * s, 0.5 * s));
%    label.bot(btex $c_{i,j+1}$ etex, 	(1.5 * s, 0.5 * s));
%    label.top(btex $BR$ etex, 		(2.5 * s, 0.5 * s));
%    label.bot(btex $c_{i+1,j+1}$ etex, 	(2.5 * s, 0.5 * s));
%   
%   draw (0, 1*s) -- (3*s, 1*s);
%   draw (0, 2*s) -- (3*s, 2*s);
%   draw (0, 3*s) -- (3*s, 3*s);
% 
%   draw (0, 0)   -- (3*s, 0);
%   draw (0, 1*s) -- (3*s, 1*s);
%   draw (0, 2*s) -- (3*s, 2*s);
%   draw (0, 3*s) -- (3*s, 3*s);
%   
  

endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


beginfig(5); %edge through cell

  fill (+1.5 * s, -1.5 * s) -- (-1.5 * s, -1.5 * s) -- (-1.5 * s, +0.5 * s) -- (+1.5 * s, +0.5 * s) -- cycle withcolor 0.8;
  
  numeric as, ae;
  as = 0.2 * s;
  ae = 0.8 * s;
  
  drawarrow (-s, +as) -- (-s, +ae) withcolor red;
  drawarrow ( 0, +as) -- ( 0, +ae) withcolor red withpen pencircle scaled 2bp;
  drawarrow (+s, +as) -- (+s, +ae) withcolor red;
  
  drawarrow (+as, -s) -- (+ae, -s) withcolor green;
  drawarrow (+as,  0) -- (+ae,  0) withcolor green withpen pencircle scaled 2bp;
  drawarrow (+as, +s) -- (+ae, +s) withcolor green;
  
  dimm( 0,  0, btex $\ $ etex, btex $c_{i,j}$ etex, btex etex);
  
  %just padding, hihi
  draw (-2 * s, -2 * s) -- (+2 * s, -2 * s) -- (+2 * s, +2 * s) -- (-2 * s, +2 * s) -- cycle withcolor 1;

drawGrid(42);

% fill (-1.5 * s, -1.5 * s) -- (-1.5 * s, -0.5 * s) 
%   -- (-0.5 * s, -0.5 * s) -- (-0.5 * s, +0.5 * s)
%   -- (+0.5 * s, +0.5 * s) -- (+0.5 * s, +1.5 * s) 
%   -- (+1.5 * s, +1.5 * s) 
%   -- (+1.5 * s, -1.5 * s) -- cycle withcolor 0.8;
%   




endfig;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(6);	%simple church
  
  path ToverL, RoofTL, Main, RoofM, ToverR, RoofTR;
  ToverL := (30, 0) -- (50, 0) -- (50, 100) -- (30, 100) -- cycle;
  RoofTL := (30, 100) -- (50, 100) -- (40, 120) -- cycle;
  
  Main :=  (50, 0) -- (100, 0) -- (100, 50) -- (50, 50) -- cycle;
  RoofM := (50, 50) -- (100, 50) -- (75, 70) -- cycle;
  
  ToverR := ToverL shifted (70, 0);
  RoofTR := RoofTL shifted (70, 0);
  
  draw ToverL;	draw RoofTL;
  draw Main;	draw RoofM;
  draw ToverR;	draw RoofTR;
  
  label.bot(btex $t_l$ etex, center ToverL);
  label.bot(btex $r_l$ etex, center RoofTL);
  
  label.bot(btex $b_m$ etex, center Main);
  label.bot(btex $r_m$ etex, center RoofM);
  
  
  label.bot(btex $t_r$ etex, center ToverR);
  label.bot(btex $r_l$ etex, center RoofTR);

endfig;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(7);	%tree of church
  
  pair NR, NlT, NmT, NrT, NlB, NmB, NrB, NlR, NmR, NrR;
  
  NR := (100, 60);	
  NlT := (40, 40);	NmT := (100, 40);	NrT := (160, 40);	
  NlB := (20, 20);	NmB := ( 80, 20);	NrB := (140, 20);	
  NlR := (60, 20);	NmR := (120, 20);	NrR := (180, 20);	
  
  
  dotlabel.ulft(btex $C$ etex, NR);
  dotlabel.ulft(btex $T_l$ etex, NlT);
  dotlabel.urt(btex $M$ etex, NmT);
  dotlabel.urt(btex $T_r$ etex, NrT);
  dotlabel.bot(btex $t_l$ etex, NlB);
  dotlabel.bot(btex $t_m$ etex, NmB);
  dotlabel.bot(btex $t_r$ etex, NrB);
  dotlabel.bot(btex $r_l$ etex, NlR);
  dotlabel.bot(btex $r_m$ etex, NmR);
  dotlabel.bot(btex $r_r$ etex, NrR);
  
  draw NR -- NlT;	draw NR -- NmT;		draw NR -- NrT;
  draw NlT -- NlB;	draw NlT -- NlR;
  draw NmT -- NmB;	draw NmT -- NmR;
  draw NrT -- NrB;	draw NrT -- NrR;
endfig;


end