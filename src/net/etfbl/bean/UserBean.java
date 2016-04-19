package net.etfbl.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.dao.TravelsDao;
import net.etfbl.dao.UserDao;
import net.etfbl.dto.Messages;
import net.etfbl.dto.Travels;
import net.etfbl.dto.User;

@ManagedBean
@SessionScoped
public class UserBean {
	private boolean loggedIn = false;
	private int loginId;
	private int loggedInUser;
	private String loginUsername;
	private String loginPassword;
	private String messageLogin;
	private User newUser = new User();
	private Date tempDate;
	private Object messageRegister;
	private ArrayList<User> userTable = new ArrayList<User>();
	private int userPrivilegie;
	public static int userIdentifier;
	private ArrayList<Travels> userTravelsSearchResult = new ArrayList<Travels>();
	private String searchType;
	private String searchKey;
	private boolean hasResult = true;
	private String searchKeyAuthor;
	private String userSearchName;
	private String userSearchSurname;
	private ArrayList<User> userSearchResults = new ArrayList<User>();
	private String addContactMsg = "";
	private String numberOfUnreadMessages;
	private ArrayList<Messages> userMessages = new ArrayList<Messages>();
	private Messages messageView = new Messages();

	public String login() {
		User user = new User();
		user = UserDao.login(loginUsername, loginPassword);
		if (user != null) {
			if (user.getActive() == 0) {
				messageLogin = "User account is not activated by Administrator.";
			} else {
				setLoggedIn(true);
				loginId = user.getId();
				loggedInUser = loginId;
				userIdentifier = loginId;
				setUserPrivilegie(user.getPrivilegies_id());
				if (user.getPrivilegies_id() == 1) {
					this.userTable = UserDao.userTable();
					return "/admin/adminMainPage.xhtml?faces-redirect=true";
				} else if (user.getPrivilegies_id() == 2) {
					numberOfUnreadMessages = UserDao.numberOfUnreadMessages(user.getId());
					userMessages = UserDao.userMessages(user.getId());
					return "/user/userMainPage.xhtml?faces-redirect=true";
				}
			}
		} else {
			messageLogin = "Invalid username or password!";
		}

		return "";

	}

	public void registration() {
		User u = new User();
		u.setName(newUser.getName());
		u.setSurname(newUser.getSurname());
		u.setUsername(newUser.getUsername());
		u.setPassword(newUser.getPassword());
		u.setBiography(newUser.getBiography());
		u.setEmail(newUser.getEmail());
		u.setBirthday(new Timestamp(getTempDate().getTime()));
		u.setPrivilegies_id(2);
		messageRegister = UserDao.registration(u);
	}

	public String logout() {
		setLoggedIn(false);
		return "/index.xhtml?faces-redirect=true";
	}

	/**
	 * Redirects to users Inbox page
	 * 
	 * @return
	 */
	public String userInbox() {
		return "/user/userInboxPage.xhtml?faces-redirect=true";

	}

	/**
	 * User activation or deactivation
	 * 
	 */
	public void userActivation(int id) {
		int userStatus = UserDao.getUserStatus(id);
		if (userStatus == 0) {
			UserDao.userActivation(id);
		} else {
			UserDao.userDeactivation(id);
		}
		this.userTable = UserDao.userTable();

	}

	public String messageStatus(int id) {
		String status = "Read";
		if (id == 1) {
			status = "Unread";
		}
		return status;
	}

	/**
	 * 
	 * @return User Travel requested Travel page
	 */
	public String userTravelsSearch() {
		setUserTravelsSearchResult(TravelsDao.userTravelsSearchResult(searchKey, searchType));
		if (getUserTravelsSearchResult().isEmpty()) {
			hasResult = false;
		} else {
			hasResult = true;
			return "/user/userTravelSearchResult.xhtml?faces-redirect=true";
		}
		return "";

	}

	public void addToContacts(int id) {
		addContactMsg = UserDao.addToContacts(loginId, id);
	}

	public String userSearch() {
		setUserSearchResults(UserDao.userSearchResults(userSearchName, userSearchSurname));
		return "";

	}

	/**
	 * Opens user message, sets Unread status to Read
	 * 
	 * @param id
	 * @return
	 */
	public String openUserMessage(int id) {
		UserDao.setMessageToRead(id);
		for (Messages message : userMessages) {
			if (message.getId() == id) {
				messageView.setId(message.getId());
				messageView.setSender(message.getSender());
				messageView.setReceiver(message.getReceiver());
				messageView.setText(message.getText());
				messageView.setDate(message.getDate());
			}
		}
		return "/user/userMessageView.xhtml?faces-redirect=true";

	}

	/**
	 * Returns user's Name and Surname by provided ID
	 * 
	 * @param id
	 * @return
	 */
	public static String userNameAndSurnameByID(int id) {
		return UserDao.getUserNameAndSurname(id);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the loggedInUser
	 */
	public int getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @param loggedInUser
	 *            the loggedInUser to set
	 */
	public void setLoggedInUser(int loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	/**
	 * @return the loginUsername
	 */
	public String getLoginUsername() {
		return loginUsername;
	}

	/**
	 * @param loginUsername
	 *            the loginUsername to set
	 */
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword
	 *            the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * @return the messageLogin
	 */
	public String getMessageLogin() {
		return messageLogin;
	}

	/**
	 * @param messageLogin
	 *            the messageLogin to set
	 */
	public void setMessageLogin(String messageLogin) {
		this.messageLogin = messageLogin;
	}

	/**
	 * @return the newUser
	 */
	public User getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser
	 *            the newUser to set
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	/**
	 * @return the tempDate
	 */
	public Date getTempDate() {
		return tempDate;
	}

	/**
	 * @param tempDate
	 *            the tempDate to set
	 */
	public void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}

	/**
	 * @return the messageRegister
	 */
	public Object getMessageRegister() {
		return messageRegister;
	}

	/**
	 * @param messageRegister
	 *            the messageRegister to set
	 */
	public void setMessageRegister(Object messageRegister) {
		this.messageRegister = messageRegister;
	}

	/**
	 * @return the userTable
	 */
	public ArrayList<User> getUserTable() {
		return userTable;
	}

	/**
	 * @param userTable
	 *            the userTable to set
	 */
	public void setUserTable(ArrayList<User> userTable) {
		this.userTable = userTable;
	}

	/**
	 * @return the userPrivilegie
	 */
	public int getUserPrivilegie() {
		return userPrivilegie;
	}

	/**
	 * @param userPrivilegie
	 *            the userPrivilegie to set
	 */
	public void setUserPrivilegie(int userPrivilegie) {
		this.userPrivilegie = userPrivilegie;
	}

	/**
	 * @return the userTravelsSearchResult
	 */
	public ArrayList<Travels> getUserTravelsSearchResult() {
		return userTravelsSearchResult;
	}

	/**
	 * @param userTravelsSearchResult
	 *            the userTravelsSearchResult to set
	 */
	public void setUserTravelsSearchResult(ArrayList<Travels> userTravelsSearchResult) {
		this.userTravelsSearchResult = userTravelsSearchResult;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType
	 *            the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @param searchKey
	 *            the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the hasResult
	 */
	public boolean isHasResult() {
		return hasResult;
	}

	/**
	 * @param hasResult
	 *            the hasResult to set
	 */
	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	/**
	 * @return the searchKeyAuthor
	 */
	public String getSearchKeyAuthor() {
		return searchKeyAuthor;
	}

	/**
	 * @param searchKeyAuthor
	 *            the searchKeyAuthor to set
	 */
	public void setSearchKeyAuthor(String searchKeyAuthor) {
		this.searchKeyAuthor = searchKeyAuthor;
	}

	/**
	 * @return the userSearchName
	 */
	public String getUserSearchName() {
		return userSearchName;
	}

	/**
	 * @param userSearchName
	 *            the userSearchName to set
	 */
	public void setUserSearchName(String userSearchName) {
		this.userSearchName = userSearchName;
	}

	/**
	 * @return the userSearchSurname
	 */
	public String getUserSearchSurname() {
		return userSearchSurname;
	}

	/**
	 * @param userSearchSurname
	 *            the userSearchSurname to set
	 */
	public void setUserSearchSurname(String userSearchSurname) {
		this.userSearchSurname = userSearchSurname;
	}

	/**
	 * @return the userSearchResults
	 */
	public ArrayList<User> getUserSearchResults() {
		return userSearchResults;
	}

	/**
	 * @param userSearchResults
	 *            the userSearchResults to set
	 */
	public void setUserSearchResults(ArrayList<User> userSearchResults) {
		this.userSearchResults = userSearchResults;
	}

	/**
	 * @return the addContactMsg
	 */
	public String getAddContactMsg() {
		return addContactMsg;
	}

	/**
	 * @param addContactMsg
	 *            the addContactMsg to set
	 */
	public void setAddContactMsg(String addContactMsg) {
		this.addContactMsg = addContactMsg;
	}

	/**
	 * @return the numberOfUnreadMessages
	 */
	public String getNumberOfUnreadMessages() {
		return numberOfUnreadMessages;
	}

	/**
	 * @param numberOfUnreadMessages
	 *            the numberOfUnreadMessages to set
	 */
	public void setNumberOfUnreadMessages(String numberOfUnreadMessages) {
		this.numberOfUnreadMessages = numberOfUnreadMessages;
	}

	public ArrayList<Messages> getUserMessages() {
		return userMessages;
	}

	public void setUserMessages(ArrayList<Messages> userMessages) {
		this.userMessages = userMessages;
	}

	public Messages getMessageView() {
		return messageView;
	}

	public void setMessageView(Messages messageView) {
		this.messageView = messageView;
	}

}
