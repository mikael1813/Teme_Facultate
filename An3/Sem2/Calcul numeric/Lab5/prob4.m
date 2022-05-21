function [jacobi,gauss_seidel,sor] = prob4(n)

    %b=zeros(n,1);
    %A=zeros(n);
    %for i=1:n
    %    b(i,1)=i;
    %    A(i,i)=rand(1)*100;
    %end
    A=rand(n);
    A=A+A';
    A=A+max(sum(A,2))*eye(n);

    b=A*[1:n]';

    jacobi = prob1(A,b)

    gauss_seidel = Gauss_Seidel(A,b)

    sor = prob2(A,b)

end