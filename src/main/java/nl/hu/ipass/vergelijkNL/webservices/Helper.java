package nl.hu.ipass.vergelijkNL.webservices;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import nl.hu.ipass.vergelijkNL.model.Product;

public class Helper {

	public JsonObjectBuilder buildProduct(int id, String from, Product prod){
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		job.add("ID", id)
			.add("naam", prod.getNaam())
			.add("prijs", prod.getPrijs())
			.add("url", prod.getUrl())
			.add("image", prod.getImage())
			.add("from", from);
		
		return job;
	}
}
