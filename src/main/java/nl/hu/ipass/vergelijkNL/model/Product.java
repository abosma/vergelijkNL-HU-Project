package nl.hu.ipass.vergelijkNL.model;

public class Product {

	private String naam;
	private String image;
	private String url;
	private double prijs;
	
	public Product(String naam, String image, String url, double prijs){
		this.setNaam(naam);
		this.setImage(image);
		this.setUrl(url);
		this.setPrijs(prijs);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPrijs() {
		return prijs;
	}

	public void setPrijs(double prijs) {
		this.prijs = prijs;
	}
	
	@Override
	public String toString(){
		return this.getNaam() + "\n" + this.getPrijs() + "\n" + this.getImage() + "\n" + this.getUrl();
	}
}
