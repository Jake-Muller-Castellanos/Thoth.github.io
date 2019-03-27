package thoth.src.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtractOperator {

	/**
	 * ファイルを[.xlsx]形式で出力する
	 * @param workbook 出力するファイル内容
	 * @param fileName 出力先のファイル名
	 * @throws IOException
	 */
	private void extract(Workbook workbook, String fileName, String outputStream) throws IOException {
	    FileOutputStream out = null;
	    try {
		// 出力先のファイルを指定
		out = new FileOutputStream(outputStream);
		// 上記で作成したブックを出力先に書き込み
		workbook.write(out);
	    } catch (FileNotFoundException e) {
		System.out.println(e.getStackTrace());
	    } finally {
		out.close();
		workbook.close();
	    }
	}
	
	/**
	 * UserMemento内の状態を書き出す
	 * @param users UserMemento内の状態
	 * @throws IOException
	 */
	public void extractUserMemento(ArrayList<User> users) throws IOException {
		Workbook workbook = new XSSFWorkbook(); //「.xlsx」形式のファイルの素を作成
		Sheet sheet = workbook.createSheet("UserMemento"); // シートを[UserMemento]という名前で作成
		Row row = null; // 行
		Cell cell = null; // セル
		
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell = row.createCell(1);
		cell.setCellValue("password");
				
		for(int i = 0; i < users.size(); i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(users.get(i).getId()); // 1列目にIDを登録
			cell = row.createCell(1);
			cell.setCellValue(users.get(i).getPassward()); // 2列目にパスワードを登録
		}
		
		extract(workbook, "./WebContent/WEB-INF/UserMemento.xlsx", "./WebContent/WEB-INF/UserMemento.xlsx");
	}
	
	/**
	 * LogMemento内の状態を書き出す
	 * @param logs LogMemento内の状態
	 * @throws IOException 
	 */
	public void extratLogMemento(ArrayList<Log> logs) throws IOException {
		Workbook workbook = new XSSFWorkbook(); //「.xlsx」形式のファイルの素を作成
		Sheet sheet = workbook.createSheet("LogMemento"); // シートを[LogMemento]という名前で作成
		Row row = null; // 行
		Cell cell = null; // セル
		
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell = row.createCell(1);
		cell.setCellValue("項目");
		cell = row.createCell(2);
		cell.setCellValue("日付");
		cell = row.createCell(3);
		cell.setCellValue("以降の列は結果");
		
		for(int i = 0; i < logs.size(); i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(logs.get(i).getUserId()); // 1行目にユーザIDを登録
			cell = row.createCell(1);
			cell.setCellValue(logs.get(i).getDid()); // 2列目に何をしたかを登録
			cell = row.createCell(2);
			cell.setCellValue(logs.get(i).getDate()); // 3列目に日付を登録
			for(int j = 0; j < logs.get(i).getResult().size(); j++) {
				cell = row.createCell(j + 3);
				cell.setCellValue(logs.get(i).getResult().get(j)); // 4列目以降に結果を登録
			}
		}
		
		extract(workbook, "./WebContent/WEB-INF/LogMemento.xlsx", "./WebContent/WEB-INF/LogMemento.xlsx");
	}
}
