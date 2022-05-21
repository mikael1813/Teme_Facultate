function intervaldeincredereptproportie(x,alfa=0.05)
  Xn=mean(x);
  n=length(x);
  z=norminv(1-alfa/2);
  m1=max([n-sqrt(Xn*(1-Xn)/n)*z,0]);
  m2=min([n+sqrt(Xn*(1-Xn)/n)*z,1]);
  
  y=[m1,m2]
endfunction
