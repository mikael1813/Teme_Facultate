function prob4(n)
    a = 2;
    bb = 10;
    for i = 1:n
        m = round(a+(bb-a)*rand(1,1));
        A = gallery('lehmer',m);
        b = zeros(m,1);
        for i = 1:m
            b(i,1)=1;
        end
        tic
        x_calculat = ecuatie_cholesky(A,b)
        toc
        tic
        x_adevarat = A\b
        toc
    end

end