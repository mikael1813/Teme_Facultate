syms t u
err=1e-9;
ve=vpa(int(1/sqrt(sin(t)),t,sym(0),sym(pi)/sym(2)))
ved=double(ve);
II = int(1/sqrt(sin(t)),t,0,sym(pi)/sym(2),'Hold',true)
changeIntegrationVariable(II,t,asin((u+1)/2))
f=@(x) sqrt(2)./sqrt(x+3);
n0=10; b=-1/2; a=-1/2;
[gn,gc]=gauss_jacobi(n0,a,b);
vi(1)=vquad(gn,gc,f); k=1;

for n=n0+1:10*n0
    k=k+1;
    [gn,gc]=gauss_jacobi(n,a,b);
    vi(k)=vquad(gn,gc,f);
    if (abs(vi(k)-ved)<err) || n>1000
        fprintf('vi(%d)=%11.9f\n',n,vi(k));
        break
    end
end
n,vi(k)