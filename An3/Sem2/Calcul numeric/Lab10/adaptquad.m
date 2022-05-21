function output = adaptquad(f,a,b,n,caz)
    if nargin < 5
        caz = 1;
    end
    tol = 0.1;
    if a==b
        output = 0;
    end
    if abs(met(f,a,b,n,caz) - met(f,a,b,n*2,caz)) < tol
        output = met(f,a,b,n*2,caz);
    else
        output = adaptquad(f,a,(a+b)/2,n,caz) + adaptquad(f,(a+b)/2,b,n,caz);
    end


end


function output = met(f,a,b,n,caz)
    switch caz
        case 1
            output = algoritm_Simpson(f,a,b,n);
        case 2
            output = algoritm_trapez(f,a,b,n);
        case 3
            output = algoritm_dreptunghi(f,a,b,n);

    end

end