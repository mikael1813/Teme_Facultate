function prob_practica3()

    x=sym('x');
    f=sqrt(x);

    m=5;
    %for i = 1:m
    %    a=rand()*100;
    %    t(i)=a;
    %end
    tt=[114:0.1:116]
    t = tt(tt~=115)

    rezultat = prob4(115,f,m,t)
end