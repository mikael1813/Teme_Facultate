nrapariti([],_,0).

nrapariti([H|T],E,R):-
     E=H,
     !,
     nrapariti(T,E,R2),
     R is R2+1.

nrapariti([_|T],E,R):-
    nrapariti(T,E,R).


stergere([],_,[]).

stergere([H|T],C,[H|R]):-
    nrapariti(C,H,R1),
    R1>1,
    !,
    stergere(T,C,R).

stergere([_|T],C,R):-stergere(T,C,R).

