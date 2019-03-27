package thoth.src.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import thoth.src.model.*;

@WebServlet("/thoth/html/diseaseListSe")
public class DiseaseListServlet extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");

	    
	    String output = "";

	    
	    if(id.equals("0")){
		    Facade facade = Facade.getInstance();
		    ArrayList<String> outputData = facade.getDiseaseNameList();
		    
		    output = "[";
		    for(String data: outputData){
			output += "\"" + data + "\",";
		    }
		    
		    output = output.substring(0,output.length() -1);
		    output += "]";
		    facade.close();
	    }
	    
	    //output = "[\"かぜ\",\"麻疹\",\"胸膜炎\",\"気管支炎\"]";
	     StringBuilder builder = new StringBuilder();
		    builder.append(output);
		    String json = builder.toString();
		    System.out.println(json);
		    response.setContentType("application/json");
		    PrintWriter writer = response.getWriter();
		    writer.append(json);
		    writer.flush();
	}
}
