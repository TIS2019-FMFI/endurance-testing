package DataProcessing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
	
	private static String filePath;
	private static File file;
	private static String kunde = "q";
	private static String bezeichnung = "q";
	private static String zeichnungsnummer = "q";
	private static String nr = "q";
	private static String index = "q";
	
	public static void readExcel(String path) {
		filePath = path;
		doReadExcel();
	}
	
	private static boolean fileExist() {
		File f = new File(filePath);
		if(f.exists() && !f.isDirectory()) { 
			String compare = filePath.substring(filePath.length() - 4);
			if(compare.equals(".xls") || compare.equals("xlsx")) {
				file = new File(filePath);
			    return true;
			}
		}
		return false;
	}
	
	private static void doReadExcel() {
		if (fileExist()) {	
		    try {	
		    	try {
		    		//Workbook workbook = WorkbookFactory.create(file);
		    		XSSFWorkbook tab = null;
		    		FileInputStream file = new FileInputStream(filePath);
		    		tab = new XSSFWorkbook(file);
					
					kunde = tab.getSheetAt(0).getRow(2).getCell(0).toString();
					bezeichnung = tab.getSheetAt(0).getRow(2).getCell(3).toString();
					zeichnungsnummer = tab.getSheetAt(0).getRow(2).getCell(6).toString();
					nr = tab.getSheetAt(0).getRow(0).getCell(1).toString();
					index = tab.getSheetAt(0).getRow(0).getCell(3).toString();
		    		
		    	}
		    	catch (FileNotFoundException e) {
		    		JOptionPane.showMessageDialog(null, "Zadan� s�bor sa pou��va. Zatvorte ho pros�m");
		    	}
			} catch (EncryptedDocumentException  | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else {
			//JOptionPane.showMessageDialog(null, "Zadan� s�bor neexistuje");
		}
		
	}
	
	public static String getKunde() {
		return kunde.toString();
	}
	
	public static String getBezeichnung() {
		return bezeichnung.toString();
	}
	
	public static String getZeichnungsnummer() {
		return zeichnungsnummer.toString();
	}
	
	public static String getNr() {
		return nr.toString();
	}
	
	public static String getIndex() {
		return index.toString();
	}
	
	public static void main(String[] args) {
		
	    //readExcel("C:\\Users\\Koso\\Downloads\\AA_477015_11_outer_bush_zaoblene_IT (2).xlsx");
	    System.out.println(getNr());
	    System.out.println(getIndex());
	}
}


