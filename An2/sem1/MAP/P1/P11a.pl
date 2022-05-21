%vale(l1,l2,..ln, amonte, aval)=
%                  true , daca l e vida si amonte = aval = 1
%                  vale(l2,...ln , 1, aval), daca l1 > l2 si aval = 0
%                  vale(l2,...ln , amonte, 1), daca l1 < l2 si amonte =0



vale_aux([H|[]] , 1 , 1).

vale_aux([H1,H2 | T] , Amonte , Aval):-
   H1 > H2,
   Aval =:= 0,
   append([H2],T,List),
   vale_aux(List,1,Aval).

vale_aux([H1,H2 | T] , Amonte , Aval):-
   H1 < H2,
   Amonte =:= 1,
   append([H2],T,List),
   vale_aux(List,Amonte,1).


vale(List):-
   vale_aux(List,0,0).
