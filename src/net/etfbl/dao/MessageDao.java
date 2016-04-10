package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageDao {

	private static final String sendMessage = "insert into messages values(?,now(),0,?,?)";

	public static void sendMessage(String messageText, int sender, int receiver) {
		try {
			Class.forName("com.mysql.Driver");
			Connection conn = DriverManager.getConnection("com.mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(sendMessage);
			st.setString(1, messageText);
			st.setInt(2, sender);
			st.setInt(3, receiver);
			st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
