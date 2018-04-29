input automata;
outputtemplate:="%j%c.mps";


XXXXXXXXXXXXXXXXXXXXXX

beginfig(0);	% automata representing faces emotions of chess player
  
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


beginfig(1);	% handwritten text recognition sample
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

end