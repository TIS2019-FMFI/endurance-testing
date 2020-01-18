package DataProcessing;

import DatabaseConnector.ProduktInsert;
import DatabaseConnector.VerziaInsert;

public class Verzia {
	String Zeichnungsnummer; //seriove cislo
	String Verzia;
	Integer Id_objednavky;
	
	public Verzia(String zeichnungsnummer, String verzia, Integer id_objednavky) {
		Zeichnungsnummer = zeichnungsnummer;
		Verzia = verzia;
		Id_objednavky = id_objednavky;
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
		VerziaInsert pi = new VerziaInsert();
		pi.insert(Zeichnungsnummer, Verzia, Id_objednavky);
	}
	
	/*
	 * Vytvori sa 
	 * */
}
