
par([],_,[]).

par([H|T],E,Rez):-
    mod(H,2) =:= 0,
    par(T,E,X),
    Rez = [H,E|X].

par([H|T],E,Rez):-
    mod(H,2) =:= 1,
    par(T,E,X),
    Rez = [H|X].

