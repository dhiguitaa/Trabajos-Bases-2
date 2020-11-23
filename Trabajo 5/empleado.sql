drop table empleado;
CREATE TABLE empleado(
codigoE NUMBER(10) PRIMARY KEY, nombreE VARCHAR2(20) NOT NULL, depE NUMBER(8) NOT NULL 
);


insert into empleado values(10,'a',1);
insert into empleado values(40,'d',4);
insert into empleado values(20,'b',2);
insert into empleado values(60,'f',6);
insert into empleado values(50,'e',5);
insert into empleado values(90,'re',9);