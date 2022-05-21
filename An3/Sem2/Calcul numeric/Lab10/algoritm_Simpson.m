function integral = algoritm_Simpson(g,a,b,n)

    syms f(x);
    f(x) = g;

    m = floor(n/2);
    h = (b-a)/n;
    sum1 = 0;
    sum2 = 0;
    for i = 1:m-1
        sum1 = sum1 + f(a + 2*i*h);
    end
    sum1 = sum1*2;
    for i = 1:m
        sum2 = sum2 + f(a + (2*i-1)*h);
    end
    sum2 = sum2*4;

    e = a + (b-a)*rand();
    df = diff(f,x);
    ddf = diff(df,x);

    R2f = -((b-a)^5)/(2880*((n/2)^4));
    integral = (b-a)/(3*n)*(f(a)+f(b)+sum1 + sum2) + R2f;


end