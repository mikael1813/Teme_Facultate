function prob3_a
    for n = 10:15
        vector = [];
        for k = 1:n+1
            vector(k) = -1 + (k-1)*(2/n);
        end
        vector;
        vander_matrix = vander(vector);
        %cond_matrix_vander = cond(matrix_vander)
    
    
        Cebisev_norm = norm(vander_matrix,Inf)
    end
end