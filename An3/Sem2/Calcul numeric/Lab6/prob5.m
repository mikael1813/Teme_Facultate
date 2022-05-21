function f=prob5(t,y)

    x = sym('x');
    numarator=0;
    numitor=0;
    for i = 1:size(t,2)
        wj=1;
        for j = 1:size(t,2)
            if (i~=j)
                wj = wj * 1/(t(i)-t(j));
            end
        end
        numarator = numarator + (y(i)*wj)/(x-t(i));
        numitor = numitor + wj/(x-t(i));
    end
    f = numarator/numitor

end