import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel;
class jframe extends JFrame implements ActionListener {

  // frame
  static JFrame jf;

  // main class
  public static void main(String[] args) {
    // create a new frame
    jf = new JFrame("frame");

    // create a object
    jframe s = new jframe();

    // create a panel
    JPanel p = new JPanel();

    JButton b = new JButton("click");

    // add actionlistener to button
    b.addActionListener(s);

    // add button to panel
    p.add(b);

    jf.add(p);

    // set the size of frame
    jf.setSize(500, 500);

    jf.show();
  }

  public void actionPerformed(ActionEvent e) {
    String s = e.getActionCommand();
    if (s.equals("click")) {
      // create a dialog Box
      JDialog d = new JDialog(jf, true);
      JTextField jt = new JTextField(5);
      // create a label
      JLabel l = new JLabel("this is a dialog box");
      JPanel p = new JPanel();
      JButton button = new JButton("clic me");
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          System.out.println(" im working. att: button");
          d.dispose();
        }
      });


      p.add(l);
      p.add(jt);
      p.add(button);
      d.add(p);
      // d.add(l);
      // setsize of dialog
      d.pack();

      d.setSize(300, 300);
      d.setResizable(false);
      // set visibility of dialog
      d.setVisible(true);
    }
  }
}