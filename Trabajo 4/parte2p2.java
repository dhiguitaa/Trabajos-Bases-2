import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class parte2p2 {

  // JF de creacion de la ventana principal
  public class ventana3 implements ActionListener {
    JFrame jf = new JFrame("ventana inicial");
    JButton iniciar, registrar;

    public ventana3() {
      
      jf.setLayout(new FlowLayout());
      iniciar = new JButton("Iniciar");
      registrar = new JButton("Registrar");
      registrar.addActionListener(this);
      jf.add(iniciar);
      jf.add(registrar);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// finaliza el programa cuando se da click en la X
      jf.setResizable(false);// para configurar si se redimensiona la ventana
      jf.setSize(450, 300);// configurando tamaño de la ventana (ancho, alto)
      jf.setVisible(true);

    }

    public void actionPerformed(final ActionEvent e) {
      // creacion del jdialog donde el padre es jf
      if (e.getSource() == registrar) {
        System.out.println("registrar");
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
            // cierra el diagol al hacer clic
            registro.dispose();
          }
        });

        //agregamos los componentes al panel y luego al registro
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
    }
  }

  public static void main(String[] args) {
    parte2p2 x = new parte2p2();
    ventana3 gj = x.new ventana3();
  }

}
