
CREATE OR REPLACE PROCEDURE consultarmaxvalnotion(l in number, r in number) IS
indice number;
total number;
n number;
fac number;
begin
  select count(valornotion) into n from tash_sultana;
  if l<1 or r>n or l>r then 
    dbms_output.put_line('datos invalidos');
  else
    select count(valornotion) into total from indexsegtree;
    indice :=1;

    dbms_output.put_line('valor maximo: '||maxvalor(1,n,l,r,1));
  end if;
end;
/