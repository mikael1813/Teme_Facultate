
paranteze(0,X,X).
   % print(X),nl.


paranteze(N,X,Y):-
    N > 0,
    N1 is N-1,
    append(['('],X,X1),
    %append(X,[b],X2),
    paranteze(N1,X1,Y1),
    Y=Y1.
   % paranteze(N1,X2,_).

paranteze(N,X,Y):-
    N > 0,
    N1 is N-1,
    append(X,[')'],X2),
    paranteze(N1,X2,Y1),
    Y=Y1.
