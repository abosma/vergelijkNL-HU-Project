package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class BartSmitController {
	public BartSmitController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "http://www.bartsmit.com/SearchDisplay?searchTerm=" + query + "&categoryId=&storeId=10151&catalogId=10051&langId=-104&pageSize=12&beginIndex=0&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&searchSource=Q&pageView=&currentSearchTerm=" + query;
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	        	Document doc = con.get();
	        	Elements prodEl = doc.select("div#product_1");
	        	
	        	Element link = prodEl.select("div.product_image > a").first();
	        	Element image = prodEl.select("a > img").first();
	        	Element naam = prodEl.select("div.product_name.dotdotdot > a").first();
	        	Element prijs1 = prodEl.select("span.price.int_price > span.product_price_integer").first();
	        	Element prijs2 = prodEl.select("span.price.int_price > span.product_price_fraction").first();
	        	
	        	String linkString = link.attr("href");
	        	String imageString = image.attr("src");
	        	String naamString = naam.text();
	        	String prijs1String = prijs1.text();
	        	String prijs2String = prijs2.text();
	        	String prijsString = prijs1String + prijs2String;
	        	
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
