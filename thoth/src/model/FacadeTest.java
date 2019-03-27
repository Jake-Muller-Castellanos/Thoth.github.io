package thoth.src.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Facadeのテストクラス
 * @author Kaguya
 *
 */
public class FacadeTest {

	public static void main(String[] arg) throws IOException {
		Facade facade = Facade.getInstance();
		
		facade.signUp("1610370040", "password"); // ユーザを登録
		facade.signUp("1610370045", "パスワード"); // ユーザを登録
		
		if(facade.login("1610370040", "パスワード")) // ログインを試みる. 失敗すればOK
			System.out.println("ログイン成功");
		else System.out.println("ログイン失敗");
		if(facade.login("1610370040", "password")) // ログインを試みる. 成功すればOK
			System.out.println("ログイン成功");
		else System.out.println("ログイン失敗");
		
		// 履歴を登録
		/**ArrayList<String> point = new ArrayList<String>();
		point.add("96点");
		facade.registerLog("1610370040", "クイズ", point);
		point = new ArrayList<String>();
		point.add("かぜ");
		facade.registerLog("1610370040", "診断", point);
		point = new ArrayList<String>();
		point.add("67点");
		facade.registerLog("1610370045", "クイズ", point);
		point = new ArrayList<String>();
		point.add("73点");
		facade.registerLog("1610370045", "クイズ", point);
		point = new ArrayList<String>();
		point.add("かぜ");
		point.add("インフルエンザ");
		facade.registerLog("1610370045", "診断", point);
		**/
		
		// クイズを取得し, 出力する
		ArrayList<Quiz> quizes = facade.getQuiz("DiseaseQuiz.xlsx", "DiseaseQuiz");
		for(Quiz quiz : quizes) {
			System.out.println(quiz.getQuestion());
			for(String choice : quiz.getChoices()) System.out.print(choice);
			System.out.println("\n" + quiz.getAnswer());
		}
		
		// 履歴を各ユーザごとに出力
		ArrayList<Log> logs = facade.getLogs("1610370040");
		System.out.println("1610370040");
		for(Log log : logs) System.out.println(log.getDid());
		logs = facade.getLogs("1610370045");
		System.out.println("1610370045");
		for(Log log : logs) System.out.println(log.getDid());
		
		// 病名の一覧を取得し出力
		ArrayList<String> diseaseNameList = facade.getDiseaseNameList();
		for(String diseaseName : diseaseNameList) System.out.println(diseaseName);
		
		// Facadeを閉じる. その際に状態を書き出す
		facade.close();
	}
}
