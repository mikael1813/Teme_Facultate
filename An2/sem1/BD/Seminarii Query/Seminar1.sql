

--Crearea unei baze da date
CREATE DATABASE GarajDeTractoare;

--Conectarea la baza de date GarajDeTractoare
USE GarajDeTractoare;

--Modificarea numelui bazei de date GarajDeTractoare
ALTER DATABASE GarajDeTractoare
MODIFY Name=TractoareDeGaraj;

--Stergerea bazei de date
USE master;
DROP DATABASE TractoareDeGaraj;

--Crearea bazei de date
CREATE DATABASE Restaurant224;

--Crearea unui tabel
USE Restaurant224
CREATE TABLE Angajati
(cod_a INT,
nume VARCHAR(100),
data_nasterii DATE,
salariu FLOAT,
functia VARCHAR(100)
);

--Adaugarea unei coloane
ALTER TABLE Angajati
ADD telefon VARCHAR(15);

--Schimbam tipul de date al unei coloane
ALTER TABLE Angajati
ALTER COLUMN salariu MONEY;

--Stergerea unei coloane
ALTER TABLE Angajati
DROP COLUMN data_nasterii;

--Stergerea unui table
DROP TABLE Angajati

--Constreangere NOT NULL
CREATE TABLE FeluriDeMancare
(cod_f INT NOT NULL,
denumire VARCHAR(100) NOT NULL,
pret FLOAT,
de_post BIT,
tara_de_origine VARCHAR(100),
timp_de_preparare TIME,
gramaj FLOAT NOT NULL
);

--Constrangere UNIQUE	
CREATE TABLE Ingrediente
(cod_i INT UNIQUE NOT NULL,
nume_ingredient VARCHAR(100) NOT NULL UNIQUE,
data_expirarii DATE,
tara_de_origine VARCHAR(100)
);

--Dupa crearea tabelului - constrangerea UNIQUE
ALTER TABLE Ingrediente
ADD CONSTRAINT uc_data_expirarii_tara_de_origine UNIQUE(data_expirarii,
tara_de_origine);

--Constrangerea PRIMARY KEY
CREATE TABLE Clienti
(cod_c INT PRIMARY KEY,
nume_client VARCHAR(100),
email VARCHAR(100)
);


CREATE TABLE Comenzi
(cod_com INT PRIMARY KEY,
nr_com INT,
cod_c INT FOREIGN KEY REFERENCES Clienti(cod_c) ON DELETE CASCADE ON UPDATE
SET NULL
);