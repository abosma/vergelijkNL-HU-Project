package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class CoolblueController {
	
	public CoolblueController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "https://www.coolblue.nl/zoeken?query=" + query;
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	        	Document doc = con.get();
	        	Elements elProd = doc.select("div.product__content");
	        	
	        	Element naam = elProd.select("a.product__title.js-product-title").first();
	        	Element link = elProd.select("a.product__title.js-product-title").first();
	        	Element prijs = elProd.select("strong.product__sales-price").first();
	        	Element img = elProd.select("img.product__image").first();
	        	
	        	String naamString = naam.text();
	        	String linkString = link.attr("href");
	        	String prijsString = prijs.text();
	        	String imgString = img.attr("src");
	        	
	        	if(prijsString.contains(",-")){
	            	prijsString = prijsString.replaceAll(",-", "");
	            }
	        	
	        	if(prijsString.contains(",")){
	            	prijsString = prijsString.replaceAll(",", ".");
	            }
	            
	            double dPrijs = Double.parseDouble(prijsString);
	            
	            linkString = "https://www.coolblue.nl" + linkString;
	            
	            Product prod = new Product(naamString, imgString, linkString, dPrijs);
	        	
	        	return prod;
	        }
	   }catch(Exception e){
		   e.printStackTrace();
		   return new Product("null", "null", "null", 0.0);
	   }
		return null;
	}
}
