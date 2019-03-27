package thoth.src.model;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

/**
 * 引数で渡されたExcelファイルを読み込み、
 * ArrayList化するクラス
 * @author sumiryoutarou
 *
 */
public class ReadOperator {
	private Workbook currentWorkBook; //現在作業中のxlsxファイルブック
	private ArrayList<Sheet> workSheetList;	//currentWorkBookが持つWorkSheetのリスト
	private ArrayList<String> sheetNameList; //取得したいWorkSheetの名称のリスト
	private ArrayList<ArrayList<ArrayList<String>>> workBookList = new ArrayList<ArrayList<ArrayList<String>>>(); // ワークブックのリスト
	
	/**
	 * 引数で渡されたxlsxファイル名のインスタンスを生成し,
	 * currentWorkBookに参照させる
	 * また、currentWorkBookからデータの抽出作業を開始する
	 * 
	 * @param fileName スキャンさせるデータ(.xlsx)
	 */
	public ReadOperator(String fileName,String ... sheetNameList){
		try {
			InputStream	inputStream = new FileInputStream(fileName);
			currentWorkBook = WorkbookFactory.create(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sheetNameList = new ArrayList<String>(Arrays.asList(sheetNameList));
		workSheetList = new ArrayList<Sheet>();
		startExtract();
	}
	
	/**
	 * xlsxファイルの読み込みを開始する
	 * 
	 * @param args
	 */
	private void startExtract(){
		setWorkSheetList(); //WorkSheetListをセット
		makeWorkBook();	//WorkBookを作成
		closeWorkBook(); //作業中のWorkBookを閉じる
	}
	
	/**
	 * 現在作業中のxlsxファイルブックにあるWorkSheetを
	 * フィールドworkSheetListが保持する
	 */
	private void setWorkSheetList(){
		for(String sheetName : sheetNameList)
			workSheetList.add(currentWorkBook.getSheet(sheetName));
	}
	
	/**
	 * 引数の行番号, データを取得する
	 * 
	 * @param currentSheet このシートに記入されているデータを取り出す
	 * @param rowNumber 取り出すセルの行番号
	 * @param colomnNumber 取り出すセルの列番号
	 */
	@SuppressWarnings("deprecation")
	private String getCellValue(Sheet currentSheet,int rowNumber,int colomnNumber){
		try{
			String temporaryString = currentSheet.getRow(rowNumber).getCell(colomnNumber).getStringCellValue();
			return temporaryString;
		}catch(IllegalStateException o){
			//CellのDateインタフェースを利用して時刻を取得
			if(DateUtil.isCellDateFormatted(currentSheet.getRow(rowNumber).getCell(colomnNumber))){
				if(currentSheet.getRow(rowNumber).getCell(colomnNumber).getDateCellValue().getMinutes() < 10)
				return Integer.toString(currentSheet.getRow(rowNumber).getCell(colomnNumber).getDateCellValue().getHours()) 
							+ ":0" + Integer.toString(currentSheet.getRow(rowNumber).getCell(colomnNumber).getDateCellValue().getMinutes());
				else 
					return Integer.toString(currentSheet.getRow(rowNumber).getCell(colomnNumber).getDateCellValue().getHours()) 
							+ ":" + Integer.toString(currentSheet.getRow(rowNumber).getCell(colomnNumber).getDateCellValue().getMinutes());
			}
			else{
				double temporaryDouble = currentSheet.getRow(rowNumber).getCell(colomnNumber).getNumericCellValue();
				return Double.toString(temporaryDouble);
			}	
		}
		catch(NullPointerException e){
			return "";
		}
	}
	
	/**
	 * workBookListのゲッター
	 * @return workBookOfArrayList ワークブックのリスト
	 */
	public ArrayList<ArrayList<ArrayList<String>>> getWorkBookOfArrayList(){
		return workBookList;
	}
	
	/**
	 * 実行時に引数で渡したシート名の数のワークシートをワークブックとして取得する
	 * @return　workBookList ワークブックのリスト
	 */
	private ArrayList<ArrayList<ArrayList<String>>> makeWorkBook(){
		for(int i = 0; i < sheetNameList.size(); i++) workBookList.add(makeWorkSheet(i));
		return workBookList;
	}
	
	/**
	 * 取得するシート番号を引数に渡すと,
	 * getRowListを利用して, 指定したシートのセルデータ全てをArrayListで返す
	 * @param sheetNameNumber
	 * @return　workSheet ワークシート
	 */
	private ArrayList<ArrayList<String>> makeWorkSheet(int sheetNameNumber){
		ArrayList<ArrayList<String>> temporaryWorkSheet = new ArrayList<ArrayList<String>>(); //一時的なワークシート
		System.out.print("シート" + sheetNameNumber + "\n");
		for(int i = 1; i < currentWorkBook.getSheet(sheetNameList.get(sheetNameNumber)).getLastRowNum() + 1; i++)
			temporaryWorkSheet.add(makeRowList(sheetNameNumber, i));
		return temporaryWorkSheet;
	}
	
	/**
	 * 取得するシート番号とそのシート行番号を引数に渡すと,
	 * その行のデータ全てを取得する
	 * 
	 * @param sheetNameNumber　取得するシート番号
	 * @param row 取得するそのシートの行番号
	 * @return rowList 行データ
	 */
	private ArrayList<String> makeRowList(int sheetNameNumber,int row){
		int lastCellNum = currentWorkBook.getSheet(sheetNameList.get(sheetNameNumber)).getRow(row).getLastCellNum(); //項目数を記憶
		ArrayList<String> temporaryRowList = new ArrayList<String>(); //一時的な一行文のデータ
		for(int i = 0 ; i < lastCellNum ; i++){
			temporaryRowList.add(getCellValue(currentWorkBook.getSheet(sheetNameList.get(sheetNameNumber)), row, i));
			System.out.println("セル:[" + getCellValue(currentWorkBook.getSheet(sheetNameList.get(sheetNameNumber)), row, i) + "]...読込完了");
		}
		return temporaryRowList;
	}
	
	/**
	 * 現在選択されているデータの全WorkSheetの全データを表示する
	 */
	public void showData(){
		for(int i = 0; i < workBookList.size(); i++){
			System.out.println("シート番号:" + i);
			for(ArrayList<String> sheet : workBookList.get(i)) {
				for(String cell : sheet) System.out.print("[" + cell + "]");
				System.out.println();
			}
			System.out.println();
		}
	}
	
	/**
	 * WorkBookを閉じる
	 * ついでに、SheetListもリセットする
	 * ※WorkBookを使わないなら必ず実行して閉じて下さい※
	 */
	private void closeWorkBook(){
		try {
			currentWorkBook.close();
			workSheetList.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ReadOperator eo = new ReadOperator("studentData.xlsx","中1");
		eo.getWorkBookOfArrayList();
		System.out.println("-");
		eo.showData();
		System.out.println("-");
		System.out.println();
		/*switch(eo.getWorkBookOfArrayList().get(0).get(0).get(8)) {
		case "":
			System.out.println("aaaa");
			break;
		case " ":
			System.out.println("bbb");
			break;
		default:
			if(eo.getWorkBookOfArrayList().get(0).get(0).get(8).equals(null))
				System.out.println("null");
			break;
		}*/
		
		//for(int i=0;i<temp.get(0).get(0).size();i++){
			//System.out.println(temp.get(0).get(1).get(0) + "のデータ:" + temp.get(0).get(1));
			//System.out.println(temp.get(0).get(1).get(0) + "の電話番号は:" + temp.get(0).get(1).get(2));
		//}
	}

}
