create database [Malea Lukoil]



use [Malea Lukoil]



create table Angajati(

id int primary key identity,
prenume nvarchar(50) not null,
nume nvarchar(50) not null,
data_nasterii date not null,
salariu bigint not null,
cetatenia nvarchar(50) not null



);