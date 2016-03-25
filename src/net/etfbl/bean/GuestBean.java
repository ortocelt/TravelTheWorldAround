package net.etfbl.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.dto.Travels;

@ManagedBean
@SessionScoped
public class GuestBean {

	private ArrayList<Travels> guestTravelsSearchResult = new ArrayList<Travels>();
	

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
}
