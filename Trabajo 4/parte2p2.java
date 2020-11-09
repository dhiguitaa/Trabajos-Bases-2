
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import multichain.command.*;
import multichain.object.*;
import java.util.ArrayList;

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

      cm = new CommandManager("localhost", "4344", "multichainrpc", "7yebJt75FUcMVZEfgRP1o7v825ifBd2mptiCPdPESe6z");

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
            String nombreUsuario = usuario.getText();
            String usContrasena = contrasena.getText();
            try {
              // se crea la nueva direccion
              // Permission[] permisos = {connect,recive,send};
              Object direccion = cm.invoke(CommandElt.GETNEWADDRESS);
              cm.invoke(CommandElt.GRANT,String.valueOf(direccion),"connect");
              cm.invoke(CommandElt.GRANT,String.valueOf(direccion),"send");
              cm.invoke(CommandElt.GRANT,String.valueOf(direccion),"receive");
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
                  .executeQuery("select * from usuario where nombre_usuario=" + "'" + nombreUsuario + "'");
              boolean vacio = true;
              while (resultado.next()) {
                vacio = false;
                if (!(usContrasena.equals(resultado.getString("contrasena")))) {
                  JOptionPane.showMessageDialog(null, "datos incorrectos");
                } else if (usContrasena.equals(resultado.getString("contrasena"))) {
                  // cuadro de accciones
                  acciones(nombreUsuario, resultado.getString("direccion"));
                }
              }
              if (vacio) {
                JOptionPane.showMessageDialog(null, "datos incorrectos");
              }

            } catch (Exception err) {
              System.out.println(err);
            }
            // cierra el diagol al hacer clic
            ingreso.dispose();
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

    private void acciones(String nombreUsuario, String miDicreccion) {
      JButton consultar, pagar, cerrar;
      JDialog acciones = new JDialog(ingreso, true);
      JPanel p = new JPanel();
      consultar = new JButton("consultar saldo");
      pagar = new JButton("pagar");
      cerrar = new JButton("cerrar seccion");
      // pagar
      pagar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog pagar = new JDialog(acciones, true);
          JPanel pPagar = new JPanel();
          JLabel Jlus = new JLabel("Usuario destino");
          JLabel Jlvalor = new JLabel("Valor");
          JTextField Jtus = new JTextField(20);
          JTextField Jtvalor = new JTextField(20);
          JButton bPagar = new JButton("pagar");
          pPagar.add(Jlus);
          pPagar.add(Jtus);
          pPagar.add(Jlvalor);
          pPagar.add(Jtvalor);
          pPagar.add(bPagar);
          pagar.add(pPagar);
          
          //accion de pagar
          bPagar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
              String nombre = Jtus.getText(); 
              int valor = Integer.parseInt(Jtvalor.getText());
              
              try {
                resultado = sentencia
                .executeQuery("select * from usuario where nombre_usuario ="+"'"+nombre+"'");
                String sendTo="";
                while(resultado.next()){
                  sendTo = resultado.getString("direccion");
                }
                try {
                  cm.invoke(CommandElt.SENDASSETFROM,miDicreccion,sendTo,"bdcoin",valor);
                  JOptionPane.showMessageDialog(null, "Pago exitoso");
                } catch (Exception err) {
                  JOptionPane.showMessageDialog(null, "Alguno de los datos no es valido");
                }
              } catch (Exception err) {
                System.out.println(err);
              }
              // cm.invoke(CommandElt.SENDFROM, )
            }
          });

          pagar.pack();
          pagar.setSize(450, 300);
          pagar.setResizable(false);
          pagar.setVisible(true);
        }
      });
      // cerrar
      cerrar.addActionListener((new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          acciones.dispose();
        }
      }));
      // consultar
      consultar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          JDialog consultar = new JDialog(acciones, true);
          JPanel pConsultar = new JPanel();
          JLabel Jlsaldo = new JLabel();
          JButton salirSaldo = new JButton("Salir");
          
          
          try {
            ArrayList<BalanceAssetGeneral> list =(ArrayList<BalanceAssetGeneral>) cm.invoke(CommandElt.GETADDRESSBALANCES,miDicreccion);
            try {
              
              String valor = String.valueOf(list.get(0).getQty());
              Jlsaldo.setText(valor);
            } catch (Exception err) {
              Jlsaldo.setText("0");            }
            
            pConsultar.add(Jlsaldo);

          } catch (Exception err) {
            System.out.println(err);
          }
          pConsultar.add(salirSaldo);
          salirSaldo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){             
              consultar.dispose();
              // cm.invoke(CommandElt.SENDFROM, )
            }
          });
          consultar.add(pConsultar);
          consultar.pack();
          consultar.setSize(450, 300);
          consultar.setResizable(false);
          consultar.setVisible(true);
        }
        
      });
    
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
