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
	@Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;
	
	public ProduktFinder() { }
	
	public List<Produkt> produktFinder(String kunde, String zeichnungsnummer, String bezeichnung){
		List<Produkt> zoznam = new ArrayList<Produkt>(); 
		try {
			java.sql.Connection c = dataSource.getConnection();
			PreparedStatement s = c.prepareStatement("Select * from produkt where Kunde = ? AND Zeichnungsnummer = ? and Bezeichnung = ?");
			s.setString(0, kunde);
			s.setString(1, zeichnungsnummer);
			s.setString(2, bezeichnung);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Produkt p = new Produkt("","","");
				p.setBezeichnung(rs.getString("Bezeichnung"));
				p.setKunde(rs.getString("Kunde"));
				p.setZeichnungsnummer(rs.getString("Zeichnungsnummer"));
				zoznam.add(p); 
			}
			return zoznam;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
