import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainForm extends JFrame{

    private double F;
    private double d;
    private double dMax;
    private String file;
    private JButton btnFILE;
    private JPanel pnlMAIN;
    private JTextField txtFILE;
    private JTextField txtPARAF;
    private JTextField txtPARAD;
    private JButton btnSTART;
    private JLabel labFILE;
    private JLabel labPARAF;
    private JLabel labPARAD;
    private JPanel pnlIMAGE;
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
                    //TODO: se il controllo campi va a buon fine
                    //TODO: richiamo l'algoritmo e eseguo la dct
                }
            }
        });

        Init(this);
    }

    private void Init(MainForm mainForm){
        //Form Init
        mainForm.setContentPane(mainForm.pnlMAIN);
        mainForm.setTitle("JPEGCompression");
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.setSize(400, 500);

        //Form icon setting
        ImageIcon frameIcon = new ImageIcon(mainForm.getClass().getResource("/logo_bicocca_vector.png"));
        mainForm.setIconImage(frameIcon.getImage());

        mainForm.setResizable(false);
        mainForm.setVisible(true);
    }

    private void ApriFile() {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "\\desktop");
        chooser.setFileFilter(new FileNameExtensionFilter("bitmap", "bmp"));
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File chosenFile = chooser.getSelectedFile();
            if (chosenFile == null) {
                return;
            }

            file = chooser.getSelectedFile().getAbsolutePath();
            txtFILE.setText(file);
        }
    }

    private void ImpostaImmagine(){

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
