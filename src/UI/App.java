package UI;

import Encryptors.ExtendedPalette;
import Encryptors.LSB;
import Helpers.DecryptException;
import Helpers.EncryptException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * Created by JFormDesigner on Mon Mar 25 02:42:56 EET 2019
 */



/**
 * @author MishaPrigara
 */
public class App extends JFrame {
    public App() {
        initComponents();
    }

    private String[] encMethods = {"Palette extension", "LSB"};
    private File leftFile;
    private File output = new File("temp.gif");


    private String stringShortener (String curText) {
        if(curText.length() > 10) {
            String upd = "";
            int lst = curText.length() - 1;
            for(int i = curText.length() - 1; i >= 0; i--){
                upd = curText.charAt(i) + upd;
                lst = i;
                if(curText.charAt(i) == '.') break;
            }
            upd = ".." + upd;
            for(int i = Math.min(3, lst) - 1; i >= 0; i--) {
                upd = curText.charAt(i) + upd;
            }
            curText = upd;
        }
        return curText;
    }

    private void Choose_leftActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Gifs", "gif"));

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            leftFile = fileChooser.getSelectedFile();
            String curText = leftFile.getName();
            curText = stringShortener(curText);
            leftChooserLabel.setText(curText);
        } else {
            leftFile = null;
            leftChooserLabel.setText("Choose file");
        }
    }

    private boolean LSBEncrypt(File input, String text) {
        LSB lsb = new LSB();
        try {
            lsb.Encrypt(input, output, text, (byte)1);
        } catch (EncryptException | IOException err) {
            JOptionPane.showMessageDialog(null, err);
            return false;
        }

//        text = "Failed";
//        try {
//            text = lsb.Decrypt(new File("/home/mishaprigara/Downloads/kerker.gif"), (byte)1);
//        } catch (DecryptException err) {
//            System.out.println("Decryption failed: " + err);
//        } catch (IOException err) {
//            System.out.println("Something's wrong with input: " + err);
//        }
//        System.out.println(text);

        return true;
    }

    private boolean EPEncrypt(File input, String text) {
        ExtendedPalette extendedPalette = new ExtendedPalette();
        try {
            extendedPalette.Encrypt(input, output, text, (byte)1);
        } catch (IOException | EncryptException err) {
            JOptionPane.showMessageDialog(null, err);
            return false;
        }

        return true;
//        text = "Failed";

//        try {
//            text = extendedPalette.Decrypt(new File("/home/mishaprigara/Downloads/test.gif"), (byte)1);
//        } catch (IOException err) {
//            System.out.println("Something's wrong with input: " + err);
//        } catch (DecryptException err) {
//            System.out.println("Decryption failed: " + err);
//        }
//
//        System.out.println(text);
    }

    private void createUIComponents() {
        choosedEncMethod = new JComboBox(encMethods);
    }

    private void encryptButtonActionPerformed(ActionEvent e) {
        if(leftFile == null) {
            JOptionPane.showMessageDialog(null, "No file is chosen!");
            return;
        }

        if(choosedEncMethod.getSelectedItem().equals("LSB")) {
            if(!LSBEncrypt(leftFile, editorPaneLeft.getText())) return;
        } else {
            if(!EPEncrypt(leftFile, editorPaneLeft.getText())) return;
        }

        JFileChooser fileSaver = new JFileChooser();
        fileSaver.setSelectedFile(new File("Encrypted.gif"));
        fileSaver.showSaveDialog(null);

        output.renameTo(fileSaver.getSelectedFile());
        try {
            Files.copy(output.toPath(), fileSaver.getSelectedFile().toPath());
        } catch (IOException err) {
            JOptionPane.showMessageDialog(null, err);
        }

        output.renameTo(new File("temp.gif"));
        editorPaneLeft.setText("");
        leftChooserLabel.setText("Choose file");
        leftFile = null;
        return;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - MIshaPrigara
        createUIComponents();

        Choose_left = new JButton();
        leftChooserLabel = new JLabel();
        scrollPaneLeft = new JScrollPane();
        editorPaneLeft = new JEditorPane();
        encryptButton = new JButton();
        separator1 = new JSeparator();
        Choose_right = new JButton();
        rightChooserLabel = new JLabel();
        decryptButton = new JButton();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- Choose_left ----
        Choose_left.setText("Choose file");
        contentPane.add(Choose_left);
        Choose_left.setBounds(125, 50, Choose_left.getPreferredSize().width, 25);

        //---- leftChooserLabel ----
        leftChooserLabel.setText("Choose file");
        leftChooserLabel.setFont(leftChooserLabel.getFont().deriveFont(leftChooserLabel.getFont().getSize() + 2f));
        contentPane.add(leftChooserLabel);
        leftChooserLabel.setBounds(15, 55, 95, leftChooserLabel.getPreferredSize().height);
        contentPane.add(choosedEncMethod);
        choosedEncMethod.setBounds(new Rectangle(new Point(10, 105), choosedEncMethod.getPreferredSize()));

        //======== scrollPaneLeft ========
        {
            scrollPaneLeft.setViewportView(editorPaneLeft);
        }
        contentPane.add(scrollPaneLeft);
        scrollPaneLeft.setBounds(15, 155, 215, 120);

        //---- encryptButton ----
        encryptButton.setText("Encrypt");
        encryptButton.addActionListener(e -> {
			encryptButtonActionPerformed(e);
			encryptButtonActionPerformed(e);
		});
        contentPane.add(encryptButton);
        encryptButton.setBounds(70, 295, 100, encryptButton.getPreferredSize().height);

        //---- separator1 ----
        separator1.setOrientation(SwingConstants.VERTICAL);
        contentPane.add(separator1);
        separator1.setBounds(260, 0, 20, 335);

        //---- Choose_right ----
        Choose_right.setText("Choose file");
        Choose_right.addActionListener(e -> Choose_leftActionPerformed(e));
        contentPane.add(Choose_right);
        Choose_right.setBounds(415, 50, 103, 25);

        //---- rightChooserLabel ----
        rightChooserLabel.setText("Choose file");
        rightChooserLabel.setFont(rightChooserLabel.getFont().deriveFont(rightChooserLabel.getFont().getSize() + 2f));
        contentPane.add(rightChooserLabel);
        rightChooserLabel.setBounds(285, 55, 95, 17);

        //---- decryptButton ----
        decryptButton.setText("Decrypt");
        decryptButton.addActionListener(e -> {
			encryptButtonActionPerformed(e);
			encryptButtonActionPerformed(e);
		});
        contentPane.add(decryptButton);
        decryptButton.setBounds(340, 90, 100, 30);

        contentPane.setPreferredSize(new Dimension(540, 410));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void Choose_rightActionPerformed(ActionEvent e) {
    }

    private void decryptButtonActionPerformed(ActionEvent e) {

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - MIshaPrigara
    private JButton Choose_left;
    private JLabel leftChooserLabel;
    private JComboBox choosedEncMethod;
    private JScrollPane scrollPaneLeft;
    private JEditorPane editorPaneLeft;
    private JButton encryptButton;
    private JSeparator separator1;
    private JButton Choose_right;
    private JLabel rightChooserLabel;
    private JButton decryptButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
