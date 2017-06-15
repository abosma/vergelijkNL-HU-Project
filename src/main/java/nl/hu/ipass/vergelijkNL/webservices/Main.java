package nl.hu.ipass.vergelijkNL.webservices;

import java.io.StringReader;
import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import nl.hu.ipass.vergelijkNL.persistance.ProductDAO;
import nl.hu.ipass.vergelijkNL.persistance.UserDAO;

@Path("/search")
@RolesAllowed({"guest", "User"})
public class Main {
	
	RunnableController rc = new RunnableController();
	EmailController ec = new EmailController();
	ProductDAO pd = new ProductDAO();
	UserDAO ud = new UserDAO();
	
	
	@GET
	@Path("{query}")
	@RolesAllowed({"guest", "User"})
	public String getProducten(@PathParam("query") String query){
		JsonObjectBuilder job = rc.getProductenJson(query);
		return job.build().toString();
	}
	
	@POST
	@RolesAllowed("User")
	@Path("favoriet")
	@Consumes(MediaType.APPLICATION_JSON)
	public String favorietProduct(String jsonString){
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		JsonObject jo = jsonReader.readObject();
		jsonReader.close();
		
		int ID = jo.getInt("productID");
		String token = jo.getString("token");
		
		JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
		Claims claims = parser.parseClaimsJws(token).getBody();
		
		String user = claims.getSubject();
		
		if(pd.favorietProduct(user, ID)){
			return "Product gefavoriet!";
		}else{
			return "Product is al gefavoriet!";
		}
	}
	
	@DELETE
	@RolesAllowed("User")
	@Path("verwijderFavoriet")
	@Consumes(MediaType.APPLICATION_JSON)
	public String verwijderFavorietProduct(String jsonString){
		JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
		JsonObject jo = jsonReader.readObject();
		jsonReader.close();
		
		int ID = jo.getInt("productID");
		String token = jo.getString("token");
		
		JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
		Claims claims = parser.parseClaimsJws(token).getBody();
		
		String user = claims.getSubject();
		
		if(pd.verwijderFavoriet(user, ID)){
			return "Product verwijdert uit favorieten";
		}else{
			return "Product is nog niet gefavoriet";
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
	
	@POST
	@RolesAllowed("guest")
	@Path("register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(@FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email){
		if(ud.createUser(username, password, email)){
			ec.sendMail(username, password, email);
			return Response.ok().build();
		}else{
			return Response.status(Response.Status.NOT_FOUND).build();
		}		
	}
	
	@GET
	@RolesAllowed("guest")
	@Path("recovery")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getPassword(@QueryParam("username") String username, @QueryParam("email") String email){
		return ec.sendRecovery(username, email);	
	}
}
