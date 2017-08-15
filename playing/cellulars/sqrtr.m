function [y] = sqrtr(x, n)
% [y] = sqrtr(x)

y = arrayfun(@sqrtrNum, x, n);
end

function [y] = sqrtrNum(x, n)
	if (x > 0)
		y = NaN; %nthroot(x, n);
	elseif (x < 0)
		if (n > 0)
			y = - nthroot(- x, n);
		else
			y = 1;
		end
	else
		y = 0;
	end
end
