clear
syms t f(t)
R=diff(f(t),t,20)/factorial(20)*int((1-t^2)^(-sym(1)/sym(2))*chebyshevT(10,t)^2,t,-1,1);
f=@(x) x.*exp(-x.^2)
[gn,gc]=gauss_cebisev1(10);
vi=vquad(gn,gc,f)