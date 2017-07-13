outputtemplate:="%j%c.mps";



beginfig(0);	%ctverice korenovych stromu pro termy 
  %% S(x)
  pair As, Ap;
  As := (15, 60); Ap := (15, 40);
  
  dotlabel.ulft(btex $S$ etex, As);
  dotlabel.ulft(btex $x$ etex, Ap);
  
  draw As--Ap;

  %% R(M(x S(y)), x)
  pair Br, Bm, Bp, Bs, Bx, By;
  Br := (75, 60); Bm := (60, 40); Bp := (45, 20); Bs := (75, 20); Bx := (75, 00); By := (90, 40); 
  
  dotlabel.ulft(btex $R$ etex, Br);
  dotlabel.ulft(btex $M$ etex, Bm);
  dotlabel.urt(btex $S$ etex, Bs);
  dotlabel.urt(btex $x$ etex, Bx);
  dotlabel.ulft(btex $y$ etex, Bp);
  dotlabel.urt(btex $y$ etex, By);
  
  draw Br--Bm;	draw Br--By;
  draw Bm--Bs;	draw Bm--Bp;
  draw Bs--Bx;	
  
  %% M(x, x)
  pair Cm, Cx, Cxx;
  Cm := (135, 60); Cx := (120, 40); Cxx := (150, 40); 
  
  dotlabel.ulft(btex $M$ etex, Cm);
  dotlabel.ulft(btex $x$ etex, Cx);
  dotlabel.urt(btex $x$ etex, Cxx);
  
  draw Cm--Cx;	draw Cm--Cxx;
  
%   
%   %% y
%   pair Dy;
%   Dy := (175, 50); 
%   
%   dotlabel.ulft(btex $y$ etex, Dy);
%   
endfig;

end.


