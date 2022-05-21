%generate(i,o)
%generate(N-int,L-list de 0-uri)
%
%generate(N) = [], daca N=0
%            = 0 (+) generate(N-1), altfel


generate(0,[]).

generate(N,X):-
    N>0,
    N1 is N - 1,
    generate(N1,Rez),
    X = [0|Rez].

backtracking(N):-
    P is N+N+1,
    generate(P,X),
    sir(X,1,P,X),
    fail.

%change_at_i(i,i,i,i,o)
%change_at_i(L-list,E-int,I-int,Start-int,X-List)
%
%change_at_i(l1..ln,E,I,Start) = [], daca L e vida
%                              = E (+) change_at_i(l2..ln,E,I,Start+1)
%                              = l1 (+) change_at_i(l2..ln,E,I,Start+1)


change_at_i([_|T],E,I,I,X):-
    Q is I+1,
    !,
    change_at_i(T,E,I,Q,Rez),
    X = [E|Rez].

change_at_i([H|T],E,I,Start,X):-
    Q is Start+1,
    !,
    change_at_i(T,E,I,Q,Rez),
    X = [H|Rez].

change_at_i([],_,_,_,X):-
    X = [].

%sir(i,i,i,i)
%sir(X-lista,Start-int,End-int,L-lista)
%
%sir(X,Start,End,l1..ln) : apeleaza ii_bun(X,X), daca Start+1=End
%
%                        : apeleaza
%                        sir(change_at_i(X,1,Start+1,1),Start+1,End,1;l3..ln)
%                        sir(change_at_i(X,-1,Start+1,1),Start+1,End,-1;l3..ln),
%                           daca Start < End-1 si l1=0
%
%                        : apeleaza
%                        sir(change_at_i(X,-1,Start+1,1),Start+1,End,-1;l3..ln)
%                        sir(change_at_i(X,0,Start+1,1),Start+1,End,0;l3..ln),
%                           daca Start < End-1 si l1=1
%
%                        : apeleaza
%                        sir(change_at_i(X,1,Start+1,1),Start+1,End,1;l3..ln)
%                        sir(change_at_i(X,0,Start+1,1),Start+1,End,0;l3..ln),
%                           daca Start < End-1 si l1=-1
%
%

sir(L,Start,End,_):-
    Start+1 =:= End,
    ii_bun(L,L).

sir(L,Start,End,[H1,_|T]):-
    E is End-1,
    Start < E,
    %H1 \= H2,
    H1 =:= 0,
    !,
    Q is Start+1,
    change_at_i(L,1,Q,1,Rez),
    sir(Rez,Q,End,[1|T]),
    change_at_i(L,-1,Q,1,Rez2),
    sir(Rez2,Q,End,[-1|T]).

sir(L,Start,End,[H1,_|T]):-
    E is End-1,
    Start < E,
    %H1 \= H2,
    H1 =:= 1,
    !,
    Q is Start+1,
    change_at_i(L,-1,Q,1,Rez),
    sir(Rez,Q,End,[-1|T]),
    change_at_i(L,0,Q,1,Rez2),
    sir(Rez2,Q,End,[0|T]).

sir(L,Start,End,[H1,_|T]):-
    E is End-1,
    Start < E,
    %H1 \= H2,
    H1 =:= -1,
    !,
    Q is Start+1,
    change_at_i(L,1,Q,1,Rez),
    sir(Rez,Q,End,[-1|T]),
    change_at_i(L,0,Q,1,Rez2),
    sir(Rez2,Q,End,[0|T]).


%ii_bun(i,i)
%ii-bun(L-list,X-list)
%
%ii_bun(l1..ln,X) : printeaza X, daca n = 1
%                 : se incheie, daca l1=l2
%                 : apeleaza ii_bun(l2..ln,X), altfel


ii_bun([_],L):-
    !,
    print(L),nl.

ii_bun([H1,H2|_],_):-
    H1 =:= H2,
    !.

ii_bun([_,H2|T],L):-
    ii_bun([H2|T],L).
