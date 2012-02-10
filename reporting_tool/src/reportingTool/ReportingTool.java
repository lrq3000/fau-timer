package reportingTool;

import input.ReadCsv;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerException;

import output.Write;
import output.WriteHTML;
import output.WritePDF;
import output.WriteXML;

/**
 * This class contains the main method.
 * 
 * @author Isabell Schmitt
 */
public class ReportingTool {

	static Logger logger = Logger.getLogger("Seecurity ReportingTool");

	public static String getGnuplotExecutable() {
		return Conf.get("gnuplot");
	}

	public static Logger getLogger() {
		return logger;
	}

	/**
	 * This method checks if the dir already exists. 
	 * When it doesn't exist, this method creates a new folder.
	 * 
	 * @param dir String of folder to be checked
	 */
	public static void checkDir(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			logger.info("\nFolder \"" + dir + "\" already exists.\n");
		} else {
			boolean success = file.mkdir();

			if (success) {
				logger.info("\nFolder \"" + dir + "\" was created successfully.\n");
			} else {
				logger.warning("Error: Folder " + dir + " wasn't created successfully.");
				System.exit(1);
			}
		}
	}

	/**
	 * This method checks whether all the folders available.
	 */
	public static void prepareReportingTool(String report) {
		String sep = File.separator;

		// this folder is necessary to save latex information
		checkDir("reporting_tool_tmp" + sep);

		// all the necessary folders to store the gnuplot data
		checkDir("reporting_tool_tmp" + sep + "graphData" + sep);
		checkDir("reporting_tool_tmp" + sep + "graphData" + sep + "histogramGraphData" + sep);
		checkDir("reporting_tool_tmp" + sep + "graphData" + sep + "scatterplotGraphData" + sep);
		checkDir("reporting_tool_tmp" + sep + "graphData" + sep + "cdfGraphData" + sep);
		checkDir("reporting_tool_tmp" + sep + "graphData" + sep + "whiskerDiagramGraphData" + sep);

		// all necessary folder to save the output of the reporting tool
		checkDir(report + sep);
		checkDir(report + sep + "graphs");
		checkDir(report + sep + "graphs" + sep + "filtered");
	}

	/**
	 * This method deletes a folder.
	 * @param path
	 */
	public static void deleteTree( File path )
	{
		for ( File file : path.listFiles() )
		{
			if ( file.isDirectory() )
				deleteTree( file );
			file.delete();
		}
		path.delete();
		logger.info("\nFolder \"" + path + "\" was deleted successfully.\n");
	}

	/**
	 * This is the main method of the reporting tool.
	 * 
	 * @param args
	 * @throws TransformerException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TransformerException {

		Conf.getInstance(args);
		String outputFile = "reports_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		prepareReportingTool(outputFile);

		ReadCsv readCsv = new ReadCsv();
		Measurement.getInstance().readMeasurement(readCsv, Conf.get("inputFile"));
		logger.setLevel(Level.ALL);
		Measurement.getInstance().setMeasurementName(Conf.get("name"));
		Measurement.getInstance().setOutputFile(outputFile);
		Measurement.getInstance().plot();
		Measurement.getInstance().filteredPlot();
		logger.info("num: " + Conf.get("numBin"));
		logger.info("scale: " + Conf.get("scale"));

		logger.info("num: " + Conf.get("lowerBound"));
		logger.info("scale: " + Conf.get("upperBound"));

		try {
			//write HTML
			Write writeHTML = new WriteHTML();
			writeHTML.writeFile();

			//write XML
			Write writeXML = new WriteXML();
			writeXML.writeFile();

			//write PDF
			Write writePDF = new WritePDF();
			writePDF.writeFile();

			//delete folder tmp
			deleteTree( new File("reporting_tool_tmp"));

			//System.out.println("The report was successfully generated.");
		} catch (Exception e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}

}
