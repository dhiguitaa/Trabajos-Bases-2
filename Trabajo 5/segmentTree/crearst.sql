CREATE OR REPLACE PROCEDURE crearst IS
indice number;
suma number;
total number;
fac number;
arr arry;
begin
  indice :=1;
  arr := arry();
  select count(valornotion) into total from tash_sultana;
  arr.extend(total);
  for i in (select * from tash_sultana order by valornotion) loop
    arr(indice) := i.valornotion;
    indice := indice +1;
  end loop;
  suma := arr.count;
  dbms_output.put_line(segmentTree(arr,1,suma,1));

end;
/