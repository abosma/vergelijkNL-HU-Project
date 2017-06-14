package nl.hu.ipass.vergelijkNL.webservices;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import nl.hu.ipass.vergelijkNL.model.Product;
import nl.hu.ipass.vergelijkNL.persistance.ProductDAO;

public class RunnableController {
	
	Helper h = new Helper();
	
	BolController bc = new BolController();
	WehkampController wc = new WehkampController();
	CoolblueController cc = new CoolblueController();
	BartSmitController bsc = new BartSmitController();
	NeckermannController nc = new NeckermannController();
	MediamarktController mc = new MediamarktController();
	BCCController bccc = new BCCController();
	
	ProductDAO pd = new ProductDAO();
	
	public JsonObjectBuilder getProductenJson(String query){
		String bolQuery = query.replaceAll(" ", "%2B");
		String cbQuery = query.replaceAll(" ", "+");
		
		JsonObjectBuilder job = Json.createObjectBuilder();
		ExecutorService service =  Executors.newFixedThreadPool(7);
		
		Runnable task1 = () -> {
			final Product bp = bc.getProduct(bolQuery);
			int a = pd.insertProduct(bp.getNaam(), bp.getPrijs(), bp.getUrl(), bp.getImage());
			JsonObjectBuilder job1 = h.buildProduct(a, "Bol.com", bp);
			job.add("ProductBol", job1);
		};
		Runnable task2 = () -> {
			final Product wp = wc.getProduct(query);
			int b = pd.insertProduct(wp.getNaam(), wp.getPrijs(), wp.getUrl(), wp.getImage());
			JsonObjectBuilder job2 = h.buildProduct(b, "Wehkamp.nl", wp);
			job.add("ProductWehkamp", job2);
		};
		Runnable task3 = () -> {
			final Product cp = cc.getProduct(cbQuery);
			int c = pd.insertProduct(cp.getNaam(), cp.getPrijs(), cp.getUrl(), cp.getImage());
			JsonObjectBuilder job3 = h.buildProduct(c, "Coolblue.nl", cp);
			job.add("ProductCoolblue", job3);
		};
		Runnable task4 = () -> {
			final Product bsp = bsc.getProduct(cbQuery);
			int d = pd.insertProduct(bsp.getNaam(), bsp.getPrijs(), bsp.getUrl(), bsp.getImage());
			JsonObjectBuilder job4 = h.buildProduct(d, "BartSmit.com", bsp);
			job.add("ProductBartSmit", job4);
		};
		Runnable task5 = () -> {
			final Product ncp = nc.getProduct(cbQuery);
			int e = pd.insertProduct(ncp.getNaam(), ncp.getPrijs(), ncp.getUrl(), ncp.getImage());
			JsonObjectBuilder job5 = h.buildProduct(e, "Neckermann.com", ncp);
			job.add("ProductNeckermann", job5);
		};
		Runnable task6 = () -> {
			final Product mcp = mc.getProduct(cbQuery);
			int f = pd.insertProduct(mcp.getNaam(), mcp.getPrijs(), mcp.getUrl(), mcp.getImage());
			JsonObjectBuilder job6 = h.buildProduct(f, "MediaMarkt.nl", mcp);
			job.add("ProductMediamarkt", job6);
		};
		Runnable task7 = () -> {
			final Product bccp = bccc.getProduct(cbQuery);
			int g = pd.insertProduct(bccp.getNaam(), bccp.getPrijs(), bccp.getUrl(), bccp.getImage());
			JsonObjectBuilder job7 = h.buildProduct(g, "BCC.nl", bccp);
			job.add("ProductBCC", job7);
		};
		
		service.execute(task1);
		service.execute(task2);
		service.execute(task3);
		service.execute(task4);
		service.execute(task5);
		service.execute(task6);
		service.execute(task7);
		service.shutdown();
		
		try {
			  service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
		
		return job;
	}
}
