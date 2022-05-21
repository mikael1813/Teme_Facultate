create database lab224_2;



go
use lab224_2;



create table mobila(
id int primary key identity,
nume nvarchar(30),
marca nvarchar(30),
pret int
)



Insert into mobila(nume, marca, pret)
values ('masa','ikea',200),
('masa mare','ikea',300),
('birou','jisk',1000),
('scaun','jisk',300)