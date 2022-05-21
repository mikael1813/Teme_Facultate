function prob3_a
    for n = 10:15
        vector = [];
        for k = 1:n
            vector(k) = 1/k;
        end
        vector;
        vander_matrix = vander(vector);
        %cond_matrix_vander = cond(matrix_vander)
    
    
        Cebisev_norm = norm(vander_matrix,Inf)
    end
end