DROP TYPE tipo_infoEmp FORCE;
CREATE OR REPLACE TYPE tipo_infoEmp AS OBJECT( 
  id number(10),
  nombre varchar2(20)
  );
/

DROP TYPE tabla_infoEmp FORCE;
CREATE OR REPLACE TYPE
tabla_infoEmp AS TABLE OF tipo_infoEmp;
/

DROP TYPE tipo_indexempskip FORCE;
CREATE OR REPLACE TYPE tipo_indexempskip AS 
OBJECT(numnodo number(30),
  infoEmp tabla_infoEmp,
  depE VARCHAR2(20),
  ptrback number(30),
  puntero1 number(30),
  puntero2 number(30),
  puntero3 number(30),
  puntero4 number(30),
  puntero5 number(30),
  puntero6 number(30),
  puntero7 number(30),
  puntero8 number(30),
  puntero9 number(30),
  puntero10 number(30),
  puntero11 number(30),
  puntero12 number(30),
  puntero13 number(30),
  puntero14 number(30),
  puntero15 number(30),
  puntero16 number(30));
/

DROP TABLE indexempskip FORCE;
CREATE TABLE indexempskip OF tipo_indexempskip
(PRIMARY KEY(numnodo)) 
NESTED TABLE infoEmp STORE AS store_indexempskip;


INSERT INTO indexempskip VALUES(1,
            tabla_infoEmp(tipo_infoEmp(10,'10'),
                         tipo_infoEmp(13,'13'),
                         tipo_infoEmp(17,'17'))
                         );
                         
INSERT INTO TABLE(SELECT infoEmp FROM indexempskip WHERE depE = 1)
VALUES(tipo_infoEmp(11,'a'));

UPDATE TABLE(SELECT * FROM indexempskip WHERE depE = 1) SET v = 10 WHERE x = 10 AND y = 10;

SELECT depE, t2.* FROM indexempskip t, TABLE(t.infoEmp) t2 Where depE = 1;