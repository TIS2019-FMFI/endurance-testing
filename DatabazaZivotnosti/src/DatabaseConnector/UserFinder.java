package DatabaseConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.sql.DataSource;

import DataProcessing.Produkt;

public class UserFinder {
	private DataSource dataSource;
	
	public UserFinder(DataSource datasource) {
		dataSource = datasource;
	}
	
	public Boolean findUser(String user, String pass) {
		Connection c=null;
		PreparedStatement st = null;	
		Integer p = 0;
		try{
			c = dataSource.getConnection();
			st = c.prepareStatement("Select * from uzivatelia Where meno = ? AND heslo = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, user);
			st.setString(2, pass);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				p++;
			}
			return p != 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (st != null) st.close(); } catch (Exception e) {e.printStackTrace() ;}
			try { if (c != null) c.close(); } catch (Exception e) {e.printStackTrace() ;}
		}
		return p != 0;
	}
	} 