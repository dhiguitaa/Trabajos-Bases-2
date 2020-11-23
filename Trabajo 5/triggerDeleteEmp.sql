CREATE OR REPLACE TRIGGER delete_emp
for delete ON empleado 
COMPOUND TRIGGER
  TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
  TYPE tipo_var IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
  type tipo_anidado is table of tipo_var index by BINARY_INTEGER;
  codigosEmpViejos tipo_anidado; 
  nombresEmpViejos tipo_anidado; 
  codigosViejos tipo_int;   
  cantidadPunterosViejos tipo_int;  
  codigosEmp tipo_anidado; 
  nombresEmp tipo_anidado; 
  codigos tipo_int;    
  cantidadPunteros tipo_int;   
  punterosPorNodo tipo_int;  
  nombresEmpNew tipo_var;  
  codigosEmpNew tipo_int;  
  nuevaAltura number(8);
  existeDep BOOLEAN;
  indice number(30);
  indiceNodo number(30);
  maxPtrs NUMBER(10);
  aux NUMBER(10);
  swapped BOOLEAN;
  tmp     VARCHAR2(10);
  new_dep number(30);
AFTER EACH ROW IS
BEGIN
      new_dep := :new.depE;
END AFTER EACH ROW;  

AFTER STATEMENT IS
BEGIN

    -- sacar informacion vieja
    indiceNodo := 1;
    FOR fila IN (SELECT * FROM indexempskip) LOOP
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

      if fila.depE is not null then
        cantidadPunterosViejos(fila.depE) := indice; -- no contiene la cabeza ni los null
        codigosViejos(indiceNodo) := fila.depE;
        indiceNodo := indiceNodo +1;
      end if;
      if fila.numnodo = 1 then
        maxPtrs := indice;
      end if;
    end loop;


    if codigosViejos.count != 0 then
    
      -- borramos los datos de la tabla index
      delete from indexempskip;
     
      -- crearslemp
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

      -- actualizar punteros correctos
      cantidadPunteros(1) := maxPtrs;
      for i in 2 .. codigos.count+1 LOOP
        cantidadPunteros(i) := cantidadPunterosViejos(codigos(i));
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

      -- insertar datos restantes

      for i in 2 .. codigos.last loop
        for j in 2 .. codigosEmp(i).last loop 
          INSERT INTO TABLE(SELECT infoEmp FROM indexempskip WHERE depE = codigos(i)) VALUES(tipo_infoEmp(codigosEmp(i)(j),nombresEmp(i)(j)));
        end loop;
      end loop;  
      

    end if;
      dbms_output.put_line('Dato actualizado');
END AFTER STATEMENT;
END delete_emp;
/