package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class MediamarktController {
	
	public MediamarktController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "http://www.mediamarkt.nl/nl/search.html?query=" + query + "&searchProfile=onlineshop&channel=mmnlnl";
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	        	Document doc = con.get();
	        	Elements elProd = doc.select("ul.products-list");
	        	
	        	Element naam = elProd.select("h2 > a").first();
	        	Element link = elProd.select("h2 > a").first();
	        	Element prijs = elProd.select("div.price.small").first();
	        	Element img = elProd.select("span.photo.clickable > img").first();
	        	
	        	String naamString = naam.text();
	        	String linkString = link.attr("href");
	        	String prijsString = prijs.text();
	        	String imgString = img.attr("data-original");
	        	
	        	System.out.println(naamString);
	        	System.out.println(linkString);
	        	System.out.println(prijsString);
	        	System.out.println(imgString);
	        	
	        	if(prijsString.contains(",-")){
	            	prijsString = prijsString.replaceAll(",-", "");
	            }
	        	
	        	if(prijsString.contains(",")){
	            	prijsString = prijsString.replaceAll(",", ".");
	            }
	            
	            double dPrijs = Double.parseDouble(prijsString);
	            
	            linkString = "http://www.mediamarkt.nl" + linkString;
	            
	            Product prod = new Product(naamString, imgString, linkString, dPrijs);
	        	
	        	return prod;
	        }
	   }catch(Exception e){
		   System.out.println(e);
		   e.printStackTrace();
		   return new Product("null", "null", "null", 0.0);
	   }
		return null;
	}
}