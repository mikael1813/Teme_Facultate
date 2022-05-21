function x = prob2(A,b)

    p = spectralradius(A,b);
    omega = 2/(1+sqrt(1-p^2));
    x = sor(A,b,omega);

end