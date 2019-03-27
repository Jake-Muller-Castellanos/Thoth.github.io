package thoth.src.model;

import java.util.ArrayList;

/**
 * 問題と解答をセットにしたクラス
 * @author Kaguya
 *
 */
public class Quiz {

	private String question; // 問題
	private String answer; // 解答
	private ArrayList<String> choices; // 選択肢
	
	/**
	 * コンストラクタ
	 * @param question 問題
	 * @param answer 解答
	 */
	public Quiz(String question, String answer, ArrayList<String> choices) {
		this.question = question;
		this.answer = answer;
		this.choices = choices;
	}
	
	/**
	 * questionのゲッター
	 * @return question 問題
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * answerのゲッター
	 * @return answer 解答
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * choicesのゲッター
	 * @return choices 選択肢
	 */
	public ArrayList<String> getChoices() {
		return choices;
	}
}
