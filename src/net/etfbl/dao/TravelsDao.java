package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.etfbl.dto.Travels;

public class TravelsDao {

	private static String insertNewTravel = "insert into travels(author,title,text,date,location,keywords,brief) values (?,?,?,now(),?,?,?)";
	private static String userTravels = "select * from travels where author = ?";
	private static String selectTravel = "select * from travels where id = ?";
	private static String allTravelsPhotoPaths = "SELECT path FROM travel.photo where travel = ?";

	public static ArrayList<Travels> guestTravelsSearchResult(String searchKey,
			String searchType) {

		ArrayList<Travels> travels = new ArrayList<Travels>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = null;
			if (searchType.equals("title")) {
				st = conn.prepareStatement("select " + "travel.travels.id,"
						+ "travel.travels.title, "
						+ "travel.travels.brief from travels "
						+ "where travel.travels.title like '%" + searchKey
						+ "%'");
			} else {
				st = conn.prepareStatement("select " + "travel.travels.id,"
						+ "travel.travels.title, "
						+ "travel.travels.brief from travels"
						+ "where travel.travels.keyword like '%" + searchKey
						+ "%'");
			}
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Travels travel = new Travels();
				travel.setId(rs.getInt("id"));
				travel.setTitle(rs.getString("title"));
				travel.setBrief(rs.getString("brief"));
				travels.add(travel);

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return travels;
	}

	public static int addNewTravelData(int userIdentifier, String title,
			String text, String brief, String keywords, String location) {
		int i = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");

			PreparedStatement st = conn.prepareStatement(insertNewTravel,
					Statement.RETURN_GENERATED_KEYS);
			// author,title,text,date,location,keywords,brief
			st.setInt(1, userIdentifier);
			st.setString(2, title);
			st.setString(3, text);
			st.setString(4, brief);
			st.setString(5, keywords);
			st.setString(6, location);
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				i = rs.getInt(1);
			}

			// if (i != 0) {
			// ResultSet keys = st.getGeneratedKeys();
			// keys.next();
			// int lastId = keys.getInt(1);
			// ArrayList<String> recenzenti = noviRecezenti;
			// for (String k : recenzenti) {
			// PreparedStatement st1 = conn
			// .prepareStatement(insertRecenzentiNoveKonferencije);
			// st1.setInt(1, Integer.parseInt(k));
			// st1.setInt(2, lastId);
			// st1.executeUpdate();
			// PreparedStatement st2 =
			// conn.prepareStatement(automatskaPorukaRecenzentu);
			// st2.setInt(1, Integer.parseInt(k));
			// st2.setString(2, "Dodati ste za recenzenta konferencije " +
			// novaKonferencija.getNaziv());
			// st2.executeUpdate();
			// }

			return i;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static ArrayList<Travels> userTravels(int userIdentifier) {
		ArrayList<Travels> userTravels = new ArrayList<Travels>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");

			PreparedStatement st = conn
					.prepareStatement(TravelsDao.userTravels);
			st.setInt(1, userIdentifier);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Travels travel = new Travels();
				travel.setId(rs.getInt("id"));
				travel.setActive(rs.getInt("active"));
				travel.setAuthor(rs.getInt("author"));
				travel.setBrief(rs.getString("brief"));
				travel.setDate(rs.getTimestamp("date"));
				travel.setKeywords(rs.getString("keywords"));
				travel.setLocation(rs.getString("location"));
				travel.setShared(rs.getInt("shared"));
				travel.setText(rs.getString("text"));
				travel.setTitle(rs.getString("title"));
				userTravels.add(travel);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userTravels;
	}

	public static Travels selectTravel(int travelId) {
		Travels travel = new Travels();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");

			PreparedStatement st = conn.prepareStatement(selectTravel);
			st.setInt(1, travelId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				travel.setId(rs.getInt("id"));
				travel.setActive(rs.getInt("active"));
				travel.setAuthor(rs.getInt("author"));
				travel.setBrief(rs.getString("brief"));
				travel.setDate(rs.getTimestamp("date"));
				travel.setKeywords(rs.getString("keywords"));
				travel.setLocation(rs.getString("location"));
				travel.setShared(rs.getInt("shared"));
				travel.setText(rs.getString("text"));
				travel.setTitle(rs.getString("title"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return travel;
	}

	public static ArrayList<Travels> userTravelsSearchResult(String searchKey,
			String searchType) {
		ArrayList<Travels> travels = new ArrayList<Travels>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = null;
			if (searchType.equals("title")) {
				st = conn.prepareStatement("select " + "travel.travels.id,"
						+ "travel.travels.title, " + "travel.travels.brief, "
						+ "travel.travels.location," + "travel.travels.author,"
						+ "travel.travels.date,"
						+ "travel.travels.text from travels "
						+ "where travel.travels.title like '%" + searchKey
						+ "%' and travel.travels.active != 0");
			} else {
				st = conn.prepareStatement("select " + "travel.travels.id,"
						+ "travel.travels.title, " + "travel.travels.brief, "
						+ "travel.travels.location," + "travel.travels.author,"
						+ "travel.travels.date,"
						+ "travel.travels.text from travels "
						+ "where travel.travels.keyword like '%" + searchKey
						+ "%' and travel.travels.active != 0");
			}
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Travels travel = new Travels();
				travel.setId(rs.getInt("id"));
				travel.setTitle(rs.getString("title"));
				travel.setBrief(rs.getString("brief"));
				travel.setAuthor(rs.getInt("author"));
				travel.setDate(rs.getTimestamp("date"));
				travel.setText(rs.getString("text"));
				travel.setLocation(rs.getString("location"));
				travels.add(travel);

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return travels;
	}

	public static ArrayList<String> allTravelPhotos(int id) {
		ArrayList<String> allPhotos = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(allTravelsPhotoPaths);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				allPhotos.add(rs.getString("path"));
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return allPhotos;
	}
}