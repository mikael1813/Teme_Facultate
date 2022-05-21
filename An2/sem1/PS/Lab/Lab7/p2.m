n=500;
m=unifrnd(150,170)#distributia uniforma, inalt. medie intre 150 si 170

sigma = unifrnd(5,20);

x=normrnd(m,sigma,1,500);

#contor = 0;
#for i=1:1000
#  x=normrnd(m,sigma,1,500);
#  y = intervaldeincrederefaraabaterestandard(x);
#  if (m>= y(1) && m<= y(2))
#    contor++;
#  endif
#endfor
#
#contor/1000
[left,right]=interval_incredere_medie_i(x,alpha=0.05)
[left,right]=proportie_iv(x>155 & x<165)
