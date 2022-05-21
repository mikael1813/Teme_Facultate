function integral = algoritm_dreptunghi(g,a,b,n)

    syms f(x);
    f(x) = g;

    h = (b-a)/n;
    sum = 0;
    for i = 1:n
        sum = sum + f((a + i*h + a + (i-1)*h)/2);
    end

    e = a + (b-a)*rand();
    df = diff(f,x);
    ddf = diff(df,x);

    R1f = ((b-a)^3)/(24*n^2)*ddf(e);
    integral = (b-a)/n*sum + R1f;


end