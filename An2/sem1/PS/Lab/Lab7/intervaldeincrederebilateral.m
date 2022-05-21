function y = intervaldeincrederebilateral(x,sigma,alfa=0.05)
  Xn=mean(x);
  n=length(x);
  z=norminv(1-alfa/2);
  m1=Xn - (sigma/sqrt(n))*z;
  m2=Xn + (sigma/sqrt(n))*z;
  
  y = [m1,m2];
endfunction
