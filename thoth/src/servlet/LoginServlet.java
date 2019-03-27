package thoth.src.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thoth.src.model.*;

@WebServlet("thoth/html/loginSe")
public class LoginServlet extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    
	    String id = (request.getParameter("name") == null) ? "" : request.getParameter("name");

	    String pass = (request.getParameter("pass") == null) ? "" : request.getParameter("pass");
	    String output = null;
	    

	    Facade facade= Facade.getInstance();
	    System.out.println(id+":"+pass);
	    if(facade.login(id,pass)){
		output = "Main.html";
	        String s_id=session.getId();
	        session.removeAttribute(s_id);
		session.setAttribute(s_id,id);
		System.out.println(session.getAttribute(s_id));
	    }
	    else output = "Login.html";
	    facade.close();


		
		
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"message\":\"").append(output).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
