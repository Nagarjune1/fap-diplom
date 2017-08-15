function [B] = betterCA(steps, cell)
% [] = betterCA(steps, cell)
% cell = @(c, round) (0 or 1)
% https://cs.wikipedia.org/wiki/Hra_života#Algoritmus

SIZE=100; % počet řádků a sloupců
t=steps; % čas
U=rand(SIZE); % matice s náhodnými elementy
%U=1-(U .* U);
A=U; % počáteční matice se kterou se hraje
B=zeros(SIZE); % pomocná matice, kam zapisuji další iterační stavy
for i=1:t
%	disp(['Generation ', num2str(i)]);
	for x=2:SIZE-1
    for y=2:SIZE-1
      N=[A(x-1,y-1), A(x-1,y), A(x-1,y+1); A(x,y-1), A(x,y), A(x,y+1); A(x+1,y-1), A(x+1,y), A(x+1,y+1)];
			okoli=A(x-1,y-1)+A(x-1,y)+A(x-1,y+1)+A(x,y-1)+A(x,y+1)+A(x+1,y-1)+A(x+1,y)+A(x+1,y+1);
			n=A(x-1,y-1)+A(x+0,y-1)+A(x+1,y-1);
			w=A(x-1,y-1)+A(x-1,y+0)+A(x-1,y+1);
			e=A(x+1,y-1)+A(x+1,y+0)+A(x+1,y+1);
			s=A(x-1,y+1)+A(x+0,y+1)+A(x-1,y+1);
			nw=A(x-1,y+0)+A(x-1,y-1)+A(x+0,y-1);
			ne=A(x+1,y+0)+A(x+1,y-1)+A(x+0,y-1);
			sw=A(x-1,y-0)+A(x-1,y+1)+A(x+0,y+1);
			se=A(x+1,y-0)+A(x+1,y+1)+A(x+0,y+1);
      c = A(x, y);
			nc = cell(c, okoli, N, n,w,e,s, nw,ne,sw,se);
			B(x, y) = nc;
    end
  end
  imshow(B);
	pause(.01)
  A=B; % před dalším krokem je nutno matici A aktualizovat
end
