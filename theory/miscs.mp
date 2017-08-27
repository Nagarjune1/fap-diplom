outputtemplate:="%j%c.mps";

beginfig(0);	% 8 dimms around the cell
  
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

beginfig(1); %edge through cell

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

end
  
  