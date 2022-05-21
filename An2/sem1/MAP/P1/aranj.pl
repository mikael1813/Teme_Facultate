aranj(0,X,X).

aranj(N,X,Y):-
    N>0,
    N1 is N-1,
    aranj(N1,X,Y).

aranj(N,X,Y):-
    N>0,
    X1=[N|X],
    N1 is N-1,
    aranj(N1,X1,Y1),
    Y=Y1.


exista([],_).

exista([H|T],E):-
    H=\=E,
    exista(T,E).

