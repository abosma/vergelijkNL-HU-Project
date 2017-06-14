package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class BCCController {
	
	public BCCController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "http://www.bcc.nl/search?fh_location=%2F%2Fcatalog01%2Fnl_NL%2Fchannel>%7Bm2ebcc2enl%7D&search=" + query;
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	        	Document doc = con.get();
	        	Elements elProd = doc.select("div.productlist");
	        	
	        	Element naam = elProd.select("div.lister-product.product").first();
	        	Element link = elProd.select("div.lister-product.product").first();
	        	Element prijs = elProd.select("div.lister-product-offer__price").first();
	        	Element img = elProd.select("img.lister-product__image.img-responsive").first();
	        	
	        	String naamString = naam.attr("data-title");
	        	String linkString = link.attr("data-url");
	        	String prijsString = prijs.text();
	        	String imgString = img.attr("src");
	        	
	        	if(prijsString.contains(",")){
	            	prijsString = prijsString.replaceAll(",", ".");
	            }
	        	
	        	if(prijsString.contains("€")){
	            	prijsString = prijsString.replaceAll("€", "");
	            }
	        	
	        	if(prijsString.contains(" ")){
	            	prijsString = prijsString.replaceAll(" ", "");
	            }
	            
	            double dPrijs = Double.parseDouble(prijsString);
	            
	            linkString = "http://www.bcc.nl" + linkString;
	            
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