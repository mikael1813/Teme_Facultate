function [s0,s1,s2,s3]=complete_spline(x,y)

if any(size(x) ~= size(y)) || size(x,2) ~= 1
   error('inputs x and y must be column vectors of equal length');
end

n = length(x)

h = x(2:n) - x(1:n-1);
d = (y(2:n) - y(1:n-1))./h;

lower = h(1:end-1);
main  = 2*(h(1:end-1) + h(2:end));
upper = h(2:end);

T = spdiags([lower main upper], [-1 0 1], n-2, n-2);
rhs = 6*(d(2:end)-d(1:end-1));

m = T\rhs;

% Use natural boundary conditions where second derivative
% is zero at the endpoints

m = [ 0; m; 0];

s0 = y;
s1 = d - h.*(2*m(1:end-1) + m(2:end))/6;
s2 = m/2;
s3 =(m(2:end)-m(1:end-1))./(6*h);