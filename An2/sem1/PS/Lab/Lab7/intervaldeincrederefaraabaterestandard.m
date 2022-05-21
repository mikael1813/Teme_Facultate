function y=intervaldeincrederefaraabaterestandard(x,alfa=0.05)
  Xn=mean(x);
  n=length(x);
  
  Sn=std(x);
  t=tinv(1-alfa/2,n-1);
  
  m1=Xn-(alfa/sqrt(n)*t);
  m2=Xn+(alfa/sqrt(n)*t);
  
  y=[m1,m2];
endfunction
