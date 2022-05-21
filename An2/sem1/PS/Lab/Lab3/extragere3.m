function p=extragere3(N)
  urna = 'RRRRRAAAVV';
  contor=0;
  contor2=0;
  for i=1:N
    extrase = randsample(urna,3);
    if(extrase(1)=='R' || extrase(2)=='R' || extrase(3)=='R')
      contor++;
      if(extrase(1)=='R' && extrase(2)=='R' && extrase(3)=='R')
        contor2++;
      endif  
    endif  
  endfor
  p = contor2/contor;
endfunction
