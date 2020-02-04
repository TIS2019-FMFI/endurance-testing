package DatabaseConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class VerziaInsert {
	private DataSource dataSource;
	
	public VerziaInsert(DataSource datasource) {
		dataSource = datasource;
	}
	
	public Integer insert(String zeichnungsnummer, String verzia, Integer id_objednavky){
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
			li.insert(li.getUser(), zeichnungsnummer, "insert");
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
	
	public void insertFile(String nazov,String relnazov, String typ, String cesta, String user, String verzia) {
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into subory_k_verzii (dokument_originalny_nazov, dokument_relativny_nazov, relativna_cesta, typ_suboru, Inserted_by, Inserted_at, Updated_at, Updated_by, Verzia ) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, nazov);
			st.setString(2, relnazov);
			st.setString(3, cesta);
			st.setString(4, typ);
			st.setString(5, user);
			st.setDate(6, Date.valueOf(LocalDate.now()));
			st.setString(7, user);
			st.setDate(8, Date.valueOf(LocalDate.now()));
			st.setString(9, user);
			st.executeUpdate();
			
			LogInsert li = new LogInsert(dataSource);
			li.insert(li.getUser(), nazov, "insert");
			
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
	
	public void insertDate(String nazov, Date datum, Integer id) {
		Connection c = null;
		PreparedStatement st = null;	
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("UPDATE verzia Datum_testovania = ? Where id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setDate(1, datum);
			st.setInt(2, id);
			st.executeUpdate();
			
			LogInsert li = new LogInsert(dataSource);
			li.insert(li.getUser(), nazov, "nastavenie datumu");
			
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
	
	public void insertSignal(String file, String signal, String verzia, String user) {
		Connection c = null;
		PreparedStatement st = null;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into signaly (verzia, signal, subor) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, verzia);
			st.setString(2, signal);
			st.setString(3, file);
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
}
