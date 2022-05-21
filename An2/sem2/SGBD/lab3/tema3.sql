use [Ski Resort]
select * from Servicii;
select *from FacturaServicii;
select * from Clienti;

alter procedure Procedura1 @IdAntrenor varchar(20),@Descriere varchar(100),@Pret varchar(20),@IdPachet varchar(20),@IdServiciu varchar(20),@IdClient varchar(20),@Nume varchar(50),@Prenume varchar(50),@DataNasterii varchar(20),@Telefon varchar(20)
as

declare @Str nvarchar(100) = 'Valorile: ';


if ISNUMERIC(@IdAntrenor) <> 1
begin
	set @Str = @Str+'IdAntrenor, ';
end

if ISNUMERIC(@Pret) <> 1
begin
	set @Str = @Str+'Pret, ';
end

if ISNUMERIC(@IdPachet) <> 1
begin
	set @Str = @Str+'IdPachet, ';
end

if ISNUMERIC(@IdServiciu) <> 1
begin
	set @Str = @Str+'IdServiciu, ';
end

if ISNUMERIC(@IdClient) <> 1
begin
	set @Str = @Str+'IdClient, ';
end

if @Nume LIKE '%[0-9]%'
begin
	set @Str = @Str+'Nume, ';
end


if @Prenume LIKE '%[0-9]%'
begin
	set @Str = @Str+'Prenume, ';
end

if ISDATE(@DataNasterii) <> 1
begin
	set @Str = @Str+'DataNasterii, ';
end

if ISNUMERIC(@Telefon) <> 1
begin
	set @Str = @Str+'Telefon, ';
end

if @Str = 'Valorile: '
begin
BEGIN TRY  
    begin tran;
	save tran savepoint;
	insert into Servicii(IdAntrenor,Descriere,Pret,IdPachet) VALUES(@IdAntrenor,@Descriere,@Pret,@IdPachet);
	print 'Insert into Servicii';
	insert into Clienti VALUES(@Nume,@Prenume,@DataNasterii,@Telefon);
	print 'Insert into Clienti';
	insert into FacturaServicii(IdServiciu,IdClient,Pret) VALUES(@IdServiciu,@IdClient,@Pret);
	print 'Insert into FacturaServicii';
	commit tran;
END TRY  
BEGIN CATCH 
	rollback tran savepoint;
	commit tran; 
    print N'Rollback(se sterg toate modificarile de mai sus)'; 
END CATCH; 
end
else
	begin
		set @Str = @Str + 'nu sunt valide!';
		print(@Str);
	end
Go







alter procedure Procedura2 @IdAntrenor varchar(20),@Descriere varchar(100),@Pret varchar(20),@IdPachet varchar(20),@IdServiciu varchar(20),@IdClient varchar(20),@Nume varchar(50),@Prenume varchar(50),@DataNasterii varchar(20),@Telefon varchar(20)
as

declare @Str nvarchar(100) = 'Valorile: ';


if ISNUMERIC(@IdAntrenor) <> 1
begin
	set @Str = @Str+'IdAntrenor, ';
end

if ISNUMERIC(@Pret) <> 1
begin
	set @Str = @Str+'Pret, ';
end

if ISNUMERIC(@IdPachet) <> 1
begin
	set @Str = @Str+'IdPachet, ';
end

if ISNUMERIC(@IdServiciu) <> 1
begin
	set @Str = @Str+'IdServiciu, ';
end

if ISNUMERIC(@IdClient) <> 1
begin
	set @Str = @Str+'IdClient, ';
end

if @Nume LIKE '%[0-9]%'
begin
	set @Str = @Str+'Nume, ';
end


if @Prenume LIKE '%[0-9]%'
begin
	set @Str = @Str+'Prenume, ';
end

if ISDATE(@DataNasterii) <> 1
begin
	set @Str = @Str+'DataNasterii, ';
end

if ISNUMERIC(@Telefon) <> 1
begin
	set @Str = @Str+'Telefon, ';
end

declare @Save int = 0;

if @Str = 'Valorile: '
begin
BEGIN TRY  
    begin tran;
	insert into Clienti VALUES(@Nume,@Prenume,@DataNasterii,@Telefon);
	print 'Insert into Clienti';
	set @Save = 1;
	save tran savepoint1;
	insert into Servicii(IdAntrenor,Descriere,Pret,IdPachet) VALUES(@IdAntrenor,@Descriere,@Pret,@IdPachet);
	print 'Insert into Servicii';
	set @Save = 2;
	save tran savepoint2;
	insert into FacturaServicii(IdServiciu,IdClient,Pret) VALUES(@IdServiciu,@IdClient,@Pret);
	print 'Insert into FacturaServicii';
	commit tran;
END TRY  
BEGIN CATCH 
	if @Save = 1
	begin
		print 'rollback la save 1(doar clientul ramane inserat)';
		rollback tran savepoint1;
		commit tran; 
	end
	if @Save = 2
	begin
		print 'rollback la save2(doar clientul si serviciul raman inserate)';
		rollback tran savepoint2;
		commit tran; 
	end
	if @Save = 0
	begin
    print 'Nu s-a facut rollback'; 
	end
END CATCH; 
end
else
	begin
		set @Str = @Str + 'nu sunt valide!';
		print(@Str);
	end
Go




exec Procedura1 'a','descriere','b','1','1','1','Ion1','Elena','2001-03-035','0778987231';
exec Procedura1 '3','descriere','100','1','1','1','Ion','Elena','2001-03-03','0778987231';
exec Procedura1 '1','descriere','20','1','5','100','Ion','Elena','2001-03-03','0778987231';
exec Procedura1 '1','descriere','1','1','1','1','Ion','Elena','2001-03-03','0778987231';


exec Procedura2 'a','descriere','b','1','1','1','Ion1','Elena','2001-03-035','0778987231';
exec Procedura2 '3','descriere','100','1','1','1','Ion','Elena','2001-03-03','0778987231';
exec Procedura2 '1','descriere','20','1','5','100','Ion','Elena','2001-03-03','0778987231';
exec Procedura2 '1','descriere','1','1','1','1','Ion','Elena','2001-03-03','0778987231';


select * from Servicii;
select *from FacturaServicii;
select * from Clienti;