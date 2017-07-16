outputtemplate:="%j%c.mps";


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

beginfig(1);	%korenovy strom S(x)
  pair As, Ap;
  As := (15, 60); Ap := (15, 40);
  
  dotlabel.ulft(btex $S$ etex, As);
  dotlabel.ulft(btex $x$ etex, Ap);
  
  draw As--Ap;
endfig;
  

beginfig(2);	%korenovy strom M(x, x)
  pair Cm, Cx, Cxx;
  Cm := (135, 60); Cx := (120, 40); Cxx := (150, 40); 
  
  dotlabel.ulft(btex $M$ etex, Cm);
  dotlabel.ulft(btex $x$ etex, Cx);
  dotlabel.urt(btex $x$ etex, Cxx);
  
  draw Cm--Cx;	draw Cm--Cxx;
endfig;

%   
%   %% y
%   pair Dy;
%   Dy := (175, 50); 
%   
%   dotlabel.ulft(btex $y$ etex, Dy);
%   
% endfig;

end.


