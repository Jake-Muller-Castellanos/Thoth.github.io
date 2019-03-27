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

@WebServlet("thoth/html/quiz_input")
public class DiseaseQuiz_inputServlet extends HttpServlet{
	
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String id = (request.getParameter("id") == null) ? "" : request.getParameter("id");

	    String output ="[";
	    Facade facade = Facade.getInstance();
	    ArrayList<Quiz> quizes = facade.getQuiz("./WebContent/WEB-INF/DiseaseQuiz.xlsx","DiseaseQuiz");
	    for(Quiz quiz:quizes){
		output += "\""+quiz.getQuestion()+"\",\""+quiz.getAnswer()+"\",";
		output += getChoicesArryString(quiz.getChoices()) +",";
	    }
	    output = output.substring(0,output.length() -1);
	    output += "]";
	    facade.close();

	    //output ="[\"out of jointの意味は?\",\"1\",\"脱臼\",\"骨折\",\"関節痛\",\"「ナース」の元々の意味は?\",\"3\",\"女神\",\"神様\",\"乳母\",\"医療用語「アッペ」の意味は?\",\"2\",\"中耳炎\",\"虫垂炎\",\"外耳炎\",\"ユニバーサルデザインとは?\",\"2\",\"環境に優しいデザイン\",\"誰にとっても使いやすいデザイン\",\"地域の特色を反映したデザイン\",\"「腎盂」の読み方は?\",\"3\",\"じんもう\",\"じんてい\",\"じんう\",\"インフルエンザに感染することで併発する病気は?\",\"1\",\"脳炎\",\"やけど\",\"全身感染症\",\"「MRI」とは ?\",\"3\",\"コンピュータ断面撮影\",\"超音波検査\",\"磁気共鳴画像\",\"「MOF」とは?\",\"1\",\"多臓器不全\",\"心不全\",\"免疫不全\",\"日本人死亡原因第1位は?\",\"2\",\"脳卒中\",\"ガン\",\"老衰\",\"消化器官でないのは？\",\"1\",\"脾臓\",\"肝臓\",\"膵臓\"]";//コメントアウト
		
		StringBuilder builder = new StringBuilder();
		//builder.append('{');
		//builder.append("\"message\":\"").append(output).append("\"");
		//builder.append('}');
		builder.append(output);
		String json = builder.toString();
		System.out.println(json);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();

	}


    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	String s_id = session.getId();
	String result[] = new String[10];
	boolean b = false;
	for(int num = 0; num < 10; num++){
	    String str = "q" + String.valueOf(num+1);
	    String q = (request.getParameter(str) == null) ? "" :request.getParameter(str);
	    result[num] = q;
	}
	
	String output = "[";
	int point = 0;
	int num = 0;
	
	Facade facade = Facade.getInstance();
	ArrayList<Quiz> quizes = facade.getQuiz("./WebContent/WEB-INF/DiseaseQuiz.xlsx","DiseaseQuiz");
	for(Quiz quiz:quizes){
	    
	    String kai ="";
	    for(int i=0;i<3;i++){
		String num1= String.valueOf(i+1);
		num1+=".0";
		if(num1.equals(quiz.getAnswer())) {
			kai = quiz.getChoices().get(i);
			break;
		    }
	    }
	    String myKai ;
            if(result[num].equals("a")) myKai="未解答";
            else myKai=quiz.getChoices().get(Integer.parseInt(result[num]));
	    
	    if(myKai.equals(kai)) point++;
	    output += "\""+myKai+"\",\""+kai+"\",";
	    num++;
	}
	String id = session.getAttribute(s_id).toString();
	ArrayList<String> strInt = new ArrayList<String>();
	strInt.add(String.valueOf(point) + "点");
	
	facade.registerLog(id,"クイズ",strInt);
	facade.close();
	output = output.substring(0,output.length() -1);
	output += "]";
	//output ="[\"脱臼\",\"脱臼\",\"女神\",\"乳母\",\"中耳炎\",\"虫垂炎\",\"環境に優しいデザイン\",\"誰にとっても使いやすいデザイン\",\"じんもう\",\"じんう\",\"脳炎\",\"脳炎\",\"コンピュータ断面撮影\",\"磁気共鳴画像\",\"多臓器不全\",\"多臓器不全\",\"脳卒中\",\"ガン\",\"脾臓\",\"脾臓\"]";//コメントアウト
	StringBuilder builder = new StringBuilder();

	builder.append(output);
	String json = builder.toString();
	System.out.println(json);
	response.setContentType("application/json");
	PrintWriter writer = response.getWriter();
	writer.append(json);
	writer.flush();
    }
	    private String getChoicesArryString(ArrayList<String> choices){
		String choiceString = "";
		for(String str:choices){
		    choiceString += "\""+str+"\",";
		}
		choiceString = choiceString.substring(0,choiceString.length() -1);
		return choiceString;
	    }
}
