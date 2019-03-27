package thoth.src.model;
import java.util.ArrayList;

/**
 * 病気の一覧の工場
 * @author Kaguya
 *
 */
public class DiseaseListFactory {

	private final int MAX_CHARACTERISTIC = 20;
	
	/**
	 * 医療診断に必要な病気の一覧を返す
	 * @param fileName ファイル名
	 * @param sheetNameList シート名
	 * @return diseaseList 医療診断に必要な病気の一覧
	 */
	public ArrayList<Disease> createDiseaseList() {
		ReadMediator readMediator = ReadMediator.getInstance();
		return readMediator.readMedicalDiagnosis(MAX_CHARACTERISTIC, "./WebContent/WEB-INF/DiseaseList.xlsx", "DiseaseList");
	}
		
}
