package UI;

import Encryptors.ExtendedPalette;
import Encryptors.LSB;
import Encryptors.Steganography;
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
    private File rightFile;
    private File output;


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

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            leftFile = fileChooser.getSelectedFile();
            String curText = leftFile.getName().toLowerCase();
            if(!curText.endsWith(".gif") && !curText.endsWith(".png")
                    && !curText.endsWith(".jpg") && !curText.endsWith(".jpeg")) {
                leftFile = null;
                leftChooserLabel.setText("Choose file");
                return;
            }
            curText = stringShortener(curText);
            leftChooserLabel.setText(curText);
        } else {
            leftFile = null;
            leftChooserLabel.setText("Choose file");
        }
    }

    private boolean STGEncryp(File input, String text) {
        Steganography steganography = new Steganography();
        String ext;
        if(input.getName().toLowerCase().endsWith("png")) {
            ext = "png";
        } else {
            ext = "jpg";
        }
        return steganography.encode(input.getPath().substring(0, input.getPath().lastIndexOf(File.separator))
                , input.getName().substring(0, input.getName().lastIndexOf("."))
                , ext, "Encrypted", text);
    }

    private String STGDecrypt(File input) {
        Steganography steganography = new Steganography();
        return steganography.decode(input.getPath(), input.getName());
    }


    private boolean LSBEncrypt(File input, String text) {
        LSB lsb = new LSB();
        try {
            lsb.Encrypt(input, output, text, (byte)1);
        } catch (EncryptException | IOException err) {
            JOptionPane.showMessageDialog(null, err);
            return false;
        }

        return true;
    }

    private String LSBDecrypt(File input) {
        LSB lsb = new LSB();
        String text = null;
        try {
            text = lsb.Decrypt(input, (byte)1);
        } catch (DecryptException | IOException err) {
            JOptionPane.showMessageDialog(null, err);
        }

        return text;
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
    }

    private String EPDecrypt(File input) {
        ExtendedPalette extendedPalette = new ExtendedPalette();
        String text = null;

        try {
            text = extendedPalette.Decrypt(input, (byte)1);
        } catch (IOException | DecryptException err) {
            JOptionPane.showMessageDialog(null, err);
        }

        return text;
    }

    private void createUIComponents() {

        choosedEncMethod = new JComboBox(encMethods);
        choosedDecMethod = new JComboBox(encMethods);
    }

    private void encryptButtonActionPerformed(ActionEvent e) {
        if(leftFile == null) {
            JOptionPane.showMessageDialog(null, "No file is chosen!");
            return;
        }

        String ext;
        if(leftFile.getName().toLowerCase().endsWith("png")) {
            if(!STGEncryp(leftFile, editorPaneLeft.getText())) return;
            output = new File("temp.png");
            ext = "png";
        } else if(choosedEncMethod.getSelectedItem().equals("LSB")) {
            if(!LSBEncrypt(leftFile, editorPaneLeft.getText())) return;
            output = new File("temp.gif");
            ext = "gif";
        } else {
            JOptionPane.showMessageDialog(null, "Maybe next time.");
            return;
        }


        JFileChooser fileSaver = new JFileChooser();

        if(leftFile.getName().toLowerCase().endsWith("gif")) {
            fileSaver.setSelectedFile(new File("Encrypted.gif"));
        } else if(leftFile.getName().toLowerCase().endsWith("png")) {
            fileSaver.setSelectedFile(new File("Encrypted.png"));
        } else if(leftFile.getName().toLowerCase().endsWith("jpg") ||
                    leftFile.getName().toLowerCase().endsWith("jpeg")) {
            fileSaver.setSelectedFile(new File("Encrypted.png"));
        }

//        fileSaver.showSaveDialog(null);
//
//        output.renameTo(fileSaver.getSelectedFile());
//        try {
//            Files.copy(output.toPath(), fileSaver.getSelectedFile().toPath());
//        } catch (IOException err) {
//            System.out.println(err);
//        }
//        if(ext.equals("gif")) {
//            output.renameTo(new File("temp.gif"));
//        }else {
//            output.renameTo(new File("temp.png"));
//        }
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
        scrollPane1 = new JScrollPane();
        resultArea = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();

        //======== this ========
        setResizable(false);
        setFont(new Font("Dialog", Font.ITALIC, 12));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- Choose_left ----
        Choose_left.setText("Choose file");
        Choose_left.addActionListener(e -> Choose_leftActionPerformed(e));
        contentPane.add(Choose_left);
        Choose_left.setBounds(125, 50, Choose_left.getPreferredSize().width, 25);

        //---- leftChooserLabel ----
        leftChooserLabel.setText("Choose file");
        leftChooserLabel.setFont(leftChooserLabel.getFont().deriveFont(leftChooserLabel.getFont().getSize() + 2f));
        contentPane.add(leftChooserLabel);
        leftChooserLabel.setBounds(15, 55, 95, leftChooserLabel.getPreferredSize().height);
        contentPane.add(choosedEncMethod);
        choosedEncMethod.setBounds(10, 105, 215, choosedEncMethod.getPreferredSize().height);

        //======== scrollPaneLeft ========
        {
            scrollPaneLeft.setViewportView(editorPaneLeft);
        }
        contentPane.add(scrollPaneLeft);
        scrollPaneLeft.setBounds(15, 155, 215, 120);

        //---- encryptButton ----
        encryptButton.setText("Encrypt");
        encryptButton.addActionListener(e -> encryptButtonActionPerformed(e));
        contentPane.add(encryptButton);
        encryptButton.setBounds(70, 295, 100, encryptButton.getPreferredSize().height);

        //---- separator1 ----
        separator1.setOrientation(SwingConstants.VERTICAL);
        contentPane.add(separator1);
        separator1.setBounds(260, 0, 20, 335);

        //---- Choose_right ----
        Choose_right.setText("Choose file");
        Choose_right.addActionListener(e -> Choose_rightActionPerformed(e));
        contentPane.add(Choose_right);
        Choose_right.setBounds(415, 50, Choose_right.getPreferredSize().width, 25);

        //---- rightChooserLabel ----
        rightChooserLabel.setText("Choose file");
        rightChooserLabel.setFont(rightChooserLabel.getFont().deriveFont(rightChooserLabel.getFont().getSize() + 2f));
        contentPane.add(rightChooserLabel);
        rightChooserLabel.setBounds(285, 55, 95, 17);

        //---- decryptButton ----
        decryptButton.setText("Decrypt");
        decryptButton.addActionListener(e -> decryptButtonActionPerformed(e));
        contentPane.add(decryptButton);
        decryptButton.setBounds(345, 150, 100, 30);
        contentPane.add(choosedDecMethod);
        choosedDecMethod.setBounds(285, 105, 215, choosedDecMethod.getPreferredSize().height);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(resultArea);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(290, 200, 215, 130);

        //---- label1 ----
        label1.setText("Encrypt");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 4f));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(95, 15), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("Decrypt");
        label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 4f));
        contentPane.add(label2);
        label2.setBounds(365, 15, 80, 19);

        contentPane.setPreferredSize(new Dimension(540, 410));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void Choose_rightActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            rightFile = fileChooser.getSelectedFile();
            String curText = rightFile.getName();
            if(!curText.endsWith(".gif") && !curText.endsWith(".png")
                    && !curText.endsWith(".jpg") && !curText.endsWith(".jpeg")) {
                rightFile = null;
                rightChooserLabel.setText("Choose file");
                return;
            }
            curText = stringShortener(curText);
            rightChooserLabel.setText(curText);
        } else {
            rightFile = null;
            rightChooserLabel.setText("Choose file");
        }
    }

    private void decryptButtonActionPerformed(ActionEvent e) {
        if(rightFile == null) {
            JOptionPane.showMessageDialog(null, "No file is chosen!");
            return;
        }

        if(rightFile.getName().toLowerCase().endsWith("png")) {
            resultArea.setText(STGDecrypt(rightFile));
            return;
        }

        if(choosedDecMethod.getSelectedItem().equals("LSB")) {
            resultArea.setText(LSBDecrypt(rightFile));
        } else {
            JOptionPane.showMessageDialog(null, "mnogo hochesh");
            return;
        }
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
    private JComboBox choosedDecMethod;
    private JScrollPane scrollPane1;
    private JTextArea resultArea;
    private JLabel label1;
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
