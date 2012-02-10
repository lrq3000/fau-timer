package reportingTool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;

public class Conf {

	private static Conf instance;
	private HashMap<String, String> conf = new HashMap<String, String>();
	Logger logger = null;

	private Conf(String[] args) {
		logger = ReportingTool.getLogger();

		parseParameter(args);
		parseIni();
		setDefaultValues();
	}

	public synchronized static Conf getInstance(String[] args) {
		if (Conf.instance == null) {
			Conf.instance = new Conf(args);
		}

		return Conf.instance;
	}

	public static Conf getInstance() {
		return Conf.instance;
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String key) {
		return conf.containsKey(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return Conf.getInstance().conf.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public String put(String key, String value) {
		return conf.put(key, value);
	}

	/**
	 * @return
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString() {
		return conf.toString();
	}

	private void parseParameter(String[] args) {
		JSAP jsap = new JSAP();

		try {
			// help
			Switch help = new Switch("help").setShortFlag('h').setLongFlag(
					"help");
			help.setHelp("Prints this help message.");
			jsap.registerParameter(help);

			// pdflatexPath
			FlaggedOption pdflatexPath = new FlaggedOption("pdflatexPath")
			.setLongFlag("pdflatexPath");
			pdflatexPath
			.setHelp("Path to the directory where pdflatex is located.");
			jsap.registerParameter(pdflatexPath);

			// makeindexPath
			FlaggedOption makeindexPath = new FlaggedOption("makeindexPath")
			.setLongFlag("makeindexPath");
			makeindexPath
			.setHelp("Path to the directory where makeindex is located.");
			jsap.registerParameter(makeindexPath);

			// gnuplotPath
			FlaggedOption gnuplot = new FlaggedOption("gnuplot")
			.setLongFlag("gnuplot");
			gnuplot.setHelp("Path to the directory where gnuplot is located.");
			jsap.registerParameter(gnuplot);

			// inputFile
			FlaggedOption inputFile = new FlaggedOption("inputFile")
			.setShortFlag('i').setLongFlag("inputFile");
			inputFile
			.setHelp("Path and name of the input file containing the measurements.");
			jsap.registerParameter(inputFile);

			// name
			FlaggedOption name = new FlaggedOption("name").setShortFlag('n')
					.setLongFlag("name");
			name.setHelp("Name of the final PDF report.");
			jsap.registerParameter(name);

			// numBin
			FlaggedOption numBin = new FlaggedOption("numBin")
			.setLongFlag("numBin");
			numBin.setHelp("Number of bins at the x-axis of histograms.");
			jsap.registerParameter(numBin);

			// scale
			FlaggedOption scale = new FlaggedOption("scale")
			.setLongFlag("scale");
			scale.setHelp("Set the scale of some plots to logarithmic (l) or (q). Leave this blank to use a normal scale.");
			jsap.registerParameter(scale);

			// lowerBound
			FlaggedOption lowerBound = new FlaggedOption("lowerBound")
			.setLongFlag("lowerBound");
			lowerBound
			.setHelp("Lower Bound of the measurement filter (default is 0.05).");
			jsap.registerParameter(lowerBound);

			// upperBound
			FlaggedOption upperBound = new FlaggedOption("upperBound")
			.setLongFlag("upperBound");
			upperBound
			.setHelp("Upper Bound of the measurement filter (default is 0.10).");
			jsap.registerParameter(upperBound);

		} catch (JSAPException e) {
		}

		JSAPResult result = jsap.parse(args);

		if (result.getBoolean("help")) {
			printHelp(jsap);
		}

		if (result.contains("pdflatexPath")) {
			this.conf.put("pdflatexPath", result.getString("pdflatexPath"));
		}

		if (result.contains("makeindexPath")) {
			this.conf.put("makeindexPath", result.getString("makeindexPath"));
		}

		if (result.contains("gnuplot")) {
			this.conf.put("gnuplot", result.getString("gnuplot"));
		}

		if (result.contains("inputFile")) {
			this.conf.put("inputFile", result.getString("inputFile"));
		}

		if (result.contains("name")) {
			this.conf.put("name", result.getString("name"));
		}

		if (result.contains("numBin")) {
			try {
				Integer.parseInt(result.getString("numBin"));
				this.conf.put("numBin", result.getString("numBin"));
			} catch (NumberFormatException e) {
				logger.warning("The number of bins must be an int.");
				System.exit(1);
			}
		}

		if (result.contains("scale")) {
			if (result.getString("scale").equals("l")
					|| result.getString("scale").equals("q") || result.getString("scale").equals("n")) {
				this.conf.put("scale", result.getString("scale"));
			} else {
				logger.warning("No valide input for the value of scale.");
				System.exit(1);
			}
		}

		if (result.contains("lowerBound")) {
			try {
				Double.parseDouble(result.getString("lowerBound"));
				this.conf.put("lowerBound", result.getString("lowerBound"));
			} catch (NumberFormatException e) {
				logger.warning("The value of lower bound must be a double.");
				System.exit(1);
			}
		}

		if (result.contains("upperBound")) {
			try {
				Double.parseDouble(result.getString("upperBound"));
				this.conf.put("upperBound", result.getString("upperBound"));
			} catch (NumberFormatException e) {
				logger.warning("The value of upper bound must be a double.");
				System.exit(1);
			}
		}
	}

	private void setDefaultValues() {
		boolean isError = false;

		if (!this.conf.containsKey("pdflatexPath")) {
			File pdfLatex = findPdflatex();
			if (pdfLatex == null) {
				logger.severe("Could not find pdfLatex path.");
				isError = true;
			} else {
				this.conf.put("pdflatexPath", pdfLatex.getAbsolutePath());
			}
		}

		if (!this.conf.containsKey("makeindexPath")) {
			File makeIndex = findMakeIndex();
			if (makeIndex == null) {
				logger.severe("Could not find makeIndex path.");
				isError = true;
			} else {
				this.conf.put("makeindexPath", makeIndex.getAbsolutePath());
			}
		}

		if (!this.conf.containsKey("gnuplot")) {
			File gnuplot = findGnuplot();
			if (gnuplot == null) {
				logger.severe("Could not find gnuplot path.");
				isError = true;
			} else {
				this.conf.put("gnuplot", gnuplot.getAbsolutePath());
			}
		}

		if (!this.conf.containsKey("inputFile")) {
			logger.severe("You have to specify an input file");
			isError = true;
		}

		if (!this.conf.containsKey("name")) {
			this.conf.put("name", "no name");
		}

		if (!this.conf.containsKey("numBin")) {
			this.conf.put("numBin", "4");
		}

		if (!this.conf.containsKey("scale")) {
			this.conf.put("scale", "n");
		}

		if (!this.conf.containsKey("lowerBound")) {
			this.conf.put("lowerBound", "0.05");
		}

		if (!this.conf.containsKey("upperBound")) {
			this.conf.put("upperBound", "0.10");
		}

		if (isError) {
			System.exit(1);
		}
	}

	private void parseIni() {
		try {
			Ini ini = new Ini(new File("config.ini"));
			Section section = ini.get("reportingTool");

			if (!this.conf.containsKey("pdflatexPath")
					&& section.containsKey("pdflatexPath")) {
				this.conf.put("pdflatexPath", section.get("pdflatexPath"));
			}

			if (!this.conf.containsKey("makeindexPath")
					&& section.containsKey("makeindexPath")) {
				this.conf.put("makeindexPath", section.get("makeindexPath"));
			}

			if (!this.conf.containsKey("gnuplot")
					&& section.containsKey("gnuplot")) {
				this.conf.put("gnuplot", section.get("gnuplot"));
			}

			if (!this.conf.containsKey("inputFile")
					&& section.containsKey("inputFile")) {
				this.conf.put("inputFile", section.get("inputFile"));
			}

			if (!this.conf.containsKey("name") && section.containsKey("name")) {
				this.conf.put("name", section.get("name"));
			}

			if (!this.conf.containsKey("numBin")
					&& section.containsKey("numBin")) {
				try {
					Integer.parseInt(section.get("numBin"));
					this.conf.put("numBin", section.get("numBin"));
				} catch (NumberFormatException e) {
					logger.warning("The number of bins must be an int.");
					System.exit(1);
				}
			}

			if (!this.conf.containsKey("scale") && section.containsKey("scale")) {
				if (section.get("scale").equals("l")
						|| section.get("scale").equals("q") || section.get("scale").equals("n")) {
					this.conf.put("scale", section.get("scale"));
				} else {
					logger.warning("No valide input for the value of scale.");
					System.exit(1);
				}
			}

			if (!this.conf.containsKey("lowerBound")
					&& section.containsKey("lowerBound")) {
				try {
					Double.parseDouble(section.get("lowerBound"));
					this.conf.put("lowerBound", section.get("lowerBound"));
				} catch (NumberFormatException e) {
					logger.warning("The value of lower bound must be a double.");
					System.exit(1);
				}
			}

			if (!this.conf.containsKey("upperBound")
					&& section.containsKey("upperBound")) {
				try {
					Double.parseDouble(section.get("upperBound"));
					this.conf.put("upperBound", section.get("upperBound"));
				} catch (NumberFormatException e) {
					logger.warning("The value of upper bound must be a double.");
					System.exit(1);
				}
			}

		} catch (InvalidFileFormatException e) {
		} catch (IOException e) {
		}
	}

	private void printHelp(JSAP jsap) {
		System.out.println();

		System.out.println(" -----------------------");
		System.out.println(" - reporting tool help -");
		System.out.println(" -----------------------");

		System.out.println();
		System.out.println();

		System.out.println("  Usage: java -jar reportingTool.jar"
				+ jsap.getUsage());

		System.out.println();
		System.out.println();

		System.out.println(jsap.getHelp());

		System.exit(0);
	}

	/**
	 * Tries to find a given executable in the system path.
	 * 
	 * @param name
	 *            Executable that should be searched for
	 * @return If found the executable otherwise NULL
	 */
	private File findExecutable(String name) {
		String pathString = System.getenv("PATH");
		String separator = System.getProperty("path.separator");
		String[] path = pathString.split(separator);

		File file = null;
		for (int i = 0; i < path.length; i++) {
			file = new File(path[i], name);
			if (file.exists() && file.canExecute()) {
				return file;
			}
		}

		return null;
	}

	/**
	 * Tries to find the pdflatex executable.
	 * 
	 * @return If found the executable otherwise NULL
	 */
	private File findPdflatex() {
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
			return findExecutable("pdflatex");
		} else {
			return findExecutable("pdflatex.exe");
		}
	}

	/**
	 * Tries to find the makeindex executable.
	 * 
	 * @return If found the executable otherwise NULL
	 */
	private File findMakeIndex() {
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
			return findExecutable("makeindex");
		} else {
			return findExecutable("makeindex.exe");
		}
	}

	/**
	 * Tries to find the gnuplot executable.
	 * 
	 * @return If found the executable otherwise NULL
	 */
	private File findGnuplot() {
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
			return findExecutable("gnuplot");
		} else {
			return findExecutable("gnuplot.exe");
		}
	}

}