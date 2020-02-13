package DataProcessing;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.ws.rs.core.MultivaluedMap;

import DatabaseConnector.ProduktFinder;

public class Filter {
	private DataSource dataSource;
	String FromDate = "";
	String ToDate = "";
	String Kunde = "";
	String Zeichnungsnummer = "";
	String Bezeichnung = ""; 
	String zostava = ""; 
	String nrNumber = "";
	
	String whereFilter = "";
	
	public Filter(MultivaluedMap<String, String> headerParams, DataSource datasource) {
		FromDate = headerParams.getFirst("FromDate");
		ToDate = headerParams.getFirst("ToDate");
		Kunde = headerParams.getFirst("Kunde");
		Zeichnungsnummer = headerParams.getFirst("Zeichnungsnummer");
		Bezeichnung = headerParams.getFirst("Bezeichnung"); 
		zostava = headerParams.getFirst("zostava");
		nrNumber = headerParams.getFirst("nrNumber");
		
		dataSource = datasource;
	} 

	public List<HashMap<String, String>> vytvorWhere() {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");			
		Boolean from = false;
		Boolean to = false;
	    try {
	    	
	    	Date datum1 = null;
	    	Date datum2 = null;
	    	
	    	if (FromDate.length() != 0) {
	    		datum1 = format.parse(FromDate);
	    		FromDate = format2.format(datum1);
	    		from = true;
	        }
	        if (ToDate.length() != 0) {
	        	format.parse(ToDate);
	        	datum2 = format.parse(ToDate);
	        	ToDate = format2.format(datum2);
	        	to = true;
	        }
	        
	        if ((datum1 != null && datum2 != null) && (datum1.after(datum2))) {
	        	return null;
	        }

	    } catch (ParseException e1) {
	    	return null;
	        //JOptionPane.showMessageDialog(null, badDateFormat);
	    }
	    String[] varNames = {"FromDate", "ToDate", "p.Kunde", "v.Zeichnungsnummer", "p.Bezeichnung", "p.zostava", "p.nrNumber"};
 		String[] values = {FromDate, ToDate, Kunde, Zeichnungsnummer, Bezeichnung, zostava, nrNumber};
 		for (int i=0; i < 7; i++) {
			if (values[i].length() > 0) {
				if (i == 0 && from) {
					whereFilter = "v.Datum_testovania" + " >= '" + values[i] + "'::DATE AND ";
				}
				else if (i == 1 && to) {
					whereFilter +=  "v.Datum_testovania" + " <= '" + values[i] + "'::DATE AND ";
				}
				else if (i > 1) {
					if (values[i].contains(",")) {
						whereFilter += separator(values[i], varNames[i]) + " AND ";
					}
					else {
						whereFilter +=   varNames[i] + " = '" + values[i] + "' AND ";
					}
				}
			}
		}
 		String pom = "";
		if (whereFilter != "") {
			whereFilter = whereFilter.substring(0, whereFilter.length() - 5);
			pom = " Where " + whereFilter;
		}
		
		ProduktFinder pf = new ProduktFinder(dataSource);
		return pf.produktAllFinder(pom);
	}
	
	
	private static String separator(String text, String varName) {
		String result = "";
		String[] parts = text.split(",");
		for (int i = 0; i < parts.length; i++) {
			result = result + varName + " = '" + parts[i].trim() + "' OR "; 
		}
		result = result.substring(0, result.length() - 4);
		return result;
	}
}
