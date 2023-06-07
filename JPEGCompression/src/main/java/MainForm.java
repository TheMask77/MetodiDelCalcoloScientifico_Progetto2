import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainForm extends JFrame{

    private static int PANEL1_HEIGHT;
    private static int PANEL1_WIDTH;
    private static int PANEL2_HEIGHT;
    private static int PANEL2_WIDTH;
    private double F;
    private double d;
    private double dMax;
    private String imagePath;
    private String newImagePath;
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
    private JButton btnAPRI;
    private JLabel labIMAGE;

    public MainForm(){

        btnFILE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriFile();
                impostaImmagine(pnlIMAGE1, null);
            }
        });
        btnSTART.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlloCampi()){
                    PANEL2_HEIGHT = pnlIMAGE2.getHeight();
                    PANEL2_WIDTH = pnlIMAGE2.getWidth();
                    elaborazioneImmagine();
                }
            }
        });

        init(this);
        btnPULISCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlIMAGE1.repaint();
                pnlIMAGE2.repaint();
                txtFILE.setText("");
                txtPARAD.setText("");
                txtPARAF.setText("");
            }
        });
        btnAPRI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Desktop.getDesktop().open(new File(newImagePath));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void elaborazioneImmagine() {

        int F = Integer.parseInt(txtPARAF.getText());
        int d = Integer.parseInt(txtPARAD.getText());

        double[][] imageArray = Utils.getGrayLevelsMatrixFromFile(imagePath);
        ArrayList<double[][]> imageArraylist = Utils.getBlocksFromGrayscale(imageArray, F);
        ArrayList<double[][]> compressedBlocks = Compression.compress(imageArraylist, F, d);

        //dimensions adjustment
        int adjustedImageWidth = Utils.getImageWidth();
        int adjustedImageHeight = Utils.getImageHeight();
        adjustedImageWidth = adjustedImageWidth - (adjustedImageWidth % F);
        adjustedImageHeight = adjustedImageHeight - (adjustedImageHeight % F);

        double[][] adjustedImageMatrix = new double[adjustedImageWidth][adjustedImageHeight];
        Utils.getImageMatrixFromBlocks(adjustedImageMatrix, F, compressedBlocks, adjustedImageWidth, adjustedImageHeight);
        System.out.println();
        BufferedImage image = new BufferedImage(adjustedImageWidth, adjustedImageHeight, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < adjustedImageWidth; i++) {
            for(int j = 0; j < adjustedImageHeight; j++) {
                int grayLevelInt = (int) adjustedImageMatrix[i][j];
                Color grayLevel = new Color(grayLevelInt, grayLevelInt, grayLevelInt);
                image.setRGB(i, j, grayLevel.getRGB());
            }
        }

        newImagePath = "src/main/resources/ElaboratedImages/";
        newImagePath += imagePath.split("\\\\")[imagePath.split("\\\\").length - 1].split("\\.")[0] + ".jpeg";
        File outputfile = new File(newImagePath);
        try {
            if(!outputfile.exists()) {
                outputfile.createNewFile();
            }
            ImageIO.write(image, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        impostaImmagine(pnlIMAGE2, image);

        /*//File outputfile = new File("output.jpg");
        //ImageIO.write(image, "jpg", outputfile);

            File output = new File("GrayScale.jpg");
            pnlIMAGE2.getGraphics().drawImage(image, 0, 0, null);

            //ImageIO.write(image, "jpg", output);*/


    }

    private void init(MainForm mainForm){

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

    private void apriFile() {
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

    private void impostaImmagine(JPanel panel, BufferedImage imageOptional){
        BufferedImage image;
        File imageFile;
        Image scaledImage;
        int imageWidth;
        int imageHeight;
        int panelWidth;
        int panelHeight;
        int x;
        int y;
        double scaleFactor;

        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        panel.getGraphics().clearRect(0, 0, panelWidth, panelHeight);

        /*//JPANEL dimension set
        PANEL1_HEIGHT = pnlIMAGE1.getHeight();
        PANEL1_WIDTH = pnlIMAGE1.getWidth();*/

        if(imageOptional != null){
            image = imageOptional;
        }else{
            try {
                imageFile = new File(imagePath);
                image = ImageIO.read(imageFile);
            }catch (IOException e){
                return;
            }
        }

        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        if(imageWidth > panelWidth || imageHeight > panelHeight) {
            if(imageWidth > imageHeight){
                scaleFactor = (double) imageHeight/imageWidth;
                imageWidth = panelWidth;
                imageHeight = (int) (panelHeight * scaleFactor);
                x = 0;
                y = (panelHeight - imageHeight)/2;
            }else{
                scaleFactor = (double) imageWidth/imageHeight;
                imageHeight = panelHeight;
                imageWidth = (int) (panelHeight * scaleFactor);
                x = (panelWidth - imageWidth)/2;
                y = 0;
            }

            scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_REPLICATE);
            panel.getGraphics().drawImage(scaledImage, x, y, null);
        }else{
            x = (panelWidth - imageWidth)/2;
            y = (panelHeight - imageHeight)/2;
            panel.getGraphics().drawImage(image, x, y, null);
        }
    }

    private boolean controlloCampi(){

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
