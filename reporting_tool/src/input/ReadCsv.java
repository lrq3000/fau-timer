package input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import reportingTool.ReportingTool;
import reportingTool.Secret;

/**
 * An Object of this class reads the csv files.
 * 
 * @author Isabell Schmitt
 */
public class ReadCsv extends Read {
	Logger logger = null;

	public ReadCsv(){
		logger = ReportingTool.getLogger();
	}
	@Override
	public ArrayList<Secret> readFile(String file) {
		ArrayList<Secret> secrets = new ArrayList<Secret>();
		FileReader fileReader;
		String line;
		String[] splitArray;
		long id = 0;
		long time = 0;

		try {
			fileReader = new FileReader(file);
			BufferedReader data = new BufferedReader(fileReader);
			int lineCounter = 1; 

			while ((line = data.readLine()) != null) {

				splitArray = line.split(";");
				boolean check = true;
				try{
					id = Long.parseLong(splitArray[0]);
				} catch(NumberFormatException nfe) {
					logger.warning("NumberFormatException in line " + lineCounter + ": " + nfe.getMessage());	
					System.exit(1);
				}
				try{
					time = Long.parseLong(splitArray[2]);
				} catch(NumberFormatException nfe) {
					logger.warning("NumberFormatException in line " + lineCounter + ": " + nfe.getMessage());	
					System.exit(1);
				}

				for(int i = 0; i < secrets.size(); i++){

					for(int j = 0; j < secrets.get(i).getCountTimes(); j++){
						if (secrets.get(i).getTimes().get(j).getId() == id){
							logger.warning("Duplicate id in line " + lineCounter + ".");
							System.exit(1);
						}
					}
					if (splitArray[1].equals(secrets.get(i).getName())){
						secrets.get(i).addTime(id, time);
						check = false;
					}

				}
				if(check){
					secrets.add(new Secret(splitArray[1]));
					secrets.get(secrets.size()-1).addTime(id, time);
				}
				lineCounter++;
			}

		} catch (FileNotFoundException e) {
			logger.warning("File " + file + " not found.");
			System.exit(1);
		} catch (IOException e) {
			logger.warning("E/A-Error");
			System.exit(1);
		}

		return secrets;
	}
}
