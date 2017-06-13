package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class NeckermannController {
	
	public NeckermannController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "https://nl.neckermann.com/search/" + query + "/";
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	        	Document doc = con.get();
	        	Elements prodEl = doc.select("div.product.special-product.col-xs-6.col-sm-4.col-md-6.col-lg-4");
	        	
	        	Element link = prodEl.select("a[title]").first();
	        	Element image = prodEl.select("img[title]").first();
	        	Element naam = prodEl.select("div.info > a").first();
	        	Elements prijs = prodEl.select("div.price > span");
	        	
	        	String linkString = link.attr("href");
	        	String imageString = image.attr("src");
	        	String naamString = naam.attr("title");
	        	String prijsString = "";
	        	
	        	for(Element e : prijs){
	        		prijsString = e.text();
	        	}
	        	
	        	if(prijsString.contains("€")){
	        		prijsString = prijsString.replace("€", "");
	        	}
	        	
	        	if(prijsString.contains(",")){
	        		prijsString = prijsString.replace(",", ".");
	        	}
	        	
	        	double dPrijs = Double.parseDouble(prijsString);
	        	
        		Product prod = new Product(naamString, imageString, linkString, dPrijs);
	        	
	        	return prod;
	        }
	   }catch(Exception e){
		   e.printStackTrace();
		   return new Product("null", "null", "null", 0.0);
	   }
		return null;
	}
}
