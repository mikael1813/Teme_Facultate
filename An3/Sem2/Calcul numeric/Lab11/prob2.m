
syms x

integ_sin = eval(int(sin(x^2), -1, 1));
integ_cos = eval(int(cos(x^2), -1, 1));
err = 10^(-7);

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ_sin) > err
    contor = contor+1;
    [nodes,coef] = gauss_legendre(contor);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*sin(nodes(i)^2);
    end

end

times_for_sin = contor
int_calculat

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ_cos) > err
    contor = contor+1;
    [nodes,coef] = gauss_legendre(contor);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*cos(nodes(i)^2);
    end

end

times_for_cos = contor
int_calculat