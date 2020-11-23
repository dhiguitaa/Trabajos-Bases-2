CREATE OR REPLACE TRIGGER insert_dpto
before INSERT ON departamento 
FOR EACH ROW 
declare
  TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
  TYPE tipo_var IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
  codigosViejos tipo_int;   
  punterosPorNodo tipo_int;   
  cantidadPunterosViejos tipo_int;  
  nombresDepViejos tipo_var; 
  direccionesDepViejos tipo_var; 
  codigos tipo_int;    
  cantidadPunteros tipo_int;  
  nombresDep tipo_var; 
  direccionesDep tipo_var; 
  indice number(30);
  aux NUMBER(10);
BEGIN

    -- sacar informacion vieja
    FOR fila IN (SELECT * FROM indexdepskip) LOOP
      indice := 0;    

      IF (fila.puntero1 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero2 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero3 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero4 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero5 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero6 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero7 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero8 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero9 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero10 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero11 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero12 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero13 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero14 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero15 != -1) THEN
        indice := indice + 1;
      END IF;

      IF (fila.puntero16 != -1) THEN
        indice := indice + 1;
      END IF;

      cantidadPunterosViejos(fila.numnodo) := indice;
      nombresDepViejos(fila.numnodo) := fila.nombreD;
      direccionesDepViejos(fila.numnodo) := fila.direccionD;
      codigosViejos(fila.numnodo) := fila.codigoD;
    end loop;

    cantidadPunterosViejos(cantidadPunterosViejos.last) := cantidadPunterosViejos(1);
    -- borramos los datos de la tabla index
    delete from indexdepskip;
    --insetar nuevo
    indice := 0;

    for i in 2 .. cantidadPunterosViejos.last-1 loop
      if codigosViejos(i) > :new.codigoD THEN
        indice := i;  
        exit;
      end if;
    end loop;
    if indice = 0 then
      indice := cantidadPunterosViejos.last;
    end if;

    dbms_output.put_line(indice);
    for i in 1 .. indice-1 loop
      codigos(i) := codigosViejos(i);
      nombresDep(i) := nombresDepViejos(i);
      direccionesDep(i) := direccionesDepViejos(i);
      cantidadPunteros(i) := cantidadPunterosViejos(i);
    end loop; 

    codigos(indice) := :new.codigoD;
    nombresDep(indice) := :new.nombreD;
    direccionesDep(indice) := :new.direccionD;
    cantidadPunteros(indice) := round(DBMS_RANDOM.VALUE(1,cantidadPunteros(1)));
    
    for i in indice .. cantidadPunterosViejos.last loop
      codigos(i+1) := codigosViejos(i);
      nombresDep(i+1) := nombresDepViejos(i);
      direccionesDep(i+1) := direccionesDepViejos(i);
      cantidadPunteros(i+1) := cantidadPunterosViejos(i);
    end loop;

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
    dbms_output.put_line('Nuevo dato insertado');
  

END;
/