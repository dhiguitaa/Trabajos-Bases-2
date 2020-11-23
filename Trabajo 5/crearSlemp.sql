
CREATE OR REPLACE PROCEDURE CrearSLemp(maxPtrs IN NUMBER) IS
  TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
  TYPE tipo_var IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
  type tipo_anidado is table of tipo_var index by BINARY_INTEGER;
  codigos tipo_int;   
  punterosPorNodo tipo_int;   
  cantidadPunteros tipo_int;  
  codigosEmp tipo_anidado; 
  nombresEmp tipo_anidado; 
  nom VARCHAR2(20);
  dir VARCHAR2(20);
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  indice NUMBER(30);
  aux NUMBER(10);
BEGIN
  if maxPtrs = 1 or maxPtrs = 2 or maxPtrs = 4 or maxPtrs = 8 or maxPtrs = 16 then
    indice :=2;
    FOR fila IN (SELECT DISTINCT depE FROM empleado)
    LOOP
      codigos(indice) := fila.depE;
      indice := indice+1;
    END LOOP;  

  -- ordena los codigoD
    LOOP
    swapped := false;
    FOR i IN 3 .. codigos.LAST
    LOOP

      IF codigos(i-1) > codigos(i)
      THEN
        tmp := codigos(i);
        codigos(i) := codigos(i-1);
        codigos(i-1) := tmp;
        swapped := true;
        END IF;
    END LOOP;

    EXIT WHEN NOT swapped;

    END LOOP; 
    
    -- llenando informacion de empleado
    for i in codigos.FIRST .. codigos.LAST loop
      indice := 1;
      for fila in (select * from empleado where depE=codigos(i)) loop
        nombresEmp(i)(indice) := fila.nombreE;
        codigosEmp(i)(indice) := fila.codigoE;
        indice := indice+1;
      end loop;
    end loop;

    -- random
    cantidadPunteros(1) := maxPtrs;
    for i in 2 .. codigos.count+1 LOOP
      cantidadPunteros(i) := round(DBMS_RANDOM.VALUE(1,maxPtrs));
    END LOOP;
    cantidadPunteros(codigos.count+2) := maxPtrs;

    -- flechas
    for i in 1 .. cantidadPunteros.LAST-1 LOOP -- NODOS
      for j in 1.. cantidadPunteros(i) loop -- bloques
        for k in i+1.. cantidadPunteros.LAST loop -- nodos posteriores
          if j <= cantidadPunteros(k) THEN
            punterosPorNodo(j) := k;
            EXIT;  
          end if;
        end loop;
      end loop;
      if punterosPorNodo.LAST <16 then
        for h in punterosPorNodo.LAST+1 .. 16 loop
          punterosPorNodo(h) := -1;
        end loop;
      end if;
      if i = 1 then
        insert into indexempskip values(i,null,null,null,punterosPorNodo(1),punterosPorNodo(2),punterosPorNodo(3),punterosPorNodo(4),punterosPorNodo(5),punterosPorNodo(6),punterosPorNodo(7),punterosPorNodo(8),punterosPorNodo(9),punterosPorNodo(10),punterosPorNodo(11),punterosPorNodo(12),punterosPorNodo(13),punterosPorNodo(14),punterosPorNodo(15),punterosPorNodo(16));
      else
        insert into indexempskip values(i,tabla_infoEmp(tipo_infoEmp(codigosEmp(i)(1),nombresEmp(i)(1))),codigos(i),i-1,punterosPorNodo(1),punterosPorNodo(2),punterosPorNodo(3),punterosPorNodo(4),punterosPorNodo(5),punterosPorNodo(6),punterosPorNodo(7),punterosPorNodo(8),punterosPorNodo(9),punterosPorNodo(10),punterosPorNodo(11),punterosPorNodo(12),punterosPorNodo(13),punterosPorNodo(14),punterosPorNodo(15),punterosPorNodo(16));  
      end if;
      punterosPorNodo.delete();
    END LOOP;
    aux := cantidadPunteros.LAST;
    insert into indexempskip values(aux,null,null,aux-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1); 
    dbms_output.put_line('Skip list creada');
  else
    dbms_output.put_line('El maxPtrs tiene que ser potencia de 2 (1,2,4,8,16)');

  end if;
END;
/