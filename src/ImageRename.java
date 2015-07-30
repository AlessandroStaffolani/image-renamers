import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Created by alessandro on 29/07/15.
 */
public class ImageRename {

    protected List<String> newNames;
    Path source; //original file
    Path targetDir;

    public ImageRename(List<String> codici, String path, String destination){
        newNames = codici;
        source = Paths.get(path);
        targetDir = Paths.get(destination);

        if (codici != null) {
            rename();
        }
    }

    private void rename(){

        for (String name : newNames) {
            try {

                name = name.concat("." + getExtension(source.toString()));

                Path target = targetDir.resolve(name);// create new path ending with `name` content
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                // I decided to replace already existing files with same name
            } catch (IOException e) {

            }
        }
        JOptionPane.showMessageDialog(null, "Immagini create e salvate in: " + targetDir, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String getExtension(String str){
        int dot = str.lastIndexOf(".");
        return str.substring(dot + 1);
    }
}
