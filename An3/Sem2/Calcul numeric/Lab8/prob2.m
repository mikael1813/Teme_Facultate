% eval_spline - calculeaza valorile unei cubice spline
% apel z = eval_spline(x,c,t)
% z - valorile
% t - punctele in care evaluam
% x - nodurile
% c - coeficientii length(x) - 1
function z = prob2(x, c, t)
    n = length(x);
    x = x(:);
    t = t(:);
    k = ones(size(t));
    for j = 2:n-1
        k(x(j) <= t) = j;
    end
    % interpolant evaluation
    s = t - x(k);
    %z = d(k) + s.*(c(k) + s.*(b(k) + s.*a(k)));
    z = c(k,4) + s.*(c(k,3) + s.*(c(k,2) + s.*c(k,1)));
end
