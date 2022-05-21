function c = lsq_aprox_discr(x,y,n)
%x - punctele
%y - valoarea functiei din x
%n - gradul functiei calculate

x_size = size(x,1);
assert(x_size == size(y,1));
assert(n>=1);

A = zeros(x_size,n+1);
b=y;

for i = 0:n
    for j = 1:x_size
        A(i*x_size+j) = x(j)^i;
    end
end

xx= sym('x',[1 n+1]);

xx = xx.';
At = A.';
t1 = At*A;
t2 = At*b;
xx = t1\t2;
c = xx;
%At*A*xx=At*b


end