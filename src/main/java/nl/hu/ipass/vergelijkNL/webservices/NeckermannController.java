package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import nl.hu.ipass.vergelijkNL.model.Product;

public class NeckermannController {
	
	public NeckermannController(){
		
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
	        	
	        }
	   }catch(Exception e){
		   System.out.println(e);
	   }
		return null;
	}
}
