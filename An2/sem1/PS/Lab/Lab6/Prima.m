

x=normrnd(165,10,1,1000);
[c,xx]=hist(x,10);
w=10/(max(x)-min(x));
hist(x,xx,w)
hold on
t=linspace(min(x),max(x),1001);
plot(t,normpdf(t,165,10))