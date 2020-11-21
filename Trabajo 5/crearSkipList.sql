DECLARE
  TYPE tipo_int IS TABLE OF NUMBER
  INDEX BY BINARY_INTEGER;
  codigos tipo_int;   
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  indice NUMBER(30);
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

  FOR i in codigos.FIRST .. codigos.LAST
  LOOP

    dbms_output.put_line(codigos(i));

  END LOOP;

END;
/