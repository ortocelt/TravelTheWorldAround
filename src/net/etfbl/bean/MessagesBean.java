package net.etfbl.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.dto.Messages;

@ManagedBean
@SessionScoped
public class MessagesBean {

	private ArrayList<Messages> messageList = new ArrayList<Messages>();
	

	public ArrayList<Messages> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<Messages> messageList) {
		this.messageList = messageList;
	}
}
