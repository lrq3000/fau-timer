package reportingTool;

import graphs.Boxplot;
import graphs.CDF;
import graphs.Histogram;
import graphs.Scatterplot;
import input.Read;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import reportingTool.evaluation.StatisticEvaluation;
import reportingTool.filter.FilterUserInput;

/**
 * This class includes the measurement tuples of the whole measurement. A
 * measurement tuple contains a secrete with all measured times.
 * 
 * @author Isabell Schmitt
 */
public class Measurement implements Serializable {

	private static final long serialVersionUID = -7205633139775711605L;
	private static Measurement instance = null;
	private ArrayList<Secret> secrets = new ArrayList<Secret>();
	private Read reader;
	private String measurementName;
	private StatisticEvaluation evaluation = new StatisticEvaluation();
	private String outputFile;

	private Measurement() {

	}

	public static synchronized Measurement getInstance() {
		if(Measurement.instance == null)
			Measurement.instance = new Measurement();

		return Measurement.instance;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getOutputFile() {
		return this.outputFile;
	}

	public void readMeasurement(Read reader, String file) throws IOException {
		setReader(reader);
		try {
			readFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//TODO:: EVALUATION
		evaluation.makeEvaluation(this);
	}

	/**
	 * This method plots all graphs with no filters.
	 * @throws IOException
	 */
	public void plot() throws IOException {
		String sep = File.separator;
		String path = getOutputFile() + sep + "graphs" + sep;
		// plot Scatterplot
		new Scatterplot().plotAllFormats(path);

		// plot Boxplot
		new Boxplot().plotAllFormats(path);

		// plot CDF
		new CDF().plotAllFormats(path);

		// plot Histogram
		new Histogram().plotAllFormats(path);
	}

	/**
	 * This method plots all graphs with the 5 to 10 filter.
	 * @throws IOException
	 */
	public void filteredPlot() throws IOException {
		String sep = File.separator;
		String path = getOutputFile() + sep + "graphs" + sep + "filtered" + sep;
		// create filter with bounds
		FilterUserInput filter = new FilterUserInput();

		// the default filter is 0.05 to 0.10
		filter.setBound(Double.parseDouble(Conf.get("lowerBound")), Double.parseDouble(Conf.get("upperBound"))); 	

		// plot Scatterplot
		new Scatterplot().plotAllFormats(filter, path);

		// plot Boxplot
		new Boxplot().plotAllFormats(filter, path);

		// plot CDF
		new CDF().plotAllFormats(filter, path);

		// plot Histogram
		new Histogram().plotAllFormats(filter, path);
	}

	/**
	 * This is the getter of the secrets.
	 * 
	 * @return
	 */
	public ArrayList<Secret> getSecrets() {
		return secrets;
	}

	/**
	 * This is the setter of the secrets.
	 * 
	 * @param secrets
	 */
	public void setSecrets(ArrayList<Secret> secrets) {
		this.secrets = secrets;
	}

	/**
	 * This is the getter of the reader.
	 * 
	 * @return
	 */
	public Read getReader() {
		return reader;
	}

	/**
	 * This method sets the reader for the reading.
	 * 
	 * @param reader
	 */
	public void setReader(Read reader) {
		this.reader = reader;
	}

	/**
	 * This method reads the file when the reader is defined.
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void readFile(String file) throws Exception {
		ArrayList<Secret> secrets;

		if (this.reader == null) {
			throw new Exception("You have to set a reader");
		}
		secrets = reader.readFile(file);

		this.secrets.addAll(secrets);
	}

	/**
	 * This method is used to check the secrets.
	 */
	@Override
	public String toString() {
		return "Measurement [secrets=" + secrets.toString() + "]";
	}

	/**
	 * This is the setter of the measurement name.
	 * 
	 * @param measurementName
	 *            the measurementName to set
	 */
	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}

	/**
	 * This is the getter of the measurement name.
	 * 
	 * @return the measurementName
	 */
	public String getMeasurementName() {
		return measurementName;
	}

}
