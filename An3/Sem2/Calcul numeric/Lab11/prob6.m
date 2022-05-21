
integ_sin = eval(int(exp(-(x^2))*sin(x), -inf, inf))
integ_cos = eval(int(exp(-(x^2))*cos(x), -inf, inf))
err = 1/2;

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ_sin) > err
    contor = contor+1;
    [nodes,coef] = gauss_hermite(contor);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*exp(-(nodes(i)^2))*sin(nodes(i));
    end

end

%times_for_sin = contor
int_sin_calculat = int_calculat

contor = 0;
int_calculat = 100;


while abs(int_calculat-integ_cos) > err
    contor = contor+1;
    [nodes,coef] = gauss_hermite(contor);

    int_calculat = 0;
    for i = 1:contor
        int_calculat = int_calculat + coef(i)*exp(-(nodes(i)^2))*cos(nodes(i));
    end

end

%times_for_cos = contor
int_cos_calculat = int_calculat