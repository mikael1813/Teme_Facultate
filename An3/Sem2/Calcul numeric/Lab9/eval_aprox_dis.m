function output = eval_aprox_dis(x,c)
%c - coeficienti
%x - numar oarecare

len = size(c,1);
output = 0;
for i = 1:len
    output = output + c(i)*(x^(i-1));
end


end