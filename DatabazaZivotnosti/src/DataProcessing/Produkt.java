package DataProcessing;

public class Produkt {
	String Bezeichnung;	//umiestnenie dielu
	String Kunde; //zakaznik
	String Zeichnungsnummer; //seriove cislo
	
	public Produkt(String bezeichnung, String kunde, String zeichnungsnummer) {
		Bezeichnung = bezeichnung;
		Kunde = kunde;
		Zeichnungsnummer = zeichnungsnummer;
	}
	
	public String getBezeichnung() {
		return Bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		Bezeichnung = bezeichnung;
	}
	public String getKunde() {
		return Kunde;
	}
	public void setKunde(String kunde) {
		Kunde = kunde;
	}
	public String getZeichnungsnummer() {
		return Zeichnungsnummer;
	}
	public void setZeichnungsnummer(String zeichnungsnummer) {
		Zeichnungsnummer = zeichnungsnummer;
	}
	
	
	public void vytvorStrukturu(String path) {
		ExcelReader subor = new ExcelReader();
		subor.readExcel(path);
		Produkt novy = new Produkt(subor.getBezeichnung(), subor.getKunde(), subor.getZeichnungsnummer());
		
	}
	
	public boolean existuje(Produkt produkt) {
		
		return false;
	}
}

