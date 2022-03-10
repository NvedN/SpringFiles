package ru.nvn.spring.application;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import ru.nvn.spring.models.FormDataWithFile;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class FileParseUtil
{

		public static void parseFile(MultipartFile file) throws Exception
		{
				String type = file.getContentType();
				switch (Objects.requireNonNull(type))
				{
				case FormDataWithFile.TXT_TYPE:
						parseTxtFile(file);
						break;
				case FormDataWithFile.EXCEL_TYPE:
						parseExcelFile(file);
						break;
				}
		}

		private static void parseTxtFile(MultipartFile file) throws Exception
		{
				ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
				String myString = IOUtils.toString(stream, "UTF-8");
				System.out.println("-----------mysString = " + myString);
		}

		private static void parseExcelFile(MultipartFile file) throws Exception
		{
				//				HSSFWorkbook wb  = new HSSFWorkbook(file.getInputStream());
				DataFormatter df = new DataFormatter();
//				Workbook workbook = new HSSFWorkbook(file.getInputStream());
//				Workbook workbook = new XSSFWorkbook(file.getInputStream());
				InputStream inp = file.getInputStream();
				System.out.println("---------inp = " + inp);
				Workbook workbook = WorkbookFactory.create(inp);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next(); // skip header
				while (rowIterator.hasNext())
				{
						Row row = rowIterator.next();
						Cell assIdCell = row.getCell(0);
						Cell assDateCell = row.getCell(1);
						Cell reasonCell = row.getCell(2);
						Cell rateCell = row.getCell(3);
						// check cells values
						{
								String assId = df.formatCellValue(assIdCell);
								LocalDate assDate = assDateCell.getLocalDateTimeCellValue().toLocalDate();
								String reason = df.formatCellValue(reasonCell);
								String rate = df.formatCellValue(rateCell);
								System.out.println("------------assId = " + assId);
								System.out.println("------------assDate = " + assDate);
								System.out.println("------------reason = " + reason);
								System.out.println("------------rate = " + rate);
								//pack assignment Data
								HashMap<String, Object> assData = new HashMap<>(4);
								//								assData.put(ECG_Constants_mxJPO.ATTRIBUTE_OEBS_ID, assId.toUpperCase());
								//								assData.put(ECG_Constants_mxJPO.ATTRIBUTE_CP_DATEOFPAYMENT, assDate);
								//								assData.put(ECG_Constants_mxJPO.ATTRIBUTE_ABSENCE_REASON, reason);
								//								assData.put(ECG_Constants_mxJPO.ATTRIBUTE_WORKDAY_DURATION, rate);
								//								assignmentsData.add(assData);
						}
						//				String resultMessage = ECG_PnO_mxJPO
						//						.updateAssignments(ctx, assignmentsData);
						//				ContextUtil.commitTransaction(ctx);
						//				result.put(MESSAGE, resultMessage);
						//				result.put(STATUS, STATUS_COMPLETE);
				}
		}
}
