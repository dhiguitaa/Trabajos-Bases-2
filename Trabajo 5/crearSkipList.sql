DECLARE
  TYPE tipo_int IS TABLE OF NUMBER
  INDEX BY BINARY_INTEGER;
  codigos tipo_int;   
  cantidadPunteros tipo_int;   
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  indice NUMBER(30);
  maxPtrs NUMBER(10);
BEGIN
  indice :=0;
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

  for i in 1 .. codigos.LAST LOOP
    cantidadPunteros(i) := round(DBMS_RANDOM.VALUE(1,maxPtrs));
  END LOOP
  ; 

  FOR i in cantidadPunteros.FIRST .. cantidadPunteros.LAST
  LOOP
    dbms_output.put_line(cantidadPunteros(i));

  END LOOP;

  

END;
/