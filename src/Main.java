import javax.swing.*;

/**
 * Created by alessandro on 29/07/15.
 */
public class Main {

    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        Interface program = new Interface();

    }
}
