function x=prob2(A,b)

    [L,U,P] = lup(A);

    y = L\(P*b);
    x = U\y;

end