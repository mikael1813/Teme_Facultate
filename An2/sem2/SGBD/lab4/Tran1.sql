use [Ski Resort]


-- dirty read
BEGIN TRAN;
UPDATE Antrenori
SET 
    Nume='Nume_Diferit'
WHERE IdAntrenor = 1;
WAITFOR DELAY '00:00:10';
ROLLBACK TRAN;
 
SELECT *
FROM Antrenori
WHERE IdAntrenor = 1;



-- non-repeatable read

BEGIN TRANSACTION
SELECT * FROM Antrenori WHERE IdAntrenor = 1

WAITFOR DELAY '00:00:15'
SELECT * FROM Antrenori WHERE IdAntrenor = 1
COMMIT TRANSACTION

-- non-repeatable read evitare
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRANSACTION
SELECT * FROM Antrenori WHERE IdAntrenor = 1

WAITFOR DELAY '00:00:15'
SELECT * FROM Antrenori WHERE IdAntrenor = 1
COMMIT TRANSACTION

-- phantom read

BEGIN TRAN
SELECT * FROM Antrenori

WAITFOR DELAY '00:00:10.000'
 
SELECT * FROM Antrenori
COMMIT TRAN

-- phantom read evitare

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
BEGIN TRAN
SELECT * FROM Antrenori

WAITFOR DELAY '00:00:10.000'
 
SELECT * FROM Antrenori
COMMIT TRAN


-- deadlock

BEGIN TRANSACTION
UPDATE Antrenori Set Nume = 'Ion cel Voinic from 1' WHERE IdAntrenor = 1
WAITFOR DELAY '00:00:10.000'
UPDATE Angajati Set Nume = 'Ghiocel from 1' WHERE IdAngajat = 1

COMMIT TRANSACTION

select * from Angajati;
select * from Antrenori;

-- deadlock try-catch

BEGIN TRY  
	BEGIN TRANSACTION
	WAITFOR DELAY '00:00:05.000'
	UPDATE Antrenori Set Nume = 'Ion cel Voinic from 1' WHERE IdAntrenor = 1
	WAITFOR DELAY '00:00:10.000'
	UPDATE Angajati Set Nume = 'Ghiocel from 1' WHERE IdAngajat = 1

COMMIT TRANSACTION
END TRY  
BEGIN CATCH  
      PRINT N'S-A PRINS EROAREA';
END CATCH  
; 