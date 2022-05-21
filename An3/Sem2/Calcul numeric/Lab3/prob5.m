n=input('n=');

%recurenta aplicata direct
E=recdir(n)

%recurenta inversa
k=20;
E=recinv(n,k)
%e cu precizia eps
e=1/recinv(1,k)
%verificare
precision = (e-exp(1))/exp(1)