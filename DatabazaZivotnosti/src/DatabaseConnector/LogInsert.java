package DatabaseConnector;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import DataProcessing.Log;
import DataProcessing.Produkt;

public class LogInsert {
	private DataSource dataSource;
	
	public LogInsert(DataSource datasource) {
		dataSource = datasource;
	}
	
	
	public Integer insert(String user, String what,  String how){
		Connection c=null;
		PreparedStatement st = null;	
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Insert into Log (User,Updated_what,Updated_how, Updated_at) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user);
			st.setString(2, what);
			st.setString(3, how);
			st.setDate(4, Date.valueOf(LocalDate.now()));
			st.executeUpdate();
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
	
	public List<Log> logFinder(){
		List<Log> zoznam = new ArrayList<Log>(); 
		Connection c=null;
		PreparedStatement s = null;
		try {
			c = dataSource.getConnection();
			s = c.prepareStatement("Select * from log");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				Log p = new Log("","","","");
				p.setUser(rs.getString("User"));
				p.setUpdated_how(rs.getString("Updated_how"));
				p.setUpdated_at(rs.getString("Updated_at"));
				p.setUpdated_what(rs.getString("Updated_what"));
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
}
