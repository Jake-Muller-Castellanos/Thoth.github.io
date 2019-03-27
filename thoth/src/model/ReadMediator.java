package thoth.src.model;

import java.util.ArrayList;

public class ReadMediator {

	private static ReadMediator instance; 
	private ReadOperator readOperator; // ExtractOperaterの参照型変数
	
	/**
	 * Singletonメソッド
	 * @return instance this
	 */
	public static ReadMediator getInstance() {
		if(instance == null) instance = new ReadMediator();
		return instance;
	}
	
	/**
	 * 医療診断に必要な病気の一覧を返す
	 * @param MAX_CHARACTERISTIC 症状数
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return diseaseList 医療診断に必要な病気の一覧
	 */
	public ArrayList<Disease> readMedicalDiagnosis(int MAX_CHARACTERISTIC, String fileName, String ... sheetNameList) {
		readOperator = new ReadOperator(fileName, sheetNameList);
		ArrayList<Disease> diseaseList = new ArrayList<Disease>();
		boolean[] characteristic;
		ArrayList<ArrayList<ArrayList<String>>> workBookList = readOperator.getWorkBookOfArrayList();
		for(ArrayList<ArrayList<String>> sheet : workBookList)
			for(ArrayList<String> cellList : sheet) {
				characteristic = new boolean[MAX_CHARACTERISTIC];
				for(int i = 0;  i < MAX_CHARACTERISTIC; i++) {
					if(cellList.get(i + 1).equals("T")) characteristic[i] = true;
					else characteristic[i] = false;
				}
				diseaseList.add(new Disease(cellList.get(0), characteristic));
			}
		return diseaseList;
	}
	
	/**
	 * Quiz型のArrayListを作成し返す
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return quizList クイズの一覧
	 */
	public ArrayList<Quiz> readQuizList(String fileName,String... sheetNameList) {
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		ArrayList<String> choices;
		readOperator = new ReadOperator(fileName, sheetNameList);
		ArrayList<ArrayList<ArrayList<String>>> workBookList = readOperator.getWorkBookOfArrayList();
		for(ArrayList<ArrayList<String>> sheet : workBookList)
			for(ArrayList<String> cellList : sheet) {
				choices = new ArrayList<String>();
				for(int i = 2; i < cellList.size(); i++) choices.add(cellList.get(i));
				quizList.add(new Quiz(cellList.get(0), cellList.get(1), choices));
			}
		return quizList;
	}
	
	/**
	 * UserMemento内の状態をファイルから読み込む
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return users UserMemento内の状態
	 */
	public ArrayList<User> readUserMemento(String fileName,String... sheetNameList) {
		ArrayList<User> users = new ArrayList<User>();
		readOperator = new ReadOperator(fileName, sheetNameList);
		ArrayList<ArrayList<ArrayList<String>>> workBookList = readOperator.getWorkBookOfArrayList();
		for(ArrayList<ArrayList<String>> sheet : workBookList) {
			if(sheet.size() == 0) return users;
			for(ArrayList<String> cellList : sheet) users.add(new User(cellList.get(0), cellList.get(1)));
		}
		return users;
	}
	
	/**
	 * LogMemento内の状態をファイルから読み込む
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return logs LogMemento内の状態
	 */
	public ArrayList<Log> readLogMemento(String fileName,String... sheetNameList) {
		ArrayList<Log> logs = new ArrayList<Log>();
		ArrayList<String> result;
		readOperator = new ReadOperator(fileName, sheetNameList);
		ArrayList<ArrayList<ArrayList<String>>> workBookList = readOperator.getWorkBookOfArrayList();
		for(ArrayList<ArrayList<String>> sheet : workBookList) {
			if(sheet.size() == 0) return logs;
			for(ArrayList<String> cellList : sheet) {
				result = new ArrayList<String>();
				for(int i = 3; i < cellList.size(); i++) result.add(cellList.get(i));
				logs.add(new Log(cellList.get(0), cellList.get(1), cellList.get(2), result));
			}
		}
		return logs;
	}
}
