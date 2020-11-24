drop table indexsegtree;
create table indexsegtree(
  id number(30),
  inicio number(30),
  fin number(30),
  valornotion number(30),
  izq number(30),
  der number(30)
);
drop table tash_sultana;
CREATE TABLE tash_sultana(
  consecutivo NUMBER(8) PRIMARY KEY, 
  valornotion NUMBER(8) NOT NULL
);

insert into tash_sultana values(0,10);
insert into tash_sultana values(1,30);
insert into tash_sultana values(4,10);
insert into tash_sultana values(3,20);
insert into tash_sultana values(2,66);