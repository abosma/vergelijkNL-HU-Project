package nl.hu.ipass.vergelijkNL.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class ProductDAO extends BaseDAO{

	public int insertProduct(String naam, double prijs, String link, String imagelink){
		String insertquery = "INSERT INTO public.product(naam, prijs, link, imagelink)" + 
							"VALUES (?, ?, ?, ?)";
		int id = 0;
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(insertquery, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, naam);
			pstmt.setDouble(2, prijs);
			pstmt.setString(3, link);
			pstmt.setString(4, imagelink);
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			while(rs.next()){
				id = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			String duplicatequery = "SELECT productid FROM public.product WHERE naam = ?";
			
			try (Connection con = super.getConnection()) {
				PreparedStatement pstmt = con.prepareStatement(duplicatequery);
				pstmt.setString(1, naam);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()){
					id = rs.getInt(1);
				}
				
			}catch (SQLException b) {
				b.printStackTrace();
			}
		}
		return id;
	}
	
	public boolean favorietProduct(String username, int prodID){
		String favorietquery = "INSERT INTO favorietenlijst(gebruikerid, productid) " +
							    "SELECT gebruikerid, productid " +
							    "FROM gebruiker, product " +
								"WHERE gebruiker.username = ? " + 
								"AND product.productid = ?";
		
		try(Connection con = super.getConnection()){
			PreparedStatement pstmt = con.prepareStatement(favorietquery);
			
			pstmt.setString(1, username);
			pstmt.setInt(2, prodID);
			
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean verwijderFavoriet(String username, int prodID){
		String favorietquery = "DELETE FROM favorietenlijst " +
							   "WHERE favorietenlijst.productid = ? " +
							   "AND favorietenlijst.gebruikerid in (SELECT gebruiker.gebruikerid " +
				                                    				"FROM gebruiker " +
			                                    					"WHERE gebruiker.username = ?)";
		
		try(Connection con = super.getConnection()){
			PreparedStatement pstmt = con.prepareStatement(favorietquery);
			
			pstmt.setInt(1, prodID);
			pstmt.setString(2, username);
			
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public JsonObjectBuilder getProducten(String username){
		JsonObjectBuilder returnProducten = Json.createObjectBuilder();
		String getquery = "SELECT product.productid, product.naam, product.prijs, product.link, product.imagelink " +
						  "FROM favorietenlijst " +
						  "LEFT OUTER JOIN gebruiker ON favorietenlijst.gebruikerid = gebruiker.gebruikerid " +
						  "LEFT OUTER JOIN product ON favorietenlijst.productid = product.productid " +
						  "WHERE gebruiker.gebruikerid IN (SELECT gebruiker.gebruikerid " +
                                						   "FROM gebruiker " +
                                						   "WHERE gebruiker.username = ?)";
		
		try(Connection con = super.getConnection()){
			PreparedStatement pstmt = con.prepareStatement(getquery);
			
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				returnProducten.add("Product" + rs.getInt("productid"), Json.createObjectBuilder()
						.add("ID", rs.getInt("productid"))
						.add("naam", rs.getString("naam"))
						.add("prijs", rs.getDouble("prijs"))
						.add("url", rs.getString("link"))
						.add("image", rs.getString("imagelink")));
			}
		}catch(SQLException e){
			System.out.println(e);
		}
		
		return returnProducten;
	}
	
}
