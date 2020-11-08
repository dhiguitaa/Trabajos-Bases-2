import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import multichain.command.*;
import multichain.object.*;
import sun.net.www.content.image.jpeg;

import java.sql.*;

public class parte2p2 {
  Connection conn;
  Statement sentencia;
  ResultSet resultado;
  CommandManager cm;
  JDialog ingreso;

  // JF de creacion de la ventana principal
  public class ventana3 implements ActionListener {
    JFrame jf = new JFrame("ventana inicial");
    JButton iniciar, registrar;

    public ventana3() {

      jf.setLayout(new FlowLayout());
      iniciar = new JButton("Iniciar");
      registrar = new JButton("Registrar");
      registrar.addActionListener(this);
      iniciar.addActionListener(this);
      jf.add(iniciar);
      jf.add(registrar);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza el programa cuando se da click en la X
      jf.setResizable(false);// para configurar si se redimensiona la ventana
      jf.setSize(450, 300);// configurando tamaño de la ventana (ancho, alto)
      jf.setVisible(true);

    }

    public void actionPerformed(final ActionEvent e) {
      // creacion del jdialog donde el padre es jf

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

      cm = new CommandManager("localhost", "6468", "multichainrpc", "8VQt9ESdffXquEXFUXjZvd8jCCfQaYajiDGoHbu1ytc3");

      if (e.getSource() == registrar) {
        JDialog registro = new JDialog(jf, true);
        JLabel JLusuario = new JLabel("Usuario");
        JLabel JLcontrasena = new JLabel("Contraseña");
        JTextField usuario = new JTextField(10);
        JTextField contrasena = new JTextField(10);
        // create a label
        JPanel panel = new JPanel();
        JButton bRegistro = new JButton("Registrar");
        bRegistro.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.out.println(" im working. att: button");
            String nombreUsuario = usuario.getText();
            String usContrasena = contrasena.getText();
            try {
              // se crea la nueva direccion
              Object direccion = cm.invoke(CommandElt.GETNEWADDRESS);
              // se agrega la info a la bd
              PreparedStatement stmtInsertar = conn.prepareStatement("INSERT INTO usuario VALUES (?,?,?)");
              stmtInsertar.setString(1, nombreUsuario);
              stmtInsertar.setString(2, usContrasena);
              stmtInsertar.setString(3, String.valueOf(direccion));
              stmtInsertar.executeUpdate();

            } catch (Exception err) {
              System.out.println(err);
            }
            // cierra el diagol al hacer clic
            registro.dispose();
          }
        });

        // agregamos los componentes al panel y luego al registro
        panel.add(JLusuario);
        panel.add(usuario);
        panel.add(JLcontrasena);
        panel.add(contrasena);
        panel.add(bRegistro);
        registro.add(panel);
        registro.pack();
        registro.setSize(450, 300);
        registro.setResizable(false);
        registro.setVisible(true);

      }

      if (e.getSource() == iniciar) {
        ingreso = new JDialog(jf, true);
        JLabel JLusuario = new JLabel("Usuario");
        JLabel JLcontrasena = new JLabel("Contraseña");
        JTextField usuario = new JTextField(10);
        JTextField contrasena = new JTextField(10);
        // create a label
        JPanel panel = new JPanel();
        JButton bIngreso = new JButton("continuar");
        bIngreso.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String nombreUsuario = usuario.getText();
            String usContrasena = contrasena.getText();
            try {
              // se agrega la info a la bd
              resultado = sentencia
                  .executeQuery("select contrasena from usuario where nombre_usuario=" + "'" + nombreUsuario + "'");
              boolean vacio = true;
              while (resultado.next()) {
                vacio = false;
                if (!(usContrasena.equals(resultado.getString("contrasena")))) {
                  JOptionPane.showMessageDialog(null, "datos incorrectos");
                } else if (usContrasena.equals(resultado.getString("contrasena"))) {
                  // cuadro de accciones
                  acciones();
                }
              }
              if (vacio) {
                JOptionPane.showMessageDialog(null, "datos incorrectos");
              }

            } catch (Exception err) {
              System.out.println(err);
            }
            // cierra el diagol al hacer clic

          }
        });
        panel.add(JLusuario);
        panel.add(usuario);
        panel.add(JLcontrasena);
        panel.add(contrasena);
        panel.add(bIngreso);
        ingreso.add(panel);
        ingreso.pack();
        ingreso.setSize(450, 300);
        ingreso.setResizable(false);
        ingreso.setVisible(true);
      }
    }

    private void acciones() {
      JButton consultar, pagar, cerrar;
      JDialog acciones = new JDialog(ingreso, true);
      JPanel p = new JPanel();
      consultar = new JButton("consultar saldo");
      pagar = new JButton("pagar");
      cerrar = new JButton("cerrar");
      // pagar
      pagar.addActionListener((new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
      }));
      // cerrar
      cerrar.addActionListener((new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
      }));
      // consultar
      consultar.addActionListener((new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
      }));
      p.add(consultar);
      p.add(pagar);
      p.add(cerrar);
      acciones.add(p);
      acciones.pack();
      acciones.setSize(450, 300);
      acciones.setResizable(false);
      acciones.setVisible(true);
    }
  }

  public static void main(String[] args) {
    parte2p2 x = new parte2p2();
    ventana3 gj = x.new ventana3();
  }

}
