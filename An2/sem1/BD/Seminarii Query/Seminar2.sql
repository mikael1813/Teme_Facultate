create database Seminar2

use Seminar2

create table Youtuberi (
	id int primary key identity,
	nume nvarchar(50),
	prenume nvarchar(50),
	data_nasterii date,
	domeniu nvarchar(50),
	networth money
);


insert into Youtuberi
(nume,prenume,data_nasterii,domeniu,networth)
VALUES ('Kjelberg','Felix','1990-07-12','Gaming',2000000),
('Popa','Dorian','1987-04-27','Vlogging',500),
('Charles','James','1995-03-19','Vlogging',1000)

select * from Youtuberi

update Youtuberi set networth=3000 where prenume='Felix' and nume='Kjelberg'

delete from Youtuberi where prenume='James' and nume='Charles'

