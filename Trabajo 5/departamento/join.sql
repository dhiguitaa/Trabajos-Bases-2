CREATE OR REPLACE PROCEDURE Join IS
TYPE tipo_int IS TABLE OF Number(30) INDEX BY BINARY_INTEGER;
TYPE tipo_nodos IS TABLE OF tipo_int INDEX BY BINARY_INTEGER;

nodos tipo_nodos;
indice number(30);
nodoInicio number(30);
puntero number(30);
nodoFin number(30);
nom varchar2(20);
dir varchar2(20);
begin
  -- sacar el max de pisos
  FOR fila IN (SELECT * FROM indexdepskip) LOOP
    indice := 1;    
    nodos(fila.numnodo)(0) := fila.codigoD;

    IF (fila.puntero1 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero1; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero2 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero2; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero3 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero3; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero4 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero4; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero5 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero5; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero6 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero6; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero7 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero7; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero8 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero8; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero9 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero9; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero10 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero10; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero11 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero11; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero12 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero12; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero13 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero13; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero14 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero14; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero15 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero15; 
      indice := indice + 1;
    END IF;

    IF (fila.puntero16 != -1) THEN
      nodos(fila.numnodo)(indice) := fila.puntero16; 
      indice := indice + 1;
    END IF;
  end loop;

  for filaE in (select * from empleado) loop
    nodoInicio := 1;
    puntero := nodos(1).last;

    loop
      -- existe
      nodoFin := nodos(nodoInicio)(puntero);
      
      if nodos(nodoFin)(0) = filaE.depE then
        -- encontrado
        select nombreD,direccionD into nom,dir from indexdepskip where numnodo=nodoFin;
        dbms_output.put_line('('||filaE.codigoE||',' || filaE.nombreE|| ',' ||filaE.depE||','||nodos(nodoFin)(0)||','||nom||','||dir||')');
        exit;
      elsif nodos(nodoFin)(0) < filaE.depE then
        nodoInicio := nodoFin;
      else
        puntero := puntero-1;
      end if;

      -- no existe
      if puntero = 0 then
        exit;
      end if;

    end loop;  
  end loop;
end;
/