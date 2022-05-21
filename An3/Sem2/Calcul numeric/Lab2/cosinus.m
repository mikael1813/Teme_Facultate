function cosinus(x)
  epsilon = eps;
  output = 1;
  n=1;
  termen = ((-1)^n)*(x^(2*n))/factorial(2*n);
  while(abs(termen) >= epsilon)
    output = output + termen;
    n = n + 1;
    termen = ((-1)^n)*(x^(2*n))/factorial(2*n);
  
  endwhile
  n=n
  rezultat = output
endfunction

#pentru un x foarte mare, va dura un pic pana cand termeni vor tinde catre 0, astfel ca suma "output" poate sa depaseasca realmax sau realmin
#solutia ar fi sa il reprezentam pe x in [0,2*pi)