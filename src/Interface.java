import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alessandro on 29/07/15.
 */
public class Interface extends JFrame {

    private JPanel interfacePanel;
    private JButton sfogliaFile;
    private JTextField file;
    private JTextField colonna;
    private JButton sfogliaImage;
    private JTextField image;
    private JButton close;
    private JButton rename;
    private JButton sfogliaFolder;
    private JTextField folder;
    private JRadioButton none;
    private JRadioButton two;
    private JRadioButton one;
    private JFileChooser fileChooser;
    private JFileChooser imageChooser;
    private JFileChooser folderChooser;
    private ButtonGroup type;

    public Interface(){

        setContentPane(interfacePanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        fileChooser = new JFileChooser();
        imageChooser = new JFileChooser();
        folderChooser = new JFileChooser();
        imageChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        type = new ButtonGroup();
        type.add(none);
        type.add(one);
        type.add(two);

        sfogliaFile.addActionListener(e -> showFileChooser());
        sfogliaImage.addActionListener(e -> showImageChooser());
        sfogliaFolder.addActionListener(e -> showFolderChooser());
        rename.addActionListener(e -> renameImage());

        onlyInt();

        close.addActionListener(e -> System.exit(0));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        pack();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        setVisible(true);
    }

    private void showFileChooser() {
        fileChooser.setDialogTitle("Seleziona il file excel");
        fileChooser.setApproveButtonText("Apri");

        int returnValue = fileChooser.showOpenDialog(null);
        String path = "";
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getPath();
        }

        file.setText(path);
    }

    private void showImageChooser() {
        imageChooser.setDialogTitle("Seleziona l'immagine");
        imageChooser.setApproveButtonText("Apri");

        int returnValue = imageChooser.showOpenDialog(null);
        String path = "";
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = imageChooser.getSelectedFile();
            path = selectedFile.getPath();
        }

        image.setText(path);
    }

    private void showFolderChooser() {

        folderChooser.setDialogTitle("Seleziona cartella immaigni");
        folderChooser.setApproveButtonText("Apri");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        folderChooser.setAcceptAllFileFilterUsed(false);

        String path;

        if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = folderChooser.getSelectedFile().getAbsolutePath();
        }
        else {
            path = "";
        }

        folder.setText(path);
    }

    private void onlyInt(){
        colonna.addKeyListener(new KeyAdapter() {
            private int countCar = 0;

            public void keyTyped(KeyEvent e) {
                countCar = colonna.getText().length();
                char c = e.getKeyChar();

                if (!((c >= '0') && (c <= '9') && countCar < 3) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
    }

    private void renameImage(){
        Map<String, String> dati = new HashMap();

        if (file.getText().equalsIgnoreCase("") || colonna.getText().equalsIgnoreCase("") || image.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Tutti i campi sono obbligatori!", "ERRORE",
                    JOptionPane.ERROR_MESSAGE);
        } else {

            String typeSelceted;

            if (none.isSelected()){
                typeSelceted = "none";
            } else if (one.isSelected()){
                typeSelceted = "one";
            } else {
                typeSelceted = "two";
            }

            dati.put("file", file.getText());
            dati.put("colonna", colonna.getText());
            dati.put("type", typeSelceted);
            dati.put("image", image.getText());
            dati.put("folder", folder.getText());

            ExcelReader excelReader = new ExcelReader(dati);

        }
    }
}
