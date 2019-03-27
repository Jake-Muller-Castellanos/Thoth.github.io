package thoth.src.model;
import java.util.ArrayList;

/**
 * ユーザ情報を保存しておくクラス
 * @author Kaguya
 * <Memento>
 */
public class UserMemento {

	private ArrayList<User> users; // ユーザ
	
	/**
	 * コンストラクタ
	 */
	public UserMemento() {
		ReadMediator readMediator = ReadMediator.getInstance();
		users = readMediator.readUserMemento("./WebContent/WEB-INF/UserMemento.xlsx", "UserMemento");
	}
	
	/**
	 * ユーザを登録する
	 * @param id ユーザID
	 * @param passward パスワード
	 * @return 成功すればtrue, 失敗すればfalse
	 */
	public boolean addUser(String id, String passward) {
		if(existUser(id)) return false;
		users.add(new User(id, passward));
		return true;
	}
	
	/**
	 * 指定されたidのユーザを返す
	 * @param id ID
	 * @return user ユーザ
	 */
	public User getUser(String id) {
		for(User user : users)
			if(user.getId().equals(id)) return user;
		return null;
	}
	
	/**
	 * 指定されたIDのユーザが存在するか確認する
	 * @param id ユーザID
	 * @return 存在すればtrue, しなければfalse
	 */
	private boolean existUser(String id) {
		for(User user : users)
			if(user.getId().equals(id)) return true;
		return false;
	}
	
	/**
	 * usersのゲッター
	 * @return user ユーザ一覧
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
}
