D10=diff(exp(-t^2),t,20)
fplot(D10,[-1,1])
Md=subs(D10,t,sym(0))
R=1/factorial(20)*int(sqrt(1-t^2)*chebyshevU(10,t)^2,t,-1,1)*Md
vpa(R)
f=@(x) exp(-x.^2);
[gn,gc]=gauss_cebisev1(10);
vi=vquad(gn,gc,f)