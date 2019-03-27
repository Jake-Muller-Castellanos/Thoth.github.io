package thoth.src.model;
import java.util.ArrayList;

/**
 * 記録を表すクラス
 * @author Kaguya
 */
public class Log {

	private String userId; // ユーID
	private String did; // 何をしたか?
	private String date; // 日付 0000年00月00日00時00分00秒
	private ArrayList<String> result = null; // 診断結果
	
	/**
	 * コンストラクタ
	 */
	public Log(String userId, String did, String date, ArrayList<String> result) {
		this.userId = userId;
		this.did = did;
		this.date = date;
		this.result = result;
	}
	
	/**
	 * userIdのゲッター
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * didのゲッター
	 * @return did 何をしたか
	 */
	public String getDid() {
		return did;
	}
	
	/**
	 * dateのゲッター
	 * @return date 日付
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * resultのゲッター
	 * @return result 診断結果
	 */
	public ArrayList<String> getResult() {
		return result;
	}

}
