function prob_practica2()

    x=sym('x');
    f = exp(x^2-1);
    t=[1;1.1;1.2;1.3;1.4];
    
    for i=1:5
        x=t(i);
        y(i)=eval(f);
    end
    x=1.25;
    rezultat_adevarat=eval(f)
    rezultat_calculat = prob1(t,y,1.25)
    error = abs(rezultat_adevarat-rezultat_calculat)

end