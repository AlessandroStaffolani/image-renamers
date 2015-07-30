import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSDocumentPath;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alessandro on 29/07/15.
 */
public class ExcelReader {

    protected Map<String, String> dati;

    public ExcelReader(Map<String, String> data){
        dati = data;
        List<String> codici = readFile();
        if (codici.size() != 0){
            ImageRename rename = new ImageRename(codici, dati.get("image"), dati.get("folder"), dati.get("type"));
        } else {
            JOptionPane.showMessageDialog(null, "La colonna richiesta è vuota! Inseriscine un'altra!", "ATTENZIONE",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private List<String> readFile(){

        List<String> codici = new ArrayList<>();

        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(dati.get("file")));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            DataFormatter objDefaultFormat = new DataFormatter();
            FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {

                    cell = row.getCell(Integer.parseInt(dati.get("colonna")) - 1);
                    objFormulaEvaluator.evaluate(cell);
                    if(cell != null) {
                        codici.add(codici.size(), objDefaultFormat.formatCellValue(cell, objFormulaEvaluator));
                    }
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
            codici = null;
        }

        return codici;
    }
}
