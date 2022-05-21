function fx = prob4(k,f,m,t)
    y=zeros(m,1);

    for i = 1:m
        x=t(i);
        y(i)=eval(f);
    end
    fx=prob1(t,y,k);
end