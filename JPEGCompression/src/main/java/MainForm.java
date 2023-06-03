import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainForm extends JFrame{

    private static int PANEL1_HEIGHT;
    private static int PANEL1_WIDTH;
    private static int PANEL2_HEIGHT;
    private static int PANEL2_WIDTH;
    private double F;
    private double d;
    private double dMax;
    private String imagePath;
    private String latestPath;
    private JButton btnFILE;
    private JPanel pnlMAIN;
    private JTextField txtFILE;
    private JTextField txtPARAF;
    private JTextField txtPARAD;
    private JButton btnSTART;
    private JLabel labFILE;
    private JLabel labPARAF;
    private JLabel labPARAD;
    private JPanel pnlIMAGE1;
    private JPanel pnlIMAGE2;
    private JButton btnPULISCI;
    private JLabel labIMAGE;

    public MainForm(){

        btnFILE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApriFile();
                ImpostaImmagine();
            }
        });
        btnSTART.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ControlloCampi()){
                    PANEL2_HEIGHT = pnlIMAGE2.getHeight();
                    PANEL2_WIDTH = pnlIMAGE2.getWidth();
                    //TODO: se il controllo campi va a buon fine
                    //TODO: richiamo l'algoritmo e eseguo la dct
                }
            }
        });

        Init(this);
    }

    private void Init(MainForm mainForm){

        //Variable init
        latestPath = "";

        //Form Init
        mainForm.setContentPane(mainForm.pnlMAIN);
        mainForm.setTitle("JPEGCompression");
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.setSize(1280, 720);

        //Form icon setting
        ImageIcon frameIcon = new ImageIcon(mainForm.getClass().getResource("/logo_bicocca_vector.png"));
        mainForm.setIconImage(frameIcon.getImage());

        mainForm.setResizable(false);
        mainForm.setVisible(true);
    }

    private void ApriFile() {
        JFileChooser chooser;
        if (latestPath.isEmpty()){
            chooser = new JFileChooser(System.getProperty("user.home") + "\\desktop");
        }else{
            chooser = new JFileChooser(latestPath);
        }

        chooser.setFileFilter(new FileNameExtensionFilter("bitmap", "bmp"));
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File chosenFile = chooser.getSelectedFile();
            if (chosenFile == null) {
                return;
            }

            imagePath = chooser.getSelectedFile().getAbsolutePath();
            latestPath = chooser.getSelectedFile().getPath();
            txtFILE.setText(imagePath);
        }
    }

    private void ImpostaImmagine(){
        BufferedImage image;
        File imageFile;
        Image scaledImage;
        int width;
        int height;
        int x;
        int y;
        double scaleFactor;

        //JPANEL dimension set
        PANEL1_HEIGHT = pnlIMAGE1.getHeight();
        PANEL1_WIDTH = pnlIMAGE1.getWidth();

        try {
            int pnlWidth = pnlIMAGE1.getWidth();
            int pnlHeight = pnlIMAGE1.getHeight();
            imageFile = new File(imagePath);
            image = ImageIO.read(imageFile);
            width = image.getWidth();
            height = image.getHeight();

            if(width > 500 || height > 500) {
                if(width > height){
                    scaleFactor = (double) height/width;
                    width = PANEL1_WIDTH;
                    height = (int) (PANEL1_WIDTH * scaleFactor);
                    x = 0;
                    y = (PANEL1_HEIGHT - height)/2;
                }else{
                    scaleFactor = (double) width/height;
                    height = PANEL1_HEIGHT;
                    width = (int) (PANEL1_HEIGHT * scaleFactor);
                    x = (PANEL1_WIDTH - width)/2;
                    y = 0;
                }

                scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                pnlIMAGE1.getGraphics().drawImage(scaledImage, x, y, null);
            }else{
                x = (PANEL1_WIDTH - width)/2;
                y = (PANEL1_HEIGHT - height)/2;
                pnlIMAGE1.getGraphics().drawImage(image, x, y, null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ControlloCampi(){

        if(!txtFILE.getText().trim().isEmpty()){
            File file = new File(txtFILE.getText());
            if(!file.exists()) return false;
        } else return false;

        if (!txtPARAF.getText().trim().isEmpty()){
            try{
                F = Double.parseDouble(txtPARAF.getText());
            }catch (NumberFormatException e){
                return false;
            }
        } else return false;

        if(!txtPARAD.getText().trim().isEmpty()){
            dMax = 2 * F - 2;
            try{
                d = Double.parseDouble(txtPARAD.getText());
            }catch (NumberFormatException e){
                return false;
            }
        } else return false;

        return true;
    }
}
