package thoth.src.model;

import java.util.ArrayList;

/**
 * クイズの工場
 * @author Kaguya
 * <Factory>
 */
public class QuizFactory {
	
	/**
	 * Quiz型のArrayListを作成し返す
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return quizList クイズの一覧
	 */
	public ArrayList<Quiz> createQuizList(String fileName,String... sheetNameList) {
			// TODO 自動生成されたメソッド・スタブ
			ReadMediator readMediator = ReadMediator.getInstance();
			return readMediator.readQuizList(fileName, sheetNameList);
	}
}
