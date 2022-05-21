
syms x
integ = eval(int(1/(sqrt(sin(x))), 0, pi/2))
err = 10^(-9);

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ) > err
    contor = contor+1;
    [nodes,coef] = gauss_jacobi(contor,0,-1/2);

    int_calculat = 0;
    for i = 1:contor
        y = (pi/4*nodes(i) + pi/4)*pi/4;
        int_calculat = int_calculat + coef(i)*1/(sqrt(sin(y)));
    end
    int_calculat

end

int_calculat

