package gov.fda;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/auth", initParams = { @WebInitParam(name = "id", value = "") })
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Auth() {

	}

	public void init() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("This is the Test Servlet");

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			out.print("<br/>Header Name: <em>" + headerName);
			String headerValue = request.getHeader(headerName);
			out.print("</em>, Header Value: <em>" + headerValue);
			out.println("</em>");
		}

		out.println("<hr/>");
		String authHeader = request.getHeader("authorization");
		if (authHeader != null) {
			String encodedValue = authHeader.split(" ")[1];

			out.println("Base64-encoded Authorization Value: <em>" + encodedValue);
			byte[] decodedValue= new byte[]{};
			try {
				decodedValue = Base64.getDecoder().decode(encodedValue);
			}catch(Exception e) {
			};
			out.println("</em><br/>Base64-decoded Authorization Value: <em>" + decodedValue);
			out.println("</em>");
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.addHeader("WWW-Authenticate", "DIGEST realm=\"TEST.ORG\"");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		doGet(request, response);
	}
}