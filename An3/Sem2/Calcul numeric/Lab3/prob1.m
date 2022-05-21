A=[10 7 8 7;7 5 6 5;8 6 10 9;7 5 9 10];
b=[32;23;33;31];
x=A\b;
bp=[32.1;22.9;33.1;30.9];
eroare_relativa_intrare_a = norm(b-bp)/norm(b)

xp=A\bp;
eroare_relativa_iesire_a = norm(x-xp)/norm(x)

raport_a = eroare_relativa_iesire_a/eroare_relativa_intrare_a

Ap=[10 7 8.1 7.2;7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98];
eroare_relativa_intrare_b = norm(A-Ap)/norm(A)

xpp=Ap\b;
eroare_relativa_iesire_b = norm(x-xpp)/norm(x)
raport_b = eroare_relativa_iesire_b/eroare_relativa_intrare_b