package input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import reportingTool.ReportingTool;
import reportingTool.Secret;

/**
 * This class reads the xls files.
 * 
 * @author  Isabell Schmitt
 */
public class ReadXls extends Read {
	Logger logger = null;

	public ReadXls(){
		logger = ReportingTool.getLogger();
	}

	@Override
	public ArrayList<Secret> readFile(String file) {
		ArrayList<Secret> secrets = new ArrayList<Secret>();
		HSSFWorkbook wb = null;

		// Open the xls
		try {
			wb = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.warning("File not found.");
			System.exit(1);
		} catch (IOException e) {
			logger.warning("E/A-Error");
			System.exit(1);
		}

		// Read row by row
		HSSFSheet sheet = wb.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		for (int r = 0; r < rows; r++) {

			HSSFRow row = sheet.getRow(r);

			if (row == null) {
				logger.warning("Skiping row " + r);
				continue;
			}
			String secret = row.getCell(1).getStringCellValue();

			long id = (long) row.getCell(0).getNumericCellValue();
			if(new Double(row.getCell(0).getNumericCellValue()).compareTo(new Long(id).doubleValue()) != 0) {
				logger.warning("NumberFormatException in row " + row);	
				System.exit(1);
			}

			long time = (long) row.getCell(2).getNumericCellValue();
			if(new Double(row.getCell(2).getNumericCellValue()).compareTo(new Long(time).doubleValue()) != 0) {
				logger.warning("NumberFormatException in row " + row);	
				System.exit(1);
			}

			boolean check = true;

			for(int i = 0; i < secrets.size(); i++){

				for(int j = 0; j < secrets.get(i).getCountTimes(); j++){
					if (secrets.get(i).getTimes().get(j).getId() == id){
						logger.warning("Duplicate id in row " + row + ".");
						System.exit(1);
					}
				}

				if (secret.equals(secrets.get(i).getName())){
					secrets.get(i).addTime(id, time);
					check = false;
				}

			}
			if(check){
				secrets.add(new Secret(secret));
				secrets.get(secrets.size()-1).addTime(id, time);
			}
		}

		return secrets;
	}


}
