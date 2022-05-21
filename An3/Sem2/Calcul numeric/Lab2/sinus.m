function sinus(x)
  epsilon = eps;
  output = x;
  n=1;
  termen = ((-1)^n)*(x^(2*n+1))/factorial(2*n+1);
  while(abs(termen) >= epsilon)
    output = output + termen;
    n = n + 1;
    termen = ((-1)^n)*(x^(2*n+1))/factorial(2*n+1);
  
  endwhile
  n=n
  rezultat = output
endfunction

#pentru un x foarte mare, va dura un pic pana cand termeni vor tinde catre 0, astfel ca suma "output" poate sa depaseasca realmax sau realmin
#solutia ar fi sa il reprezentam pe x in [0,2*pi)
