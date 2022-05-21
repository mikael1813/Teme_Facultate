function f=polinom_fundamental(t,k)

    x = sym('x');

    f=1;
    for i = 1:size(t,2)
        if(k~=i-1)
            f=f*(x-t(i))/(t(k+1)-t(i));

        end
    end


end