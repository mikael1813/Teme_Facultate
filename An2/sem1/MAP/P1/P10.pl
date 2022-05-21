%lista_aux(lista,Int,Int,Int,lista)
%lista_aux(i,i,i,i,o)
%
%lista_aux(l1..ln,Index1,Index2,e) = [], daca lista e vida
%                        = l1 (+) e (+)
%                           lista_aux(l2..ln,index1+1,Index2*2+1,e),
%                           daca Index1 = Index2
%                        = lista_aux(l2..ln,index1+1,index2,e)


lista_aux([],_,_,_,[]).

lista_aux([H|T],I1,I2,E,[H,E|Rez1]):-
    I1 =:= I2,
    !,
    lista_aux(T,I1+1,(I2*2)+1,E,Rez2),
    Rez1 = Rez2.

lista_aux([H|T],I1,I2,E,[H|R]):-
    lista_aux(T,I1+1,I2,E,R).


%lista_a(lista,int,lista)
%lista_a(i,i,o)

lista_a(L,E,X):-
    lista_aux(L,1,1,E,X).


%lista_b(lista, lista)
%lista_b(i,o)
%
%lista_b(l1..ln) = [], daca l e vida
%                = [l1], daca l mai are doar un element
%                = l1 (+) lista_a(l2,l1) (+) lista_b(l3..ln), daca l1
%                este numar si l2 este lista
%                = [l1] (+) lista_b(l2..ln), daca l1 si l2 sunt numere


lista_b([],[]).

lista_b([H|[]],[H]).


lista_b([H1,H2|T],[H1,X|Rez1]):-
    number(H1),
    not(number(H2)),
    lista_a(H2,H1,X),
    lista_b(T,Rez2),
    Rez1=Rez2.


lista_b([H1,H2|T],[H1|Rez1]):-
    number(H1),
    number(H2),
    append([H2],T,X),
    lista_b(X,Rez2),
    Rez1=Rez2.


