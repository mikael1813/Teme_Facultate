function intervaldeincrederepentruabaterestandard(x,alfa)
  n=length(x);
  c1=chi2inv(1-alfa/2,n-1);
  c2=chi2inv(alfa/2,n-1);
  
  sigma = std(x);
  
  m1=sqrt((n-1)/c1)*sigma;
  m2=sqrt((n-1)/c2)*sigma;
  
  y=[m1,m2]
endfunction
