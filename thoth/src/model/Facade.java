package thoth.src.model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Facadeクラス
 * @author Kaguya
 *
 */
public class Facade {

	private static Facade instance; // Facade型のインスタンス
	private UserMemento userMemento; // UserMemento型の参照型変数
	private LogMemento logMemento; // logMementoの参照型変数
	private DiseaseListFactory diseaseListFactory; // DiseaselistFactoryの参照型変数
	private QuizFactory quizFactory; // QuizFactory型の参照型変数
	private ArrayList<Disease> diseaseList; //病気一覧
	private ExtractOperator extractOperator; // ExtractOperaterの参照型変数
	
	/**
	 * コンストラクタ
	 */
	private Facade() {
		userMemento = new UserMemento();
		logMemento = new LogMemento();
		quizFactory = new QuizFactory();
		diseaseListFactory = new DiseaseListFactory();
		diseaseList = diseaseListFactory.createDiseaseList();
		extractOperator = new ExtractOperator();
	}
	
	/**
	 * Singletonメソッド
	 * @return instance this
	 */
	public static Facade getInstance() {
		if(instance == null) instance = new Facade();
		return instance;
	}
	
	/**
	 * 引数のユーザIDとパスワードを登録する
	 * @param id ユーザID
	 * @param passward パスワード
	 * @return 登録に成功すればtrue, 失敗すればfalse
	 */
	public boolean signUp(String id, String passward) {
		if(userMemento.addUser(id, passward)) return true; // 登録成功
		return false; // 登録失敗
	}
	
	/**
	 * 引数のユーザIDとパスワードでログインする
	 * @param id ユーザID
	 * @param passward パスワード
	 * @return ログインに成功すればtrue, 失敗すればfalse
	 */
	public boolean login(String id, String passward) {
		if(userMemento.getUser(id) == null) return false; // ログイン失敗
		else if(userMemento.getUser(id).check(passward)) return true; // ログイン成功
		return false; // ログイン失敗
	}
	
	/**
	 * diseaseListのゲッター
	 * @return diseaseList 病気の一覧
	 */
	public ArrayList<Disease> getDiseaseList() {
		return diseaseList;
	}
	
	/**
	 * 結果を保存する
	 */
	public void registerLog(String userId, String did, ArrayList<String> result) {
		String date = getDate(); // 日付 0000年00月00日00時00分00秒
		logMemento.addLog(new Log(userId, did, date, result));
	}
 
	/**
	 * 病気に関するクイズを返す
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return クイズの一覧
	 * 引数は("DiseaseQuiz.xlsx", "DiseaseQuiz")のみ
	 */
	public ArrayList<Quiz> getQuiz(String fileName,String... sheetNameList) {
		return quizFactory.createQuizList(fileName, sheetNameList);
	}
	
	/**
	 * 日付の一覧を返す
	 * @return　dates 診断日の一覧
	 */
	/*public ArrayList<int[]> getDates() {
		return logMemento.getDates();
	}*/
	
	/**
	 * 指定された日付の診断結果を返す
	 * @param date 日付
	 * @return 診断結果
	 */
	/*public Log selectLog(int[] date) {
		return logMemento.getLog(date);
	}*/
	
	/**
	 * Log型のリストを返す
	 * @return Log型のリスト
	 */
	public ArrayList<Log> getLogs() {
		return logMemento.getLogs();
	}
	
	/**
	 * ユーザIDと一致するユーザの履歴を返す
	 * @param userId ユーザID
	 * @return ユーザの履歴
	 */
	public ArrayList<Log> getLogs(String userId) {
		return logMemento.getLogs(userId);
	}
	
	/**
	 * 病名の一覧を返す
	 * @return names 病名の一覧
	 */
	public ArrayList<String> getDiseaseNameList() {
		ArrayList<String> names = new ArrayList<String>();
		for(Disease disease : diseaseList) names.add(disease.getName());
		return names;	
	}
	
	/**
	 * 日付を取得する
	 * @return date 日付 0000年00月00日00時00分00秒
	 */
	private String getDate() {
		Calendar cal = Calendar.getInstance();
		String date;
		int i;
		date = Integer.toString(cal.get(Calendar.YEAR));
		i = cal.get(Calendar.MONTH) + 1;
		if(i < 10) date += "0";
		date += Integer.toString(i);
		i = cal.get(Calendar.DATE);
		if(i < 10) date += "0";
		date += Integer.toString(i);
		i = cal.get(Calendar.HOUR);
		if(i < 10) date += "0";
		date += Integer.toString(i);
		i = cal.get(Calendar.MINUTE);
		if(i < 10) date += "0";
		date += Integer.toString(i);
		i = cal.get(Calendar.SECOND);
		if(i < 10) date += "0";
		date += Integer.toString(i);
		return date;
	}
	
	/**
	 * 終了時に呼び出すこと
	 * @throws IOException
	 */
	public void close() throws IOException {
		extractOperator.extractUserMemento(userMemento.getUsers());
		extractOperator.extratLogMemento(logMemento.getLogs());
	}
}
