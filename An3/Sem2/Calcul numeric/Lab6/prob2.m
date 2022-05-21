function prob2(t,k)
    %s = linspace(0,10,50);
    %x = 0:0.1:1;
    %for i = 1:size(t,2)
        f = polinom_fundamental(t,k-1);
        %plot(s,eval(f))
   fplot(f)
    %end



end