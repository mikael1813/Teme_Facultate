function Hf = hermit_interpol(t,y,yd)
%t - noduri
%y - valoarea functiei in noduri
%yd - valoarea derivatei functiei in noduri
    size_vector = size(t,2);
    A = zeros(size_vector*2,size_vector*2+1);
    for i =1:size_vector
        A(i*2-1) = t(i);
        A(i*2) = t(i);

        A(i*2-1 + size_vector*2) = y(i);
        A(i*2 + size_vector*2) = y(i);
    end
    A(1+size_vector*4) = yd(1);
    A(2+size_vector*4) = (y(2)-y(1))/(t(2)-t(1));
    A(3+size_vector*4) = yd(2);
    
    for i = size_vector*2:size_vector*2+1
        for j = 1:size_vector*2+2-i
            a = A(j+1+size_vector*(i-2)*2);
            b = A(j+size_vector*(i-2)*2);
            %diff = A(j+1+size_vector*(i-2)*2) - A(j+size_vector*(i-1)*2)
            diff2 = a-b;
            diff3 = t(2)-t(1);
            %A(j+size_vector*(i-1)*2) = (A(j+1+size_vector*(i-2)*2) - A(j+size_vector*(i-1)*2))/(t(2)-t(1));
            A(j+size_vector*(i-1)*2) = diff2/diff3;
        end
    end
    A;
    x = sym('x');
    Hf = 0;
    comun = 1;
    putere=0;
    step = 1;
    for i = 1:size_vector*2
        comun;
        A(1+size_vector*2*i);
        Hf = Hf + A(1+size_vector*2*i)*comun;
        if(putere<2)
            putere = putere+1;
            comun = comun * (x - t(step));
        else
            putere = 1;
            step=step+1;
            comun = comun * (x - t(step));
        end
    end
    
end