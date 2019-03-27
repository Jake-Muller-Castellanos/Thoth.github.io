package thoth.src.model;

/**
 * ユーザを表すクラス
 * @author Kaguya
 *
 */
public class User {

	private String id; // ID
	private String passward; // パスワード
	
	/**
	 * コンストラクタ
	 * @param id ID
	 * @param passward パスワード
	 */
	public User(String id, String passward) {
		this.id = id;
		this.passward = passward;
	}
	
	/**
	 * パスワードを使い, ユーザ本人か確認する
	 * @param passward パスワード
	 * @return 本人ならtrue, 他人ならfalse
	 */
	public boolean check(String passward) {
		if(this.passward.equals(passward)) return true;
		return false;
	}
	
	/**
	 * idのゲッター
	 * @return id ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * passwardのゲッター
	 * @return passward パスワード
	 */
	public String getPassward() {
		return passward;
	}
}
