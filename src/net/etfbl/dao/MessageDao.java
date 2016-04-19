package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MessageDao {

	private static final String sendMessage = "insert into messages (text, date, mread, sender, receiver) values(?,now(),0,?,?)";
	private static final String sendNotification = "insert into messages (text, date, mread, sender, receiver) values(?,now(),0,?,?)";

	public static String sendMessage(String messageText, int sender, int receiver, boolean isNotification) {
		String message = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "root");
			if (!isNotification) {
				PreparedStatement st = conn.prepareStatement(sendMessage);
				st.setString(1, messageText);
				st.setInt(2, sender);
				st.setInt(3, receiver);
				st.executeUpdate();
				message = "Message sent";
			} else {
				PreparedStatement st = conn.prepareStatement(sendNotification);
				st.setString(1, messageText);
				st.setInt(2, sender);
				st.setInt(3, receiver);
				st.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;

	}

}
