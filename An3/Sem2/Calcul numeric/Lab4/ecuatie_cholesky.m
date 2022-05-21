function x = ecuatie_cholesky(A,b)

    R = cholesky(A);
    RT = R.';

    x = R\(RT\b);

end