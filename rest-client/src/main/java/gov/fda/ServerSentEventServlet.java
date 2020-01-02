package gov.fda;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//enable async in the servlet
@WebServlet(urlPatterns={"/sseasync"}, asyncSupported=true)
public class ServerSentEventServlet extends HttpServlet {

 private ExecutorService executorService;
 public void init() {
	 executorService = Executors.newSingleThreadExecutor();
 }
 @Override
 protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {

     // set content type
	 response.setContentType("text/event-stream");
	 response.setCharacterEncoding("UTF-8");

     final String msg = request.getParameter("msg");

     // start async
     final AsyncContext ac = request.startAsync();

     final PrintWriter writer = response.getWriter();
     Runnable runnable = new Runnable() {
         @Override
         public void run() {
             // echo msg 5 times
             for (int i = 0; i < 5; i++) {
                 if (i == 4) { // last
                     // SSE event field
                     writer.write("event: close\n");
                 }
                 // SSE data field
                 // last field with blank new line
                 writer.write("data: " + msg + "\n\n");
                 writer.flush();

                 try {
                     Thread.sleep(2000);
                 } catch(InterruptedException iex) {
                     iex.printStackTrace();
                 }
             }

             // complete async
             ac.complete();
         }
     };

     // submit the runnable to managedExecutorService
     executorService.submit(runnable);
 }
}