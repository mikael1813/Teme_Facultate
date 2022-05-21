function rho=spectralradius(A,b)
    % diagonal part of A and rest
    D = diag(diag(A));
    R = A - D;
    
    % iteration matrix and offset
    T = - inv(D) * R;
    C = inv(D) * b;
    
    % spectral radius condition
    rho = max(abs(eig(T)));
    if rho >= 1
        error('no convergence')
    end
    
    % initial guess
    %x = randn(size(b));
    
    % iteration
    %while norm(A * x - b) > 1e-15
    %    x = T * x + C;
    %end
    

end