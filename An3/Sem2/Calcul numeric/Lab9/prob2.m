function prob2()

    x=[-1.024940; -0.949898; -0.866114; -0.773392; -0.671372;-0.559524; -0.437067; -0.302909; -0.159493; -0.007464];
    y=[-0.389269; -0.322894; -0.265256; -0.216557; -0.177152;-0.147582; -0.128618; -0.121353; -0.127348; -0.148895];
    
    B = zeros(10,1);
    for i = 1:10
        B(i) = x(i)^2;
    end
    
    a=sym('a');
    b=sym('b');
    c=sym('c');
    d=sym('d');
    e=sym('e');
    coef = [a;b;c;d;e];

    A = [];
    for i = 1:10
        %row = [a*(y(i)^2), b*x(i)*y(i), c*x(i), d*y(i), e];
        row = [(y(i)^2), x(i)*y(i), x(i), y(i), 1];
        A = [A;row];
    end
    t1 = (A.')*A;
    t2 = (A.')*B;
    xx = t1\t2;
    error = 0;
    for i = 1:10
        left = xx(1)*(y(i)^2) + xx(2)*x(i)*y(i) + xx(3)*x(i) + xx(4)*y(i) + xx(5);
        right = x(i)^2;
        error = error + abs(right - left);
    end
    error_a = error/10

    A = [];
    for i = 1:10
        row = [y(i),1];
        A=[A;row];
    end
    t1 = (A.')*A;
    t2 = (A.')*B;
    xx = t1\t2;
    error = 0;
    for i = 1:10
        left = xx(1)*y(i) + xx(2);
        right = x(i)^2;
        error = error + abs(right - left);
    end
    error_b = error/10
end