create type arry is table of number(30);


create or replace function segmentTree(arr arry,ss in number,se in number,si in number) return number as
valor number;
mid number; 
begin
  -- hojas
  if ss = se then
    -- guardar
    insert into indexsegtree values(si,ss,se,arr(ss),0,0);
    valor := arr(ss);
    return valor;
  else
    -- resto nodos
    mid :=ceil(ss+(se-ss)/2);
    valor := greatest(segmentTree(arr,ss,mid ,si*2+1),segmentTree(arr,mid+1,se,si*2+2));
    insert into indexsegtree values(si,ss,se,valor,si*2+1,si*2+2);
    return valor;
  end if;
end;
/