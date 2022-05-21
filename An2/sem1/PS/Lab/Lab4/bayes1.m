function bayes1(N)
  rez=[];
  
  for i=1:N
    B = zeros(1,6);
    B(1)=binornd(1,0.8);
    if B(1)
      B(2)=binornd(1,0.9);
    else
      B(2)=binornd(1,0.6);
    endif
    
    if (B(1)==1 && B(2)==1)
      B(3)=binornd(1,0.6);
    elseif (B(1)==0 && B(2)==1)
      B(3)=binornd(1,0.2);
    elseif (B(1)==1 && B(2)==0)
      B(3)=binornd(1,0.9);
    else
      B(3)=binornd(1,0.4);
    endif
    
    if B(3)
      B(4)=binornd(1,0.3);
      B(5)=binornd(1,0.5);
    else
      B(4)=binornd(1,0.5);
      B(5)=binornd(1,0.8);
    endif
    
    if (B(5)==1 && B(4)==1)
      B(6)=binornd(1,0.5);
    elseif (B(5)==1 && B(4)==0)
      B(6)=binornd(1,0.3);
    elseif (B(5)==0 && B(4)==1)
      B(6)=binornd(1,0.8);
    else
      B(6)=binornd(1,0.5);
    endif
    
    rez = [rez,bin2dec(num2str(flip(B)))];
  endfor
  prob=mean(rez==23)
  
endfunction
