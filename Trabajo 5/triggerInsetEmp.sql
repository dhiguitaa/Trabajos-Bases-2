CREATE OR REPLACE TRIGGER insert_emp
before INSERT ON empleado 
FOR EACH ROW 
declare
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
  existeDep BOOLEAN;
  indice number(30);
  aux NUMBER(10);
BEGIN

    -- verificar que el depE nuevo exista o no
    -- si no existe, hacer todo lo que sigue
    -- sacar informacion vieja
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

      cantidadPunterosViejos(fila.numnodo) := indice;
      codigosViejos(fila.numnodo) := fila.depE;

      aux := 1;
      if fila.numnodo = 1 then
        nombresEmpViejos(1)(1) := null; 
        codigosEmpViejos(1)(1) := null;
      end if;
      for info in (SELECT depE, t2.* FROM indexempskip t, TABLE(t.infoEmp) t2 Where numnodo = fila.numnodo) loop
        nombresEmpViejos(fila.numnodo)(aux) := info.nombre;
        codigosEmpViejos(fila.numnodo)(aux) := info.id;
        aux := aux+1;
      end loop;

      if :new.depE = fila.depE then
        existeDep := true;
      end if;
    end loop;


    if codigosViejos.count != 0 then
      
      -- existe el dep
      if existeDep then
        INSERT INTO TABLE(SELECT infoEmp FROM indexempskip WHERE depE = :new.depE) VALUES(tipo_infoEmp(:new.codigoE,:new.nombreE));
      -- no existe el dep
      else
        cantidadPunterosViejos(cantidadPunterosViejos.last) := cantidadPunterosViejos(1);
        nombresEmpViejos(cantidadPunterosViejos.last)(1) := null; 
        codigosEmpViejos(cantidadPunterosViejos.last)(1) := null;
        -- borramos los datos de la tabla index
        delete from indexempskip;
        --insetar nuevo
        indice := 0;

        for i in 2 .. cantidadPunterosViejos.last-1 loop
          if codigosViejos(i) > :new.depE THEN
            indice := i;  
            exit;
          end if;
        end loop;
        if indice = 0 then
          indice := cantidadPunterosViejos.last;
        end if;

        for i in 1 .. indice-1 loop
          codigos(i) := codigosViejos(i);
          cantidadPunteros(i) := cantidadPunterosViejos(i);
          aux := 1;
          for j in 1 .. nombresEmpViejos(i).last loop
            nombresEmp(i)(j) := nombresEmpViejos(i)(j);
            codigosEmp(i)(j) := codigosEmpViejos(i)(j);
            aux := aux+1;
          end loop;

        end loop; 

        codigos(indice) := :new.depE;
        cantidadPunteros(indice) := round(DBMS_RANDOM.VALUE(1,cantidadPunteros(1)));

        nombresEmp(indice)(1) := :new.nombreE;
        codigosEmp(indice)(1) := :new.codigoE;
        
        for i in indice .. cantidadPunterosViejos.last loop
          codigos(i+1) := codigosViejos(i);
          cantidadPunteros(i+1) := cantidadPunterosViejos(i);
          aux := 1;
          for j in 1 .. nombresEmpViejos(i).last loop
            nombresEmp(i+1)(j) := nombresEmpViejos(i)(j);
            codigosEmp(i+1)(j) := codigosEmpViejos(i)(j);
            aux := aux+1;
          end loop;
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

    end if;
      dbms_output.put_line('Nuevo dato insertado');

END;
/