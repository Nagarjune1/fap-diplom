outputtemplate:="%j%c.mps";

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(0);	%korenovy strom R(M(x S(y)), x)
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
beginfig(1);	%korenovy strom S(x)
  pair As, Ap;
  As := (15, 60); Ap := (15, 40);
  
  dotlabel.ulft(btex $S$ etex, As);
  dotlabel.ulft(btex $x$ etex, Ap);
  
  draw As--Ap;
endfig;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(2);	%korenovy strom M(x, x)
  pair Cm, Cx, Cxx;
  Cm := (135, 60); Cx := (120, 40); Cxx := (150, 40); 
  
  dotlabel.ulft(btex $M$ etex, Cm);
  dotlabel.ulft(btex $x$ etex, Cx);
  dotlabel.urt(btex $x$ etex, Cxx);
  
  draw Cm--Cx;	draw Cm--Cxx;
endfig;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(2);	%korenovy strom M(x, x)
  pair Cm, Cx, Cxx;
  Cm := (135, 60); Cx := (120, 40); Cxx := (150, 40); 
  
  dotlabel.ulft(btex $M$ etex, Cm);
  dotlabel.ulft(btex $x$ etex, Cx);
  dotlabel.urt(btex $x$ etex, Cxx);
  
  draw Cm--Cx;	draw Cm--Cxx;
endfig;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
beginfig(11);	%kostel s dvemi vezmi
  
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

beginfig(12);	%strom kostelu
  
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

% beginfig(11);	%strom tvoren pyramidou trojuhelniu (sierpinsky)
%   pair T[][];
%   
%   T[1][1] = (40, 20);	T[1][2] = (80, 20);	T[1][3] = (60, 0);
%   
%   T[2][1] = (20, 40);	T[2][2] = (60, 40);	T[2][3] = (40, 20);
%   T[3][1] = (60, 40);	T[3][2] = (100, 40);	T[3][3] = (80, 20);
%   
%   T[4][1] = (0, 60);	T[4][2] = (40, 60);	T[4][3] = (20, 40);
%   T[5][1] = (40, 60);	T[5][2] = (80, 60);	T[5][3] = (60, 40);
%   T[6][1] = (80, 60);	T[6][2] = (120, 60);	T[6][3] = (100, 40);
%   
% %   dotlabel.ulft(btex $M$ etex, Cm);
% %   dotlabel.ulft(btex $x$ etex, Cx);
% %   dotlabel.urt(btex $x$ etex, Cxx);
%   
%   draw T[1][1]--T[1][2]--T[1][3]--cycle;
%   draw T[2][1]--T[2][2]--T[2][3]--cycle;
%   draw T[3][1]--T[3][2]--T[3][3]--cycle;
%   draw T[4][1]--T[4][2]--T[4][3]--cycle;
%   draw T[5][1]--T[5][2]--T[5][3]--cycle;
%   draw T[6][1]--T[6][2]--T[6][3]--cycle;
%   
% endfig;

end.


