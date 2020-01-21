package DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import DataProcessing.Produkt;

public class ProduktFinder {
	private DataSource dataSource;
	
	public ProduktFinder(DataSource datasource) { 
		dataSource = datasource;
	}
	
	public List<Produkt> produktFinder(String kunde, String zeichnungsnummer, String bezeichnung, String nr){
		List<Produkt> zoznam = new ArrayList<Produkt>(); 	
		try {
			java.sql.Connection c = dataSource.getConnection();
			PreparedStatement s = c.prepareStatement("Select * from produkt where Kunde = ? AND Zeichnungsnummer LIKE ? and Bezeichnung = ? and NR = ?");
			s.setString(1, kunde);
			s.setString(2, zeichnungsnummer);
			s.setString(3, bezeichnung);
			s.setString(4, nr);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Produkt p = new Produkt("","","","",dataSource);
				p.setBezeichnung(rs.getString("Bezeichnung"));
				p.setKunde(rs.getString("Kunde"));
				p.setZeichnungsnummer(rs.getString("Zeichnungsnummer"));
				p.setNr(rs.getString("NR"));
				p.setId(rs.getInt("Id"));
				zoznam.add(p); 
			}
			return zoznam;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer produktIDFinder(String kunde, String zeichnungsnummer, String bezeichnung, String nr){
		try {
			java.sql.Connection c = dataSource.getConnection();
			PreparedStatement s = c.prepareStatement("Select Id from produkt where Kunde = ? AND Zeichnungsnummer = ? and Bezeichnung = ? and NR = ?");
			s.setString(1, kunde);
			s.setString(2, zeichnungsnummer);
			s.setString(3, bezeichnung);
			s.setString(4, nr);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getInt("Id");
			}
			return -99;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
