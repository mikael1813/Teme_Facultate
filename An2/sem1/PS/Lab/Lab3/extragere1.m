function p=extragere1(N)
  urna = 'RRRRRAAAVV';
  contor=0;
  for i=1:N
    extrase = randsample(urna,3);
    if(extrase(1)=='R' || extrase(2)=='R' || extrase(3)=='R')
      contor++;
    endif  
  endfor
  p = contor/N
  preal = 1 - nchoosek(5,3)/nchoosek(10,5)
endfunction
