function [x,ni]=sor(A,b,omega,x0,err,nitmax)
%metoda sor
%apel [z,ni]=relax(A,b,omega,err,nitmax)
%parametri
%A - matricea sistemului
%b - vectorul termenilor liberi
%omega - parametrul relaxarii
%x0 - vector de pornire
%err - toleranta (implicit 1e-3)
%nitmax - numarul maxim de iteratii (implicit 50)
%z - solutia
%ni -numarul de iteratii realizat efectiv

%verificare parametri
if nargin < 6, nitmax=500; end
if nargin < 5, err=1e-10; end
if nargin < 4, x0=zeros(size(b)); end
if (omega<=0) | (omega>=2)
    error('parametrul relaxarii ilegal')
end
[m,n]=size(A);
if (m~=n) | (n~=length(b))
    error('dimensiuni ilegale')
end
%calculul lui T si c (pregatirea iteratiilor)
M=1/omega*diag(diag(A))+tril(A,-1);
N=M-A;
T=M\N;
c=M\b;
alfa=norm(T,inf);
x=x0(:);
for i=1:nitmax
    x0=x;
    x=T*x0+c;
    if norm(x-x0,inf)/norm(x,inf)<err
        ni=i;
        return
    end
end
error('prea multe iteratii')