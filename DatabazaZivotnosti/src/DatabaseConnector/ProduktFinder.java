package DatabaseConnector;

import java.sql.Connection;
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
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select * from produkt where Kunde = ? AND Zeichnungsnummer = ? and Bezeichnung = ? and NR = ?");
			s.setString(1, kunde);
			s.setString(2, zeichnungsnummer);
			s.setString(3, bezeichnung);
			s.setString(4, nr);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Produkt p = new Produkt("","","","",dataSource,"");
				p.setBezeichnung(rs.getString("Bezeichnung"));
				p.setKunde(rs.getString("Kunde"));
				p.setZeichnungsnummer(rs.getString("Zeichnungsnummer"));
				p.setNr(rs.getString("NR"));
				p.setId(rs.getInt("Id"));
				p.setZostava(rs.getString("Zostava"));
				zoznam.add(p); 
			}
			return zoznam;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (s != null) s.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return zoznam;
	}
	
	public Integer produktIDFinder(String kunde, String zeichnungsnummer, String bezeichnung, String nr){
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select Id from produkt where Kunde = ? AND Zeichnungsnummer = ? and Bezeichnung = ? and NR = ?");
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
		finally {
			try { if (s != null) s.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return null;
	}
	
	public List<Produkt> produktAllFinder(String where) {
		Connection c=null;
		PreparedStatement s = null;
		List<Produkt> zoznam = new ArrayList<Produkt>(); 	
		String sq = "";
		sq = "Select * from produkt " + where;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement(sq);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Produkt p = new Produkt("","","","",dataSource,"");
				p.setBezeichnung(rs.getString("Bezeichnung"));
				p.setKunde(rs.getString("Kunde"));
				p.setZeichnungsnummer(rs.getString("Zeichnungsnummer"));
				p.setNr(rs.getString("NR"));
				p.setId(rs.getInt("Id"));
				p.setZostava(rs.getString("Zostava"));
				zoznam.add(p); 
			}
			return zoznam;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (s != null) s.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return null;
	}
}
