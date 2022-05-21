function prob5(n)

    b=zeros(n+1,1);
    for i = 1:n+1
        b(i,1) = 2-(i-1);
    end
    A=zeros(n+1,n+1);
    for i = 1:n+1
        for j = 1:i-1
            A(i,j)=-1;
        end
        A(i,i)=1;
        for j = i+1:n
            A(i,j) = 0;
        end
        A(i,n+1) = 1;
    end
    x_lup = prob2(A,b)
    
    x_qr = ecuatie_qr(A,b)

    b_lup = A*x_lup

    b_qr = A*x_qr
end