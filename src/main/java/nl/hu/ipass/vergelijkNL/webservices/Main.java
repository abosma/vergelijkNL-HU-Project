package nl.hu.ipass.vergelijkNL.webservices;

import java.io.StringReader;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import nl.hu.ipass.vergelijkNL.model.Product;
import nl.hu.ipass.vergelijkNL.persistance.ProductDAO;

@Path("/search")
@RolesAllowed({"guest", "User"})
public class Main {

	BolController bc = new BolController();
	WehkampController wc = new WehkampController();
	ProductDAO pd = new ProductDAO();
	
	
	@GET
	@Path("{query}")
	@RolesAllowed({"guest", "User"})
	public String getExample(@PathParam("query") String query){
		String bolQuery = query.replace(" ", "%2B");
		
		Product bp = bc.getProduct(bolQuery);
		Product wp = wc.getProduct(query);
		
		int a = pd.insertProduct(bp.getNaam(), bp.getPrijs(), bp.getUrl(), bp.getImage());
		int b = pd.insertProduct(wp.getNaam(), wp.getPrijs(), wp.getUrl(), wp.getImage());
        
		if(a != 0 && b != 0){
			JsonObjectBuilder job = Json.createObjectBuilder();
			JsonObjectBuilder job1 = Json.createObjectBuilder();
			JsonObjectBuilder job2 = Json.createObjectBuilder();
			
			job1.add("ID", String.valueOf(a))
				.add("naam", bp.getNaam())
				.add("prijs", bp.getPrijs())
				.add("url", bp.getUrl())
				.add("image", bp.getImage());
			
			job2.add("ID", String.valueOf(b))
				.add("naam", wp.getNaam())
				.add("prijs", wp.getPrijs())
				.add("url", wp.getUrl())
				.add("image", wp.getImage());
			
			
			job.add("Product" + String.valueOf(a), job1);
			job.add("Product" + String.valueOf(b), job2);
			
			return job.build().toString();
		}else{
			return "Er is iets fout gegaan";
		}
	}
	
	@POST
	@RolesAllowed("User")
	@Path("favoriet")
	@Consumes(MediaType.APPLICATION_JSON)
	public String favorietProduct(String jsonString){
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		JsonObject jo = jsonReader.readObject();
		jsonReader.close();
		
		String ID = jo.getString("productID");
		String token = jo.getString("token");
		
		JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
		Claims claims = parser.parseClaimsJws(token).getBody();
		
		String user = claims.getSubject();
		int prodID = Integer.valueOf(ID);
		
		if(pd.favorietProduct(user, prodID)){
			return "Product gefavoriet!";
		}else{
			return "Product is al gefavoriet!";
		}
	}
	
	@GET
	@RolesAllowed("User")
	@Path("getfavorieten/{token}")
	public String getFavorieten(@PathParam("token") String token){
		JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
		Claims claims = parser.parseClaimsJws(token).getBody();
		
		String user = claims.getSubject();
		
		JsonObjectBuilder producten = pd.getProducten(user);
		
		return producten.build().toString();
	}
}
