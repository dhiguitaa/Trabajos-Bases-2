drop table empleado;
CREATE TABLE empleado(
codigoE NUMBER(10) PRIMARY KEY, nombreE VARCHAR2(20) NOT NULL, depE NUMBER(8) NOT NULL 
);


insert into empleado values(10,'a',1);
insert into empleado values(11,'a',1);
insert into empleado values(12,'a',1);
insert into empleado values(13,'a',1);
insert into empleado values(40,'d',4);
insert into empleado values(41,'d',4);
insert into empleado values(42,'d',4);
insert into empleado values(20,'b',2);
insert into empleado values(21,'b',2);
insert into empleado values(22,'b',2);
insert into empleado values(60,'f',6);
insert into empleado values(50,'e',5);
insert into empleado values(90,'re',9);

insert into empleado values(900,'hola',90);

UPDATE empleado SET depE = 90 WHERE codigoE=90;

delete from empleado where depE = 90 and;