package WebServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Path("/data")
@RequestScoped
public class Services {
		@Resource(mappedName="java:jboss/datasources/databazazivotnostiDS")
		private DataSource dataSource;
	  	
		@GET
	    @Path("/ping")
	    public Response ping() {
	    	return Response.ok().entity("ping").build();
	    }
	  	
		@GET
	    @Path("/ping2")
	    public Response ping2() {
	    	return Response.ok().entity("pig").build();
	    }
	  	
		
	  	@GET
	    @Path("/databaza")
	  	@Produces(MediaType.APPLICATION_JSON)
	    public Response databaza() {
	  		ObjectMapper om = new ObjectMapper();
			ArrayNode zoznam = om.createArrayNode();
	  		try {	
				Connection c = dataSource.getConnection();
				PreparedStatement s = c.prepareStatement("Select * from test");
				ResultSet rs = s.executeQuery();
				while (rs.next()) {
					System.out.println("sme tu biac");
					ObjectNode row = om.createObjectNode();
					row.put("testovacirohlik", rs.getString("Skuska"));
					zoznam.add(row);
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	  		/*
			ObjectNode row = om.createObjectNode();
			row.put("neviem", "nemam");
			
	  		zoznam.add(row);
	  		*/
	  		try {
				return Response.ok().entity(om.writeValueAsString(zoznam)).build();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	  		return null;
	    }
	  	
}

