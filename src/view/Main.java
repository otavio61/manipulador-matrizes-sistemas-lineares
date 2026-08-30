package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        MatrizSistemaFrame m = new MatrizSistemaFrame();
        m.setSize(900, 500);
        m.setLocationRelativeTo(null);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        m.setIconImage(new ImageIcon(Main.class.getResource("/icons/somatorio.png")).getImage());

        m.setVisible(true);
    }
}
