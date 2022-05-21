aparitii([],_,0).

aparitii([H|L],E,S):-
    H = E,
    aparitii(L,E,S1),
    S is S1+1.

aparitii([_|L],E,S):-
    aparitii(L,E,S1),
    S is S1.


