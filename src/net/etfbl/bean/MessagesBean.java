package net.etfbl.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.dao.MessageDao;
import net.etfbl.dto.Messages;

@ManagedBean
@SessionScoped
public class MessagesBean {

	private ArrayList<Messages> messageList = new ArrayList<Messages>();
	private String sentStatus = "";
	private String messageText = "";
	private int messagereceiver;
	
	public void sendMessage() {
		sentStatus = MessageDao.sendMessage(messageText, UserBean.userIdentifier, messagereceiver, false);
	}
	
	public String writeMessage(int contactId) {
		setMessagereceiver(contactId);
		return "/user/userMessage.xhtml?faces-redirect=true";
	}

	public ArrayList<Messages> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<Messages> messageList) {
		this.messageList = messageList;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getMessagereceiver() {
		return messagereceiver;
	}

	public void setMessagereceiver(int messagereceiver) {
		this.messagereceiver = messagereceiver;
	}
	
	
}
