package nl.hu.ipass.vergelijkNL.webservices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nl.hu.ipass.vergelijkNL.model.Product;

public class BolController {

	public BolController(){
		
	}
	
	public Product getProduct(String query){
		try{
			Connection con = Jsoup.connect("https://www.bol.com/nl/s/algemeen/zoekresultaten/Ntt/" + query + "/N/0/Nty/1/search/true/searchType/qck/defaultSearchContext/media_all/sc/media_all/index.html").userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(10000);
			Connection.Response resp = con.execute();
	        if (resp.statusCode() == 200) {
	            Document doc = con.get();
	            Element product = doc.select("li[class=\"product-item--row hit-area js_item_root\"]").first();
	            Elements info = product.getElementsByTag("meta");
	            
	            String naam = "";
	            String imageLink = "";
	            String urlEnd = "";
	            String prijsString = "";
	            double prijs = 0;
	            
	            if(info.isEmpty()){
	            	product = doc.select("li[class=\"product-item--row hit-area js_item_root\"]").first();
	            	
	            	Element link = product.select("a.product-title").first();
	            	Element img = product.select("div.product-rollup__img.media__img > img").first();
	            	Element name = product.select("a.product-title").first();
	            	Element ePrijs = product.select("span.product-prices__currency.product-prices__bol-price").first();	      
	            	
	            	naam = name.text();
	            	imageLink = img.attr("src");
	            	urlEnd = link.attr("href");
	            	prijsString = ePrijs.text();
	            	
	            	prijsString = prijsString.replaceAll(",", ".");
	            	prijs = Double.parseDouble(prijsString);
		            
	            	Product prod = new Product(naam, imageLink, "https://www.bol.com" + urlEnd, prijs);
	            	return prod;
	            }else{
		            for(Element e : info){
		            	String infoType = e.attr("itemprop");

		            	if(infoType.equals("name")){
		            		if(naam.equals("")){
		            			naam = e.attr("content");
		            		}
		            	}else if(infoType.equals("image")){
		            		if(imageLink.equals("")){
		            			imageLink = e.attr("content");
		            		}
		            	}else if(infoType.equals("url")){
		            		if(urlEnd.equals("")){
		            			urlEnd = e.attr("content");
		            		}
		            	}else if(infoType.equals("price")){
		            		if(prijsString.equals("")){
		            			prijsString = e.attr("content");
		            		}
		            	}
		            }
		            
		            prijs = Double.parseDouble(prijsString);
		            
		            if(urlEnd.contains("www.bol.com")){
		            	Product prod = new Product(naam, imageLink, urlEnd, prijs);
		            	return prod;
		            }else{
		            	Product prod = new Product(naam, imageLink, "https://www.bol.com" + urlEnd, prijs);
		            	return prod;
		            }
	            }
	        }
		}catch(Exception e){
			System.out.println(e);
			return new Product("null", "null", "null", 0.0);
		}
		return new Product("null", "null", "null", 0.0);
	}
	
}
