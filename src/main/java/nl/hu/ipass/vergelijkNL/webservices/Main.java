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
import nl.hu.ipass.vergelijkNL.model.Product;
import nl.hu.ipass.vergelijkNL.persistance.ProductDAO;
import nl.hu.ipass.vergelijkNL.persistance.UserDAO;

@Path("/search")
@RolesAllowed({"guest", "User"})
public class Main {

	Helper h = new Helper();
	
	BolController bc = new BolController();
	WehkampController wc = new WehkampController();
	CoolblueController cc = new CoolblueController();
	BartSmitController bsc = new BartSmitController();
	NeckermannController nc = new NeckermannController();
	
	ProductDAO pd = new ProductDAO();
	UserDAO ud = new UserDAO();
	
	
	@GET
	@Path("{query}")
	@RolesAllowed({"guest", "User"})
	public String getExample(@PathParam("query") String query){
		String bolQuery = query.replaceAll(" ", "%2B");
		String cbQuery = query.replaceAll(" ", "+");
		
		Product bp = bc.getProduct(bolQuery);
		Product wp = wc.getProduct(query);
		Product cp = cc.getProduct(cbQuery);
		Product bsp = bsc.getProduct(cbQuery);
		Product ncp = nc.getProduct(cbQuery);
		
		int a = pd.insertProduct(bp.getNaam(), bp.getPrijs(), bp.getUrl(), bp.getImage());
		int b = pd.insertProduct(wp.getNaam(), wp.getPrijs(), wp.getUrl(), wp.getImage());
        int c = pd.insertProduct(cp.getNaam(), cp.getPrijs(), cp.getUrl(), cp.getImage());
		int d = pd.insertProduct(bsp.getNaam(), bsp.getPrijs(), bsp.getUrl(), bsp.getImage());
		int e = pd.insertProduct(ncp.getNaam(), ncp.getPrijs(), ncp.getUrl(), ncp.getImage());
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		JsonObjectBuilder job1 = h.buildProduct(a, "Bol.com", bp);
		JsonObjectBuilder job2 = h.buildProduct(b, "Wehkamp.nl", wp);
		JsonObjectBuilder job3 = h.buildProduct(c, "Coolblue.nl", cp);
		JsonObjectBuilder job4 = h.buildProduct(d, "BartSmit.com", bsp);
		JsonObjectBuilder job5 = h.buildProduct(e, "Neckermann.com", ncp);
		
		job.add("ProductBol", job1);
		job.add("ProductWehkamp", job2);
		job.add("ProductCoolblue", job3);
		job.add("ProductBartSmit", job4);
		job.add("ProductNeckermann", job5);
		
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
	public Response createUser(@FormParam("username") String username, @FormParam("password") String password){
		if(ud.createUser(username, password)){
			return Response.ok().build();
		}else{
			return Response.status(Response.Status.NOT_FOUND).build();
		}		
	}
}
