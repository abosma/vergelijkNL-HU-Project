package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class WehkampController {

	public WehkampController(){
		
	}
	
	public Product getProduct(String query){
		try{
			String url = "https://www.wehkamp.nl/Winkelen/SearchOverview.aspx?Ntt=" + query;
			System.out.println(url);
			
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000);
			
			Connection.Response resp = con.execute();
			
	        if (resp.statusCode() == 200) {
	            Document doc = con.get();
	            Elements product = doc.select("div.ka-content");
	            
	            Element link = product.select("a.l-article-card").first();
	            Element img = product.select("img.photo.pop210x210").first();
	            Element name = product.select("h2.article-title").first();
	            Element prijs = product.select("span.price").first();	            
	            
	            String linkString = link.attr("href");
	            String imgString = img.attr("src");
	            String nameString = name.text();
	            String prijsString = prijs.text();
	            
	            if(prijsString.contains(".-")){
	            	prijsString = prijsString.replaceAll(".-", "");
	            }
	            
	            double dPrijs = Double.parseDouble(prijsString);
	            
	            Product prod = new Product(nameString, imgString, linkString, dPrijs);
	            
	            return prod;
	        }
		}catch(Exception e){
			System.out.println(e);
			return new Product("null", "null", "null", 0.0);
		}
		return null;
	}
	
}