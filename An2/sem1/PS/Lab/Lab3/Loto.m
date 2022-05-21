m=1000;

 x=zeros(1,m);

 for i=1:m

 cate_nr_castigatoare=hygernd(49,6,6);

 while cate_nr_castigatoare<2

 x(i)++;

 cate_nr_castigatoare=hygernd(49,6,6);

 end

 end

 clf;grid on; hold on;

 p=1-hygepdf(0,49,6,6)-hygepdf(1,49,6,6);

 N=histc(x,0:max(x));

 bar(0:max(x),N/m,'hist','FaceColor','b');

 bar(0:max(x),geopdf(0:max(x),p),'FaceColor','y');

 legend('estimated probabilities','theoretical probabilities');



set(findobj('type','patch'),'facealpha',0.7);



axis([-0.5 max(x) 0 max([p,N/m])]);