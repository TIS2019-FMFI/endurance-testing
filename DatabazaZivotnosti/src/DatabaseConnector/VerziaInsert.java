package DatabaseConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class VerziaInsert {
	@Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;
	
	public VerziaInsert() { }
	
	public void insert(String zeichnungsnummer, String verzia, Integer id_objednavky){
		try(PreparedStatement st = dataSource.getConnection().prepareStatement("Insert into verzia (Zeichnungsnummer,Verzia, Id_objednavky) values (?,?,?)", Statement.RETURN_GENERATED_KEYS)){
			st.setString(1, zeichnungsnummer);
			st.setString(2, verzia);
			st.setInt(3, id_objednavky);
			st.executeUpdate();
			try (ResultSet r = st.getGeneratedKeys()) {
                r.next();
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

