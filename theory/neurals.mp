input automata;
outputtemplate:="%j%c.mps";



beginfig(0);

  size := 15;
  lblpos := 0.8;
  lblposl := 0.9;

   with spacing((60,40)) matrix.a(4,6);
   
   with shape(boxed) node.a[1][1](btex $x_1$ etex);
   with shape(boxed) node.a[2][1](btex $x_2$ etex);
   with shape(boxed) node.a[3][1](btex $x_3$ etex);
   
   node.a[1][2](btex $\theta_1$ etex);
   node.a[2][2](btex $\theta_2$ etex);
   node.a[3][2](btex $\theta_3$ etex);
   node.a[1][3](btex $\theta_4$ etex);
   node.a[2][3](btex $\theta_5$ etex);
   node.a[2][4](btex $\theta_6$ etex);
   
   with shape(boxed) node.a[1][5](btex $y_1$ etex);
   with shape(boxed) node.a[2][5](btex $y_2$ etex);
   with shape(boxed) node.a[3][5](btex $y_2$ etex);
   
   arrow.top(lblpos, btex $ w_{1,1} $ etex) (a[1][1],a[1][2]) a[1][1].c..a[1][2].c;
   arrow.top(lblpos, btex $ w_{1,2} $ etex) (a[2][1],a[2][2]) a[2][1].c..a[2][2].c;
   arrow.top(lblpos, btex $ w_{1,3} $ etex) (a[2][1],a[3][2]) a[2][1].c..a[3][2].c;
   arrow.bot(lblpos, btex $ w_{2,3} $ etex) (a[3][1],a[3][2]) a[3][1].c..a[3][2].c;
    
   arrow.top(lblpos, btex $ w_{1,4} $ etex) (a[1][2],a[1][3]) a[1][2].c..a[1][3].c;
   arrow.rt(lblpos, btex $ w_{1,5} $ etex) (a[1][2],a[2][3]) a[1][2].c..a[2][3].c;
   arrow.bot(lblpos, btex $ w_{2,5} $ etex) (a[2][2],a[2][3]) a[2][2].c..a[2][3].c;
   arrow.rt(lblpos, btex $ w_{2,4} $ etex) (a[2][2],a[1][3]) a[2][2].c..a[1][3].c;
   arrow.bot(lblposl, btex $ w_{3,6} $ etex) (a[3][2],a[2][4]) a[3][2].c..a[2][4].c;
   arrow.top(lblpos, btex $         $ etex) (a[3][2],a[3][5]) a[3][2].c..a[3][5].c;
   
   arrow.rt(lblpos, btex $ w_{1,6} $ etex) (a[1][3],a[2][4]) a[1][3].c..a[2][4].c;
   arrow.ulft(lblpos, btex $ w_{2,6} $ etex) (a[2][3],a[2][4]) a[2][3].c..a[2][4].c;
   
   arrow.top(lblpos, btex $         $ etex) (a[1][3],a[1][5]) a[1][3].c..a[1][5].c;
   arrow.top(lblpos, btex $         $ etex) (a[2][4],a[2][5]) a[2][4].c..a[2][5].c;
   
   
endfig;

end;