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

@WebServlet("thoth/html/medical_input")

public class MedicalDiagnosis_input extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String s_id = session.getId();
	    String id = session.getAttribute(s_id).toString();
	    boolean bool[] = new boolean[20];
	    boolean b = false;
	    for(int num = 0; num < 20; num++){
		String str = "q" + String.valueOf(num+1);
		String q = (request.getParameter(str) == null) ? "" :request.getParameter(str);
		if(q.equals("0")) bool[num] = true;
		else if(q.equals("1"))bool[num] = false;
		else b = true;
	    }
	    
	    String output = null;
	    String did = "医療診断";
	    ArrayList<String> result = new ArrayList<String>();
	    Facade facade = Facade.getInstance();
	    ArrayList<Disease> List = facade.getDiseaseList();
	    double maxScore = 0.0;
	    for(Disease dise:List){
		double score=checkDisease(dise.getCharacteristic(), bool);
	        if(score >=0.85) {
		    result.add(dise.getName());
		    if(maxScore<score) {
			maxScore = score;
			result.set(result.indexOf(dise.getName()),result.get(0));
			result.set(0,dise.getName());
		    }
		}
	    }
	    if(result.isEmpty()){
		result.add("健康");
	    }
	    if(result.size()==1){
		result.add("特になし");
	    }
	    facade.registerLog(id,did, result);
	    String res = null;
	    if(b){
		res ="MedicalDiagnosis_input.html";
	    }
	    else {
		res = "MedicalDiagnosis_output.html";
	    }
	    facade.close();

		
		
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"message\":\"").append(res).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}

    private double checkDisease(boolean[] dise, boolean[] bool){
	double score = 0;
	double all=0;
	for(int i = 0; i<bool.length; i++){
	    
	    //System.out.println(dise[i]);
	    if(dise[i]){
		all++;
	    if(bool[i]) score++;
	    }
	}
	return score;
    }
}
