package net.etfbl.dto;

import java.sql.Timestamp;

public class Messages {
	private int id;
	private String text;
	private Timestamp date;
	private int mread;
	private int sender;
	private int receiver;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getMread() {
		return mread;
	}

	public void setMread(int mread) {
		this.mread = mread;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

}
