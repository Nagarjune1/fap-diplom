function [] = basicGOL(steps)
% [] = basicGOL(steps)
% https://cs.wikipedia.org/wiki/Hra_života#Algoritmus

n=100; % počet řádků a sloupců
t=steps; % čas
U=rand(n); % matice s náhodnými elementy
p=0.25; % počáteční poměr
A=(U<p); % počáteční matice se kterou se hraje
B=zeros(n); % pomocná matice, kam zapisuji další iterační stavy
for i=1:t
  for x=2:n-1
    for y=2:n-1
      okoli=A(x-1,y-1)+A(x-1,y)+A(x-1,y+1)+A(x,y-1)+A(x,y+1)+A(x+1,y-1)+A(x+1,y)+A(x+1,y+1);
      if A(x,y)==1   % živá buňka
        if okoli==2 | okoli==3
          B(x,y)=1;    % zůstane
        else
          B(x,y)=0;    % zahyne
        end
      else           % mrtvá buňka
        if okoli==3
          B(x,y)=1;    % ožije
        else
          B(x,y)=0;    % neožije
        end
      end
    end
  end
  imshow(B)
  pause(.01)
  A=B; % před dalším krokem je nutno matici A aktualizovat
end
