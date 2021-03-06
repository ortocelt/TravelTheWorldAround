package net.etfbl.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.dao.ContactsDao;
import net.etfbl.dao.UserDao;
import net.etfbl.dto.Contacts;

@ManagedBean
@SessionScoped
public class ContactsBean {

	private ArrayList<Contacts> userContacts = new ArrayList<Contacts>();
	
	public void userContactsList() {
		userContacts = ContactsDao.userContactsList(UserBean.userIdentifier); 
	}
	
	public String contactNameAndSurname(int id) {
		return UserDao.getUserNameAndSurname(id);
	}

	public ArrayList<Contacts> getUserContacts() {
		return userContacts;
	}

	public void setUserContacts(ArrayList<Contacts> userContacts) {
		this.userContacts = userContacts;
	}
	
	public String contactListRedirection() { 
		userContactsList();
		return "/user/userContactList.xhtml?faces-redirect=true";
	}
	
	
}
