package nl.hu.ipass.vergelijkNL.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {
	public String findRoleForUsernameAndPassword(String username, String password) {
		String role = null;
		String query = "SELECT role FROM gebruiker WHERE username = ? AND password = ?";
		
		try (Connection con = super.getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				role = rs.getString("role");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
		}
		return role;
	}
	
	public String getUser(String email, String username){
		String query = "SELECT gebruiker.password " 
					 + "FROM gebruiker "
					 + "WHERE gebruiker.username = ? "
					 + "AND gebruiker.email = ?";
		String pass = "";
		
		try(Connection con = super.getConnection()){
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, email);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				pass = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	public boolean createUser(String username, String password, String email){
		String query = "INSERT INTO public.gebruiker(username, password, role, email) VALUES (?, ?, ?, ?)";
		
		try(Connection con = super.getConnection()){
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, "User");
			pstmt.setString(4, email);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}