function prob_practica4_1()


%test convergenta interpolare Lagrange pentru noduri Cebisev
n=4;
k=1:n;
xn=sort(cos((2*k-1)*pi/2/n));
yn=(1+xn.^2).^(-1);
xg=-1:0.04:1;
yg=(1+xg.^2).^(-1);
disp('*****')
pause
ta=-1:0.01:1;
ya=prob1(xn,yn,ta);
plot(xn,yn,'o',xg,yg,ta,ya,'-.');



end