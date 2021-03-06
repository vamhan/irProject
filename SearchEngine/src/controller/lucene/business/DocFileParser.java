package controller.lucene.business;

import java.io.FileInputStream;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DocFileParser {
    public String DocFileContentParser(String fileName) {
        POIFSFileSystem fs = null;
        try {
           
            if (fileName.endsWith(".xls")) { //if the file is excel file
                ExcelExtractor ex = new ExcelExtractor(fs);
                return ex.getText(); //returns text of the excel file
            } else if (fileName.endsWith(".ppt")) { //if the file is power point file
                PowerPointExtractor extractor = new PowerPointExtractor(fs);
                return extractor.getText(); //returns text of the power point file

            }
            
            //else for .doc file
            fs = new POIFSFileSystem(new FileInputStream(fileName));
            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            return we.getText();//if the extension is .doc
        } catch (Exception e) {
            System.out.println("document file cant be indexed");
        }
        return "";
    }
}
