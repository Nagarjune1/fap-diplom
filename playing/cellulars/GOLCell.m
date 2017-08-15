function [y] = GOLcell(c, neighs)
			if c==1   % živá buňka
        if neighs==2 | neighs==3
          y = 1;    % zůstane
        else
          y = 0;    % zahyne
        end
      else           % mrtvá buňka
        if neighs==3
          y = 1;    % ožije
        else
          y = 0;    % neožije
        end
      end
