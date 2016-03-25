package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhotoDao {

	private static String uploadedPhoto = "insert into photo (path,uploader,travel) values(?,?,?)";

	public static void uploadPhoto(int userIdentifier, int travel,
			String fileLocation) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(uploadedPhoto);
			st.setString(1, fileLocation);
			st.setInt(2, userIdentifier);
			st.setInt(3, travel);
			st.executeUpdate();

			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
