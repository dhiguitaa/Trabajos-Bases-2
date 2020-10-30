import java.util.Arrays;
import java.util.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

public class parte1p2 implements ActionListener {

  JButton enviar;
  JLabel JLhoraInicio, JLhoraFin, JLladoCuadricula, JLcolores;
  JTextField JThoraInicio, JThoraFin, JTladoCuadricula;
  JTextArea colores;
  JFrame jf = new JFrame("Formulario");

  public parte1p2() {

    jf.setLayout(new FlowLayout());
    enviar = new JButton("Ver mapa por numero de transacciones");
    JLcolores = new JLabel("Configurar escala para nro. de transacciones:");
    JLhoraFin = new JLabel("Hora y minuto final");
    JLhoraInicio = new JLabel("Hora y minuto inicial");
    JLladoCuadricula = new JLabel("Tamaño lado cuadricula");
    JThoraInicio = new JTextField(10);
    JThoraFin = new JTextField(10);
    JTladoCuadricula = new JTextField(10);
    colores = new JTextArea(10,20);
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

    String listaColores[] = {"blue","green","red","yellow","gray", "white","black","pink","purple","orange"};

    // String horaInicio = JThoraInicio.getText(); 
    // String horaFin = JThoraInicio.getText(); 
    // int ladoCuadricula = Integer.parseInt(JTladoCuadricula.getText());



    try {
      resultado = sentencia.executeQuery("select time_trans, x, y, value_usd, fee_usd , block_id, sender, recipient from transaccion");
      int index = 0;
      List<List<String>> transacciones = new ArrayList<List<String>>();
      while(resultado.next()){
        transacciones.add(new ArrayList<String>());
        transacciones.get(index).add(resultado.getString("time_trans"));
        transacciones.get(index).add(resultado.getString("x"));
        transacciones.get(index).add(resultado.getString("y"));
        transacciones.get(index).add(resultado.getString("value_usd"));
        transacciones.get(index).add(resultado.getString("fee_usd"));
        transacciones.get(index).add(resultado.getString("block_id"));
        transacciones.get(index).add(resultado.getString("sender"));
        transacciones.get(index).add(resultado.getString("recipient"));

      }
    } catch (Exception err) {
      System.out.println(err);
    }

  };

  public static void main(String[] args) {
    parte1p2 gj = new parte1p2();
  }

};