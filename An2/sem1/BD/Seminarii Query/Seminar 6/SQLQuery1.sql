create database Seminar6

go

use Seminar6

create table tipuri_de_tren(
	id int primary key identity,
	descriere nvarchar(50)
)

create table trenuri(
	id int primary key identity,
	nume nvarchar(50),
	id_tip int foreign key references tipuri_de_tren(id)
)

create table statii(
	id int primary key identity,
	nume nvarchar(50)
)

create table rute(
	id int primary key identity,
	nume nvarchar(50),
	id_tren int foreign key references trenuri(id)

)

create table sosiri_plecari(
	id_statie int foreign key references statii(id),
	id_ruta int foreign key references rute(id),
	timp_sosire time,
	timp_plecare time

	constraint Pk_Statie_Ruta Primary Key (id_statie,id_ruta)
)

go
create or alter procedure add_tip_tren @descriere nvarchar(50) as begin
if (not exists(select * from tipuri_de_tren t where t.descriere = @descriere)) begin
insert into tipuri_de_tren (descriere)
values (@descriere)
end;
end;



go
exec add_tip_tren N'sageata albastra'
exec add_tip_tren N'sageata albastra'
exec add_tip_tren N'tgv'
exec add_tip_tren N'accelerat'
exec add_tip_tren N'the tank engines'



select * from tipuri_de_tren


go
create or alter procedure add_tren @nume nvarchar(50), @id_tip int as begin
if (exists(select * from tipuri_de_tren t where t.id = @id_tip)) begin
insert into trenuri (nume, id_tip)
values (@nume, @id_tip)
end;
else begin
raiserror('nu exista tipul', 12, 1)
end;
end;



go
exec add_tren N'Thomas', 3
exec add_tren N'2032', 2
exec add_tren N'numar de la cfr whatever', 3
exec add_tren N'alt tren', 1
exec add_tren N'whatever', 6



select * from trenuri

go
create procedure add_ruta @nume_ruta nvarchar(50), @id_tren int as begin
if(not exists(select * from Trenuri T where T.id=@id_tren)) begin
raiserror('Nu exista trenul',12,1);
end
else
begin
insert into Rute(nume, id_tren) values (@nume_ruta, @id_tren);
end
end



go
exec add_ruta N'Brasov-Cluj', 1;
exec add_ruta N'Brasov-Bucuresti', 2;



select * from Rute;

GO
CREATE PROCEDURE add_statii @nume NVARCHAR(50)
AS
BEGIN
IF( NOT EXISTS(SELECT * FROM Statii WHERE nume = @nume))
BEGIN
INSERT INTO Statii (nume) VALUES (@nume)
END
END



GO
EXEC add_statii N'Brasov'
EXEC add_statii N'Cluj'
EXEC add_statii N'Bucuresti'
EXEC add_statii N'Sighisoara'
EXEC add_statii N'Predeal'
EXEC add_statii N'Sinaia'



SELECT * FROM Statii


go
CREATE PROCEDURE add_sosiri_plecari @id_ruta int, @id_statie int, @plecare time, @sosire time AS BEGIN

IF (EXISTS(SELECT * FROM sosiri_plecari WHERE id_statie=@id_statie and id_ruta=@id_ruta))
UPDATE sosiri_plecari SET timp_plecare=@plecare, timp_sosire=@sosire WHERE id_ruta=@id_ruta and id_statie=@id_statie
ELSE
INSERT INTO sosiri_plecari(id_ruta,id_statie,timp_plecare,timp_sosire) VALUES(@id_ruta,@id_statie,@plecare,@sosire)
END




go



select * from rute
select * from statii



go
exec add_sosiri_plecari 1,1,'13:00','13:10'



select * from sosiri_plecari
