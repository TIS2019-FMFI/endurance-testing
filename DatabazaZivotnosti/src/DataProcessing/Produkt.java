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
	
	
	public void nacitajStrukturu(String path) {
		ExcelReader subor = new ExcelReader();
		subor.readExcel(path);
		Produkt novy = new Produkt(subor.getBezeichnung(), subor.getKunde(), subor.getZeichnungsnummer());
		if(novy.existuje()) {
			/*pridame verziu*/
		}
		else {
			vytvorStrukturu();
		}
	}
	
	public boolean existuje() {
		/*Vytvorit ProduktFinder, ktorý bude mať funkciu find produkt by kunde, part of zeichnungsnummer a bezeichnung
		 * ak taky existuje vratime true inak false
		 * */
		return false;
	}
	
	public void vytvorStrukturu() {
		/*vytvori novu strukturu pre dany produkt*/
	}
	
}

