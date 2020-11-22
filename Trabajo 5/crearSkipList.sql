DECLARE
  TYPE tipo_int IS TABLE OF NUMBER INDEX BY BINARY_INTEGER;
  codigos tipo_int;   
  punterosPorNodo tipo_int;   
  cantidadPunteros tipo_int;   
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  indice NUMBER(30);
  maxPtrs NUMBER(10);
  aux NUMBER(10);
BEGIN
  indice :=1;
  FOR fila IN (SELECT codigoD FROM departamento)
  LOOP
    codigos(indice) := fila.codigoD;
    indice := indice+1;
  END LOOP;  

-- ordena los codigoD
  LOOP

   swapped := false;

   FOR i IN 2 .. codigos.LAST
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
  
  -- random
  maxPtrs := 8;
  cantidadPunteros(1) := maxPtrs;
  for i in 2 .. codigos.LAST+1 LOOP
    cantidadPunteros(i) := round(DBMS_RANDOM.VALUE(1,maxPtrs));
  END LOOP;
  cantidadPunteros(codigos.LAST+2) := maxPtrs;
  
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

  punterosPorNodo.delete();
  END LOOP;


  -- FOR i in cantidadPunteros.FIRST .. cantidadPunteros.LAST
  -- LOOP
  --   dbms_output.put_line(cantidadPunteros(i));

  -- END LOOP;

  

END;
/