package output;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import reportingTool.Measurement;
import reportingTool.Secret;

/**
 * This class creates the HTML file of the measurement.
 * 
 * @author Isabell Schmitt
 */
public class WriteHTML extends Write {

	public void writeFile() throws IOException{

		String sep = File.separator;
		File input = new File("templates" + sep + "html" + sep + "index.html");
		File output = new File(Measurement.getInstance().getOutputFile() + sep + "index_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html");


		HashMap<String, String> replacer = new HashMap<String, String>();

		ArrayList<Secret> secrets = Measurement.getInstance().getSecrets();	
		StringBuilder table = new StringBuilder();
		for(int i = 0; i < secrets.size(); i++){
			table.append("<tr><td>" + secrets.get(i).getName()+ 
					"</td><td>" + secrets.get(i).getCountTimes() +
					"</td><td>" + secrets.get(i).getMin() + 
					"</td><td>" + secrets.get(i).getMax() + 
					"</td><td>" + secrets.get(i).getMedian() + 
					"</td><td>" + secrets.get(i).getArithmeticMean());
			table.append("\n");
		}
		replacer.put("contentTable", table.toString());

		replace(input, output, replacer);
	}
}
