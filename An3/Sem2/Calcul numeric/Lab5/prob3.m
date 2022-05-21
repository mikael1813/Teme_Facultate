function prob3(n)
    A=zeros(n);
    b=zeros(n,1);

    for i=1:n
        if (i>1 && i<n)
            b(i,1)=3;
        else
            b(i,1)=4;
        end
        
        A(i,i)=5;
        if (i>1)
            A(i,i-1)=-1;
        end
        if (i<n)
            A(i,i+1)=-1;
        end

        
    end
    C=A;
    d=zeros(n,1);
    for i=1:n
        if (i==1 || i==n)
            d(i,1)=3;
        else
            if (i==2 || i==3 || i==n-1 || i==n-2)
                d(i,1)=2;
            else
                d(i,1)=1;
            end
        end
        for j=1:n
            if (mod(i,2)==1 && mod(j,2)==0 && j~=i)
                C(i,j)=-1;
            end
            if (mod(i,2)==0 && mod(j,2)==1 && j~=i)
                C(i,j)=-1;
            end
        end
    end
    

    jacobi_matrix_1=prob1(A,b)

    sor_matrix_1=prob2(A,b)

    jacobi_matrix_2=prob1(C,d)

    sor_matrix_2=prob2(C,d)

end