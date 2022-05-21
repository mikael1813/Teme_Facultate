Use [Ski Resort];

/*- modifica tipul unei coloane*/
Go
CREATE PROCEDURE modificaCamp
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		ALTER COLUMN IdCumparator int;
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE modificaCamp;*/


/*- undo la modifica tipul unei coloane*/
Go
CREATE PROCEDURE undoModificaCamp
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		ALTER COLUMN IdCumparator varchar(50);
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE undoModificaCamp;*/


/*- adauga o costrângere de “valoare implicită” pentru un câmp;*/
Go
CREATE PROCEDURE adaugaConstraint
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		IF NOT EXISTS(SELECT * FROM sys.default_constraints WHERE name='valoareDefault')
			BEGIN
			ALTER TABLE Carti
			ADD CONSTRAINT valoareDefault DEFAULT 'Nu are autor' FOR Autor;
		END
		ELSE
			BEGIN
				PRINT('Exista deja valoarea implicita');
			END
	END
END

/*DROP PROCEDURE adaugaConstraint;*/


/*- undo la adauga o costrângere de “valoare implicită” pentru un câmp;*/
GO
CREATE PROCEDURE undoAdaugaConstraint
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		IF EXISTS(SELECT * FROM sys.default_constraints WHERE name='valoareDefault')
		BEGIN
			ALTER TABLE Carti
			DROP CONSTRAINT valoareDefault;
		END
		ELSE
			BEGIN
				PRINT('Nu exista valoare implicita');
			END
	END
END

/*DROP PROCEDURE undoAdaugaConstraint;*/


/*- creeaza o tabelă*/
GO
CREATE PROCEDURE creareTabel
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		CREATE TABLE Carti(
		ID int primary key,
		Autor varchar(100)
		);
	END
	ELSE
		BEGIN
			PRINT('Exista deja tabela');
		END
END

/*DROP PROCEDURE creareTabel;*/

/*- şterge o tabelă;*/
GO
CREATE PROCEDURE undoCreareTabel
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		DROP TABLE Carti;
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE undoCreareTabel;*/


/*adăuga un câmp nou*/
GO
CREATE PROCEDURE adaugaCamp
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		ADD IdCumparator varchar(50);
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE adaugaCamp;*/


/*undo la adăuga un câmp nou;*/
GO
CREATE PROCEDURE undoAdaugaCamp
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		DROP COLUMN IdCumparator;
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE undoAdaugaCamp;*/


/*creea o constrângere de cheie străină.*/
GO
CREATE PROCEDURE creareCheieStraina
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		ADD CONSTRAINT cheieStraina FOREIGN KEY (IdCumparator) REFERENCES Clienti(IdClient);
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END
END

/*DROP PROCEDURE creareCheieStraina;*/



/*şterge o constrângere de cheie străină.*/
GO
CREATE PROCEDURE undoCreareCheieStraina
AS
BEGIN
	IF EXISTS (SELECT * FROM sys.tables WHERE name='Carti')
	BEGIN
		ALTER TABLE Carti
		DROP CONSTRAINT cheieStraina;
	END
	ELSE
		BEGIN
			PRINT('Nu exista tabela');
		END

END

/*DROP PROCEDURE undoCreareCheieStraina;*/




/*Procedura face trecerea de la versiunea curenta la versiunea indicata ca parametru*/
GO 
CREATE PROCEDURE goToVersion(@versiune INT)
AS
BEGIN
	/*Se va arunca eroare daca versiunea data ca parametru nu este corecta*/
	IF @versiune > 5 OR @versiune < 0
	BEGIN
		THROW 51000, 'Versiunile bazei de date sunt intre [0,5]', 1; 
	END

	DECLARE @actual INT=0;
	/*Se obtine versiunea actuala*/
	SELECT @actual=versiune from Versiune;
	PRINT('Versiunea actuala este: ');
	PRINT(@actual);	
	/*Daca se trece de la o versiune mica la una mai mare*/
	IF @actual<@versiune
	BEGIN
		WHILE(@actual!=@versiune)
		BEGIN
			SET @actual = @actual+1;
			IF @actual=1
			BEGIN
				EXEC creareTabel;
				PRINT('Creare tabela noua');
			END
			IF @actual=2
			BEGIN
				EXEC adaugaConstraint;
				PRINT('Adaugare valoare implicita');
			END
			IF @actual=3
			BEGIN
				EXEC adaugaCamp;
				PRINT('Adaugare camp nou');
			END
			IF @actual=4
			BEGIN
				EXEC modificaCamp;
				PRINT('Modifica tipul unui camp');
			END
			IF @actual=5
			BEGIN
				EXEC creareCheieStraina;
				PRINT('Crearea unei chei straine');
			END
		END
		/*Se actualizeaza valoare versiunii*/
		UPDATE Versiune
		SET versiune = @versiune;
	END
	IF @actual>@versiune
	BEGIN
		WHILE(@actual!=@versiune)
		BEGIN
			IF @actual=1
			BEGIN
				EXEC undoCreareTabel;
				PRINT('Stergere tabela');
			END
			IF @actual=2
			BEGIN
				EXEC undoAdaugaConstraint;
				PRINT('Eliminare valoare implicita');
			END
			IF @actual=3
			BEGIN
				EXEC undoAdaugaCamp;
				PRINT('Stergere camp');
			END
			IF @actual=4
			BEGIN
				EXEC undoModificaCamp
				PRINT('Elimina modificarea tipului campului');
			END
			IF @actual=5
			BEGIN
				EXEC undoCreareCheieStraina;
				PRINT('Stergerea cheiei straine');
			END
			SET @actual = @actual-1;
		END
		/*Se actualizeaza valoare versiunii*/
		UPDATE Versiune
		SET versiune = @versiune;
	END
END

/*DROP PROCEDURE goToVersion;*/

EXEC goToVersion 4;