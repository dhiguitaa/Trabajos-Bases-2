CREATE OR REPLACE PROCEDURE crearst IS

indice number;
suma number;
fac number;
arr arry;
begin
  indice :=1;
  arr := arry();
  arr.extend(5);
  for i in (select * from tash_sultana order by valornotion) loop
    arr(indice) := i.valornotion;
    indice := indice +1;
  end loop;
  suma := arr.count;
  dbms_output.put_line(segmentTree(arr,1,suma,1));
  SELECT segmentTree(arr,1,suma,1) into fac FROM dual;

end;
/