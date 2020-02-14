package DatabaseConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import DataProcessing.Produkt;
import DataProcessing.Verzia;

public class VerziaInsert {
	private DataSource dataSource;
	
	public VerziaInsert(DataSource datasource) {
		dataSource = datasource;
	}
	
	public Integer insert(String zeichnungsnummer, String verzia, Integer id_objednavky, String user){
		Connection c=null;
		PreparedStatement st = null;	
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into verzia (Zeichnungsnummer,Verzia, Id_objednavky) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, zeichnungsnummer);
			st.setString(2, verzia);
			st.setInt(3, id_objednavky);
			st.executeUpdate();

			LogInsert li = new LogInsert(dataSource);
			li.insert(user, zeichnungsnummer, "insert");
			try (ResultSet r = st.getGeneratedKeys()) {
                r.next();
                return r.getInt(1);
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return -99;
	}
	
	
	public Integer findId(String zeichnungsnummer){
		Connection c=null;
		PreparedStatement st = null;	
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Select Id from verzia where Zeichnungsnummer = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, zeichnungsnummer);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				return rs.getInt("Id");
			}
			return -99;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return null;
	}
	
	
	public void insertFile(String nazov,String relnazov, String typ, String cesta, String user, String verzia) {
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into subory_k_verzii (dokument_originalny_nazov, relativna_cesta, typ_suboru, Verzia ) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, nazov);
			st.setString(2, cesta);
			st.setString(3, typ);
			st.setString(4, verzia);
			st.executeUpdate();
			
			LogInsert li = new LogInsert(dataSource);
			li.insert(user, nazov, "insert");
			
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
	
	public void insertDate( java.util.Date datum, String id, String user) {
		Connection c = null;
		PreparedStatement st = null;	
		try{
			
			c = dataSource.getConnection();
			st = c.prepareStatement("UPDATE verzia SET Datum_testovania = ? Where Zeichnungsnummer = ?", Statement.RETURN_GENERATED_KEYS);
			st.setDate(1, new Date(datum.getTime()));
			st.setString(2, id);//Integer.parseInt(id));
			st.executeUpdate();
			
			LogInsert li = new LogInsert(dataSource);
			//li.insert(user,  findById(Integer.parseInt(id)), "nastavenie datumu");
			
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
	
	public void insertSignal(String file, String signal, String verzia, String user, String cesta) {
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into signaly (verzia, signal, subor, cesta) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, verzia);
			st.setString(2, signal);
			st.setString(3, file);
			st.setString(4, cesta);
			st.executeUpdate();
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
	
	public List<Verzia> findAll(String zeichnungsnummer){
		List<Verzia> zoznam = new ArrayList<Verzia>(); 
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Select * from verzia Where Zeichnungsnummer Like ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, zeichnungsnummer);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Verzia p = new Verzia("","",0,dataSource,"","");
				p.setZeichnungsnummer(rs.getString("Zeichnungsnummer"));
				p.setId_objednavky(rs.getInt("Id_objednavky"));
				p.setVerzia(rs.getString("Verzia"));
				p.setId(rs.getInt("Id"));
				p.setDatum_testovania(rs.getDate("Datum_testovania"));
				zoznam.add(p); 
			}
			return zoznam;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return zoznam;
	}
	
	public String findById(Integer id) {
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Select Zeichnungsnummer from verzia Where Id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				return rs.getString("Zeichnungsnummer");
			}
			return "";
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return "";
	}
}