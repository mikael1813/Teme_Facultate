function prob_practica1()

   t=[0 1 2];
   for i =1:3
        y(i) = exp(i-1);
        yd(i) = exp(i-1);
   end
   rezultat_hermit = prob1(t,y,yd,0.25)

   rezultat_lagrange = lagr(t,y,0.25)

   rezultat_corect = exp(0.25)

   error_hermit = rezultat_corect-rezultat_hermit

   error_lagrange = rezultat_corect - rezultat_lagrange

end