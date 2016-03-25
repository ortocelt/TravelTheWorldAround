package net.etfbl.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import net.etfbl.dao.TravelsDao;
import net.etfbl.dto.Travels;
import net.etfbl.rss.Feed;
import net.etfbl.rss.FeedMessage;
import net.etfbl.rss.RssFeedParser;

@ManagedBean
@ViewScoped
public class IndexBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<FeedMessage> rssMessages;
	private ArrayList<Travels> guestTravelsSearchResult = new ArrayList<Travels>();
	private ArrayList<Travels> userTravelsSearchResult = new ArrayList<Travels>();
	private String searchType;
	private String searchKey;
	private boolean hasResult = true;
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try { 
		RssFeedParser parser = new RssFeedParser("http://feeds.feedburner.com/MatadorNetwork");
		Feed feed = parser.readFeed();
		rssMessages = feed.getMessages();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void guestTravelsSearch() {
		setGuestTravelsSearchResult(TravelsDao.guestTravelsSearchResult(searchKey,searchType));
		if (getGuestTravelsSearchResult().isEmpty()) {
			hasResult = false;
		} else {
			hasResult = true;
		}
	}
	
	public String userTravelsSearch() {
		setUserTravelsSearchResult(TravelsDao.userTravelsSearchResult(searchKey,searchType));
		if (getUserTravelsSearchResult().isEmpty()) {
			hasResult = false;
		} else {
			hasResult = true;
			return "/user/userTravelSearchResult.xhtml?faces-redirect=true";
		}
		return "";
		
	}

	public List<FeedMessage> getRssMessages() {
		return rssMessages;
	}

	public void setRssMessages(List<FeedMessage> rssMessages) {
		this.rssMessages = rssMessages;
	}
	
	public String guestLoginPath(){
		return "/guest/login.xhtml?faces-redirect=true";
	}
	
	public String guestRegisterPath(){
		return "/guest/register.xhtml?faces-redirect=true";
	}
	
	public String userManagementPage(){
		return "/admin/adminUserManagement.xhtml?faces-redirect=true";
	}
	
	public String userAddNewTravel(){
		return "/user/userNewTravel.xhtml?faces-redirect=true";
	}
	
	public String userMainPage() {
		return "/user/userMainPage.xhtml?faces-redirect=true";
	}
	
	
	/**
	 * @return the guestTravelsSearchResult
	 */
	public ArrayList<Travels> getGuestTravelsSearchResult() {
		return guestTravelsSearchResult;
	}

	/**
	 * @param guestTravelsSearchResult the guestTravelsSearchResult to set
	 */
	public void setGuestTravelsSearchResult(
			ArrayList<Travels> guestTravelsSearchResult) {
		this.guestTravelsSearchResult = guestTravelsSearchResult;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
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
	 * @param searchKey the searchKey to set
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
	 * @param hasResult the hasResult to set
	 */
	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	/**
	 * @return the userTravelsSearchResult
	 */
	public ArrayList<Travels> getUserTravelsSearchResult() {
		return userTravelsSearchResult;
	}

	/**
	 * @param userTravelsSearchResult the userTravelsSearchResult to set
	 */
	public void setUserTravelsSearchResult(
			ArrayList<Travels> userTravelsSearchResult) {
		this.userTravelsSearchResult = userTravelsSearchResult;
	}
	

}
