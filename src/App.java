import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Mar 25 02:42:56 EET 2019
 */



/**
 * @author MIshaPrigara
 */
public class App extends JFrame {
    public App() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - MIshaPrigara
        button1 = new JButton();
        label1 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("Choose file");
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(290, 55), button1.getPreferredSize()));

        //---- label1 ----
        label1.setText("File name");
        contentPane.add(label1);
        label1.setBounds(30, 60, 250, label1.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(505, 410));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - MIshaPrigara
    private JButton button1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
