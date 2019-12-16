package DataProcessing;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
//import com.sun.java.util.jar.pack.Package.File;

public class ExcelReader {

private static String filePath;
private static File file;
private static String kunde = "q";
private static String bezeichnung = "q";
private static String zeichnungsnummer = "q";

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
			Workbook workbook = WorkbookFactory.create(file);
			
			kunde = workbook.getSheetAt(0).getRow(2).getCell(0).toString();
			bezeichnung = workbook.getSheetAt(0).getRow(2).getCell(3).toString();
			zeichnungsnummer = workbook.getSheetAt(0).getRow(2).getCell(6).toString();

		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	else {
		System.out.println("Toto neexistuje ty kokote");
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

public static void main(String[] args) {
	//System.out.println(getKunde());
    readExcel("C:\\Users\\Koso\\Downloads\\AA_477015_11_outer_bush_zaoblene_IT (2).xlsx");
}
}