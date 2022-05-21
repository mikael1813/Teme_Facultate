function x=prob1(A,B)
    A = [A B];
    lines = size(A,1);
    cols = size(A,2);
    for i = 1:(lines-1)
        max = realmin;
        pos = 0;
        for j = i:lines
            if A(lines*(i-1)+j) > max
                max = A(lines*(i-1)+j);
                pos = j;
            end
        end
        if pos ~= i
            A([i j],:)=A([j i],:);
        end
        for j = (i+1):lines
            fraction = A(j+(i-1)*lines)/A(i+(i-1)*lines);
            for k = i:cols
                A(j+(k-1)*lines) = A(j+(k-1)*lines) - (A(i+(k-1)*lines)*fraction);
            end
        end
    end
    A
    x = 1:lines;
    for i = 0:(lines-1)
        index = lines - i;
        sum = A(lines*(cols-1)+index);
        for j = 2:(i+1)
            sum = sum - A((cols-j)*lines+index)*x(cols-j+1);
        end
        A(lines*(index-1)+index);
        x(index) = sum/A(lines*(index-1)+index);
    end
    x=x.';
end