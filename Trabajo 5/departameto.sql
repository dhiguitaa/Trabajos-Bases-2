DROP TABLE departamento;
CREATE TABLE departamento
(
codigoD NUMBER(8) PRIMARY KEY, nombreD VARCHAR2(20) NOT NULL, direccionD VARCHAR2(20) NOT NULL
);

insert into departamento values(1,'a','direccion a');
insert into departamento values(4,'d','direccion d');
insert into departamento values(2,'b','direccion b');
insert into departamento values(3,'c','direccion c');
insert into departamento values(6,'f','direccion f');
insert into departamento values(5,'e','direccion e');