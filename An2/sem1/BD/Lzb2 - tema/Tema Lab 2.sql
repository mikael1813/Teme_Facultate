use [Ski Resort]

-- 7 interogari where

select * from Angajati where Nume='Ludvic';

select * from Servicii where Pret>100;

insert into Clienti
(IdClient,Nume,Prenume,DataNasterii,Telefon)
values(11,'Pascal','Pascalopol','1960-10-11','077128934');

select * from Clienti where Nume='Pascal' and Prenume='Pascalopol';

update Clienti set Telefon='0777777777' where Nume='Pascal' and Prenume='Pascalopol';

delete from Clienti where Nume='Pascal' and Prenume='Pascalopol';

--3 interogari group by

select IdClient,COUNT(IdClient)[Numar de servicii achizitionate],SUM(Pret)[Suma Cheltuita] from FacturaServicii group by IdClient;

select IdClient,COUNT(IdClient)[Numar de pachete achizitionate],SUM(Pret)[Suma Cheltuita] from FacturaPachete group by IdClient;

select Nume,Pret from Echipamente group by Pret,Nume;

--2 interogari cu dinstinct

select distinct IdStil from Antrenori;

select distinct IdClient from FacturaServicii;

--2 interogari cu having

select Nume,Pret from Echipamente group by Pret,Nume having Pret>=100;

select IdClient,COUNT(IdClient)[Numar de servicii achizitionate],SUM(Pret)[Suma Cheltuita] from FacturaServicii group by IdClient having SUM(Pret)>300;

--7 interogari ce folosesc cel putin 2 tabele

select s.Descriere,s.Pret,a.Nume,a.Prenume from Antrenori a inner join Servicii s on a.IdAntrenor=s.IdAntrenor;

select distinct c.Nume,SUM(f.Pret)[Total suma consumata] from Clienti c inner join FacturaServicii f
on c.IdClient=f.IdClient
group by c.Nume;

select distinct c.Nume,count(f.IdClient)[Servici achizitionate] from Clienti c inner join FacturaServicii f
on c.IdClient=f.IdClient
group by c.Nume;

select distinct t.Denumire from TipEchipament t inner join Echipamente e on t.IdTip=e.IdTip;

select distinct t.Denumire,SUM(e.Pret) from TipEchipament t inner join Echipamente e on t.IdTip=e.IdTip
group by t.Denumire;

select c.Nume,f.Pret from FacturaPachete f inner join Clienti c
on c.IdClient=f.IdClient;

select c.Nume,sum(f.Pret)[Suma Totala Cheltuita pe Pachete] from FacturaPachete f inner join Clienti c
on c.IdClient=f.IdClient
group by c.Nume;

--2 interogari intre tabele cu rel. m-n

select c.Nume,c.Prenume,e.Pret
from Clienti c
inner join Inchirieri i on c.IdClient=i.IdClient
inner join Tranzactii t on t.IdInchiriere=i.IdInchiriere
inner join Echipamente e on e.IdEchipament=t.IdEchipament;

select c.Nume,c.Prenume,sum(e.pret)[Pret Inchirieri],count(i.IdInchiriere)[Numar inchirieri]
from Clienti c
inner join Inchirieri i on c.IdClient=i.IdClient
inner join Tranzactii t on t.IdInchiriere=i.IdInchiriere
inner join Echipamente e on e.IdEchipament=t.IdEchipament
group by c.Nume,c.Prenume;