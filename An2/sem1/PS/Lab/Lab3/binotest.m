clf; grid on; hold on;
p=0.5; n=10; m=1000;
x=binornd(n,p,1,m);
N=histc(x,0:n);
bar(0:n,N/m,'hist','FaceColor','b');
bar(0:n,binopdf(0:n,n,p),'FaceColor','y');
legend('estimated probabilities','theoretical probabilities');
set(findobj('type','patch'),'facealpha',0.7);

 axis([-2 12 0 0.3])
