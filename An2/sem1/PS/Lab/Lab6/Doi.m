function rez=Doi(N)
  g=@(x)exp(-x.^2);
  
  con=0;
  
  for i=1:N
    x=unifrnd(-2,2);
    y=unifrnd(0,1);
    if g(x)>y
      con++;
    endif 
  endfor 
 
  rez=(con/N) * 4;
  integral(g,-2,2)
  
endfunction
