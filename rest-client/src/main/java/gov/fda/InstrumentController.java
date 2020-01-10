package gov.fda;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
/**
 * Servlet implementation class Instrument
 */
@WebServlet(urlPatterns = "/instrument", initParams = { @WebInitParam(name = "id", value = "") })
public class InstrumentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public InstrumentController() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String referrer = request.getHeader("referer");
		response.sendRedirect(referrer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final ClientConfig config = new ClientConfig();
	    HazelcastInstance hz = com.hazelcast.client.HazelcastClient.newHazelcastClient(config);
		IQueue<String> queue = hz.getQueue("STARTFLOW.Q");
		for (int k = 1; k < 10; k++) {
			try {
			queue.put(new Integer(k).toString());
			System.out.println("Producing: " + k);
			Thread.sleep(1000);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Producer Finished!");
		doGet(request, response);
		hz = null;
	}

	
}
