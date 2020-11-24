CREATE OR REPLACE PROCEDURE imprimirST(sw IN NUMBER) IS
    maxNodo NUMBER(30);
    nivel NUMBER(30);
    inicio NUMBER(30);
    fin NUMBER(30);
    idNodo number(30);
    inicioNodo number(30);
    finNodo number(30);
    valornotionNodo number(30);
    izqNodo number(30);
    derNodo number(30);
begin
    SELECT count(*) into maxNodo FROM indexsegtree;

    IF sw = 0 THEN
        for i in 1 .. maxNodo loop
            SELECT * into idNodo, inicioNodo, finNodo, valornotionNodo, izqNodo, derNodo FROM indexsegtree WHERE id = i;
            dbms_output.put_line('ID del nodo: ' || idNodo);
            dbms_output.put_line('Inicio del rango: ' || inicioNodo);
            dbms_output.put_line('Fin del rango: ' || finNodo);
            dbms_output.put_line('ValorNotion: ' || valornotionNodo);
            if izqNodo = 0 THEN
                dbms_output.put_line('Hijo izquiedo: No tiene');
            else
                dbms_output.put_line('Hijo izquiedo: ' || izqNodo);
            end if;

            if derNodo = 0 THEN
                dbms_output.put_line('Hijo Derecho: No tiene');
            else
                dbms_output.put_line('Hijo Derecho: ' || derNodo);
            end if;
            dbms_output.put_line('---------------------------------');
        end loop;

    ELSIF sw = 1 THEN 
        nivel := floor(LOG(2, maxNodo)) ; 
        while nivel >= 0 loop
            inicio := POWER(2, nivel);
            if nivel = floor(LOG(2, maxNodo)) THEN
                fin := maxNodo;
            else
                fin := POWER(2, nivel+1) - 1;
            end if;
            
            for i in inicio .. fin loop
                SELECT * into idNodo, inicioNodo, finNodo, valornotionNodo, izqNodo, derNodo FROM indexsegtree WHERE id = i;
                dbms_output.put_line('ID del nodo: ' || idNodo);
                dbms_output.put_line('Inicio del rango: ' || inicioNodo);
                dbms_output.put_line('Fin del rango: ' || finNodo);
                dbms_output.put_line('ValorNotion: ' || valornotionNodo);
                if izqNodo = 0 THEN
                    dbms_output.put_line('Hijo izquiedo: No tiene');
                else
                    dbms_output.put_line('Hijo izquiedo: ' || izqNodo);
                end if;

                if derNodo = 0 THEN
                    dbms_output.put_line('Hijo Derecho: No tiene');
                else
                    dbms_output.put_line('Hijo Derecho: ' || derNodo);
                end if;
                dbms_output.put_line('---------------------------------');
            end loop;
            
            nivel := nivel - 1;
        end loop;

    END IF;
    

end;
/
