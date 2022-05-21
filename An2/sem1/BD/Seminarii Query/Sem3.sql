CREATE DATABASE Seminar3;

USE Seminar3;

CREATE TABLE Biscuiti
(id INT PRIMARY KEY IDENTITY,
nume VARCHAR(50),
gramaj INT ,
umplut BIT,
umpllutura VARCHAR(50),
producator VARCHAR(50),
pret INT
);

GO
CREATE PROCEDURE adaugaBiscuiti
(@nume VARCHAR(50),
@gramaj INT,
@umplut BIT,
@umpllutura VARCHAR(50),
@producator VARCHAR(50),
@pret INT
)
AS
BEGIN
INSERT INTO Biscuiti
(nume,gramaj,umplut,umpllutura,producator,pret)
VALUES
(@nume,@gramaj,@umplut,@umpllutura,@producator,@pret);

PRINT 'S-a adaugat ' + @nume + ' in tabelul Biscuiti';
END


EXEC adaugaBiscuiti 'oreo',50,1,'vanilie','SRL',5;
EXEC adaugaBiscuiti 'pepito',50,0,'Nu are','Pepito',2;
EXEC adaugaBiscuiti 'eugenie',170,1,'rom','Eugenie',1;

SELECT * FROM Biscuiti



GO
CREATE PROCEDURE SelectBiscuiti
(@producator VARCHAR(50)
)
AS
BEGIN
SELECT nume,gramaj,umplut,umpllutura,producator,pret
FROM Biscuiti
WHERE producator=@producator;
END

EXEC SelectBiscuiti 'Eugenie';


GO
CREATE PROCEDURE numaraBiscuitiProducator
(@producator VARCHAR(50),
@numar INT OUTPUT
)
AS
BEGIN
SELECT @numar=COUNT(id) FROM Biscuiti
WHERE producator=@producator;
END


DECLARE @nr INT;
SET @nr=0;
EXEC numaraBiscuitiProducator 'SRL',@numar=@nr OUTPUT;
PRINT @nr;

GO ALTER PROCEDURE eroareGramaj(@producator VARCHAR(50)) AS BEGIN DECLARE @gramaj_total INT = 0; SELECT @gramaj_total = SUM(gramaj) FROM Biscuiti WHERE producator = @producator; IF (@gramaj_total > 80) RAISERROR ('Prea multi biscuiti de la acest producator!',10,1); ELSE PRINT @gramaj_total; END



EXEC eroareGramaj 'Oreo SRL';

 BEGIN TRY EXEC eroareGramaj 'SRL'; END TRY BEGIN CATCH
PRINT(ERROR_PROCEDURE())
END CATCH
