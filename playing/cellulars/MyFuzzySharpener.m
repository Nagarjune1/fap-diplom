function [y] = MyFuzzySharpener(c, neighs)
	avgNeigh = neighs/8;
	
	nc = c - 0.5;
	nan = avgNeigh - 0.5;

	COEF = 0.01;
	y = max(0, min(1, ((2 .* nc .+ 0.5) .+ (COEF .* nan))));


%	COEF = 0;
%	nyr = 0.5 .*  ((2*nc + 1) .*  (1 .+ (sqrtr((nc .- nan), COEF))));
%	nyl = -0.5 .* ((-2*nc + 1) .* (1 .+ (sqrtr(((-nc) .- (-nan)), COEF)))) + 1;

%	ny = min(nyr, nyl);
%	y = ny + 0.0;
	

	%ny = max(0, min(1, (nc .+ 0.5) .* (0.5 .+ sqrtr(nc .- nan))));
	%ny = -0.4.*exp(((-nc) .* (-nan)) .- max(nc, nan));
	
%		y = 1 - (abs(c - avgNeigh))^0.55;

%			if c > 0.5   % živá buňka
%        if neighs < 4 
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
