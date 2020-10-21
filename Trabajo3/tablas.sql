drop table venta;
drop table sucursal;
drop table ciudad;
drop table producto;
drop table vendedor;
drop table dpto;
drop table pais;
drop table marca;
drop table gremio;

CREATE TABLE pais(
nombre varchar(50) PRIMARY KEY,
moneda varchar(50) NOT NULL
);
CREATE TABLE marca(
nombre varchar(50) PRIMARY KEY,
descripcion varchar(50) NOT NULL
);
CREATE TABLE gremio(
codigo NUMBER(30) PRIMARY KEY,
nombre varchar(50) NOT NULL
);

CREATE TABLE dpto(
codigo NUMBER(30) PRIMARY KEY,
nombrePais varchar(50) REFERENCES pais NOT NULL,
nombre varchar(50) NOT NULL
);
CREATE TABLE producto(
codbarras NUMBER(30) PRIMARY KEY,
nombreMarca varchar(50) REFERENCES marca NOT NULL,
nombre varchar(50) NOT NULL,
tipo varchar(50) NOT NULL
);
CREATE TABLE vendedor(
codigo NUMBER(30) PRIMARY KEY,
codigoGremio number(30) REFERENCES gremio NOT NULL,
nombre varchar(50) NOT NULL,
salario number(30) NOT NULL
);

CREATE TABLE ciudad(
codigo NUMBER(30) PRIMARY KEY,
codigoDpto number(30) REFERENCES dpto NOT NULL,
nombre varchar(50) NOT NULL,
poblacion number(30) NOT NULL
);
CREATE TABLE sucursal(
codigo NUMBER(30) PRIMARY KEY,
codigoCiudad number(30) REFERENCES ciudad NOT NULL,
nombre varchar(50) NOT NULL,
direccion varchar(50) NOT NULL
);
CREATE TABLE venta(
codigo NUMBER(30) PRIMARY KEY,
codigoVendedor number(30) REFERENCES vendedor NOT NULL,
codbarrasProducto number(30) REFERENCES producto NOT NULL,
codigoSucursal number(30) REFERENCES sucursal NOT NULL,
valor number(30) NOT NULL
);

-- gremio: 1-100
-- vendedor:101-200
-- venta: 201-300
-- sucursal: 301-400
-- ciudad: 401-500
-- dpto: 501-600
INSERT INTO pais VALUES('colombia','cop');
INSERT INTO pais VALUES('ecuador','dolar');

INSERT INTO dpto VALUES(501,'colombia','antioquia');
INSERT INTO dpto VALUES(502,'ecuador','quito');

INSERT INTO ciudad VALUES(401,501,'medellin',3000000);
INSERT INTO ciudad VALUES(402,501,'entrerrios',500000);
INSERT INTO ciudad VALUES(403,502,'quito',500000);
INSERT INTO ciudad VALUES(404,502,'guayaquil',500000);
INSERT INTO ciudad VALUES(405,502,'entrerrios',500000);

INSERT INTO sucursal VALUES(301,401,'premium plaza','cra 52 #111-23');
INSERT INTO sucursal VALUES(302,402,'tesoro','parque principal');
INSERT INTO sucursal VALUES(303,403,'centro quito','calle la paz');
INSERT INTO sucursal VALUES(304,404,'centro guayaquil','calle la esperanza');
INSERT INTO sucursal VALUES(305,405,'centro entrerios-quito','calle quito');
INSERT INTO sucursal VALUES(306,401,'tesoro','en la pm');

INSERT INTO marca VALUES('zara','marca de ropa española cara');

INSERT INTO producto VALUES(1000001,'zara','falda','corta');
INSERT INTO producto VALUES(1000002,'zara','falda','larga');

INSERT INTO gremio VALUES(1,'ropa');

INSERT INTO vendedor VALUES(101,1,'carlos',870803);
INSERT INTO venta VALUES(201,101,1000001,301,300000);
INSERT INTO venta VALUES(202,101,1000001,302,200000);
INSERT INTO venta VALUES(203,101,1000001,303,150000);
INSERT INTO venta VALUES(204,101,1000001,304,150000);
INSERT INTO venta VALUES(205,101,1000001,304,100000);
INSERT INTO venta VALUES(206,101,1000001,305,400000);
INSERT INTO venta VALUES(207,101,1000002,305,100000);

commit;

-- todo
SELECT pais.nombre as pais, dpto.nombre as dpto, ciudad.nombre as ciudad, sucursal.nombre as sucursal, venta.valor as ventas
FROM ((((pais
full outer JOIN dpto ON pais.nombre=dpto.nombrePais)
full outer join ciudad on dpto.codigo=ciudad.codigoDpto)
full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)
full outer join venta on sucursal.codigo=venta.codigoSucursal); 
-- pais,depto,cuidad,sursal
SELECT sucursal.codigo as sucursal, sum(venta.valor) as ventas
FROM ((((pais
full outer JOIN dpto ON pais.nombre=dpto.nombrePais)
full outer join ciudad on dpto.codigo=ciudad.codigoDpto)
full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)
full outer join venta on sucursal.codigo=venta.codigoSucursal)
group by sucursal.codigo; 
-- pais, depto, cuidad
SELECT ciudad.codigo as ciudad, sum(venta.valor) as ventas
FROM ((((pais
full outer JOIN dpto ON pais.nombre=dpto.nombrePais)
full outer join ciudad on dpto.codigo=ciudad.codigoDpto)
full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)
full outer join venta on sucursal.codigo=venta.codigoSucursal) 
group by ciudad.codigo;
-- pais, depto
SELECT dpto.codigo as dpto,sum(venta.valor) as ventas
FROM ((((pais
full outer JOIN dpto ON pais.nombre=dpto.nombrePais)
full outer join ciudad on dpto.codigo=ciudad.codigoDpto)
full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)
full outer join venta on sucursal.codigo=venta.codigoSucursal) 
group by dpto.codigo;
-- pais
SELECT pais.nombre as pais,sum(venta.valor) as ventas
FROM ((((pais
full outer JOIN dpto ON pais.nombre=dpto.nombrePais)
full outer join ciudad on dpto.codigo=ciudad.codigoDpto)
full outer join sucursal on ciudad.codigo=sucursal.codigoCiudad)
full outer join venta on sucursal.codigo=venta.codigoSucursal) 
group by pais.nombre;
-- gremio, vendedor
SELECT vendedor.codigo as vendedor, sum(venta.valor) as ventas
FROM ((gremio
full outer JOIN vendedor ON gremio.codigo=vendedor.codigoGremio)
full outer join venta on vendedor.codigo=venta.codigoVendedor)
group by vendedor.codigo;
-- gremio
SELECT gremio.codigo as gremio, sum(venta.valor) as ventas
FROM ((gremio
full outer JOIN vendedor ON gremio.codigo=vendedor.codigoGremio)
full outer join venta on vendedor.codigo=venta.codigoVendedor)
group by gremio.codigo;

-- marca, producto
SELECT marca.nombre as marca, producto.codbarras as producto, sum(venta.valor) as ventas FROM ((marca full outer JOIN producto ON marca.nombre=producto.nombreMarca)
full outer join venta on producto.codbarras=venta.codbarrasProducto)
group by marca.nombre, producto.codbarras;
-- marca
SELECT marca.nombre as marca, sum(venta.valor) as ventas
FROM ((marca
full outer JOIN producto ON marca.nombre=producto.nombreMarca)
full outer join venta on producto.codbarras=venta.codbarrasProducto)
group by marca.nombre;

INSERT INTO pais VALUES('colombia','cop');
INSERT INTO pais VALUES('ecuador','dolar');

INSERT INTO dpto VALUES(501,'colombia','antioquia');
INSERT INTO dpto VALUES(502,'colombia','santander');
INSERT INTO dpto VALUES(503,'ecuador','chimborazo');
INSERT INTO dpto VALUES(504,'ecuador','imbabura');