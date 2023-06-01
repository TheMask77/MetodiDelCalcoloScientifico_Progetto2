import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Myframe extends JFrame {

    JButton button;

    Myframe() {
        //Frame Init
        this.setTitle("JPEGCompression");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Size settings
        this.setResizable(false);
        this.setSize(1280, 720);
        this.setLayout(null);

        //Icon setting
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/logo_bicocca_vector.png"));
        this.setIconImage(frameIcon.getImage());

        //File explorer button init
        button = new JButton();
        button.setBounds(200, 100, 100, 50);
        button.setFocusable(false);
        button.setText("Seleziona una foto");

        ImageIcon explorerIcon = new ImageIcon(getClass().getResource("/file_explorer.png"));
        Image scaledExplorerIcon = explorerIcon.getImage().getScaledInstance(26, 21, java.awt.Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledExplorerIcon));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello World!");
            }
        });
        this.add(button);

        //Color settings
        this.getContentPane().setBackground(Color.LIGHT_GRAY);

        //Display the frame
        this.setVisible(true);
    }
}
