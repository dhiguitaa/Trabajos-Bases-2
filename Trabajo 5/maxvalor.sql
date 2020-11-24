create or replace function maxvalor(ss in number,se in number,l in number, r in number, node in number) return number as
valor number;
mid number; 
begin
  -- hojas
  if l <= ss and r >=se then
    select valornotion into valor from indexsegtree where id=node;
    dbms_output.put_line('valor: '||valor||' nodo: '||node);
    return valor;
  end if;

  if se < l or ss > r then
    select valornotion into valor from indexsegtree where id=node;
    dbms_output.put_line('valor: '||valor||' nodo: '||node);
    return -1;
  end if;
  -- resto nodos
  mid :=floor(ss+(se-ss)/2);
  valor := greatest(maxvalor(ss,mid,l,r,2*node),maxvalor(mid+1,se,l,r,2*node+1));
  dbms_output.put_line('valor res: '||valor);
  return valor;
end;
/