package thoth.src.model;
import java.util.ArrayList;

/**
 * 結果を保管しておくクラス
 * @author Kaguya
 * <Memento>
 */
public class LogMemento {

	private ArrayList<Log> logs; // 結果の履歴
	
	/**
	 * コンストラクタ
	 */
	public LogMemento() {
		ReadMediator readMediator = ReadMediator.getInstance();
		logs = readMediator.readLogMemento("./WebContent/WEB-INF/LogMemento.xlsx", "LogMemento");
	}
	
	/**
	 * 結果を登録する
	 * @param diagnosis 結果
	 */
	public void addLog(Log log) {
		logs.add(log);
	}
	
	/**
	 * logsのゲッター
	 * @return log 結果の履歴
	 */
	public ArrayList<Log> getLogs() {
		return logs;
	}
	
	/**
	 * ユーザIDと一致するユーザの履歴を返す
	 * @param userId ユーザID
	 * @return ユーザの履歴
	 */
	public ArrayList<Log> getLogs(String userId) {
		ArrayList<Log> temporaryLogs = new ArrayList<Log>();
		for(Log log : logs) if(log.getUserId().equals(userId))
			temporaryLogs.add(log);
		return temporaryLogs;
	}
}
