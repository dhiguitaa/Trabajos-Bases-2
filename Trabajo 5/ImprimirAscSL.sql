CREATE OR REPLACE PROCEDURE ImprimirAscSL IS
    TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
    punterosCorrectos tipo_int;  
    indice NUMBER(30);
    imprimir VARCHAR(3000);
begin
    FOR fila IN (SELECT * FROM indexdepskip) LOOP
        dbms_output.put_line('NODO ' || fila.numnodo);

        -- Imprimir datos dpto
        IF (fila.codigoD IS NULL) THEN
            dbms_output.put_line('Datos dpto: No tiene');

        ELSE
            dbms_output.put_line('Datos dpto: ' || fila.codigoD || ', ' || fila.nombreD || ', ' || fila.direccionD);
        END IF;

        -- Sacar los punteros diferentes a -1
        indice := 1;
        IF (fila.puntero1 != -1) THEN
            punterosCorrectos(indice) := fila.puntero1;
            indice := indice + 1;
        END IF;

        IF (fila.puntero2 != -1) THEN
            punterosCorrectos(indice) := fila.puntero2;
            indice := indice + 1;
        END IF;

        IF (fila.puntero3 != -1) THEN
            punterosCorrectos(indice) := fila.puntero3;
            indice := indice + 1;
        END IF;

        IF (fila.puntero4 != -1) THEN
            punterosCorrectos(indice) := fila.puntero4;
            indice := indice + 1;
        END IF;

        IF (fila.puntero5 != -1) THEN
            punterosCorrectos(indice) := fila.puntero5;
            indice := indice + 1;
        END IF;

        IF (fila.puntero6 != -1) THEN
            punterosCorrectos(indice) := fila.puntero6;
            indice := indice + 1;
        END IF;

        IF (fila.puntero7 != -1) THEN
            punterosCorrectos(indice) := fila.puntero7;
            indice := indice + 1;
        END IF;

        IF (fila.puntero8 != -1) THEN
            punterosCorrectos(indice) := fila.puntero8;
            indice := indice + 1;
        END IF;

        IF (fila.puntero9 != -1) THEN
            punterosCorrectos(indice) := fila.puntero9;
            indice := indice + 1;
        END IF;

        IF (fila.puntero10 != -1) THEN
            punterosCorrectos(indice) := fila.puntero10;
            indice := indice + 1;
        END IF;

        IF (fila.puntero11 != -1) THEN
            punterosCorrectos(indice) := fila.puntero11;
            indice := indice + 1;
        END IF;

        IF (fila.puntero12 != -1) THEN
            punterosCorrectos(indice) := fila.puntero12;
            indice := indice + 1;
        END IF;

        IF (fila.puntero13 != -1) THEN
            punterosCorrectos(indice) := fila.puntero13;
            indice := indice + 1;
        END IF;

        IF (fila.puntero14 != -1) THEN
            punterosCorrectos(indice) := fila.puntero14;
            indice := indice + 1;
        END IF;

        IF (fila.puntero15 != -1) THEN
            punterosCorrectos(indice) := fila.puntero15;
            indice := indice + 1;
        END IF;

        IF (fila.puntero16 != -1) THEN
            punterosCorrectos(indice) := fila.puntero16;
            indice := indice + 1;
        END IF;

        -- Imprimir numero de punteros
        dbms_output.put_line('Número de punteros: ' || punterosCorrectos.count);

        -- Imprimir punteros de arriba a abajo
        imprimir := 'De arriba a abajo los punteros apuntan a los nodos: ';
        indice := punterosCorrectos.last;
        WHILE indice > 0 loop
            IF indice != 1 THEN 
                imprimir := imprimir || punterosCorrectos(indice) || ', ';
            ELSE
                imprimir := imprimir || punterosCorrectos(indice) || '.';
            END IF;
            
            indice := indice - 1;
        END LOOP;

        IF punterosCorrectos.count = 0 THEN
            imprimir := imprimir || 'No tiene.';
        END IF;

        dbms_output.put_line(imprimir);

        punterosCorrectos.delete();
        dbms_output.put_line('---------------------------------------------------');

    END LOOP;


end;
/