use [Ski Resort]

GO  
CREATE FUNCTION exista(@String nvarchar(max), @Elem nvarchar)  
RETURNS int   
AS   
-- Returneaza 1 daca exista elem in string, 0 in caz contrar 
BEGIN  
    DECLARE @ret int;  
    SET @ret = 0;
	declare @loop int = 1;
	declare @lung int = Len(@String);

	while (@loop <= @lung) begin
		if SUBSTRING(@String, @loop, 1) = @Elem
		begin
			SET @ret=1
			RETURN @ret; 
		end
		select @loop = @loop + 1
	end
    RETURN @ret;  
END; 

GO  
CREATE FUNCTION verificare_nume(@String nvarchar(max), @Lungime int)  
RETURNS int   
AS   
-- Returneaza 1 daca numele este corect, 0 in caz contrar 
BEGIN  
    DECLARE @ret int;  
    SET @ret = 1;
	declare @loop int = 1;
	declare @lung int = Len(@String);
	declare @list nvarchar(100)= 'abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'
	declare @e_bun int;

	if @lung > @Lungime
	begin
			set @ret=0;
			RETURN @ret;
	end

	while (@loop <= @lung) begin
		set @e_bun = dbo.exista(@list, SUBSTRING(@String, @loop, 1));
		if  @e_bun = 0
		begin
			SET @ret = 0;
			RETURN @ret;
		end
		select @loop = @loop + 1
	end
    RETURN @ret;  
END; 

GO  
CREATE FUNCTION verificare_prenume(@String nvarchar(max), @Lungime int)  
RETURNS int   
AS   
-- Returneaza 1 daca prenumele este corect, 0 in caz contrar 
BEGIN  
    DECLARE @ret int;  
    SET @ret = 1;
	declare @loop int = 1;
	declare @lung int = Len(@String);
	declare @list nvarchar(100)= ' abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
	declare @e_bun int;

	if @lung > @Lungime
	begin
			set @ret=0;
			RETURN @ret;
	end

	while (@loop <= @lung) begin
		set @e_bun = dbo.exista(@list, SUBSTRING(@String, @loop, 1));
		if  @e_bun = 0
		begin
			SET @ret = 0;
			RETURN @ret;
		end
		select @loop = @loop + 1
	end
    RETURN @ret;  
END; 

GO  
CREATE FUNCTION verificare_telefon(@String nvarchar(max))  
RETURNS int   
AS   
-- Returneaza 1 daca numarul de telefon este corect, 0 in caz contrar 
BEGIN  
    DECLARE @ret int;  
    SET @ret = 1;
	declare @loop int = 3;
	declare @lung int = Len(@String);
	declare @list nvarchar(100)= '0123456789'
	declare @e_bun int;

	if @lung != 10
	begin
			set @ret=0;
			RETURN @ret;
	end

	if SUBSTRING(@String, 1, 1) != N'0'
	begin
			set @ret=0;
			RETURN @ret;
	end

	if SUBSTRING(@String, 2, 1) != N'7'
	begin
			set @ret=0;
			RETURN @ret;
	end

	while (@loop <= 10) begin
		set @e_bun = dbo.exista(@list, SUBSTRING(@String, @loop, 1));
		if  @e_bun = 0
		begin
			SET @ret = 0;
			RETURN @ret;
		end
		select @loop = @loop + 1
	end
    RETURN @ret;  
END; 


GO  
CREATE FUNCTION verificare_int(@String nvarchar(max))  
RETURNS int   
AS   
-- Returneaza stringul convertit la int care este >= 0 , -1 in caz contrar
-- Functia arunca erori daca nu s-a putut converti la int
BEGIN  
    DECLARE @ret int;
	set @ret = convert(int, @String);

	if @ret < 0
	begin
		set @ret = -1;
	end
	return @ret;      
END

GO  
create FUNCTION verificare_date(@String nvarchar(max))  
RETURNS date   
AS   
-- Returneaza stringul convertit la date
-- Functia arunca erori daca nu s-a putut converti la date
BEGIN  
    DECLARE @ret date;
	set @ret = convert(date, @String);
	return @ret;      
END;

GO 
create FUNCTION verificare_descriere(@String nvarchar(max))
RETURNS INT
AS
--Returneaza 1 daca descrierea nu este goala, 0 in caz contrar
BEGIN
	DECLARE @ret int;
	set @ret = 0;
	if(DATALENGTH(@String) > 0)
	begin
		set @ret = 1;
	end
	return @ret;
END


--servicii


GO 
alter PROCEDURE create_serviciu (@idServiciu nvarchar(max), @idAntrenor nvarchar(max),@descriere nvarchar(max),@pret nvarchar(max),@idPachet nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @id2 int;
	declare @pret1 int;
	declare @id3 int;

	if @idServiciu is NULL or @idAntrenor is NULL or @idPachet is NULL or @pret is NULL or @descriere is NULL
	begin
		print('Eroare la create servici: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@idServiciu);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Serviciului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id2= dbo.verificare_int(@idAntrenor);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Antrenorului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id3= dbo.verificare_int(@idPachet);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Pachetului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @pret1= dbo.verificare_int(@pret);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Pretul nu este un int; ';
	END CATCH

	if dbo.verificare_descriere(@descriere) = 0
	begin
		set @eroare=@eroare + 'Descrierea nu poate fi goala; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	if exists (select * from Servicii where IdServiciu=@id1)
	begin
		set @eroare=@eroare + 'Exista deja un serviciu cu acest id; ';
	end

	if not exists (select * from Antrenori where IdAntrenor=@id2)
	begin
		set @eroare=@eroare + 'Nu exista niciun antrenor cu acest id; ';
	end

	if not exists (select * from PachetServicii where IdPachet=@id3)
	begin
		set @eroare=@eroare + 'Nu exista niciun pachet cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	INSERT INTO Servicii(IdServiciu, IdAntrenor,Descriere,Pret,IdPachet)  
            VALUES     ( @id1, @id2, @descriere, @pret1, @id3);  

	print('Adaugare cu succes in servicii!');
END 


GO
CREATE PROCEDURE read_servicii
AS  
BEGIN  
	select * from Servicii;
END 

GO 
create PROCEDURE update_serviciu (@idServiciu nvarchar(max), @idAntrenor nvarchar(max),@descriere nvarchar(max),@pret nvarchar(max),@idPachet nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @id2 int;
	declare @pret1 int;
	declare @id3 int;

	if @idServiciu is NULL or @idAntrenor is NULL or @idPachet is NULL or @pret is NULL or @descriere is NULL
	begin
		print('Eroare la update servici: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@idServiciu);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Serviciului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id2= dbo.verificare_int(@idAntrenor);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Antrenorului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id3= dbo.verificare_int(@idPachet);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Pachetului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @pret1= dbo.verificare_int(@pret);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Pretul nu este un int; ';
	END CATCH

	if dbo.verificare_descriere(@descriere) = 0
	begin
		set @eroare=@eroare + 'Descrierea nu poate fi goala; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	if not exists (select * from Servicii where IdServiciu=@id1)
	begin
		set @eroare=@eroare + 'Nu exista un serviciu cu acest id; ';
	end

	if not exists (select * from Antrenori where IdAntrenor=@id2)
	begin
		set @eroare=@eroare + 'Nu exista niciun antrenor cu acest id; ';
	end

	if not exists (select * from PachetServicii where IdPachet=@id3)
	begin
		set @eroare=@eroare + 'Nu exista niciun pachet cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	Update Servicii
	SET IdAntrenor=@id2,IdPachet=@id3,Pret=@pret1,Descriere=@descriere
	WHERE IdServiciu=@id1;
	print('Update cu succes in servicii!');
END 

GO
alter PROCEDURE delete_serviciu (@id nvarchar(max))  
AS  
BEGIN  
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;

	if @id is NULL 
	begin
		print('Eroare la delete serviciu: Id-ul serviciului nu poate fi NULL');
		RETURN;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@id);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul nu este un int; ';
		print @eroare;
		RETURN;
	END CATCH 
	
	if not exists (select * from Servicii where IdServiciu=@id1)
	begin
		set @eroare=@eroare + 'Nu exista un serviciu cu acest id pentru delete; ';
	end

	if exists (select * from FacturaServicii where IdServiciu=@id1)
	begin
		set @eroare=@eroare + 'Nu se poate sterge deoarece exista o facturaServiciu avand acest serviciu; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la delete serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	DELETE FROM Servicii where IdServiciu=@id1;

	print('Delete cu succes in servicii!');
END 


--factura servici

GO 
create PROCEDURE create_factura_serviciu (@idFactura nvarchar(max), @idServiciu nvarchar(max),@IdClient nvarchar(max),@pret nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @id2 int;
	declare @pret1 int;
	declare @id3 int;

	if @idServiciu is NULL or @idFactura is NULL or @IdClient is NULL or @pret is NULL
	begin
		print('Eroare la create facturaServici: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id2= dbo.verificare_int(@idServiciu);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Serviciului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id1= dbo.verificare_int(@idFactura);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Facturii nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id3= dbo.verificare_int(@idClient);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Clientului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @pret1= dbo.verificare_int(@pret);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Pretul nu este un int; ';
	END CATCH

	if @eroare != N''
	begin
		set @eroare='Eroare la creare facturiServiciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	if exists (select * from FacturaServicii where IdFactura=@id1)
	begin
		set @eroare=@eroare + 'Exista deja o factura cu acest id; ';
	end

	if not exists (select * from Servicii where @idServiciu=@id2)
	begin
		set @eroare=@eroare + 'Nu exista niciun serviciu cu acest id; ';
	end

	if not exists (select * from Clienti where IdClient=@id3)
	begin
		set @eroare=@eroare + 'Nu exista niciun client cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare facturaServiciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	INSERT INTO FacturaServicii(IdFactura, IdServiciu,IdClient,Pret)  
            VALUES     ( @id1, @id2, @id3,@pret1);  

	print('Adaugare cu succes in facturaServici!');
END 


GO
CREATE PROCEDURE read_factura_servicii
AS  
BEGIN  
	select * from FacturaServicii;
END 

GO 
alter PROCEDURE update_factura_serviciu (@idFactura nvarchar(max), @idServiciu nvarchar(max),@IdClient nvarchar(max),@pret nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @id2 int;
	declare @pret1 int;
	declare @id3 int;

	if @idServiciu is NULL or @idFactura is NULL or @IdClient is NULL or @pret is NULL
	begin
		print('Eroare la create facturaServici: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id2= dbo.verificare_int(@idServiciu);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Serviciului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id1= dbo.verificare_int(@idFactura);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Facturii nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @id3= dbo.verificare_int(@idClient);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Clientului nu este un int; ';
	END CATCH

	BEGIN TRY  
		set @pret1= dbo.verificare_int(@pret);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Pretul nu este un int; ';
	END CATCH

	if @eroare != N''
	begin
		set @eroare='Eroare la creare facturiServiciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	if not exists (select * from FacturaServicii where IdFactura=@id1)
	begin
		set @eroare=@eroare + 'Nu exista o factura cu acest id; ';
	end

	if not exists (select * from Servicii where @idServiciu=@id2)
	begin
		set @eroare=@eroare + 'Nu exista niciun serviciu cu acest id; ';
	end

	if not exists (select * from Clienti where IdClient=@id3)
	begin
		set @eroare=@eroare + 'Nu exista niciun client cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare facturaServiciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	Update FacturaServicii
	SET @idServiciu=@id2,IdClient=@id3,Pret=@pret1
	WHERE IdFactura=@id1;
	print('Update cu succes in facturaServicii!');
END 

GO
create PROCEDURE delete_factura_serviciu (@id nvarchar(max))  
AS  
BEGIN  
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;

	if @id is NULL 
	begin
		print('Eroare la delete facturaServiciu: Id-ul factureiServiciului nu poate fi NULL');
		RETURN;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@id);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul nu este un int; ';
		print @eroare;
		RETURN;
	END CATCH 
	
	if not exists (select * from FacturaServicii where IdFactura=@id1)
	begin
		set @eroare=@eroare + 'Nu exista un serviciu cu acest id pentru delete; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la delete facturiiServiciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	DELETE FROM FacturaServicii where IdFactura=@id1;

	print('Delete cu succes in facturaServicii!');
END 

-- client

GO 
create PROCEDURE create_client (@id nvarchar(max), @nume nvarchar(max),@prenume nvarchar(max),@data nvarchar(max),@telefon nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @zi1 date;

	if @id is NULL or @nume is NULL or @prenume is NULL or @data is NULL or @telefon is NULL
	begin
		print('Eroare la create client: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@id);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Clientului nu este un int; ';
	END CATCH

	if dbo.verificare_nume(@nume,15) = 0
	begin
		set @eroare=@eroare + 'Numele poate fi format din litere mici/mari/caracterul "-" si trebuie sa aiba lungimea maxim 15; ';
	end

	if dbo.verificare_prenume(@prenume,15) = 0
	begin
		set @eroare=@eroare + 'Prenumele poate fi format din litere mici/mari/caracterul "-" si trebuie sa aiba lungimea maxim 15; ';
	end

	BEGIN TRY  
		set @zi1= dbo.verificare_date(@data);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Ziua nu este de tip date; ';
	END CATCH  

	if dbo.verificare_telefon(@telefon) = 0
	begin
		set @eroare=@eroare + 'Telefonul poate fi format doar din 10 cifre';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare client: '+@eroare;
		print @eroare;
		RETURN;
	end

	if exists (select * from Clienti where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Exista deja un client cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare client: '+@eroare;
		print @eroare;
		RETURN;
	end

	INSERT INTO Clienti(IdClient, Nume,Prenume,DataNasterii,Telefon)  
            VALUES     ( @id1, @nume, @prenume,@zi1,@telefon);  

	print('Adaugare cu succes in client!');
END 


GO
CREATE PROCEDURE read_client
AS  
BEGIN  
	select * from Clienti;
END 

GO 
create PROCEDURE update_client (@id nvarchar(max), @nume nvarchar(max),@prenume nvarchar(max),@data nvarchar(max),@telefon nvarchar(max))
AS
BEGIN
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;
	declare @zi1 date;

	if @id is NULL or @nume is NULL or @prenume is NULL or @data is NULL or @telefon is NULL
	begin
		print('Eroare la create client: Datele de intrare nu pot fi NULL')
		return;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@id);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul Clientului nu este un int; ';
	END CATCH

	if dbo.verificare_nume(@nume,15) = 0
	begin
		set @eroare=@eroare + 'Numele poate fi format din litere mici/mari/caracterul "-" si trebuie sa aiba lungimea maxim 15; ';
	end

	if dbo.verificare_prenume(@prenume,15) = 0
	begin
		set @eroare=@eroare + 'Prenumele poate fi format din litere mici/mari/caracterul "-" si trebuie sa aiba lungimea maxim 15; ';
	end

	BEGIN TRY  
		set @zi1= dbo.verificare_date(@data);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Ziua nu este de tip date; ';
	END CATCH  

	if dbo.verificare_telefon(@telefon) = 0
	begin
		set @eroare=@eroare + 'Telefonul poate fi format doar din 10 cifre';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare client: '+@eroare;
		print @eroare;
		RETURN;
	end

	if not exists (select * from Clienti where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Nu exista un client cu acest id; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la creare client: '+@eroare;
		print @eroare;
		RETURN;
	end

	UPDATE Clienti
	Set Nume=@nume,Prenume=@prenume,DataNasterii=@zi1,Telefon=@telefon
	where IdClient=@id1  

	print('Adaugare cu succes in client!');
END 

GO
create PROCEDURE delete_client (@id nvarchar(max))  
AS  
BEGIN  
	SET NOCOUNT ON; 

	DECLARE @eroare nvarchar(256);
	set @eroare=N'';
	declare @id1 int;

	if @id is NULL 
	begin
		print('Eroare la delete client: Id-ul clientului nu poate fi NULL');
		RETURN;
	end

	BEGIN TRY  
		set @id1= dbo.verificare_int(@id);
	END TRY  
	BEGIN CATCH  
		set @eroare=@eroare + 'Id-ul nu este un int; ';
		print @eroare;
		RETURN;
	END CATCH 
	
	if not exists (select * from Clienti where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Nu exista un client cu acest id pentru delete; ';
	end

	if exists (select * from FacturaServicii where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Nu se poate sterge deoarece exista o facturaServiciu avand acest client; ';
	end

	if exists (select * from FacturaPachete where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Nu se poate sterge deoarece exista o facturaPachet avand acest client; ';
	end

	if exists (select * from Inchirieri where IdClient=@id1)
	begin
		set @eroare=@eroare + 'Nu se poate sterge deoarece exista o inchiriere avand acest client; ';
	end

	if @eroare != N''
	begin
		set @eroare='Eroare la delete serviciu: '+@eroare;
		print @eroare;
		RETURN;
	end

	DELETE FROM Clienti where IdClient=@id1;

	print('Delete cu succes in servicii!');
END 


use [Ski Resort]

exec create_serviciu N'1',N'1',N'a fost odata',N'123',N'1';
exec update_serviciu N'1',N'1',N'a fost odata ca niciodata',N'123',N'1';
exec delete_serviciu N'1';
exec read_servicii;

exec create_factura_serviciu N'1',N'1',N'1',N'123';
exec update_factura_serviciu N'1',N'1',N'1',N'128';
exec delete_factura_serviciu N'1';
exec read_factura_servicii;

exec create_client N'1',N'Mihai',N'Apostol',N'12/12/2000',N'0771277899';
exec update_client N'1',N'Mihai',N'Petru',N'12/12/2000',N'0771277899';
exec delete_client N'1';
exec read_client;

drop index IX_Servicii_Descriere on Servicii;
drop index IX_Factura_Servicii_Pret on FacturaServicii;
drop index IX_Client_Nume on Clienti;
create nonclustered index IX_Servicii on Servicii(IdServiciu,IdAntrenor,Descriere,Pret,IdPachet);
create nonclustered index IX_Factura_Servicii on FacturaServicii(Pret);
create nonclustered index IX_Client on Clienti(Nume,Prenume,DataNasterii,Telefon);

go
create view vw_Servici
as
select * from Servicii;

go
create view vw_FacturaServici
as
select * from FacturaServicii;

go
create view vw_Clienti
as
select * from Clienti;

select * from vw_Servici;
select Pret from vw_FacturaServici;
select * from vw_Clienti;