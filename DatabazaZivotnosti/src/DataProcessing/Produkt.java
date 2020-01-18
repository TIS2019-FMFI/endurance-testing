package DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import DatabaseConnector.ProduktFinder;
import DatabaseConnector.ProduktInsert;

public class Produkt {
	String Bezeichnung;	//umiestnenie dielu
	String Kunde; //zakaznik
	String Zeichnungsnummer; //seriove cislo
	String Nr;
	
	Integer Id;
	
	@Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Produkt(String bezeichnung, String kunde, String zeichnungsnummer, String nr) {
		Bezeichnung = bezeichnung;
		Kunde = kunde;
		Zeichnungsnummer = zeichnungsnummer;
		Nr = nr;
	}
	
	public String getNr() {
		return Nr;
	}

	public void setNr(String nr) {
		Nr = nr;
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
		Produkt novy = new Produkt(subor.getBezeichnung(), subor.getKunde(), subor.getZeichnungsnummer(), subor.getNr());
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
		ProduktFinder pf = new ProduktFinder();
		return pf.produktFinder(Kunde, Zeichnungsnummer, Bezeichnung, Nr).size() != 0 ;
	}
	
	public void vytvorStrukturu() {
		/*vytvori novu strukturu pre dany produkt*/
		this.insert(Kunde, Zeichnungsnummer, Bezeichnung, Nr);

		String verzia = zisti_cislo_verzie(Zeichnungsnummer);	
		ProduktFinder pf = new ProduktFinder();
		Integer id_objednavky = pf.produktIDFinder(Kunde, Zeichnungsnummer, Bezeichnung, Nr);
		Verzia prvaVerzia = new Verzia(Zeichnungsnummer, verzia, id_objednavky);
	}

	private String zisti_cislo_verzie(String zeichnungsnummer) {
		String verzia = "";
		if (zeichnungsnummer.contains("-")){
			/*este nevieme*/
		}
		else{
			verzia += zeichnungsnummer.charAt(4) + zeichnungsnummer.charAt(5) + zeichnungsnummer.charAt(6);
		}
		return verzia;
	}
	
	public void insert(String kunde, String zeichnungsnummer, String bezeichnung, String nr){
		try(PreparedStatement st = dataSource.getConnection().prepareStatement("Insert into produkt (Kunde, Bezeichnung,Zeichnungsnummer, NR) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
			st.setString(1, kunde);
			st.setString(2, bezeichnung);
			st.setString(3, zeichnungsnummer);
			st.setString(4, nr);
			st.executeUpdate();
			try (ResultSet r = st.getGeneratedKeys()) {
                r.next();
                Id = r.getInt(1);
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

