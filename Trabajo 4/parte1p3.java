import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oracle.net.aso.e;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.*;

import oracle.net.aso.q;
import java.lang.Math;

public class parte1p3 {
  List<Double> listaMiner1;
  List<Double> listaMiner2;
  double ValorUsdMayor;

  public class ventana2 implements ActionListener {

    JButton enviar;
    JLabel JLhoraInicio, JLhoraFin, JLminer1, JLminer2;
    JTextField JThoraInicio, JThoraFin, JTminer1, JTminer2;
    JFrame jf = new JFrame("Formulario");

    public ventana2() {

      jf.setLayout(new FlowLayout());
      enviar = new JButton("Graficar comparación creciente value_usd");
      JLhoraFin = new JLabel("Hora y minuto final");
      JLhoraInicio = new JLabel("Hora y minuto inicial");
      JLminer1 = new JLabel("Primer miner");
      JLminer2 = new JLabel("Segundo miner");

      JThoraInicio = new JTextField(27);
      JThoraFin = new JTextField(27);
      JTminer1 = new JTextField(27);
      JTminer2 = new JTextField(27);
      enviar.addActionListener(this);

      jf.add(JLhoraInicio);
      jf.add(JThoraInicio);
      jf.add(JLhoraFin);
      jf.add(JThoraFin);
      jf.add(JLminer1);
      jf.add(JTminer1);
      jf.add(JLminer2);
      jf.add(JTminer2);
      jf.add(enviar);

      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza el programa cuando se da click en la X
      jf.setResizable(false);// para configurar si se redimensiona la ventana
      jf.setSize(450, 300);// configurando tamaño de la ventana (ancho, alto)
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

      List<List<String>> bloques = new ArrayList<List<String>>();
      try {
        resultado = sentencia.executeQuery(
            "select id, miner, time_bloq, value_usd from bloque,transaccion where bloque.id = transaccion.block_id");
        int index = 0;
        while (resultado.next()) {
          bloques.add(new ArrayList<String>());

          bloques.get(index).add(resultado.getString("id"));
          bloques.get(index).add(resultado.getString("miner"));
          bloques.get(index).add(resultado.getString("time_bloq"));

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
          bloques.get(index).add(nuevoValueUsd);

          index++;

        }

      } catch (Exception err) {
        System.out.println("catch: " + err);
      }

      int horaInicio = Integer.parseInt(JThoraInicio.getText().split(":")[0]);
      int minutoInicio = Integer.parseInt(JThoraInicio.getText().split(":")[1]);
      int horaFinal = Integer.parseInt(JThoraFin.getText().split(":")[0]);
      int minutoFinal = Integer.parseInt(JThoraFin.getText().split(":")[1]);

      // Se pasan a minutos todo para que sea mas facil la comparación
      int minutosIniciales = horaInicio * 60 + minutoInicio;
      int minutosFinales = horaFinal * 60 + minutoFinal;

      // Miners
      String miner1 = JTminer1.getText();
      String miner2 = JTminer2.getText();
      listaMiner1 = new ArrayList<Double>(); // Contiene valor Usd de los bloques
      listaMiner2 = new ArrayList<Double>(); // Contiene valor Usd de los bloques

      // ValorUsdMayor guardará el valor mas grande para saber que tan grande hacer la
      // grafica
      ValorUsdMayor = 0;

      for (int i = 0; i < bloques.size(); i++) {
        int horaBloque = Integer.parseInt(bloques.get(i).get(2).split(" ")[1].split(":")[0]);
        int minutosBloque = Integer.parseInt(bloques.get(i).get(2).split(" ")[1].split(":")[1]);
        int minutosTotales = horaBloque * 60 + minutosBloque;

        if (minutosTotales >= minutosIniciales && minutosTotales <= minutosFinales) {
          // Está en el intervalo de tiempo
          double valueUsdBloque = Double.parseDouble(bloques.get(i).get(3));
          if (valueUsdBloque > 0) {
            // Cumple condición de value Usd
            String minerBloque = bloques.get(i).get(1);
            if (minerBloque.equals(miner1)) {
              listaMiner1.add(valueUsdBloque);
              if (valueUsdBloque > ValorUsdMayor) {
                ValorUsdMayor = valueUsdBloque;
              }
            } else if (minerBloque.equals(miner2)) {
              listaMiner2.add(valueUsdBloque);
              if (valueUsdBloque > ValorUsdMayor) {
                ValorUsdMayor = valueUsdBloque;
              }
            }
          }
        }
      }

      System.out.println("Puntos de Miner 1");
      for (int i = 0; i < listaMiner1.size(); i++) {
        System.out.println(listaMiner1.get(i));
      }

      System.out.println("Puntos de Miner 2");
      for (int i = 0; i < listaMiner2.size(); i++) {
        System.out.println(listaMiner2.get(i));
      }

      // llama la función que dibuja
      dibujo2 DrawWindow = new dibujo2();
      DrawWindow.setSize(960, 960);
      DrawWindow.setResizable(true);
      DrawWindow.setTitle("Pintando locales y ventas");
      DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      DrawWindow.setVisible(true);

    };
  };

  public class dibujo2 extends JFrame {

    public void paint(Graphics g) {
      g.setColor(Color.lightGray);
      int Valormayor = (int) ValorUsdMayor;
      g.fillRect(20, 40, 900, 900);
      g.setColor(Color.green);
      g.drawOval(Valormayor/1000 + 20, Valormayor/1000 + 40, 4, 4);
      Collections.sort(listaMiner1);
      Collections.sort(listaMiner2);
      int min1 = Collections.min(listaMiner1).intValue();
      int min2 = Collections.min(listaMiner2).intValue();

      if (min1>min2){
        min1 = min2;
      }
      int i = 0;
      while (i < listaMiner1.size()) {
        int suma = 1;
        int j = i;
        if (!(i == listaMiner1.size() - 1)) {
          while (Double.compare(listaMiner1.get(i), listaMiner1.get(j + 1)) == 0) {
            suma++;
            j++;
          }
        }
        // int escala = (int) (900/ValorUsdMayor);
        int valor = listaMiner1.get(i).intValue();
        int x = valor/1000;
        int y = valor/1000;
        y = (Valormayor/1000)-y;
        g.setColor(Color.red);
        g.drawOval(x + 20, y + 40, 4, 4);
        if (suma > 1) {
          g.drawString("(" + x + "," + suma + ")", x + 20, y + 50);
        } else {
          g.drawString(String.valueOf(listaMiner1.get(i)), x + 20, y + 50);
        }
        i += suma;
      }

    }
  }

  public static void main(String[] args) {
    parte1p3 x = new parte1p3();
    ventana2 gj = x.new ventana2();
  }

}
