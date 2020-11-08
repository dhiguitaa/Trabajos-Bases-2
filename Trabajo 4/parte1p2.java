import java.util.Arrays;
import java.util.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oracle.net.aso.e;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class parte1p2 {
  List<List<Double>> infoCudrado;
  int ladoCuadricula;
  List<Integer> rangoColores;

  public class ventana1 implements ActionListener {

    JButton enviar;
    JLabel JLhoraInicio, JLhoraFin, JLladoCuadricula, JLcolores;
    JTextField JThoraInicio, JThoraFin, JTladoCuadricula;
    JTextArea colores;
    JFrame jf = new JFrame("Formulario");

    public ventana1() {

      jf.setLayout(new FlowLayout());
      enviar = new JButton("Ver mapa por numero de transacciones");
      JLcolores = new JLabel("Configurar escala para nro. de transacciones:");
      JLhoraFin = new JLabel("Hora y minuto final");
      JLhoraInicio = new JLabel("Hora y minuto inicial");
      JLladoCuadricula = new JLabel("Tamaño lado cuadricula");
      JThoraInicio = new JTextField(10);
      JThoraFin = new JTextField(10);
      JTladoCuadricula = new JTextField(10);
      colores = new JTextArea(10, 20);
      enviar.addActionListener(this);

      jf.add(JLhoraInicio);
      jf.add(JThoraInicio);
      jf.add(JLhoraFin);
      jf.add(JThoraFin);
      jf.add(JLladoCuadricula);
      jf.add(JTladoCuadricula);
      jf.add(JLcolores);
      jf.add(colores);
      jf.add(enviar);

      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza el programa cuando se da click en la X
      jf.setResizable(false);// para configurar si se redimensiona la ventana
      jf.setSize(600, 300);// configurando tamaño de la ventana (ancho, alto)
      jf.setVisible(true);
    };

    public void actionPerformed(final ActionEvent e) {

      Connection conn;
      Statement sentencia;
      ResultSet resultado;
      try { // Se carga el driver JDBC-ODBC
        Class.forName("oracle.jdbc.driver.OracleDriver");
      } catch (final Exception err) {
        System.out.println("No se pudo cargar el driver JDBC");
        return;
      }

      try { // Se establece la conexi�n con la base de datos Oracle Express
        conn = DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-LV4ONBF:1521:xe", "dani", "dani");
        sentencia = conn.createStatement();
      } catch (final SQLException err) {
        System.out.println("No hay conexi�n con la base de datos.");
        return;
      }

      // String horaInicio = JThoraInicio.getText();
      // String horaFin = JThoraInicio.getText();
      // int ladoCuadricula = Integer.parseInt(JTladoCuadricula.getText());

      List<List<String>> transacciones = new ArrayList<List<String>>();
      try {

        // Pasar las transacciones a una lista de lista
        resultado = sentencia
            .executeQuery("select time_trans, x, y, value_usd, fee_usd , block_id, sender, recipient from transaccion");
        int index = 0;
        while (resultado.next()) {
          transacciones.add(new ArrayList<String>());
          transacciones.get(index).add(resultado.getString("time_trans"));
          transacciones.get(index).add(resultado.getString("x"));
          transacciones.get(index).add(resultado.getString("y"));

          // Supuesto. Los unicos decimales comienzan en 0
          String ValueUsd = resultado.getString("value_usd");
          String nuevoValueUsd = ValueUsd;
          if (ValueUsd.contains(".")) {
            if (!(ValueUsd.split("\\.")[0].equals("0"))) { // Entero
              nuevoValueUsd = "";
              for (int j = 0; j < ValueUsd.split("\\.").length; j++) {
                nuevoValueUsd = nuevoValueUsd + ValueUsd.split("\\.")[j]; // Concatenar
              }
            }
          }
          transacciones.get(index).add(nuevoValueUsd);

          // Supuesto. Los unicos decimales comienzan en 0
          String feeUsd = resultado.getString("fee_usd");
          String nuevofeeUsd = feeUsd;
          if (feeUsd.contains(".")) {
            if (!(feeUsd.split("\\.")[0].equals("0"))) { // Entero
              nuevofeeUsd = "";
              for (int j = 0; j < feeUsd.split("\\.").length; j++) {
                nuevofeeUsd = nuevofeeUsd + feeUsd.split("\\.")[j]; // Concatenar
              }
            }
          }
          transacciones.get(index).add(nuevofeeUsd);

          transacciones.get(index).add(resultado.getString("block_id"));
          transacciones.get(index).add(resultado.getString("sender"));
          transacciones.get(index).add(resultado.getString("recipient"));
          index++;
        }

      } catch (Exception err) {
        System.out.println("catch: " + err);
      }

      int horaInicio = Integer.parseInt(JThoraInicio.getText().split(":")[0]);
      int minutoInicio = Integer.parseInt(JThoraInicio.getText().split(":")[1]);
      int horaFinal = Integer.parseInt(JThoraFin.getText().split(":")[0]);
      int minutoFinal = Integer.parseInt(JThoraFin.getText().split(":")[1]);
      // se guardan los rangos de los colores
      String textColores = colores.getText();
      String[] splitColores = textColores.split("\n");
      rangoColores = new ArrayList<Integer>();
      for (String string : splitColores) {
        String[] rango = string.split("-");
        rangoColores.add(Integer.parseInt(rango[0]));
        try {
          rangoColores.add(Integer.parseInt(rango[1]));
        } catch (Exception err) {

        }
      }
      // Se pasan a minutos todo para que sea mas facil la comparación
      int minutosIniciales = horaInicio * 60 + minutoInicio;
      int minutosFinales = horaFinal * 60 + minutoFinal;

      // Se gurda la info de las transacciones que esten en el intervalo limite del
      // tiempo deseado
      List<Integer> indicesTransLimite = new ArrayList<Integer>();
      for (int i = 0; i < transacciones.size(); i++) {
        int hora = Integer.parseInt(transacciones.get(i).get(0).split(" ")[1].split(":")[0]);
        int minuto = Integer.parseInt(transacciones.get(i).get(0).split(" ")[1].split(":")[1]);

        int minutosTransaccion = hora * 60 + minuto;

        if (minutosTransaccion >= minutosIniciales && minutosTransaccion <= minutosFinales) {
          indicesTransLimite.add(i);
        }
      }

      // Formula de colisionamiento
      // x1 >= x2 - w1 && x1 <= x2 + w2 && y1 >= y2 - h1 && y1 <= y2 + h2

      // NOTA. Toca amplificarlo
      int restaAmplificada = 1;
      ladoCuadricula = Integer.parseInt(JTladoCuadricula.getText());
      int cuatriculaMaxima = 99;

      // Movimiento ARRIBA-abajo IZQUIERDA-derecha
      int contadorCuadrado = 0;
      infoCudrado = new ArrayList<List<Double>>();
      List<List<Integer>> transaccionesCuadrado = new ArrayList<List<Integer>>();

      // Se ingresa a infoCuadradoMatriz la informacion total de cada cuadrado
      // Se ingresa a transaccionesCudradoMatriz los indices de las transacciones de
      // cada cuadrado
      for (int h = 0; h < cuatriculaMaxima; h += ladoCuadricula) {
        int coorx = h;
        if (coorx != 0) {
          coorx = coorx - restaAmplificada;
        }
        for (int r = 0; r < cuatriculaMaxima; r += ladoCuadricula) {
          int coory = r;
          if (coory != 0) {
            coory = coory - restaAmplificada;
          }

          transaccionesCuadrado.add(new ArrayList<Integer>());
          infoCudrado.add(new ArrayList<Double>());
          double totalTransacciones = 0;
          double totalFeeUsd = 0;
          double totalValueUsd = 0;

          List<Integer> listaIndicesParaBorrar = new ArrayList<Integer>();
          for (int i = 0; i < indicesTransLimite.size(); i++) {
            int indice = indicesTransLimite.get(i);
            int xTrans = Integer.parseInt(transacciones.get(indice).get(1));
            int yTrans = Integer.parseInt(transacciones.get(indice).get(2));

            if (coorx >= xTrans - ladoCuadricula && coorx <= xTrans + ladoCuadricula && coory >= yTrans - ladoCuadricula
                && coory <= yTrans + ladoCuadricula) {
              // El punto pertenece a este cuadrado
              totalTransacciones += 1;
              totalFeeUsd += Double.parseDouble(transacciones.get(indice).get(4));
              totalValueUsd += Double.parseDouble(transacciones.get(indice).get(3)); // Error porque piensa que los
  
              transaccionesCuadrado.get(contadorCuadrado).add(indice);
              listaIndicesParaBorrar.add(indice);
            }
          }

          for (int i = 0; i < listaIndicesParaBorrar.size(); i++) {
            int indiceBorrar = indicesTransLimite.indexOf(listaIndicesParaBorrar.get(i));
            indicesTransLimite.remove(indiceBorrar);
          }

          infoCudrado.get(contadorCuadrado).add(totalTransacciones);
          infoCudrado.get(contadorCuadrado).add(totalFeeUsd);
          infoCudrado.get(contadorCuadrado).add(totalValueUsd);
          contadorCuadrado++;

        }

      }

      // llama la función que dibuja
      dibujo1 DrawWindow = new dibujo1();
      DrawWindow.setSize(560, 560);
      DrawWindow.setResizable(false);
      DrawWindow.setTitle("Pintando locales y ventas");
      DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      DrawWindow.setVisible(true);

      // Falta grafica

      System.out.println(transaccionesCuadrado.size());
      // Despues de darle en el botón ordenar
      int numeroCuadrado = 3; // Textlabel
      numeroCuadrado -= 1;

      for (Integer indiceTrans : transaccionesCuadrado.get(numeroCuadrado)) {
        transacciones.get(indiceTrans);
      }

      // Aqui se dropea la tabla auxiliar1 en BD
      Statement stmtDrop = null;
      try {
        stmtDrop = conn.createStatement();
        String sql = "DROP TABLE auxiliar1";
        stmtDrop.executeUpdate(sql);
      } catch (SQLException sqle) {
        System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
      }

      // Aqui se crea la tabla auxiliar1 en BD
      PreparedStatement stmt = null;
      try { // block_id, x, y, sender, recipient, value_usd, fee_usd y time.
        stmt = conn.prepareStatement(
            "CREATE TABLE auxiliar1 (block_id varchar(4000), x varchar(4000), y varchar(4000), sender varchar(4000), recipient varchar(4000), value_usd float(126),fee_usd float(126), timeMinutos number(38))");
        stmt.execute();
        stmt.close();
      } catch (SQLException sqle) {
        System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
      }

      // Aqui se ingresa los datos a la tabla auxiliar1 en BD
      for (int i = 0; i < transaccionesCuadrado.get(numeroCuadrado).size(); i++) {
        String block_id = transacciones.get(i).get(5);
        String x = transacciones.get(i).get(1);
        String y = transacciones.get(i).get(2);
        String sender = transacciones.get(i).get(6);
        String recipient = transacciones.get(i).get(7);
        double value_usd = Double.parseDouble(transacciones.get(i).get(3));
        double fee_usd = Double.parseDouble(transacciones.get(i).get(4));

        String time = transacciones.get(i).get(0);
        int hora = Integer.parseInt(time.split(" ")[1].split(":")[0]);
        int minuto = Integer.parseInt(time.split(" ")[1].split(":")[1]);
        int minutosTotales = hora * 60 + minuto;

        try {
          PreparedStatement stmtInsertar = conn.prepareStatement("INSERT INTO auxiliar1 VALUES (?,?,?,?,?,?,?,?)");
          stmtInsertar.setString(1, block_id);
          stmtInsertar.setString(2, x);
          stmtInsertar.setString(3, y);
          stmtInsertar.setString(4, sender);
          stmtInsertar.setString(5, recipient);
          stmtInsertar.setDouble(6, value_usd);
          stmtInsertar.setDouble(7, fee_usd);
          stmtInsertar.setInt(8, minutosTotales);
          stmtInsertar.executeUpdate();
        } catch (SQLException sqle) {
          System.out.println("Error en la ejecución: " + sqle.getErrorCode() + " " + sqle.getMessage());
        }

      }

      // Ordenada
      // Select * from auxiliar1 order by value_usd;
      // Select * from auxiliar1 order by fee_usd;
      // Select * from auxiliar1 order by timeminutos;

    };

    public class dibujo1 extends JFrame {

      public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(20, 40, 500, 500);
        Color[] listaColores = { Color.YELLOW, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.BLACK, Color.GREEN,
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED };

        int y = 0;
        int x = 0;
        int contador = 0;
        for (List<Double> list : infoCudrado) {
          double total = list.get(0);
          int indiceRango = 0;
          int indiceColor = 0;
          while (indiceRango <= rangoColores.size() - 1) {
            if (indiceRango == rangoColores.size() - 1) {
              g.setColor(listaColores[indiceRango]);
            } else if (total >= rangoColores.get(indiceRango) && total <= rangoColores.get(indiceRango + 1)) {
              g.setColor(listaColores[indiceColor]);
              System.out.println(indiceRango);
              break;
            }
            indiceRango += 2;
            indiceColor++;

          }
          g.fillRect(x + 20, y + 40, ladoCuadricula, ladoCuadricula);
          y += 25;
          contador++;
          if (contador == 100 / ladoCuadricula) {
            System.out.println("hola");
            y = 0;
            x += 25;
            contador = 0;
          }
        }

      }
    }
  }

  public static void main(String[] args) {
    parte1p2 x = new parte1p2();
    ventana1 gj = x.new ventana1();
  }
}
