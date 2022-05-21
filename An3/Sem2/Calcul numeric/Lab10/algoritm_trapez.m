function integral = algoritm_trapez(g,a,b,n)

    syms f(x);
    f(x) = g;

    h = (b-a)/n;
    sum = 0;
    for i = 1:n-1
        sum = sum + f(a + i*h);
    end
    sum = sum*2;

    e = a + (b-a)*rand();
    df = diff(f,x);
    ddf = diff(df,x);

    R1f = -((b-a)^3)/(12*n^2)*ddf(e);
    integral = (b-a)/(2*n)*(f(a)+f(b)+sum) + R1f;


end