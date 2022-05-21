function prob_practica3()

    t = [0 3  5 8 13];
    y = [0 255 383 623 993];
    yd = [75 77 80 74 72];

    Hf = hermit_interpol(t,y,yd);

    Hfd = diff(Hf);

    x=10;
    pozitia = eval(Hf)

    viteza = eval(Hfd)
    

end