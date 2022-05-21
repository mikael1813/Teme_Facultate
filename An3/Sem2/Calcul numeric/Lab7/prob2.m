function prob2(f,n)
    f1 = diff(f);
    for i = 1:n
        a = rand()*100;
        if (mod(a,2)==0)
            a = -a;
        end
        t(i) = a;
        x = a;
        y(i) = eval(f);
        yd(i) = eval(f1);
    end
    Hf = hermit_interpol(t,y,yd);
    fplot(Hf);

end