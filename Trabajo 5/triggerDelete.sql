CREATE OR REPLACE TRIGGER delete_dpto
before DELETE ON departamento 
FOR EACH ROW 
declare
  TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
  TYPE tipo_var IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
  punterosPorNodo tipo_int;   
  codigos tipo_int;    
  cantidadPunteros tipo_int;  
  nombresDep tipo_var; 
  direccionesDep tipo_var; 
  punterosNew number(8);
  indice number(30);
  aux NUMBER(10);
BEGIN
    aux := 1;
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

      if fila.codigoD !=  :old.codigoD or fila.codigoD is null then
        cantidadPunteros(aux) := indice;
        nombresDep(aux) := fila.nombreD;
        direccionesDep(aux) := fila.direccionD;
        codigos(aux) := fila.codigoD;
        aux := aux +1;
      end if;
    end loop;
    cantidadPunteros(cantidadPunteros.last) := cantidadPunteros(1);
    -- borramos los datos de la tabla index
    delete from indexdepskip;
    if codigos.count = 2 then
      codigos.delete();
    end if;
    if codigos.count != 0 then
      
      
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
      if aux > 1 then
        insert into indexdepskip values(aux,null,null,null,aux-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1); 
      end if;
    end if;
      dbms_output.put_line('Datos eliminados');

END;
/