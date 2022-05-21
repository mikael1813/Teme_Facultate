function [g_nodes,g_coeff]=gauss_cebisev1(n)
%GAUSS_CEB1 - noduri si coeficienti Gauss-Cebisev #1

g_coeff=pi/n*ones(1,n);
g_nodes=cos(pi*([1:n]'-0.5)/n);