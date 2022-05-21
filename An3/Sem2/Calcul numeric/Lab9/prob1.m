function prob1(f,n)

x = randi([-10 10],1,n+1)
y = [];
for i = 1:n+1
    y(i) = f(x(i));
end
y
c = lsq_aprox_discr(x,y,n)

rezultat = eval_aprox_dis(20,c)

end