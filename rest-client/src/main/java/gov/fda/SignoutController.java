package gov.fda;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Instrument
 */
@WebServlet(urlPatterns = "/signout", initParams= {@WebInitParam(name = "id", value = "")})
public class SignoutController extends HttpServlet {
	private static final long serialVersionUID = 101L;

    /**
     * Default constructor. 
     */
    public SignoutController() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referrer = request.getHeader("referer");
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		for (int i = 0; i < cookies.length; i++) {
		 cookies[i].setMaxAge(0);
		 response.addCookie(cookies[i]);
		}
		response.sendRedirect(referrer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
