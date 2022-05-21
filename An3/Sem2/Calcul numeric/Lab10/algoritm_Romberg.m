function output = algoritm_Romberg(f,a,b)

    R=[];
    R(1,1) = (b-a)/2*(f(a)+f(b));
    R(2,1) = 1/2*(R(1,1) + (b-a)*f(a+(b-a)/2));
    R(2,2) = (4*R(2,1)-R(1,1))/3;

    e=0.1;
    i=2;
    
    while abs(R(i,i)-R(i-1,i-1)) > e
        i=i+1;
        sum = 0;
        for k = 1:2^(i-2)
            sum = sum + f(a+(k-1/2)*(b-a)/(2^(i-2)));
        end
        sum = sum*(b-a)/(2^(i-2));

        R(i,1) = 1/2*(R(i-1,1) + sum);

        for j = 2:i
            R(i,j) = ((4^(j-1))*R(i,j-1) - R(i-1,j-1))/(4^(j-1) -1);
        end

    end

    output = R(i,i);


end