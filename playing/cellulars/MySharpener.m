function [y] = MyEdgeDetectionCell(c, neighs)
			if c > 0.5   % živá buňka
        if neighs < 4 
          y = 0;    
        else
          y = 1;    
        end
      else      % mrtvá buňka
        if neighs > 4
          y = 1;
        else
          y = 0;
        end
      end
