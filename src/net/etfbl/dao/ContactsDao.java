package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import net.etfbl.dto.Contacts;

public class ContactsDao {
	
	private static final String contactsList = "select * from contacts where user = ?";

	public static ArrayList<Contacts> userContactsList(int userIdentifier) {
		
		ArrayList<Contacts> userContacts = new ArrayList<Contacts>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(contactsList);
			st.setInt(1, userIdentifier);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				Contacts contact = new Contacts();
				contact.setId(rs.getInt("id"));
				contact.setContact(rs.getInt("contact"));
				contact.setUser(rs.getInt("user"));
				userContacts.add(contact);
			}
					
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return userContacts;
	}
	
	
	


}
