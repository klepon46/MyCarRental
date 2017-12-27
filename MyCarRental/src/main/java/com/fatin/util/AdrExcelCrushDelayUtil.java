/**
 * 
 */
package com.fatin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author garya
 * Jun 22, 2016
 */
public class AdrExcelCrushDelayUtil {

	private HSSFCellStyle titleStyle;
	private HSSFCellStyle totalStyle;
	private HSSFCellStyle cellStyle;
	private HSSFCellStyle bgStyle;
	private HSSFCellStyle bgOwnStyle;
	private HSSFCellStyle verticalStyle;
	private HSSFCellStyle plainStyle;
	private HSSFCellStyle headerStyle;
	private HSSFCellStyle subTotalStyle;
	private HSSFCellStyle grandTotalStyle;
	private HSSFCellStyle bargeSubStyle;
	private HSSFCellStyle decimalStyle;
	private HSSFFont font;
	private HSSFFont titleFont ;
	private LocalDate localDate;
	
	private List<String> headers;
	private List<Object[]> problemContents;

	public AdrExcelCrushDelayUtil() {
		// TODO Auto-generated constructor stub
	}

	public HSSFWorkbook createExcelDelayCrush(){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();


		int rowIndex = 0;
		int colIndex = 0;

		HSSFRow row = createOrGetRow(sheet, rowIndex);
		HSSFCell cell = row.createCell(0);

		headers.add(0, "Shift");
		headers.add(1, "Tanggal");

		//mengisi text headers
		for(String str : headers){
			cell = (HSSFCell) createSelf(row, sheet, rowIndex, colIndex, "headerStyle", wb, 0, 0);
			cell.setCellValue(str);
			sheet.autoSizeColumn(colIndex,true);
			colIndex++;
		}

		//isi shift dan tanggal di bagian sebelah kiri
		int x = 1;
		for(String str : getAllDaysInMonth()){
			colIndex = 0;
			row = createOrGetRow(sheet, x);
			cell = row.createCell(colIndex++);
			cell.setCellValue(1);

			cell = row.createCell(colIndex);
			cell.setCellValue(str);

			x++;

			colIndex = 0;
			row = createOrGetRow(sheet, x);
			cell = row.createCell(colIndex++);
			cell.setCellValue(2);

			cell = row.createCell(colIndex);
			cell.setCellValue(str);

			x++;
		}
		
		


		rowIndex = 1;
		colIndex = 2;

		//mengisi nilai untuk lost time masing-masing problem
		row = createOrGetRow(sheet, 1);

		//iterasi tanngal dan shift
		for(int i = row.getRowNum() ; i <= sheet.getLastRowNum(); i++){

			row = createOrGetRow(sheet, i);
			int shift = (int) row.getCell(0).getNumericCellValue();
			String tgl = row.getCell(1).getStringCellValue();
			
			//setiap shift dan tanggal lakukan iterasi hasil query 
			for(Object[] obj : problemContents){
				row = createOrGetRow(sheet, i);
				colIndex = 2;

				String tglFromDb = (String) obj[0];
				int shiftFromDb = (Integer) obj[1];
				
				//jika shift dan tanggal sama dengan hasil query dari database maka
				if(shift == shiftFromDb && tgl.equalsIgnoreCase(tglFromDb)){

					//cetak ke dalam excel berdasarkan problemnya masing masing
					for(int z = 2 ; z < obj.length ; z++){
						cell = row.createCell(colIndex++);
						Integer val = (Integer) obj[z];

						if(val != null){
							cell.setCellValue(val);
						}
					}
				}
			}
		}


		return wb;
	}


	private HSSFRow createOrGetRow(HSSFSheet sheet, int rowIndex){

		HSSFRow row = sheet.getRow(rowIndex);
		if(row == null){
			row = sheet.createRow(rowIndex);
		}

		return row;
	}

	private Cell createSelf(Row row, HSSFSheet sheet, int rowIndex, int colIndex,
			String cellStyle, HSSFWorkbook wb, int addCol, int addRow){

		Cell c = row.createCell(colIndex);
		c.setCellStyle(getCellStyle(cellStyle, wb));
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+addRow,colIndex,colIndex+addCol));
		mergedCellSetBorder2(c, sheet, 
				new CellRangeAddress(rowIndex, rowIndex+addRow,colIndex,colIndex+addCol), getCellStyle(cellStyle, wb));

		return  c;
	}

	private List<String> getAllDaysInMonth(){

		DateTimeFormatter DF= DateTimeFormat.forPattern("dd/MM/yyyy");
//		LocalDate month = new LocalDate();
		LocalDate month = localDate;
		List<String> daysInMonthLabels = new ArrayList<String>();
		LocalDate firstDay = month.withDayOfMonth(1);
		LocalDate nextMonthFirstDay = firstDay.plusMonths(1);
		while (firstDay.isBefore(nextMonthFirstDay)) {
			daysInMonthLabels.add(DF.print(firstDay));
			firstDay = firstDay.plusDays(1);
		}

		return daysInMonthLabels;
	}

	private void mergedCellSetBorder2(Cell c, HSSFSheet sheet, CellRangeAddress range, HSSFCellStyle style){

		int fr =  range.getFirstRow();
		int lr = range.getLastRow();
		int fc = range.getFirstColumn();
		int lc = range.getLastColumn();

		for(int i = fr ; i <= lr; i++){
			HSSFRow row = sheet.getRow(i);
			if(row == null){
				row = sheet.createRow(i);
			}

			for(int y = fc; y <= lc;y++){
				HSSFCell cel = row.getCell(y);
				if(cel == null){
					cel = row.createCell(y);
				}

				cel.setCellStyle(style);
			}
		}
	}

	private HSSFCellStyle getCellStyle(String styleName, HSSFWorkbook wb){

		Map<String, HSSFCellStyle> map = new HashMap<String, HSSFCellStyle>();

		if(titleStyle == null){
			titleStyle = wb.createCellStyle();
		}

		if(totalStyle == null){
			totalStyle = wb.createCellStyle();
		}

		if(cellStyle == null){
			cellStyle = wb.createCellStyle();
		}

		if(bgStyle == null){
			bgStyle = wb.createCellStyle();
		}

		if(bgOwnStyle == null){
			bgOwnStyle = wb.createCellStyle();
		}

		if(verticalStyle == null){
			verticalStyle = wb.createCellStyle();
		}

		if(font == null){
			font = wb.createFont();
		}

		if(titleFont == null){
			titleFont = wb.createFont();
		}

		if(plainStyle == null){
			plainStyle = wb.createCellStyle();
		}

		if(headerStyle == null){
			headerStyle = wb.createCellStyle();
		}

		if(subTotalStyle == null){
			subTotalStyle = wb.createCellStyle();
		}

		if(grandTotalStyle == null){
			grandTotalStyle = wb.createCellStyle();
		}

		if(bargeSubStyle == null){
			bargeSubStyle = wb.createCellStyle();
		}

		if(decimalStyle == null){
			decimalStyle = wb.createCellStyle();
		}

		verticalStyle.setRotation((short)90);
		verticalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		verticalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		verticalStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		verticalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		verticalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		verticalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		verticalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		verticalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		verticalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		verticalStyle.setFont(font);
		map.put("verticalStyle", verticalStyle);

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)10);

		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontHeightInPoints((short)16);

		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setFont(titleFont);
		map.put("titleStyle", titleStyle);

		totalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		totalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		totalStyle.setFont(titleFont);
		totalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		totalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		totalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		totalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		map.put("totalStyle", totalStyle);

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		map.put("cellStyle", cellStyle);

		bgStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		bgStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bgStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		bgStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bgStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bgStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bgStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		bgStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bgStyle.setFont(font);
		map.put("bgStyle", bgStyle);

		bgOwnStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		bgOwnStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		map.put("bgOwnStyle", bgOwnStyle);

		plainStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		plainStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		map.put("plainStyle", plainStyle);

		headerStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerStyle.setFont(font);
		map.put("headerStyle", headerStyle);


		subTotalStyle.setFillForegroundColor(HSSFColor.AQUA.index);
		subTotalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		subTotalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		subTotalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		subTotalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		subTotalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		subTotalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		subTotalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		subTotalStyle.setFont(font);
		map.put("subTotalStyle", subTotalStyle);

		grandTotalStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		grandTotalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		grandTotalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		grandTotalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		grandTotalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		grandTotalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		grandTotalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		grandTotalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		grandTotalStyle.setFont(font);
		map.put("grandTotalStyle", grandTotalStyle);

		bargeSubStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		bargeSubStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		bargeSubStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		bargeSubStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		bargeSubStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bargeSubStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bargeSubStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		bargeSubStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bargeSubStyle.setFont(font);
		map.put("bargeSubStyle", bargeSubStyle);

		decimalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		decimalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		decimalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		decimalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		decimalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		decimalStyle.setDataFormat(wb.createDataFormat().getFormat("0.000"));
		map.put("decimalStyle", decimalStyle);

		return map.get(styleName); 
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<Object[]> getProblemContents() {
		return problemContents;
	}

	public void setProblemContents(List<Object[]> problemContents) {
		this.problemContents = problemContents;
	}

	public HSSFCellStyle getTitleStyle() {
		return titleStyle;
	}

	public void setTitleStyle(HSSFCellStyle titleStyle) {
		this.titleStyle = titleStyle;
	}

	public HSSFCellStyle getTotalStyle() {
		return totalStyle;
	}

	public void setTotalStyle(HSSFCellStyle totalStyle) {
		this.totalStyle = totalStyle;
	}

	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public HSSFCellStyle getBgStyle() {
		return bgStyle;
	}

	public void setBgStyle(HSSFCellStyle bgStyle) {
		this.bgStyle = bgStyle;
	}

	public HSSFCellStyle getBgOwnStyle() {
		return bgOwnStyle;
	}

	public void setBgOwnStyle(HSSFCellStyle bgOwnStyle) {
		this.bgOwnStyle = bgOwnStyle;
	}

	public HSSFCellStyle getVerticalStyle() {
		return verticalStyle;
	}

	public void setVerticalStyle(HSSFCellStyle verticalStyle) {
		this.verticalStyle = verticalStyle;
	}

	public HSSFCellStyle getPlainStyle() {
		return plainStyle;
	}

	public void setPlainStyle(HSSFCellStyle plainStyle) {
		this.plainStyle = plainStyle;
	}

	public HSSFCellStyle getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(HSSFCellStyle headerStyle) {
		this.headerStyle = headerStyle;
	}

	public HSSFCellStyle getSubTotalStyle() {
		return subTotalStyle;
	}

	public void setSubTotalStyle(HSSFCellStyle subTotalStyle) {
		this.subTotalStyle = subTotalStyle;
	}

	public HSSFCellStyle getGrandTotalStyle() {
		return grandTotalStyle;
	}

	public void setGrandTotalStyle(HSSFCellStyle grandTotalStyle) {
		this.grandTotalStyle = grandTotalStyle;
	}

	public HSSFCellStyle getBargeSubStyle() {
		return bargeSubStyle;
	}

	public void setBargeSubStyle(HSSFCellStyle bargeSubStyle) {
		this.bargeSubStyle = bargeSubStyle;
	}

	public HSSFCellStyle getDecimalStyle() {
		return decimalStyle;
	}

	public void setDecimalStyle(HSSFCellStyle decimalStyle) {
		this.decimalStyle = decimalStyle;
	}

	public HSSFFont getFont() {
		return font;
	}

	public void setFont(HSSFFont font) {
		this.font = font;
	}

	public HSSFFont getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(HSSFFont titleFont) {
		this.titleFont = titleFont;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	
}
