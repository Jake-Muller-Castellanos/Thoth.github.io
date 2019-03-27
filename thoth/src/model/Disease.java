package thoth.src.model;
/**
 * 病気を表すクラス
 * @author Kaguya
 *
 */
public class Disease {

	private String name; // 病名
	private boolean[] characteristic; // 症状
	
	/**
	 * コンストラクタ
	 * @param name 病名
	 * @param characteristic 症状
	 */
	public Disease(String name, boolean[] characteristic) {
		this.name = name;
		this.characteristic = characteristic;
	}
	
	/**
	 * nameのゲッター
	 * @return name 病名
	 */
	public String getName() { 
		return name;
	}
	
	/**
	 *  characteristicのゲッター
	 * @return characteristic 症状
	 */
	public boolean[] getCharacteristic() {
		return characteristic;
	}
}
