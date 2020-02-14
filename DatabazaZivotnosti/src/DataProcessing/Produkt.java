package DataProcessing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import DatabaseConnector.LogInsert;
import DatabaseConnector.ProduktFinder;

public class Produkt {
	// @Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;

	String Bezeichnung; // umiestnenie dielu
	String Kunde; // zakaznik
	String Zeichnungsnummer; // seriove cislo
	String Nr;
	Integer Id;
	String Zostava;
	String Verzia = "";
	String povZei = "";

	String user = "";

	public String getZostava() {
		return Zostava;
	}

	public void setZostava(String zostava) {
		Zostava = zostava;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Produkt(String bezeichnung, String kunde, String zeichnungsnummer, String nr, DataSource datasource,
			String zostava) {
		Bezeichnung = bezeichnung;
		Kunde = kunde;
		povZei = zeichnungsnummer;
		Zeichnungsnummer = zeichnungsnummer;
		Nr = nr;
		dataSource = datasource;
		Zostava = zostava;
		if (Zeichnungsnummer.length() != 0) {
			Zeichnungsnummer = odstran_verziu_z_zeichnungsnummer(zeichnungsnummer);
		}
	}

	public void setUser(String uname) {
		user = uname;
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
		if (zeichnungsnummer.length() != 0) {
			Zeichnungsnummer = odstran_verziu_z_zeichnungsnummer(zeichnungsnummer);
		} else {
			Zeichnungsnummer = zeichnungsnummer;
		}
	}

	public void nacitajStrukturu() throws IOException {
		if (existuje()) {
			File file = new File("C:\\Users\\Koso\\Desktop\\tisT\\cesta.txt");
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String st = br.readLine();
				String cesta = st + Zeichnungsnummer;
				File ver = new File(cesta + "\\" + Verzia);
				ver.mkdir();
				Verzia verzia = new Verzia(povZei, Verzia, Id, dataSource, cesta, user);
				verzia.pridajVerziu();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			vytvorStrukturu();
		}
	}

	public boolean existuje() {
		ProduktFinder pf = new ProduktFinder(dataSource);
		List<Produkt> zoz = pf.produktFinder(Kunde, Zeichnungsnummer, Bezeichnung, Nr);
		if (zoz.size() == 0) {
			return false;
		} else {
			Produkt p = zoz.get(0);
			Id = p.getId();
			return true;
		}
	}

	public void vytvorStrukturu() throws IOException {
		this.insert(Kunde, Zeichnungsnummer, Bezeichnung, Nr, Zostava);
		File file = new File("F:\\EnduranceTesting\\Server222\\wildfly-18.0.1.Final\\welcome-content\\BOGE\\cesta.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st = br.readLine();
			String cesta = st + Zeichnungsnummer;
			File pro = new File(cesta);
			pro.mkdir();
			File ver = new File(cesta + "\\" + Verzia);
			ver.mkdir();
			Verzia verzia = new Verzia(povZei, Verzia, Id, dataSource, cesta, user);
			verzia.pridajVerziu();
			}catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

	public String odstran_verziu_z_zeichnungsnummer(String zeichnungsnummer) {
		String vystup = "";
		vystup += "" + zeichnungsnummer.charAt(0) + zeichnungsnummer.charAt(1) + zeichnungsnummer.charAt(2) + ".___.";
		if (zeichnungsnummer.contains("-")) {
			Verzia += "100";
		} else {
			Verzia += "" + zeichnungsnummer.charAt(4) + zeichnungsnummer.charAt(5) + zeichnungsnummer.charAt(6);
		}
		for (int i = 8; i < zeichnungsnummer.length(); i++) {
			vystup += zeichnungsnummer.charAt(i);
		}
		return vystup;
	}

	public void nastavZostavu(String zostava) {
		Zostava = zostava;
		update();
	}

	public void insert(String kunde, String zeichnungsnummer, String bezeichnung, String nr, String zostava) {
		Connection c = null;
		PreparedStatement st = null;
		try {
			c = dataSource.getConnection();
			st = c.prepareStatement(
					"Insert into produkt (Kunde, Bezeichnung, Zeichnungsnummer, NR, Zostava) values (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, kunde);
			st.setString(2, bezeichnung);
			st.setString(3, zeichnungsnummer);
			st.setString(4, nr);
			st.setString(5, zostava);
			st.executeUpdate();

			LogInsert li = new LogInsert(dataSource);
			li.insert(user, Zeichnungsnummer, "insert");
			try (ResultSet r = st.getGeneratedKeys()) {
				r.next();
				Id = r.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		Connection c = null;
		PreparedStatement st = null;
		try {
			c = dataSource.getConnection();
			st = c.prepareStatement("UPDATE produkt Zostava = ? Where id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, Zostava);
			st.setInt(2, Id);
			st.executeUpdate();
			try (ResultSet r = st.getGeneratedKeys()) {
				r.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
