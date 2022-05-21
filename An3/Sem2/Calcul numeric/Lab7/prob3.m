function prob3(t,y,tg)

    Hf = hermit_interpol(t,y,tg);

    fplot(Hf)


end