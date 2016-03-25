package net.etfbl.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.etfbl.bean.UserBean;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {
	private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		 HttpServletRequest req = (HttpServletRequest) request;
		 HttpServletResponse res = (HttpServletResponse) response;
		UserBean user = (UserBean)((HttpServletRequest)request).getSession().getAttribute("userBean");
		String path = req.getRequestURI().substring(req.getContextPath().length());
		if (user == null || !user.isLoggedIn()) {

			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse)response).sendRedirect(contextPath + "/guest/login.xhtml");
		}
		if (path.startsWith("/admin/") && user.getUserPrivilegie() != 1) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		if (path.startsWith("/user/") && user.getUserPrivilegie() != 2) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
