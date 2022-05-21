CREATE DATABASE S2224;
 GO
 USE S2224;
 CREATE TABLE Clienti (cod_c INT PRIMARY KEY IDENTITY, nume_client VARCHAR(100) );
 CREATE TABLE Comenzi (cod_com INT PRIMARY KEY IDENTITY, nr_com INT, cod_c INT FOREIGN KEY REFERENCES Clienti(cod_c), localitate_de_livrare VARCHAR(100), valoare_com INT ); INSERT INTO Clienti (nume_client) VALUES ('Bob'),('Tom'),('Jack'),('Jill'); INSERT INTO Comenzi (nr_com, cod_c, localitate_de_livrare, valoare_com) VALUES (121,1,'Sibiu',500),(122,1,'Sibiu',1000),(123,1,'Cluj-Napoca',1000), (124,2,'Cluj-Napoca',5000),(125,NULL,'Brasov',7000); SELECT * FROM Clienti; SELECT * FROM Comenzi; SELECT * FROM Clienti C INNER JOIN Comenzi CO ON C.cod_c=CO.cod_c; SELECT * FROM Clienti C LEFT JOIN Comenzi CO ON C.cod_c=CO.cod_c; SELECT * FROM Clienti C RIGHT JOIN Comenzi CO ON C.cod_c=CO.cod_c; SELECT * FROM Clienti C FULL JOIN Comenzi CO ON C.cod_c=CO.cod_c; SELECT * FROM Clienti C; --Numarul de comenzi pentru fiecare localitate SELECT localitate_de_livrare [localitate], COUNT(cod_com) [numar comenzi] FROM Comenzi GROUP BY localitate_de_livrare;
 --Numarul de comenzi si valoarea totala pentru fiecare localitate SELECT localitate_de_livrare [localitate], COUNT(cod_com) [numar comenzi], SUM(valoare_com) AS [valoare totala] FROM Comenzi GROUP BY localitate_de_livrare HAVING SUM(valoare_com)>5000; --Afisam numele clientilor care au comenzi--Varianta 1
SELECT nume_client FROM Clienti WHERE cod_cIN (SELECT cod_c FROM Comenzi);
--Varianta 2
SELECT DISTINCT C.nume_client FROM Clienti C INNER JOIN Comenzi COON C.cod_c=CO.cod_c;
--Varianta 3 SELECT C.nume_client FROM Clienti C WHERE EXISTS(SELECT * FROM Comenzi CO WHERE CO.cod_c=C.cod_c);