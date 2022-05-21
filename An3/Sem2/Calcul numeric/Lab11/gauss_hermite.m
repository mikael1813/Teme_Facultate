function [g_nodes,g_coeff]=gauss_hermite(n)
%GAUSS_HERMITE - noduri si coeficienti Gauss-Hermite

beta=[sqrt(pi),[1:n-1]/2];
alpha=zeros(n,1);
[g_nodes,g_coeff]=gauss_quad(alpha,beta);