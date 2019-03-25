import Encryptors.ExtendedPalette;
import Encryptors.LSB;
import Helpers.DecryptException;
import Helpers.EncryptException;
import UI.App;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    private static LSB lsb = new LSB();
    private static ExtendedPalette extendedPalette = new ExtendedPalette();

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        App frame = new App();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
