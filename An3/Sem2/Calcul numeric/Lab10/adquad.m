function Q = adquad(f,a,b,e)

    c = (a+b)/2;
    fa = f(a);
    fb = f(b);
    fc = f(c);
    Q = quadstep(f,a,b,e,fa,fb,fc);
end

function Q=quadstep(f,a,b,e,fa,fc,fb)

    h = b-a;
    c = (a+b)/2;
    fd = f((a+c)/2);
    fe = f((c+b)/2);
    Q1 = h/6*(fa+4*fc+fb);
    Q2 = h/12*(fa+4*fb+2*fc+4*fe+fb);
    if abs(Q1-Q2) < e
        Q = Q2+(Q2-Q1)/15;
    else
        Qa = quadstep(f,a,c,e,fa,fd,fc);
        Qb = quadstep(f,c,b,e,fc,fe,fb);
        Q = Qa + Qb;
    end


end