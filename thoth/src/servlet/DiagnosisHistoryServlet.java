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
import javax.servlet.http.HttpSession;


import java.util.ArrayList;

import thoth.src.model.*;
    
@WebServlet("thoth/html/diagnosisHis")
public class DiagnosisHistoryServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");
	String output = "";
	HttpSession session = request.getSession();
	String s_id = session.getId();
	String userId = session.getAttribute(s_id).toString();
	
	    
	    Facade facade = Facade.getInstance();
	    ArrayList<Log> logs = facade.getLogs(userId);
	    System.out.println(logs.size());
	    ArrayList<String> timeString = getDatesString(logs);
	    ArrayList<String> resultString = getDatasString(logs);
	    output = "[";
	    for(int i = 0; i<timeString.size();i++){
		StringBuilder string = new StringBuilder(timeString.get(i));
		string.insert(5,"年 ");
		string.insert(9,"月");
		string.insert(12,"日 ");
		string.insert(16,"時");
		string.insert(19,"分");
		string.insert(22,"秒");

		output += string +",";
		output += resultString.get(i);
		
		output +=",";
	    }
	    output = output.substring(0,output.length()-1);
	    output += "]";
	    
	    
	    facade.close();
			        
		StringBuilder builder = new StringBuilder();
		builder.append(output);
		String json = builder.toString();
		System.out.println(json);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
    }
    private ArrayList<String> getDatesString(ArrayList<Log> logs){
        ArrayList<String> dates = new ArrayList<String>();
        for(Log log : logs) dates.add("\""+log.getDate()+"\"");
	return dates;
    }
    private ArrayList<String> getDatasString(ArrayList<Log> logs){
	ArrayList<String> output = new ArrayList<String>();
	for(Log log: logs){
	    String data = "\""+log.getDid()+"\",[";
	    for(String result: log.getResult()){
		data += "\""+result + "\",";
	    }
	    data = data.substring(0,data.length()-1);
	    data += "]";
	    output.add(data);
	}
	return output;
	}
}
