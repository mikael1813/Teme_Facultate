function pb2(n=1000)
fprintf('\n');
%a)
disp('a)');
m=unifrnd(150,170);
s=unifrnd(5,20);
x=normrnd(m,s,1,n);
[m1,m2]=CI_mean2(x,0.05);
fprintf('Valoarea intervalului de incredere bilateral\n pentru medie este (%5.2f,%5.2f)\n',m1,m2);
[s1,s2]=CI_abtstd(x,0.05);
fprintf('Valoarea intervalului de incredere bilateral\n pentru abaterea standard este (%4.2f,%4.2f)\n',s1,s2);
[p1,p2]=CI_proportion(155<x&x<165,0.05);
fprintf('Valoarea intervalului de incredere bilateral\n pentru proportie este (%4.3f,%4.3f)\n',p1,p2);
fprintf('\n');
fprintf('Inaltimea medie exacta a populatiei: %5.2f cm.\n',m);
fprintf('Abaterea standard exacta a inaltimii populatiei: %4.2f cm.\n',s);
fprintf('Proportia exacta a persoanelor din populatie cu inaltimea \n in intervalul (155,165) este %3.2f.\n',...
normcdf(165,m,s)-normcdf(155,m,s));
fprintf('\n\n');
%b)
disp('b)');
m=unifrnd(150,170);
m=161;
sigma=10;
x=normrnd(m,sigma,1,n);
H=ztest(x,160,s,'alpha',0.01,'tail','both');
if H==0
  disp('Inaltimea medie este 160 cm.');
else
  disp('Inaltimea medie nu este 160 cm.');
end
H=ztest(x,155,s,'tail','left');
if H==0
  disp('Inaltimea medie este cel putin egala cu 155 cm.');
else
  disp('Inaltimea medie este strict mai mica decat 155 cm.');
end
H=ztest(x,165,s,'tail','left');
if H==0
  disp('Inaltimea medie este cel putin egala cu 165 cm.');
else
  disp('Inaltimea medie este strict mai mica decat 165 cm.');
end
fprintf('\n');
fprintf('Inaltimea medie exacta a populatiei este %5.2f cm.\n',m);