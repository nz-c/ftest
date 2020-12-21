package com.ftest.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ftest.service.SumService;
import static com.ftest.util.Log.LOG;



@WebServlet(description = "Servlet to sum received numbers", urlPatterns = { "/" })
public class SumServlet extends HttpServlet {

	private static final long serialVersionUID = 7274769849441121482L;
	public static final String CONTENT_TYPE = "text/plain";
	public static final String ENCODING = "UTF-8";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// process the first line only... enough 
		String line = request.getReader().readLine();
		LOG.info("Received data: " + line);
		
		response.setContentType(SumServlet.CONTENT_TYPE);
		response.setCharacterEncoding(SumServlet.ENCODING);
		
		response.getWriter().write("" + 
				SumService.getInstance().processInput(line));
		
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	

}
