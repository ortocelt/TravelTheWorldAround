package net.etfbl.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AjaxBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean responseRendered = false;
	
	public AjaxBean() {
    }

    public void renderUsers() {
        responseRendered = !responseRendered;
        System.out.println("finding..... ("+ responseRendered+")" + this);
    }
    public boolean isResponseRendered() {
        return responseRendered;
    }

    public void setResponseRendered(boolean responseRendered) {
        this.responseRendered = responseRendered;
    }       

}
