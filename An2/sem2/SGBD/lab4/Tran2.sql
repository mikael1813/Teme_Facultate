use [Ski Resort]

-- dirty read
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRAN;
SELECT *
FROM Antrenori
WHERE IdAntrenor = 1;
COMMIT TRAN;

-- dirty read evitare
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN TRAN;
SELECT *
FROM Antrenori
WHERE IdAntrenor = 1;
COMMIT TRAN;

-- non-repeatable read
UPDATE Antrenori SET Nume = 'Nume_Diferit' WHERE IdAntrenor = 1


-- phantom read
INSERT INTO Antrenori VALUES(5,5,'Kelu','Gras','07/05/2000','0999444555')

Delete from Antrenori where IdAntrenor=5;

--deadlock

BEGIN TRANSACTION
UPDATE Angajati Set Nume = 'Ghiocel from 2' WHERE IdAngajat = 1
WAITFOR DELAY '00:00:10.000'
UPDATE Antrenori Set Nume = 'Ion cel Voinic from 2' WHERE IdAntrenor = 1
COMMIT TRANSACTION

select * from Antrenori;

-- deadlock try-catch

BEGIN TRY  
     
	BEGIN TRANSACTION
	WAITFOR DELAY '00:00:08.000'
	UPDATE Angajati Set Nume = 'Ghiocel from 2' WHERE IdAngajat = 1
	WAITFOR DELAY '00:00:12.000'
	UPDATE Antrenori Set Nume = 'Ion cel Voinic from 2' WHERE IdAntrenor = 1
	COMMIT TRANSACTION 
END TRY  
BEGIN CATCH  
      PRINT N'S-A PRINS EROAREA';
END CATCH  
; 