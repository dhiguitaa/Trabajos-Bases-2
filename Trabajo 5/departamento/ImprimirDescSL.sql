CREATE OR REPLACE PROCEDURE ImprimirDescSL IS
    TYPE tipo_int IS TABLE OF NUMBER(8) INDEX BY BINARY_INTEGER;
    punterosCorrectos tipo_int;  
    indice NUMBER(30);
    imprimir VARCHAR(3000);
    indicePtrback NUMBER(30);
    filanumnodo number(30);
    filacodigoD NUMBER(8);
    filanombreD VARCHAR2(20);
    filadireccionD VARCHAR2(20);
    filaptrback number(30);
    filapuntero1 number(30);
    filapuntero2 number(30);
    filapuntero3 number(30);
    filapuntero4 number(30);
    filapuntero5 number(30);
    filapuntero6 number(30);
    filapuntero7 number(30);
    filapuntero8 number(30);
    filapuntero9 number(30);
    filapuntero10 number(30);
    filapuntero11 number(30);
    filapuntero12 number(30);
    filapuntero13 number(30);
    filapuntero14 number(30);
    filapuntero15 number(30);
    filapuntero16 number(30);
begin
    SELECT count(*) into indicePtrback FROM indexdepskip;

    WHILE indicePtrback > 0 loop

        SELECT * into filanumnodo, filacodigoD, filanombreD, filadireccionD, filaptrback, filapuntero1, filapuntero2, filapuntero3, filapuntero4, filapuntero5, filapuntero6, filapuntero7, filapuntero8, filapuntero9, filapuntero10, filapuntero11, filapuntero12, filapuntero13, filapuntero14, filapuntero15, filapuntero16  FROM indexdepskip where numnodo = indicePtrback;
        dbms_output.put_line('NODO ' || filanumnodo);

        -- Imprimir datos dpto
        IF (filacodigoD IS NULL) THEN
            dbms_output.put_line('Datos dpto: No tiene');

        ELSE
            dbms_output.put_line('Datos dpto: ' || filacodigoD || ', ' || filanombreD || ', ' || filadireccionD);
        END IF;

        -- Sacar los punteros diferentes a -1
        indice := 1;
        IF (filapuntero1 != -1) THEN
            punterosCorrectos(indice) := filapuntero1;
            indice := indice + 1;
        END IF;

        IF (filapuntero2 != -1) THEN
            punterosCorrectos(indice) := filapuntero2;
            indice := indice + 1;
        END IF;

        IF (filapuntero3 != -1) THEN
            punterosCorrectos(indice) := filapuntero3;
            indice := indice + 1;
        END IF;

        IF (filapuntero4 != -1) THEN
            punterosCorrectos(indice) := filapuntero4;
            indice := indice + 1;
        END IF;

        IF (filapuntero5 != -1) THEN
            punterosCorrectos(indice) := filapuntero5;
            indice := indice + 1;
        END IF;

        IF (filapuntero6 != -1) THEN
            punterosCorrectos(indice) := filapuntero6;
            indice := indice + 1;
        END IF;

        IF (filapuntero7 != -1) THEN
            punterosCorrectos(indice) := filapuntero7;
            indice := indice + 1;
        END IF;

        IF (filapuntero8 != -1) THEN
            punterosCorrectos(indice) := filapuntero8;
            indice := indice + 1;
        END IF;

        IF (filapuntero9 != -1) THEN
            punterosCorrectos(indice) := filapuntero9;
            indice := indice + 1;
        END IF;

        IF (filapuntero10 != -1) THEN
            punterosCorrectos(indice) := filapuntero10;
            indice := indice + 1;
        END IF;

        IF (filapuntero11 != -1) THEN
            punterosCorrectos(indice) := filapuntero11;
            indice := indice + 1;
        END IF;

        IF (filapuntero12 != -1) THEN
            punterosCorrectos(indice) := filapuntero12;
            indice := indice + 1;
        END IF;

        IF (filapuntero13 != -1) THEN
            punterosCorrectos(indice) := filapuntero13;
            indice := indice + 1;
        END IF;

        IF (filapuntero14 != -1) THEN
            punterosCorrectos(indice) := filapuntero14;
            indice := indice + 1;
        END IF;

        IF (filapuntero15 != -1) THEN
            punterosCorrectos(indice) := filapuntero15;
            indice := indice + 1;
        END IF;

        IF (filapuntero16 != -1) THEN
            punterosCorrectos(indice) := filapuntero16;
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

        indicePtrback := filaptrback;
    END LOOP;


end;
/