package DataProcessing;

import java.io.File;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import DatabaseConnector.VerziaInsert;

public class Verzia {
	String Zeichnungsnummer; //seriove cislo
	String Verzia;
	Integer Id_objednavky;
	DataSource dataSource;
	Integer Id;
	
	String ces = "";
	
	public Verzia(String zeichnungsnummer, String verzia, Integer id_objednavky, DataSource datasource, String cesta) {
		Zeichnungsnummer = zeichnungsnummer;
		Verzia = verzia;
		Id_objednavky = id_objednavky;
		dataSource = datasource;
		
		ces = cesta;
		this.pridajVerziu();
	}
	
	public String getZeichnungsnummer() {
		return Zeichnungsnummer;
	}
	public void setZeichnungsnummer(String zeichnungsnummer) {
		Zeichnungsnummer = zeichnungsnummer;
	}
	public String getVerzia() {
		return Verzia;
	}
	public void setVerzia(String verzia) {
		Verzia = verzia;
	}
	public Integer getId_objednavky() {			
		return Id_objednavky;
	}
	public void setId_objednavky(Integer id_objednavky) {
		Id_objednavky = id_objednavky;
	}
	
	public void pridajVerziu() {
		VerziaInsert pi = new VerziaInsert(dataSource);
		Id = pi.insert(Zeichnungsnummer, Verzia, Id_objednavky);
		String cesta = "F:\\Projekt\\" + ces + "\\" + Verzia + "\\";
		File dok = new File(cesta + "Dokumenty");
		dok.mkdir();
		dok = new File(cesta + "Signaly");
		dok.mkdir();
		dok = new File(cesta + "Fotodokumentacia");
		dok.mkdir();
		dok = new File(cesta + "Iteracia");
		dok.mkdir();
		dok = new File(cesta + "Program");
		dok.mkdir();
		
		cesta = "F:\\Projekt\\" + ces + "\\" + Verzia + "\\" + "Signaly" + "\\";
		dok = new File(cesta + "Originalny");
		dok.mkdir();
		dok = new File(cesta + "Editovany");
		dok.mkdir();
	}
	
	public void nahrajSubor(String filename, String cesta, String user) {
		String[] nazov = filename.split(".");
		VerziaInsert pi = new VerziaInsert(dataSource);
		pi.insertFile(nazov[0],"", nazov[1], cesta, user, Verzia);
	}
	
	public void nahrajSignal(String filename, String signal) {
		String[] nazov = filename.split(".");
		VerziaInsert pi = new VerziaInsert(dataSource);
		
	}
	public void nastavDatumTestovania(Date datum) {
		VerziaInsert pi = new VerziaInsert(dataSource);
		pi.insertDate(Zeichnungsnummer, datum, Id);
	}
	/*
	 * Vytvori sa 
	 * */
}
