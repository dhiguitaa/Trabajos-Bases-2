
CREATE OR REPLACE PROCEDURE CrearSL(maxPtrs IN NUMBER) IS
  TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
  TYPE tipo_var IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
  codigos tipo_int;   
  punterosPorNodo tipo_int;   
  cantidadPunteros tipo_int;  
  nombresDep tipo_var; 
  direccionesDep tipo_var; 
  nom VARCHAR2(20);
  dir VARCHAR2(20);
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  indice NUMBER(30);
  aux NUMBER(10);
BEGIN

  if maxPtrs = 1 or maxPtrs = 2 or maxPtrs = 4 or maxPtrs = 8 or maxPtrs = 16 then
    indice :=2;
    FOR fila IN (SELECT codigoD FROM departamento)
    LOOP
      codigos(indice) := fila.codigoD;
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
    
    -- llenando nombres y direcciones
    for i in codigos.FIRST .. codigos.LAST loop
      SELECT nombreD, direccionD into nom, dir FROM departamento where codigoD = codigos(i);

      nombresDep(i) := nom;
      direccionesDep(i) := dir;
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
        insert into indexdepskip values(i,null,null,null,null,punterosPorNodo(1),punterosPorNodo(2),punterosPorNodo(3),punterosPorNodo(4),punterosPorNodo(5),punterosPorNodo(6),punterosPorNodo(7),punterosPorNodo(8),punterosPorNodo(9),punterosPorNodo(10),punterosPorNodo(11),punterosPorNodo(12),punterosPorNodo(13),punterosPorNodo(14),punterosPorNodo(15),punterosPorNodo(16));
      else
        insert into indexdepskip values(i,codigos(i),nombresDep(i),direccionesDep(i),i-1,punterosPorNodo(1),punterosPorNodo(2),punterosPorNodo(3),punterosPorNodo(4),punterosPorNodo(5),punterosPorNodo(6),punterosPorNodo(7),punterosPorNodo(8),punterosPorNodo(9),punterosPorNodo(10),punterosPorNodo(11),punterosPorNodo(12),punterosPorNodo(13),punterosPorNodo(14),punterosPorNodo(15),punterosPorNodo(16));  
      end if;
      punterosPorNodo.delete();
    END LOOP;
    aux := cantidadPunteros.LAST;
    insert into indexdepskip values(aux,null,null,null,aux-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1); 
    dbms_output.put_line('Skip list creada');
  else
    dbms_output.put_line('El maxPtrs tiene que ser potencia de 2 (1,2,4,8,16)');

  end if;
END;
/