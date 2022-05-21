% Luam functia f=x(x+3)(x+6)(x-9)(x-12) si nodurile xk=[0,-3,-6,9,12]
% Vom evalua spline cu cele 4 tipuri de spline cubice
f = @(x) x.*(x+3).*(x+6).*(x-9).*(x-12);
d = [-0, 0];
t = linspace(-6, 12, 200)';
x = [0,-3,-6,9,12];
y = f(x);

% spline comprplete
c1 = prob1(x, y, 0, d);
s1 = prob2(x, c1, t);

% spline cu derivate secundare
d2 = [-1188, 1188];
c2 = prob1(x, y, 1, d2);
s2 = prob2(x, c2, t);

% spline naturale
c3 = prob1(x, y, 2);
s3 = prob2(x, c3, t);

% spline deBoor
c4 = prob1(x, y, 3);
s4 = prob2(x, c4, t);

yg = f(t);
plot(x, y, 'o', t, [yg, s1, s2, s3, s4])
legend('noduri', 'f', 'complet', 'd2', 'natural', 'deBoor', 'Location', 'best')