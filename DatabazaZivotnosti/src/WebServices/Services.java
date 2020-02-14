package WebServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import DataProcessing.Dokument;
import DataProcessing.ExcelReader;
import DataProcessing.Filter;
import DataProcessing.Log;
import DataProcessing.Produkt;
import DataProcessing.Signal;
import DataProcessing.Verzia;
import DatabaseConnector.FileFinder;
import DatabaseConnector.LogInsert;
import DatabaseConnector.ProduktFinder;
import DatabaseConnector.UserFinder;
import DatabaseConnector.VerziaInsert;

@Path("/data")
@RequestScoped
public class Services {
	@Resource(mappedName = "java:jboss/datasources/databazazivotnostiDS")
	private DataSource dataSource;
	
	@GET
    @Path("/ping")
    public Response ping() {
    	return Response.ok().entity("ping").build();
    }

	@POST
	@Path("/verzie")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response verzie(final MultivaluedMap<String, String> params) {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		//MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		VerziaInsert vi = new VerziaInsert(dataSource);
		for (Verzia x : vi.findAll(params.getFirst("zeich"))) {
			ObjectNode row = om.createObjectNode();
			JsonNode node = om.valueToTree(x);
			zoznam.add(node);
		}
		try {
			return Response.ok().entity(om.writeValueAsString(zoznam)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/verziaID")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verziaID(@Context javax.ws.rs.core.HttpHeaders hh) {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		VerziaInsert vi = new VerziaInsert(dataSource);
		try {
			return Response.ok().entity(om.writeValueAsString(vi.findId(headerParams.getFirst("zeich")))).build();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@POST
	@Path("/allprodukt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response allprodukt(final MultivaluedMap<String, String> params) {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		MultivaluedMap<String, String> headerParams = params;
		Filter filter = new Filter(headerParams, dataSource);
		List<HashMap<String, String>> zoz = filter.vytvorWhere();
		if(zoz == null) {
			return Response.ok().entity(null).build();
		}
		for (HashMap<String, String> x : zoz) {
			ObjectNode row = om.createObjectNode();
			JsonNode node = om.valueToTree(x);
			zoznam.add(node);
		}
		try {
			return Response.ok().entity(om.writeValueAsString(zoznam)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@GET
	@Path("/get_subory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubory(@Context javax.ws.rs.core.HttpHeaders hh) {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		FileFinder ff = new FileFinder(dataSource);
		for (Dokument x : ff.fileFinderByType(headerParams.getFirst("typ"), headerParams.getFirst("id_verzie"))) {
			ObjectNode row = om.createObjectNode();
			JsonNode node = om.valueToTree(x);
			zoznam.add(node);
		}
		try {
			return Response.ok().entity(om.writeValueAsString(zoznam)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@GET
	@Path("/get_signal")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSignal(@Context javax.ws.rs.core.HttpHeaders hh) {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		FileFinder ff = new FileFinder(dataSource);
		for (Signal x : ff.signalFinderByType(headerParams.getFirst("typ"), headerParams.getFirst("id_verzie"))) {
			ObjectNode row = om.createObjectNode();
			JsonNode node = om.valueToTree(x);
			zoznam.add(node);
		}
		try {
			return Response.ok().entity(om.writeValueAsString(zoznam)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/newProdukt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newProdukt(final MultivaluedMap<String, String> params) {	
		Produkt p = new Produkt(params.getFirst("Bezeichnung"), params.getFirst("Kunde"),
				params.getFirst("Zeichnungsnummer"), params.getFirst("Cislo"), dataSource, params.getFirst("Zostava"));
		p.setUser(params.getFirst("User"));
		try {
			p.nacitajStrukturu();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.ok().entity("Produkt/verzia úspešne pridané").build();
	}
	
	
	@POST
	@Path("/novyDatum")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response date(final MultivaluedMap<String, String> params) {
		VerziaInsert vi = new VerziaInsert(dataSource);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");		
		try {
			vi.insertDate(format2.parse(params.getFirst("datum")), params.getFirst("verzia"),  params.getFirst("uname"));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity("ret").build();
	}
	
	@POST
	@Path("/nastavZostavu")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response zostava(final MultivaluedMap<String, String> params) {
		ProduktFinder pf = new ProduktFinder(dataSource);	
		pf.SetZostava(params.getFirst("zeichnungsnummer"), params.getFirst("zostava"), params.getFirst("user"));
		return Response.ok().entity("ret").build();
	}

	@POST
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response user(final MultivaluedMap<String, String> params) {
		MultivaluedMap<String, String> headerParams = params;
		UserFinder uf = new UserFinder(dataSource);
		Boolean vystup = uf.findUser(headerParams.getFirst("uname"), headerParams.getFirst("pass"));
		return Response.ok().entity(vystup).build();
	}

	@GET
	@Path("/giveLink")
	@Produces(MediaType.APPLICATION_JSON)
	public Response link(@Context javax.ws.rs.core.HttpHeaders hh) {
		MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
		String link = "";
		if(headerParams.getFirst("signal").equals("false")) {
		link = "http://localhost:8080/DatabazaZivotnosti/rest/data/vrat_subor?nazov=" + headerParams.getFirst("nazov")
				+ "&id_verzie=" + headerParams.getFirst("id_verzie");
		}
		else {
			link = "http://localhost:8080/DatabazaZivotnosti/rest/data/vrat_signal?nazov=" + headerParams.getFirst("nazov")
			+ "&id_verzie=" + headerParams.getFirst("id_verzie");
		}
		return Response.ok().entity(link).build();
	}

	@GET
	@Path("/vrat_subor")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON })
	public Response vrat_subor(@QueryParam("nazov") String nazov, @QueryParam("id_verzie") String verzia) {
		FileFinder ff = new FileFinder(dataSource);
		String tmp = ff.fileFinder(nazov, verzia);
		try {
			File file = new File(tmp);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + file.getName());
			/// Files.delete(file.toPath());
			return response.build();
		} catch (Exception e) {
			String msg = String.format("Unable to download file [%s].", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(msg).build();

		}
	}

	@GET
	@Path("/vrat_signal")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_JSON })
	public Response vrat_signal(@QueryParam("nazov") String nazov, @QueryParam("id_verzie") String verzia) {
		FileFinder ff = new FileFinder(dataSource);
		String tmp = ff.signalFinder(nazov, verzia);
		try {
			File file = new File(tmp);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + file.getName());
			/// Files.delete(file.toPath());
			return response.build();
		} catch (Exception e) {
			String msg = String.format("Unable to download file [%s].", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(msg).build();

		}
	}
	
	/*
	@POST
	@Path("/nahraj_subor")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream , @FormDataParam("file") FormDataContentDisposition fileDetail,
	        @FormDataParam("path") String path)  {
		//System.out.println(fileDetail);
		// check if all form parameters are provided
		if ( uploadedInputStream == null)
			return Response.status(400).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists("F:\\Projekt\\111.___.333.444\\222\\Fotodokumentacia"); //"F:\\Projekt\\111.___.333.444\\222\\Fotodokumentacia"
		} catch (SecurityException se) {
			return Response.status(500).entity("Can not create destination folder on server").build();
		}
		String uploadedFileLocation = "F:\\Projekt\\111.___.333.444\\222\\Fotodokumentacia" + "\\" 
				+ "subor.txt" ; //"F:\\Projekt\\111.___.333.444\\222\\Fotodokumentacia\\"
		File dest=new File(uploadedFileLocation);
		try {
		dest.createNewFile();
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = uploadedInputStream;
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	    }catch (Exception e) {
		}
		//System.out.println(uploadedInputStream);
		return Response.status(200).entity("File saved to " + uploadedFileLocation).build();
	}
	private void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	private void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	*/
	@GET
	@Path("/log")
	@Produces(MediaType.APPLICATION_JSON)
	public Response log() {
		ObjectMapper om = new ObjectMapper();
		ArrayNode zoznam = om.createArrayNode();
		LogInsert li = new LogInsert(dataSource);
		for (Log x : li.logFinder()) {
			ObjectNode row = om.createObjectNode();
			JsonNode node = om.valueToTree(x);
			zoznam.add(node);
		}
		try {
			return Response.ok().entity(om.writeValueAsString(zoznam)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}


	@POST
	@Path("/Excel")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Excel(MultipartFormDataInput input) {		
        List<InputPart> imgpart = input.getFormDataMap().get("file"); //tymto viem dostavat aj ostatne hodnoty formulara
        File file = null;
        FileOutputStream filicek = null;
        try {
			InputStream is2 = input.getFormDataPart("file", InputStream.class,null);
			file = File.createTempFile("tmpExcel", ".xlsx"); //pri vzytvorenie suboru nepouyvat tmpfile, ale new File(uplna_cesta); file create if not exist
			filicek = new FileOutputStream(file);
			filicek.write(is2.readAllBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	if (filicek != null) {
        		try {
					filicek.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
			
		}
		ExcelReader er = new ExcelReader();
		er.readExcel(file.getAbsolutePath());


		ObjectMapper om = new ObjectMapper();
		ObjectNode row = om.createObjectNode();
		
		row.put("cisloObjedavkyGet", er.getNr());
		row.put("cisloVykresuGet", er.getZeichnungsnummer());
		row.put("zakaznikGet", er.getKunde());
		row.put("umiestnenieGet", er.getBezeichnung());
		row.put("zostavaGet", "");
	    return Response.status(200).entity(row).build();
	
	}
}
