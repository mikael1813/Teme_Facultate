

integ_sin = eval(int(exp(-x)*sin(x), 0, inf))
integ_cos = eval(int(exp(-x)*cos(x), 0, inf))

err = 10^(-8);

contor = 0;
int_calculat = 100;

while abs(int_calculat-integ_sin) > err
    contor = contor+1;
    [nodes,coef] = gauss_laguerre(contor,0);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*exp(-nodes(i))*sin(nodes(i));
    end
    int_calculat

end

times_for_sin = contor

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ_cos) > err
    contor = contor+1;
    [nodes,coef] = gauss_laguerre(contor,1);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*exp(-nodes(i))*cos(nodes(i));
    end

end

times_for_cos = contor