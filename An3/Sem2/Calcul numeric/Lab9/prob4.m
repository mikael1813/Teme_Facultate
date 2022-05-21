function prob4()

    aux = 1900;
    x = [1900];
    for i = 1:11
        aux = aux+10;
        x=[x;aux];
    end
    y = [75.995;91.972;105.710;123.200;131.670;150.700;179.320;203.210;226.510;249.630;281.420;308.790];

    coef = lsq_aprox_discr(x,y,3);

    pop_1975 = eval_aprox_dis(1975,coef)

    pop_2010 = eval_aprox_dis(2010,coef)


    y = log(y);

    coef = lsq_aprox_discr(x,y,1);

    pop_1975 = exp(1)^eval_aprox_dis(1975,coef)

    pop_2010 = exp(1)^eval_aprox_dis(2010,coef)

end