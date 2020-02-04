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

import javax.sql.DataSource;

public class LogInsert {
	private DataSource dataSource;
	
	public LogInsert(DataSource datasource) {
		dataSource = datasource;
	}
	
	public String getUser() {
		HttpClient client = HttpClient.newHttpClient();
		Builder builder = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/DatabazaZivotnosti/rest/data/allprodukt"));
		HttpRequest request = builder.build();
		return "";
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
                return r.getInt(3);
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
}
