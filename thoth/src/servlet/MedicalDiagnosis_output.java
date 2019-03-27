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

@WebServlet("thoth/html/medical_output")
public class MedicalDiagnosis_output extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String s_id = session.getId();
	    String id = session.getAttribute(s_id).toString();
	    //System.out.println("output"+id);//コメントアウト
	    
	    Facade facade = Facade.getInstance();
	    ArrayList<Log> logs = facade.getLogs(id);
	    Log log = logs.get(logs.size()-1);
	    ArrayList<String> results = log.getResult();
	    String res = "[";
	    for(String result:results){
		res += "\""+result + "\",";
	    }
	    res = res.substring(0,res.length()-1);
	    res += "]";
	    facade.close();
		//String res="[\"かぜ\",\"麻疹\",\"胸膜炎\"]";//コメントアウト
	    StringBuilder builder = new StringBuilder();
	    //builder.append('{');
	    //builder.append("\"message\":\"").append(res).append("\"");
	    //builder.append('}');
	    builder.append(res);
	    String json = builder.toString();
	    System.out.println(json);
	    response.setContentType("application/json");
	    PrintWriter writer = response.getWriter();
	    writer.append(json);
	    writer.flush();
	}
}
