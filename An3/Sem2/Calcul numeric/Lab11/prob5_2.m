f=@(u) exp(-u).*sin(u);
n=20;
[nodes,coeffs]=gauss_laguerre(n,-1/2);
E=vquad(nodes,coeffs,f)
vpa(int(exp(-t)*sin(t),t,0,sym(Inf)))
R_sin=vpa(1/factorial(32)*int(exp(-t)*laguerreL(n,t)^2,t,sym(0),sym(inf))*sym(2147483648))


f=@(u) exp(-u).*cos(u);
n=20;
[nodes,coeffs]=gauss_laguerre(n,0);
E=vquad(nodes,coeffs,f);
vpa(int(exp(-t)*cos(t),t,0,sym(Inf)));
R_cos=vpa(1/factorial(32)*int(exp(-t)*laguerreL(n,t)^2,t,sym(0),sym(inf))*sym(2147483648))