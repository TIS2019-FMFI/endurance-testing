package DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import DataProcessing.Dokument;
import DataProcessing.Produkt;
import DataProcessing.Signal;

public class FileFinder {
	private DataSource dataSource;
	
	public FileFinder(DataSource datasource) {
		dataSource = datasource;
	}
	
	public String fileFinder(String nazov, String verzia){
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select relativna_cesta from subory_k_verzii where dokument_originalny_nazov = ? AND Verzia = ? ");
			s.setString(1, nazov);
			s.setString(2, verzia);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getString(1) + "\\" +nazov; 
			}
			return "";
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (s != null) s.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return "";
	}
	
	public String signalFinder(String nazov, String verzia){
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select Cesta from signaly where Subor = ? AND Verzia = ? ");
			s.setString(1, nazov);
			s.setString(2, verzia);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				return rs.getString(1) + "\\" +nazov; 
			}
			return "";
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (s != null) s.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return "";
	}
	
	
	public List<Dokument> fileFinderByType(String typ, String verzia){
		List<Dokument> zoznam = new ArrayList<Dokument>();
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select * from subory_k_verzii where typ_suboru = ? AND Verzia = ? ");
			s.setString(1, typ);
			s.setInt(2, Integer.parseInt(verzia));
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Dokument dok = new Dokument("", "", "", 0);
				dok.setDokument_nazov(rs.getString("dokument_originalny_nazov"));
				dok.setRelativna_cesta(rs.getString("relativna_cesta"));
				dok.setTyp_suboru(rs.getString("typ_suboru"));
				dok.setVerzia(rs.getInt("Verzia"));
				zoznam.add(dok); 
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
	
	public List<Signal> signalFinderByType(String typ, String verzia){
		List<Signal> zoznam = new ArrayList<Signal>();
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select * from signaly"); //doplnit podmienku Where typ a verzie
			//s.setString(1, typ);
			//s.setInt(2, Integer.parseInt(verzia));
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Signal dok = new Signal("", "", 0);
				dok.setSignal(rs.getString("Signal"));
				dok.setSubor(rs.getString("Subor"));
				dok.setVerzia(rs.getInt("Verzia"));
				zoznam.add(dok); 
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
}
