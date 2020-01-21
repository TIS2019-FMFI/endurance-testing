package DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import DatabaseConnector.ProduktFinder;
import DatabaseConnector.ProduktInsert;
@RequestScoped
public class Produkt {
	//@Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;
	
	
	String Bezeichnung;	//umiestnenie dielu
	String Kunde; //zakaznik
	String Zeichnungsnummer; //seriove cislo
	String Nr;
	Integer Id;
	
	
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Produkt(String bezeichnung, String kunde, String zeichnungsnummer, String nr, DataSource datasource) {
		Bezeichnung = bezeichnung;
		Kunde = kunde;
		Zeichnungsnummer = zeichnungsnummer;
		Nr = nr;
		dataSource = datasource;
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
		Produkt novy = new Produkt(subor.getBezeichnung(), subor.getKunde(), subor.getZeichnungsnummer(), subor.getNr(), dataSource);
		if(novy.existuje()) {
			ProduktFinder pf = new ProduktFinder(dataSource);
			Integer id_objednavky = pf.produktIDFinder(Kunde, odstran_verziu_z_zeichnungsnummer(), Bezeichnung, Nr);
			Verzia verzia = new Verzia(Zeichnungsnummer, zisti_cislo_verzie(Zeichnungsnummer), id_objednavky, dataSource);
		}
		else {
			vytvorStrukturu();
		}
	}
	
	public boolean existuje() {
		/*Vytvorit ProduktFinder, ktorý bude mať funkciu find produkt by kunde, part of zeichnungsnummer a bezeichnung
		 * ak taky existuje vratime true inak false
		 * */
		ProduktFinder pf = new ProduktFinder(dataSource);
		return pf.produktFinder(Kunde, Zeichnungsnummer, Bezeichnung, Nr).size() != 0 ;
	}
	
	public void vytvorStrukturu() {
		/*vytvori novu strukturu pre dany produkt*/
		this.insert(Kunde, Zeichnungsnummer, Bezeichnung, Nr);
		String verzia = zisti_cislo_verzie(Zeichnungsnummer);	
		ProduktFinder pf = new ProduktFinder(dataSource);
		//Integer id_objednavky = pf.produktIDFinder(Kunde, odstran_verziu_z_zeichnungsnummer(), Bezeichnung, Nr);
		Verzia prvaVerzia = new Verzia(Zeichnungsnummer, verzia, Id, dataSource);
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
		try(PreparedStatement st = dataSource.getConnection().prepareStatement("Insert into produkt (Kunde, Bezeichnung, Zeichnungsnummer, NR) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
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
	
	public String odstran_verziu_z_zeichnungsnummer() {
		String vystup = "";
		vystup = Zeichnungsnummer.charAt(0) + Zeichnungsnummer.charAt(1) + Zeichnungsnummer.charAt(2)+ ".___";
		for(int i = 8; i < Zeichnungsnummer.length(); i++) {
			vystup += Zeichnungsnummer.charAt(i);
		}
		return vystup;
	}
	
}

