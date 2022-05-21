len([],0).

len([_|T],X):-
    len(T,Rez),
    X is Rez+1.
