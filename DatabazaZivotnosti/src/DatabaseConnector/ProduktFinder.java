package DatabaseConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public List<HashMap<String, String>> produktAllFinder(String where) {
		Connection c=null;
		PreparedStatement s = null;
		List<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>(); 	
		String sq = "";
		sq = "Select p.Zeichnungsnummer, p.Kunde, p.Bezeichnung, p.NR, p.Zostava, v.Zeichnungsnummer, v.Verzia, v.Datum_testovania from produkt p inner join verzia v ON v.Id_objednavky = p.Id" + where;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement(sq);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				HashMap<String,String> hm = new HashMap<String, String>();
				hm.put("p.Zeichnungsnummer", rs.getString("p.Zeichnungsnummer"));
				hm.put("Bezeichnung", rs.getString("p.Bezeichnung"));
				hm.put("Kunde", rs.getString("p.Kunde"));
				hm.put("NR", rs.getString("p.NR"));
				hm.put("Zostava", rs.getString("p.Zostava"));
				hm.put("v.Zeichnungsnummer", rs.getString("v.Zeichnungsnummer"));
				hm.put("Verzia", rs.getString("v.Verzia"));
				hm.put("Datum", rs.getString("v.Datum_testovania"));
				zoznam.add(hm); 
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

	public void SetZostava(String zeichnungsnummer, String zostava, String user) {
		Connection c = null;
		PreparedStatement st = null;	
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("UPDATE produkt SET Zostava = ? Where Zeichnungsnummer = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, zostava);
			st.setString(2, zeichnungsnummer);
			st.executeUpdate();
			
			LogInsert li = new LogInsert(dataSource);
			li.insert(user,  zeichnungsnummer, "nastavenie zostavy");
			
			try (ResultSet r = st.getGeneratedKeys()) {
                r.next();
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
	}
}
