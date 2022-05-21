function prob_practica2()
    t = [0.30 0.32 0.35];
    y = [0.29552 0.31457 0.34290];
    yd = [0.95534 0.94924 0.93937];

    rezultat_estimat1 = prob1(t,y,yd,0.34)
    rezultat_corect1 = sin(0.34)
    erroare1 = rezultat_corect1-rezultat_estimat1

    t(4) = 0.33;
    y(4) = sin(0.33);
    yd(4) = cos(0.33);

    rezultat_estimat2 = prob1(t,y,yd,0.34)
    rezultat_corect2 = sin(0.34)
    erroare2 = rezultat_corect2-rezultat_estimat2


end