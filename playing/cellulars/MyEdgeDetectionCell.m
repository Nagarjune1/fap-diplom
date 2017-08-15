function [y] = MyEdgeDetectionCell(c, neighs, N, n,w,e,s, nw,ne,sw,se)
	he = fuzzyEq3(N(2, 1), N(2, 2), N(2, 3));
	ve = fuzzyEq3(N(1, 2), N(2, 2), N(3, 2));
	de = fuzzyEq3(N(1, 1), N(2, 2), N(3, 3));
	ie = fuzzyEq3(N(3, 1), N(2, 2), N(1, 3));
	
	cmp = max([ he, ve, de, ie ]);
	y = cmp; %1 - exp(cmp);
	%[c cmp y]	




%			if c > 0.5   % živá buňka
%       if neighs < 4 
%          y = 0;    
%        else
%          y = 1;    
%        end
%      else      % mrtvá buňka
%        if neighs > 4
%          y = 1;
%        else
%          y = 0;
%        end
%      end
end

function [e] = fuzzyEq(x, y)
	e = 1 - abs(x - y);
end

function [e] = fuzzyEq3(x, y, z)
	e =  fuzzyEq(x, y) * fuzzyEq(y, z) * fuzzyEq(z, x) ;
end
