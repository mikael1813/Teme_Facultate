function rezultat = prob1(t,y,yd,xi)

    Hf = hermit_interpol(t,y,yd);

    for i = 1:size(xi,2)
        x=xi(i);
        rezultat = eval(Hf);
    end

end