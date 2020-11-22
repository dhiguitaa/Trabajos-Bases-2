declare
TYPE tipo_int IS TABLE OF Number(30) INDEX BY BINARY_INTEGER;
TYPE tipo_nodos IS TABLE OF tipo_int INDEX BY BINARY_INTEGER;
punteros tipo_int;
nodos tipo_nodos;
indice number(30);
nodoInicio number(30);
numBusqueda number(30);
puntero number(30);
nodoFin number(30);
nom varchar2(20);
dir varchar2(20);
begin
  numBusqueda := 3;
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

  nodoInicio := 1;
  puntero := nodos(1).last;
  loop
    if nodos(nodoInicio)(0) is null then
      dbms_output.put_line('numnodo inicio: '||nodoInicio||', codigoD: No tiene');
    else
      dbms_output.put_line('numnodo inicio: '||nodoInicio||', codigoD: '||nodos(nodoInicio)(0));
    end if;
    -- existe
    nodoFin := nodos(nodoInicio)(puntero);
    if nodos(nodoFin)(0) is null then
      dbms_output.put_line('numnodo fin: '||nodoFin||', codigoD: No tiene');
    else
      dbms_output.put_line('numnodo fin: '||nodoFin||', codigoD: '||nodos(nodoFin)(0));
    end if;
    
    if nodos(nodoFin)(0) = numBusqueda then
      dbms_output.put_line('Encontrado!!: ');
      select nombreD,direccionD into nom,dir from indexdepskip where numnodo=nodoFin;
      dbms_output.put_line('nombre: '||nom);
      dbms_output.put_line('direccion: '||dir);
      exit;
    elsif nodos(nodoFin)(0) < numBusqueda then
      nodoInicio := nodoFin;
      dbms_output.put_line('Como el codigoD del nodo fin es menor que el codigoD buscado entonces se actualiza nodo inicio por nodo fin');
    else
      puntero := puntero-1;
      dbms_output.put_line('Como el codigoD del nodo fin NO es menor que el codigoD buscado entonces NO se actualiza nodo inicio por nodo fin');
    end if;

    -- no existe
    if puntero = 0 then
      dbms_output.put_line('No encontrado');
      exit;
    end if;
    dbms_output.put_line('--------------');

  end loop;  
end;
/