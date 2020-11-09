drop table usuario;
create table usuario(
  nombre_usuario varchar(20) primary key,
  contrasena varchar(100),
  direccion varchar(4000)
);

insert into usuario values('admin','admin','1JhQHubeM55EkULmaKP8FWmCN6EKgZYtoDkwTi');