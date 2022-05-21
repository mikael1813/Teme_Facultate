function prob3(f,m)
    t=zeros(1,m);
    y=zeros(1,m);

    for i = 1:m
        x=rand()*10;
        t(i)=x;
        y(i)=eval(f);
    end
    x = -3:0.1:3;
    Lm = prob5(t,y)
    %plot(x,f)
    %fplot(f)
    fplot(f);
    hold on;
    fplot(Lm);

end