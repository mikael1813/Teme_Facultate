function prob3()

    B=[89;67;53;20;35];
    A=[1,1,1;1,1,0;0,1,1;0,0,1;1,0,0];
    
    t1 = (A.')*A
    t2 = (A.')*B
    coef = t1\t2

    x1 = coef(1);
    x2 = coef(2);
    x3 = coef(3);
    

end