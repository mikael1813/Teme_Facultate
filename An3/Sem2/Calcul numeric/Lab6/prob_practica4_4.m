%test convergenta interpolare Lagrange 
%pentru noduri echidistante
n=10;
k=1:n;
xn=-1:2/n:1;
yn=abs(xn);
xg=-1:0.04:1;
yg=abs(xg);
ta=[-1:0.002:-0.5,-0.5:0.032:0.5, 0.5:0.002:1];
ya=prob1(xn,yn,ta);
plot(xn,yn,'o',xg,yg,ta,ya,'-.');