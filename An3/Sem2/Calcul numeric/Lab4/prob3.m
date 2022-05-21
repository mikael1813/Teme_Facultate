function prob3(n)
    A=rand(n)

    b = zeros(n,1);
    for i = 1:n
        b(i,1)=1;
    end
    x_gauss = prob1(A,b)

    x_lup = prob2(A,b)


end