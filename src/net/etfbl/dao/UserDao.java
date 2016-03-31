package net.etfbl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dto.Messages;
import net.etfbl.dto.User;

public class UserDao {
	private static String login = "select * from user where username = ? and password = md5(?)";
	private static String registration = "insert into user (privilegies_id,name,surname,username,password,email,biography,birthday) values(?,?,?,?,md5(?),?,?,?)";
	private static String usernameCheck = "select * from user where username = ?";
	private static String allUsers = "select * from user";
	private static final String userActivation = "update user set active = 1 where id = ?";
	private static final String userDeactivation = "update user set active = 0 where id = ?";
	private static final String userStatus = "select active from user where id = ?";
	private static final String userNameAndSurname = "select name, surname from user where id = ?";
	private static final String searchUserByNameAndSurname = "select * from user where name = ? or surname = ?";
	private static String addContact = "insert into contacts (user, contact) values (?,?)";
	private static String checkContactList = "select * from contacts where user = ? and contact = ?";
	private static final String userMessages = "select * from messages where to = ? order by date asc";
	private static final String messagesCount = "select count(`to`) as number where `to` = ? and `read` = 0";

	public static User login(String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(login);
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName((rs.getString("name")));
				user.setSurname(rs.getString("surname"));
				user.setUsername(rs.getString("username"));
				user.setBiography((rs.getString("biography")));
				user.setBirthday(rs.getTimestamp("birthday"));
				user.setPrivilegies_id(rs.getInt("privilegies_id"));
				user.setActive(rs.getInt("active"));

				return user;
			}
			rs.close();
			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String registration(User u) {
		if (usernameCheck(u.getUsername())) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/travel", "root", "root");
				PreparedStatement st = conn.prepareStatement(registration);
				st.setInt(1, u.getPrivilegies_id());
				st.setString(2, u.getName());
				st.setString(3, u.getSurname());
				st.setString(4, u.getUsername());
				st.setString(5, u.getPassword());
				st.setString(6, u.getEmail());
				st.setString(7, u.getBiography());
				st.setTimestamp(8, u.getBirthday());
				st.executeUpdate();
				return "Successful registration. Administrator needs to approve the account.";

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Username is taken, please choose another";

	}

	public static boolean usernameCheck(String username) {
		boolean exists = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(usernameCheck);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return exists = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;

	}

	public static ArrayList<User> userTable() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(allUsers);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setActive(rs.getInt("active"));
				u.setBiography(rs.getString("biography"));
				u.setName(rs.getString("name"));
				u.setSurname(rs.getString("surname"));
				u.setUsername(rs.getString("username"));
				users.add(u);

			}

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public static void userActivation(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(userActivation);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getUserStatus(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(userStatus);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return rs.getInt("active");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static void userDeactivation(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn.prepareStatement(userDeactivation);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getUserNameAndSurname(int id) {
		String name = "", surname = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root","root");
		PreparedStatement st = conn.prepareStatement(userNameAndSurname);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			name = rs.getString("name");
			surname = rs.getString("surname");
			
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name + " " + surname;
	}

	public static ArrayList<User> userSearchResults(String userSearchName,
			String userSearchSurname) {
		ArrayList<User> userSearchResults = new ArrayList<User>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn
					.prepareStatement(searchUserByNameAndSurname);
			st.setString(1, userSearchName);
			st.setString(2, userSearchSurname);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				userSearchResults.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userSearchResults;
	}

	public static String addToContacts(int userId, int contactId) {
		if (checkContactList(userId, contactId)) {
			return "Contact already exists";
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st;
			st = conn.prepareStatement(addContact);
			st.setInt(1, userId);
			st.setInt(2, contactId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "Contact added";
	}
	
	public static boolean checkContactList(int userId, int contactId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/travel", "root", "root");
			PreparedStatement st = conn
					.prepareStatement(checkContactList);
			st.setInt(1, userId);
			st.setInt(2, contactId);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<Messages> userMessages(int id) {
		ArrayList<Messages> messages = new ArrayList<Messages>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel","root","root");
			PreparedStatement st = conn.prepareStatement(userMessages);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Messages message = new Messages();
				message.setId(rs.getInt("id"));
				message.setDate(rs.getTimestamp("date"));
				message.setFrom(rs.getInt("from"));
				message.setRead(rs.getInt("read"));
				message.setTo(rs.getInt("to"));
				message.setText(rs.getString("text"));
				messages.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}

	public static String numberOfUnreadMessages(int id) {
		String number = "0";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel","root","root");
		PreparedStatement st = conn.prepareStatement(messagesCount);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			number = String.valueOf(rs.getInt(number));
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return number;
	}
}
