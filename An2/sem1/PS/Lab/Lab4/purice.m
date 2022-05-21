function pozitii_finale = purice(P,K,n)
  
  pozitii_finale = zeros(1,n);
  
  for j=1:n
    
    pozitii = zeros(1,K+1);  
  
    for i=2:K+1
      
      step = (-1)^(binornd(1,P)+1);
      
      pozitii(i) = pozitii(i-1) + step;
      
    endfor
    
    pozitii_finale(j) = pozitii(K+1);
    
  endfor
  
  x=pozitii_finale;
  N=histc(x,-K:K);
  bar(-K:K,N/n,'hist','FaceColor','b');
  
endfunction
